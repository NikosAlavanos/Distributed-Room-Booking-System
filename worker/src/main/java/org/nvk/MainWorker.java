package org.nvk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.nvk.configuration.Config;
import org.nvk.network.Mapper;
import org.nvk.structures.ListRoomRequest;
import org.nvk.worker.Worker;

public class MainWorker {
    public static void main(String[] args) throws JsonProcessingException {
        int port = 0;

//        ListRoomRequest request = new ListRoomRequest("username");
//
//        ObjectMapper objectMapper = Mapper.createObjectMapper();
//        String json = objectMapper.writeValueAsString(request);
//
//        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();
//
//
//        jsonObject.addProperty("map_id", String.valueOf(123));
//
//        System.out.println(jsonObject.toString());
//
//        jsonObject.remove("map_id");
//
//        System.out.println(jsonObject.toString());
//
//
//        if (true) {
//            return;
//        }

        if (args[0].equalsIgnoreCase("A")) {
            port = Config.WORKER_PORT + 1;
        }

        if (args[0].equalsIgnoreCase("B")) {
            port = Config.WORKER_PORT + 2;
        }

        if (args[0].equalsIgnoreCase("C")) {
            port = Config.WORKER_PORT + 3;
        }

        if (port == 0) {
            System.err.println("Invalid arguments. Please provide worker A B or C from command line");
            return;
        }

        System.out.println("Starting worker " + args[0] +  " at: " + port);

        String label = args[0];

        Worker worker = new Worker(label);

        try {
            worker.openServer(port);

            worker.waitingForTcpClients();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}