package org.nvk.structures;

import org.nvk.constants.Protocol;

public class ShowStatisticsRequest {
    private String username;

    public ShowStatisticsRequest(){}

    public ShowStatisticsRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private final String request_id =  Protocol.REQUEST_ID_SHOW_STATISTICS;
}