package org.nvk.structures;

import org.nvk.constants.Protocol;

public class SearchRoomRequest {

    private String username;

    private String area;
    private String dateFrom;
    private String dateTo;
    private Integer roomcap;

    private Integer price;

    private Integer stars;

    private final String request_id = Protocol.REQUEST_ID_SEARCH;

    public SearchRoomRequest() {
    }

    public SearchRoomRequest(String username, String area, String dateFrom, String dateTo, Integer roomcap, Integer price, Integer stars) {
        this.username=username;
        this.area = area;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.roomcap = roomcap;
        this.price = price;
        this.stars = stars;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public Integer getRoomcap() {
        return roomcap;
    }

    public void setRoomcap(Integer roomcap) {
        this.roomcap = roomcap;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}