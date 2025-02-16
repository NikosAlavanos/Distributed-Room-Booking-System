package com.nvk.androidclient.ui.listRoomsOrBookings;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nvk.androidclient.R;

import org.nvk.structures.Room;

import java.util.ArrayList;

public class ListRoomsAdapter extends BaseAdapter {

    // override other abstract methods here
    private LayoutInflater inflater;

    private ArrayList<Room> items;

    public ListRoomsAdapter(LayoutInflater inflater, ArrayList<Room> items){
        this.inflater = inflater;
        this.items = items;
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
        return ((Room)items.get(i)).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup container) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.my_rooms_item, container, false);
        }

        Room room = (Room) items.get(position);

        String stars = "";

        for (int i=0;i<room.getStars();i++) {
            stars += "*";
        }

        if (room.getStars() == 0) {
            stars = "No rating yet.";
        }

        ((TextView) convertView.findViewById(R.id.room_name)).setText(room.getRoomname());
        ((TextView) convertView.findViewById(R.id.room_area)).setText(room.getArea());
        ((TextView) convertView.findViewById(R.id.stars)).setText(stars);
        ((TextView) convertView.findViewById(R.id.price)).setText("$" + room.getPrice());
        ((TextView) convertView.findViewById(R.id.persons)).setText(room.getPersons() + " persons");
        ((TextView) convertView.findViewById(R.id.room_id)).setText("#" + String.valueOf(room.getId()));
        return convertView;
    }
}
