package org.nvk.structures;

import org.nvk.constants.Protocol;

public class RateRoomRequest {
    private int room_id;
    private String username;
    private Integer stars;
    private final String request_id = Protocol.REQUEST_ID_RATE;

    public RateRoomRequest() {
    }

    public RateRoomRequest(String username,int room_id,Integer stars) {
        this.room_id = room_id;
        this.username=username;
        this.stars=stars;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getRequest_id() {
        return request_id;
    }

}