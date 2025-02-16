package org.nvk.structures;


import org.nvk.constants.Protocol;

public class ViewRentalsRequest {
    private String username;
    private final String request_id = Protocol.REQUEST_ID_VIEW_RENTALS;

    public ViewRentalsRequest() {
    }

    public ViewRentalsRequest(String username) {

        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getRequest_id() {
        return request_id;
    }
}
