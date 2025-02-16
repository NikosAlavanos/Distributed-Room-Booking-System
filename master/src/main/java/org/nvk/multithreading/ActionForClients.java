package org.nvk.multithreading;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.nvk.configuration.Config;
import org.nvk.constants.Protocol;
import org.nvk.hashing.HashCalculator;
import org.nvk.network.NetworkCommunication;
import org.nvk.network.NetworkCommunicationForMaster;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

public class ActionForClients extends Thread {
    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private static int current_room_id = 0;
    private static int map_id = 0;
    private static HashMap<Integer, HashCalculator.HashResult> cache = new HashMap<>(); // room id => worker_id

    private static HashMap<Integer, ObjectOutputStream> mapOut = new HashMap<>();
    private static HashMap<Integer, ObjectInputStream> mapIn = new HashMap<>();
    private static HashMap<Integer, Socket> mapConnections = new HashMap<>();

    public ActionForClients(Socket connection) {
        try {
            this.connection = connection;
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized static int generateNextCurrentRoomID() {
        current_room_id++;
        return current_room_id;
    }

    public synchronized static int generateNextCurrentMapID() {
        map_id++;
        return map_id;
    }


    public synchronized HashCalculator.HashResult getHashFromCache(int room_id) {
        return cache.get(room_id);
    }

    public void run() {
        boolean keep_alive= false;

        try {
            String nodetype = in.readUTF();

            String json = in.readUTF();

            if (nodetype.equalsIgnoreCase("CLIENT")) {
                System.out.println("Received from client: " + json);

                //
                // Unicast
                //
                if (json.contains(Protocol.REQUEST_ID_CREATE_ROOM) ||
                        json.contains(Protocol.REQUEST_ID_LIST_BOOKINGS) ||
                        json.contains(Protocol.REQUEST_ID_ROOM_DATES) ||
                        json.contains(Protocol.REQUEST_ID_RENT_ROOM) ||
                        json.contains(Protocol.REQUEST_ID_FIND_ROOM) ||
                        json.contains(Protocol.REQUEST_ID_RATE)) {
                    JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

                    String username = jsonObject.get("username").getAsString();

                    HashCalculator.HashResult hash = null;

                    if (json.contains(Protocol.REQUEST_ID_CREATE_ROOM)) {
                        int room_id = generateNextCurrentRoomID();
                        jsonObject.addProperty("room_id", String.valueOf(room_id));

                        json = jsonObject.toString();

                        String roomname = jsonObject.get("roomname").getAsString();
                        hash = HashCalculator.selectWorkerByHashing(username, roomname);

                        cache.put(room_id, hash);
                    } else {
                        int room_id = jsonObject.get("room_id").getAsInt();
                        hash = getHashFromCache(room_id);
                    }

                    System.out.println("UUID: " + hash.uuid + "  Forwarding to worker: " + hash.worker_offset + " as address: " + hash.ip + ":" + hash.port);

                    NetworkCommunicationForMaster nc = new NetworkCommunicationForMaster();

                    String response = nc.sendToWorker(hash.ip, hash.port, json);

                    out.writeUTF(response);
                    out.flush();

                } else {//Broadcast: client alla einai scatter se olous tous workers
                    int map_id = generateNextCurrentMapID();

                    synchronized (this) {
                        mapIn.put(map_id, in);
                        mapOut.put(map_id, out);
                        mapConnections.put(map_id, connection);
                    }

                    JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                    jsonObject.addProperty("map_id", String.valueOf(map_id));

                    if (json.contains(Protocol.REQUEST_ID_SHOW_STATISTICS)) {
                        jsonObject.addProperty("reduce_method", "count");
                    } else if (json.contains(Protocol.REQUEST_ID_SHOW_BOOKING_STATISTICS)) {
                        jsonObject.addProperty("reduce_method", "count_bookings");
                    } else {
                        jsonObject.addProperty("reduce_method", "merge");
                    }

                    json = jsonObject.toString();

                    NetworkCommunicationForMaster nc = new NetworkCommunicationForMaster();

                    for (int i=0;i< Config.WORKER_IPS.length;i++) {
                        String ip = Config.WORKER_IPS[i];
                        int port = Config.WORKER_PORT[i];

                        nc.sendToWorker(ip, port, json);
                    }

                    keep_alive = true;
                }
            } else {// reducer if (nodetype.equalsIgnoreCase("REDUCER")) gia otan tou girnaei ta apotelesmata.stoixeia sindesis client-master saved
                //apodesmevsi tou mapping otan teleionei to request tou client me ta remove pou ginontai
                //keep_alive gia na kratame to connection tou master-reducer zontano,dld na min ginetai close()
                JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
                int map_id = jsonObject.get("map_id").getAsInt();

                ObjectOutputStream outClient;
                ObjectInputStream inClient ;
                Socket connectionClient;

                synchronized (this) {
                    outClient = mapOut.put(map_id, out);
                    inClient = mapIn.put(map_id, in);
                    connectionClient = mapConnections.put(map_id, connection);

                    mapOut.remove(map_id);
                    mapIn.remove(map_id);
                    mapConnections.remove(map_id);
                }

                jsonObject.remove("map_id");
                outClient.writeUTF(jsonObject.toString());
                outClient.flush();

                try {
                    outClient.close();
                    inClient.close();
                    connectionClient.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }

                String reply = "OK";
                out.writeUTF(reply);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (keep_alive == false) {
                try {
                    in.close();
                    out.close();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }
}
