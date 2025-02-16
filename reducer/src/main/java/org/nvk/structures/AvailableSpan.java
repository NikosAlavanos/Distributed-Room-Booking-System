package org.nvk.structures;

import java.time.LocalDate;

public class AvailableSpan {
    private LocalDate from;
    private LocalDate to;

    public AvailableSpan() {
    }

    public AvailableSpan(LocalDate from, LocalDate to) {
        this.from = from;
        this.to = to;
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
