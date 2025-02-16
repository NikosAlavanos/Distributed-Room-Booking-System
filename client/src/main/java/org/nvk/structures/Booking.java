package org.nvk.structures;

import java.time.LocalDate;

public class Booking {
    private String username;
    private LocalDate from;
    private LocalDate to;

    public Booking() {
    }

    public Booking(String username, LocalDate from, LocalDate to) {
        this.username = username;
        this.from = from;
        this.to = to;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getFrom() {
        return from;
    }

    public void setFrom(LocalDate from) {
        this.from = from;
    }

    public LocalDate getTo() {
        return to;
    }

    public void setTo(LocalDate to) {
        this.to = to;
    }
}
