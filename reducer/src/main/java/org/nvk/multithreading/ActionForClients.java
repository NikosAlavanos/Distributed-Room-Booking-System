package org.nvk.multithreading;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.nvk.MappingData;
import org.nvk.network.NetworkCommunicationForReducer;
import org.nvk.structures.RoomCollection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import static org.nvk.network.Mapper.createObjectMapper;

public class ActionForClients extends Thread {
    private Socket connection;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    public static MappingData mappingData = new MappingData();

    private NetworkCommunicationForReducer nc = new NetworkCommunicationForReducer();

    public ActionForClients(Socket connection) {
        try {
            this.connection = connection;
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

            JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
            int map_id = jsonObject.get("map_id").getAsInt();
            String reduce_method = jsonObject.get("reduce_method").getAsString();
            jsonObject.remove("map_id");
            jsonObject.remove("reduce_method");

            json = jsonObject.toString();

            System.out.println("nodetype"  + nodetype + " connected  with map id : " + map_id);

            ObjectMapper objectMapper = createObjectMapper();

            RoomCollection data = objectMapper.readValue(json, RoomCollection.class);

            mappingData.commitJob(map_id, data, reduce_method);

            json = objectMapper.writeValueAsString(data);

            System.out.println("RECEIVED: " + json);

            if (mappingData.jobIsComplete(map_id)) {
                String reduceJson = mappingData.processJob(map_id);

                JsonObject reduceJsonObject = JsonParser.parseString(reduceJson).getAsJsonObject();

                reduceJsonObject.addProperty("map_id", String.valueOf(map_id));

                reduceJson = reduceJsonObject.toString();

                System.out.println("REDUCED: " + reduceJson);

                System.out.println(reduceJson);

                nc.sendToMaster(reduceJson);
            }

            out.writeUTF("OK");
            out.flush();
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
