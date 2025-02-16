package com.nvk.androidclient.ui.showStatistics.structures;

public class Area {
    private String name;
    private String frequency;

    public Area(String name, String frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public Area() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }
}


