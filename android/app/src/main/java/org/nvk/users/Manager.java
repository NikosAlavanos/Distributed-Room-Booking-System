package org.nvk.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.nvk.configuration.Config;
import org.nvk.network.Mapper;
import org.nvk.network.NetworkCommunication;
//import org.nvk.network.WorkerCollection;
import org.nvk.structures.*;
//import org.nvk.worker.Worker;

public class Manager extends Client {



//    public Manager(Profile profile, WorkerCollection workers) {
//        super(profile, workers);
//    }
    public Manager(Profile profile) {
        super(profile);
    }

    public String insertRoom(String roomname, String area, Integer persons, Integer price, Integer stars) {
        CreateRoomRequest request = new CreateRoomRequest(0, profile.getUsername(), roomname, area, persons, price, stars);

        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);
            return result;
        } catch (JsonProcessingException e) {
            return e.getMessage();
        }
    }


    public String listRooms() {
        ListRoomRequest request = new ListRoomRequest(profile.getUsername());

        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String listBookings(int room_id) {//our code
        ListBookingsRequest request = new ListBookingsRequest(profile.getUsername(), room_id);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public String setDatesForRoom(int room_id, String dateFrom, String dateTo) {//Manager.java
        DefineRoomDatesRequest request = new DefineRoomDatesRequest(profile.getUsername(), room_id, dateFrom, dateTo);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String findRoomByID(int room_id) {
        FindRoomByIdRequest request = new FindRoomByIdRequest(profile.getUsername(), room_id);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String showStatistics(){
        ShowStatisticsRequest request=new ShowStatisticsRequest(profile.getUsername());
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String showStatisticsForRooms(){
        ShowStatisticsRequest request=new ShowStatisticsRequest(null);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String searchBy(SearchTraits traits) {

        SearchRoomRequest request = new SearchRoomRequest(null, traits.getArea(), traits.getDateFrom(), traits.getDateTo(), traits.getRoomcap(), traits.getPrice(), traits.getStars());

        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String showStatisticsForBookings(String dateFrom, String dateTo) {
        BookingStatisticsRequest request = new BookingStatisticsRequest(dateFrom, dateTo);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);
            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
