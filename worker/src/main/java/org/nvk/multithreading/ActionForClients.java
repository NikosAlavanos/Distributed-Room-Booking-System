package org.nvk.multithreading;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.nvk.network.NetworkCommunicationForWorker;
import org.nvk.worker.Worker;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ActionForClients extends Thread {
    private final Worker worker;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public ActionForClients(Socket connection, Worker worker    ) {
        this.worker = worker;
        try {
            out = new ObjectOutputStream(connection.getOutputStream());
            in = new ObjectInputStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            String nodetype = in.readUTF();

            String json = in.readUTF();

            System.out.println("Received from " + nodetype + " : " + json);

            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

            if (jsonObject.getAsJsonObject().has("map_id")) {
                int map_id = jsonObject.get("map_id").getAsInt();
                String reduce_method = jsonObject.get("reduce_method").getAsString();
                jsonObject.remove("map_id");
                jsonObject.remove("reduce_method");

                String reply = worker.process(jsonObject.toString());

                System.out.println("Reply is " + reply);

                jsonObject = JsonParser.parseString(reply).getAsJsonObject();

                jsonObject.addProperty("map_id", String.valueOf(map_id));
                jsonObject.addProperty("reduce_method", reduce_method);

                reply = jsonObject.toString();

                System.out.println("Sending back to " + nodetype + " : " + " : needs reducing");

                out.writeUTF("OK - reduce needed");
                out.flush();

                System.out.println(reply);

                System.out.println("Sending to reducer: " + reply);

                NetworkCommunicationForWorker nc = new NetworkCommunicationForWorker();
                nc.sendToReducer(reply);
            } else {
                String reply = worker.process(json);

                System.out.println("Sending back to " + nodetype + " : " + json);

                out.writeUTF(reply);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
                out.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }
}
