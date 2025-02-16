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

    public void insertRoom(String roomname, String area, Integer persons, Integer price, Integer stars) {
        CreateRoomRequest request = new CreateRoomRequest(0, profile.getUsername(), roomname, area, persons, price, stars);

        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            processRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public void listRooms() {
        ListRoomRequest request = new ListRoomRequest(profile.getUsername());

        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            processRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void listBookings(int room_id) {//our code
        ListBookingsRequest request = new ListBookingsRequest(profile.getUsername(), room_id);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            processRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public void setDatesForRoom(int room_id, String dateFrom, String dateTo) {//Manager.java
        DefineRoomDatesRequest request = new DefineRoomDatesRequest(profile.getUsername(), room_id, dateFrom, dateTo);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            processRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void findRoomByID(int room_id) {
        FindRoomByIdRequest request = new FindRoomByIdRequest(profile.getUsername(), room_id);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            processRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void showStatistics(){
        ShowStatisticsRequest request=new ShowStatisticsRequest(profile.getUsername());
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            processRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void showStatisticsForRooms(){
        ShowStatisticsRequest request=new ShowStatisticsRequest(null);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            processRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void searchBy(SearchTraits traits) {

        SearchRoomRequest request = new SearchRoomRequest(profile.getUsername(), traits.getArea(), traits.getDateFrom(), traits.getDateTo(), traits.getRoomcap(), traits.getPrice(), traits.getStars());

        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            processRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void showStatisticsForBookings(String dateFrom, String dateTo) {
        BookingStatisticsRequest request=new BookingStatisticsRequest(dateFrom, dateTo);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            processRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
