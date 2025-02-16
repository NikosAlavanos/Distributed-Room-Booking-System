package org.nvk.structures;

import java.util.ArrayList;

public class RoomCollection {
    public ArrayList<Room> rooms = new ArrayList<>();

    public RoomCollection(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }

    public RoomCollection() {

    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void setRooms(ArrayList<Room> rooms) {
        this.rooms = rooms;
    }
}
