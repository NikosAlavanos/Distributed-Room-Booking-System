package org.nvk;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nvk.configuration.Config;
import org.nvk.network.Mapper;
import org.nvk.structures.AvailableSpan;
import org.nvk.structures.Booking;
import org.nvk.structures.Room;
import org.nvk.structures.RoomCollection;

import java.util.HashMap;

public class MappingData {
    private HashMap<Integer, RoomCollection> roomsPerMapId = new HashMap<>(); // mapId -> collection of rooms
    private HashMap<Integer, Integer> packetsPerMapId = new HashMap<>(); // mapId -> collection of rooms
    private HashMap<Integer, String> methodsPerMapId = new HashMap<>(); // mapId -> reduce method (i.e. merge, count ...)
    private final int TOTAL_WORKERS = Config.TOTAL_WORKERS; // reduce limit

    public synchronized void commitJob(int mapid, RoomCollection workerCollection, String method) {
        if (!roomsPerMapId.containsKey(mapid)) {
            packetsPerMapId.put(mapid, 1);
            methodsPerMapId.put(mapid, method);
            roomsPerMapId.put(mapid, workerCollection);

            System.out.println("New job started with mapid: " + mapid + " with reduce goal: " + method);
        } else {
            RoomCollection existingRoomCollection = roomsPerMapId.get(mapid);

            for (Room room : workerCollection.getRooms()) {
                existingRoomCollection.getRooms().add(room);
            }

            packetsPerMapId.put(mapid, packetsPerMapId.get(mapid) + 1);
        }
    }

    public synchronized boolean jobIsComplete(int mapid) {
        if (!roomsPerMapId.containsKey(mapid) || !packetsPerMapId.containsKey(mapid)) {
            return false;
        }

        int x = packetsPerMapId.get(mapid);

        if (x == TOTAL_WORKERS) {
            return true;
        } else {
            return false;
        }
    }

    public synchronized String processJob(int mapid) throws JsonProcessingException {
        RoomCollection roomCollection = roomsPerMapId.get(mapid);
        String method = methodsPerMapId.get(mapid);

        packetsPerMapId.remove(mapid);
        methodsPerMapId.remove(mapid);
        roomsPerMapId.remove(mapid);

        System.out.println("Job completed with mapid: " + mapid + " with reduce goal: " + method);

        if (method.equalsIgnoreCase("merge")) {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(roomCollection);
            return json;
        } else if (method.equalsIgnoreCase("count")) {
            HashMap<String, Integer> result = new HashMap<String, Integer>();

            for (Room room : roomCollection.getRooms()) {
                String area = room.getArea();
                if (!result.containsKey(area))  {
                    result.put(area, 1);
                } else {
                    result.put(area, result.get(area) + 1);
                }
            }

            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(result);
            return json;
        } else if (method.equalsIgnoreCase("count_bookings")) {
            HashMap<String, Integer> result = new HashMap<String, Integer>();

            for (Room room : roomCollection.getRooms()) {
                String area = room.getArea();
                if (!result.containsKey(area))  {
                    result.put(area, room.getBookings().size());
                } else {
                    result.put(area, result.get(area) + room.getBookings().size());
                }
            }

            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(result);
            return json;
        } else {
            throw new UnsupportedOperationException();
        }
    }




}
