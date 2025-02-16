package org.nvk.structures;

import java.util.ArrayList;

public class Room {
    private int id;
    private String username;
    private String roomname;
    private Integer persons;
    private String area;

    private ArrayList<String> reviews = new ArrayList<>();

    private Integer price;
    private Integer stars = 0;
    private ArrayList<AvailableSpan> spans = new ArrayList<>();
    private ArrayList<Booking> bookings = new ArrayList<>();


    public Room() {
    }


    public Room(String username, String roomname, String area, Integer persons, Integer price) {
        this.username = username;
        this.roomname = roomname;
        this.persons = persons;
        this.area = area;
        this.price = price;

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

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<String> reviews) {
        this.reviews = reviews;
    }

    public ArrayList<AvailableSpan> getSpans() {
        return spans;
    }

    public void setSpans(ArrayList<AvailableSpan> spans) {
        this.spans = spans;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(ArrayList<Booking> bookings) {
        this.bookings = bookings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}

