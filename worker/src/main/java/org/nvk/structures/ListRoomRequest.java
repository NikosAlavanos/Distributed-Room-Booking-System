package org.nvk.structures;

import org.nvk.constants.Protocol;

public class ListRoomRequest {
    private String username;
    private final String request_id = Protocol.REQUEST_ID_LIST;

    public ListRoomRequest() {
    }

    public ListRoomRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRequest_id() {
        return request_id;
    }


}
