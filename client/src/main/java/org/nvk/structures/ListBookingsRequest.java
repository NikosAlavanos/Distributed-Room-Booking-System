package org.nvk.structures;

import org.nvk.constants.Protocol;

public class ListBookingsRequest {
    private String username;
    private int room_id;
    private final String request_id = Protocol.REQUEST_ID_LIST_BOOKINGS;

    public ListBookingsRequest() {
    }

    public ListBookingsRequest(String username,int room_id) {
        this.username = username;
        this.room_id = room_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getRequest_id() {
        return request_id;
    }

}