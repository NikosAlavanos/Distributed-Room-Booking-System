package org.nvk.structures;

import org.nvk.constants.Protocol;

public class BookingStatisticsRequest {
    private String dateFrom;
    private String dateTo;
    private final String request_id = Protocol.REQUEST_ID_SHOW_BOOKING_STATISTICS;

    public BookingStatisticsRequest() {
    }

    public BookingStatisticsRequest(String dateFrom, String dateTo) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
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
}