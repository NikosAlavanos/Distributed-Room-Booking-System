package org.nvk.worker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.nvk.constants.Protocol;
import org.nvk.multithreading.ActionForClients;
import org.nvk.network.Mapper;
import org.nvk.structures.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;

public class Worker {
    private final String label;
    private ArrayList<Room> rooms = new ArrayList<Room>();
    private ObjectMapper objectMapper = Mapper.createObjectMapper();
    private ServerSocket providerSocket;

    public Worker(String label) {
        this.label = label;
    }

    public String process(String json) {
        try {
            //
            // Manager
            //
            if (json.contains(Protocol.REQUEST_ID_CREATE_ROOM)) {
                CreateRoomRequest data = objectMapper.readValue(json, CreateRoomRequest.class);

                String result = createRoom(data.getUsername(), data.getRoom_id(), data.getRoomname(), data.getArea(), data.getPersons(), data.getPrice());


                return result;
            }
            if (json.contains(Protocol.REQUEST_ID_LIST)) {
                ListRoomRequest data = objectMapper.readValue(json, ListRoomRequest.class);

                String result = listRooms(data.getUsername());
                return result;
            }
            if (json.contains(Protocol.REQUEST_ID_SHOW_STATISTICS)) {
                ShowStatisticsRequest data = objectMapper.readValue(json, ShowStatisticsRequest.class);

                String result = listRooms(data.getUsername());
                return result;
            }

            if (json.contains(Protocol.REQUEST_ID_SHOW_BOOKING_STATISTICS)) {
                BookingStatisticsRequest data = objectMapper.readValue(json, BookingStatisticsRequest.class);

                String result = bookingStatistics(data.getDateFrom(), data.getDateTo());
                return result;
            }
            if (json.contains(Protocol.REQUEST_ID_FIND_ROOM)) {
                FindRoomByIdRequest data = objectMapper.readValue(json, FindRoomByIdRequest.class);
                String result = findRoomById(data.getUsername(), data.getRoom_id());
                return result;
            }
            if (json.contains(Protocol.REQUEST_ID_LIST_BOOKINGS)) {
                ListBookingsRequest data = objectMapper.readValue(json, ListBookingsRequest.class);
                String result = listBookings(data.getUsername(), data.getRoom_id());
                return result;
            }
            if (json.contains(Protocol.REQUEST_ID_ROOM_DATES)) { //worker.java
                DefineRoomDatesRequest data = objectMapper.readValue(json, DefineRoomDatesRequest.class);
                String result = defineRoomDates(data.getUsername(), data.getRoom_id(), data.getDateFrom(), data.getDateTo());


                return result;
            }

            //
            // renter
            //

            if (json.contains(Protocol.REQUEST_ID_SEARCH)) {
                SearchRoomRequest data = objectMapper.readValue(json, SearchRoomRequest.class);
                String success = search(data.getUsername(), data.getArea(), data.getDateFrom(), data.getDateTo(), data.getRoomcap(), data.getPrice(), data.getStars());
                return success;
            }

            if (json.contains(Protocol.REQUEST_ID_RENT_ROOM)) {
                RentRoomRequest data = objectMapper.readValue(json, RentRoomRequest.class);
                String message = createBooking(data.getUsername(), data.getRoom_id(), data.getDateFrom(), data.getDateTo());
                return message;
            }

            if (json.contains(Protocol.REQUEST_ID_RATE)) {
                RateRoomRequest data = objectMapper.readValue(json, RateRoomRequest.class);
                String message = rateRoom(data.getUsername(), data.getRoom_id(), data.getStars());
                return message;
            }

            if (json.contains(Protocol.REQUEST_ID_VIEW_RENTALS)) {
                ViewRentalsRequest data = objectMapper.readValue(json, ViewRentalsRequest.class);
                String result = viewRentals(data.getUsername());
                return result;

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR:" + ex.getMessage();
        }

        return "ERROR:UNKNOWN COMMAND";
    }


    private String findRoomById(String username, int roomId) {
        for (Room room : rooms) {
            if (room.getId() == roomId) {
                try {
                    String json = objectMapper.writeValueAsString(room);

                    return json;
                } catch (Exception ex) {
                    ex.printStackTrace();
                    return "ERROR:" + ex.getMessage();
                }
            }
        }

        return "ROOM NOT FOUND";
    }


    public String createRoom(String username, int room_id, String roomname, String area, Integer persons, Integer price) {
        for (Room room : rooms) {
            if (room.getUsername().equalsIgnoreCase(username) && room.getRoomname().equalsIgnoreCase(roomname)) {
                return "Room name already created with this username and roomname";
            }
        }

        Room room = new Room(room_id, username, roomname, area, persons, price);

        rooms.add(room);

        return "Room name created.ID="+room.getId();
    }


    public String defineRoomDates(String username, int room_id, String dateFrom, String dateTo) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = LocalDate.parse(dateFrom, formatter);
        LocalDate to = LocalDate.parse(dateTo, formatter);

        for (Room room : rooms) {
            if (room.getUsername().equalsIgnoreCase(username) && room.getId() == room_id) {

                for (AvailableSpan span : room.getSpans()) {
                    LocalDate min = span.getFrom();
                    LocalDate max = span.getTo();
                    if ((from.isEqual(min) ||  from.isEqual(max) || (from.isAfter(min) && from.isBefore(max))) || (to.isEqual(min) || to.isEqual(max) || (to.isAfter(min) && to.isBefore(max)))) {
                        return "Definition of room dates already defined for that span";
                    }
                }
                AvailableSpan span = new AvailableSpan(from, to);
                room.getSpans().add(span);
                return "Success, Room dates are defined for the room id=" + room.getId();
            }
        }

        return "You do not have permission to process this room ";
    }

    public String listRooms(String username) {
        RoomCollection result = new RoomCollection();

        for (Room room : rooms) {
            if (username == null || room.getUsername().equalsIgnoreCase(username)) {
                result.rooms.add(room);
            }
        }

        try {
            String json = objectMapper.writeValueAsString(result);

            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR:" + ex.getMessage();
        }
    }

    private String bookingStatistics(String dateFrom, String dateTo) {
        RoomCollection result = new RoomCollection();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate min = LocalDate.parse(dateFrom, formatter);
        LocalDate max = LocalDate.parse(dateTo, formatter);

        for (Room room : rooms) {

            Room copy = new Room(room.getId(), room.getUsername(), room.getRoomname(), room.getArea(), room.getPersons(), room.getPrice());

            //int totalBookingsInThatSpan = 0;

            for (Booking booking : room.getBookings()) {
                LocalDate from = booking.getFrom();
                LocalDate to = booking.getTo();

                if ((from.isEqual(min) || from.isAfter(min)) && (to.isEqual(max) || to.isBefore(max))) {
                    //totalBookingsInThatSpan++;
                    copy.getBookings().add(booking);
                }
            }

//            if (totalBookingsInThatSpan > 0) {
//                result.rooms.add(copy);
//            }
            result.rooms.add(copy);
        }

        try {
            String json = objectMapper.writeValueAsString(result);

            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR:" + ex.getMessage();
        }

    }

    public String listBookings(String username, int room_id) {
        RoomCollection result = new RoomCollection();

        for (Room room : rooms) {
            if (room.getUsername().equalsIgnoreCase(username) && room.getId() == room_id) {// TODO check
                result.rooms.add(room);
            }
        }

        try {
            String json = objectMapper.writeValueAsString(result);

            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR:" + ex.getMessage();
        }
    }

    //Renter actions

    public String search(String username, String area, String dateFrom, String dateTo, Integer cap, Integer price, Integer stars) {
        RoomCollection result = new RoomCollection();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//        LocalDate from = LocalDate.parse(dateFrom, formatter);
//        LocalDate to = LocalDate.parse(dateTo, formatter);
        LocalDate from = null;
        LocalDate to = null;


        if (dateFrom != null && !dateFrom.isEmpty()) {
            from = LocalDate.parse(dateFrom, formatter);
        }
        if (dateTo != null && !dateTo.isEmpty()) {
            to = LocalDate.parse(dateTo, formatter);
        }

        for (Room room : rooms) {
            boolean matchUsername = true;
            boolean matchArea  = true;
            boolean matchDate = (from == null || to == null) ? true : false;
            boolean matchCapacity  = true;
            boolean matchPrice  = true;
            boolean matchStars = true;

            if (username != null && !room.getUsername().equalsIgnoreCase(username)) {
                 matchUsername = false;
            }

            if (area != null && !room.getArea().equalsIgnoreCase(area)) {
                matchArea = false;
            }

            if (matchDate == false) {
                for (AvailableSpan span : room.getSpans()) {
                    boolean matchDateFrom = false;
                    boolean matchDateTo = false;

                    LocalDate min = span.getFrom();
                    LocalDate max = span.getTo();

                    if (from.isEqual(min) || from.isEqual(max) || (from.isAfter(min) && from.isBefore(max))) {
                        matchDateFrom = true;
                    }

                    if (to.isEqual(min) || to.isEqual(max) || (to.isAfter(min) && to.isBefore(max))) {
                        matchDateTo = true;
                    }

                    if (matchDateFrom && matchDateTo) {
                        matchDate = true;
                        break;
                    }
                }
            }

            if (cap != null && room.getPersons() < cap) {
                matchCapacity = false;
            }

            if (price != null && room.getPrice() > price) {
                matchPrice = false;
            }

            if (stars != null && room.getStars() < stars) {
                matchStars = false;
            }

            if (matchUsername && matchArea && matchDate && matchCapacity && matchPrice && matchStars) {
                result.rooms.add(room);
            }
        }


        try {
            String json = objectMapper.writeValueAsString(result);
            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR:" + ex.getMessage();
        }
    }

    public String createBooking(String renter_username, int room_id, String dateFrom, String dateTo) {//rent room
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate from = LocalDate.parse(dateFrom, formatter);
        LocalDate to = LocalDate.parse(dateTo, formatter);

        for (Room room : rooms) {
            if (room.getId() == room_id) {
                for (Booking b : room.getBookings()) {
                    if (((from.isAfter(b.getFrom()) || from.isEqual(b.getFrom())) && (from.isBefore(b.getTo()) || from.isEqual(b.getTo()))) ||
                            (((to.isAfter(b.getFrom()) || to.isEqual(b.getFrom())) && (to.isBefore(b.getTo()) || to.isEqual(b.getTo()))))) {
                        return "date out of range";
                    }
                }

                for (AvailableSpan span : room.getSpans()) {
                    if ((span.getFrom().isBefore(from) || span.getFrom().isEqual(from)) && (span.getTo().isAfter(to) || span.getTo().isEqual(to))) {
                        Booking booking = new Booking(renter_username, from, to);
                        room.getBookings().add(booking);
                        return "success";
                    }
                }

                return "owner does not allow this span";
            }
        }
        return "room not found";
    }


    public String rateRoom(String username, int room_id, Integer stars) {

        for (Room room : rooms) {
            if (room.getId() == room_id) {
                for (Booking b : room.getBookings()) {
                    if (b.getUsername().equalsIgnoreCase(username)) {
                        room.setStars(stars);
                        return "success";
                    }
                }
                return "rating not allowed - you have no bookings";
            }
        }
        return "room not found";
    }

    public String viewRentals(String username) {
        RoomCollection result = new RoomCollection();
        for (Room room : rooms) {
            for (Booking b : room.getBookings()) {
                if (b.getUsername().equalsIgnoreCase(username)) {
                    result.rooms.add(room);
                }
            }

        }

        try {
            String json = objectMapper.writeValueAsString(result);
            return json;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR:" + ex.getMessage();
        }

    }


    public void openServer(int port) throws IOException {
        providerSocket = new ServerSocket(port);
    }

    public void waitingForTcpClients() throws IOException {
        while (true) {
            System.out.println("[Worker thread " + label + "] Waiting for master to connect");

            Socket connection = providerSocket.accept();

            System.out.println("[Worker thread " + label + "] Master connected. ");

            Thread t = new ActionForClients(connection, this);
            t.start();
        }
    }
}
