package org.nvk.structures;

import org.nvk.constants.Protocol;

public class CreateRoomRequest {
    private int room_id;
    private String username;
    private String roomname;
    private Integer persons;
    private String area;
    private Integer price;
    private Integer stars;
    private final String request_id = Protocol.REQUEST_ID_CREATE_ROOM;

    public CreateRoomRequest() {
    }

    public CreateRoomRequest(int room_id, String username, String roomname, String area, Integer persons, Integer price, Integer stars) {
        this.room_id =room_id;
        this.username = username;
        this.roomname = roomname;
        this.persons = persons;
        this.area = area;
        this.price = price;
        this.stars = stars;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public Integer getPersons() {
        return persons;
    }

    public void setPersons(Integer persons) {
        this.persons = persons;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Integer getStars() {
        return stars;
    }

    //changed it to static
    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRequest_id() {
        return request_id;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }
}

