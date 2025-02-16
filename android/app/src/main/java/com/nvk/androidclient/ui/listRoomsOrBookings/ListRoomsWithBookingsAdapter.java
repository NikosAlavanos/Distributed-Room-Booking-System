package com.nvk.androidclient.ui.listRoomsOrBookings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nvk.androidclient.R;

import org.nvk.structures.Booking;
import org.nvk.structures.Room;

import java.util.ArrayList;

public class ListRoomsWithBookingsAdapter extends BaseAdapter {

    // override other abstract methods here
    private LayoutInflater inflater;

    private ArrayList<Room> rooms;
    private ArrayList<Booking> items;

    public ListRoomsWithBookingsAdapter(LayoutInflater inflater, ArrayList<Room> rooms){
        this.inflater = inflater;

        if (rooms != null && rooms.size() > 0) {
            this.rooms = rooms;
            this.items = rooms.get(0).getBookings();
        } else {
            this.items = new ArrayList<>();
            this.rooms = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_room_booking_item, container, false);
        }

        Booking booking = (Booking) items.get(position);

        ((TextView) convertView.findViewById(R.id.username)).setText(booking.getUsername());
        ((TextView) convertView.findViewById(R.id.booking_from)).setText(booking.getFrom().toString());
        ((TextView) convertView.findViewById(R.id.booking_to)).setText(booking.getTo().toString());
        return convertView;
    }
}
