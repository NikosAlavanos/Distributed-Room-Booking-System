package org.nvk.structures;

import org.nvk.constants.Protocol;

import javax.management.remote.JMXConnectorFactory;

public class RentRoomRequest {
    private int room_id;
    private String username;
    private String dateFrom;
    private String dateTo;
    private final String request_id = Protocol.REQUEST_ID_RENT_ROOM;

    public RentRoomRequest() {
    }

    public RentRoomRequest(int room_id, String renterUsername, String dateFrom, String dateTo) {
        this.room_id = room_id;
        this.username = renterUsername;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getRequest_id() {
        return request_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}