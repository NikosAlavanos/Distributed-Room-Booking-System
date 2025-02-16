package com.nvk.androidclient.ui.showStatistics;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nvk.androidclient.R;

import java.util.List;

import com.nvk.androidclient.ui.showStatistics.structures.Area;

public class StatisticsAdapter extends BaseAdapter {

    // override other abstract methods here
    private LayoutInflater inflater;

    private List<Area> areas;

    public StatisticsAdapter(LayoutInflater inflater, List<Area> areas){
        this.inflater = inflater;
        this.areas = areas;
    }

    @Override
    public int getCount() {
        return areas.size();
    }

    @Override
    public Object getItem(int i) {
        return areas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup container) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.statistics_item, container, false);
        }

        Area area = (Area) areas.get(i);



        ((TextView) convertView.findViewById(R.id.area_name)).setText(area.getName());
        ((TextView) convertView.findViewById(R.id.value_area)).setText(area.getFrequency());
        return convertView;
    }
}
