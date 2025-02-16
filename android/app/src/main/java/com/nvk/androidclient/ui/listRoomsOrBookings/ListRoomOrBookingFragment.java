package com.nvk.androidclient.ui.listRoomsOrBookings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nvk.androidclient.R;
import com.nvk.androidclient.ui.listRoomsOrBookings.ListMyBookings.ListMyBookingsFragment;
import com.nvk.androidclient.ui.listRoomsOrBookings.ListRoomByID.ListRoomByIDFragment;
import com.nvk.androidclient.ui.listRoomsOrBookings.ListRooms.ListRoomsFragment;

public class ListRoomOrBookingFragment extends Fragment implements OnItemSelectedListener {

    String[] choices = {"List your rooms", "List room by ID", "List my Bookings For specific room ID"};
    ArrayAdapter<String> adapter;
    private ListRoomsFragment f1 = new ListRoomsFragment();
    private ListRoomByIDFragment f2 = new ListRoomByIDFragment();
    private ListMyBookingsFragment f3 = new ListMyBookingsFragment();
    private ViewGroup flContent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_rooms_or_bookings, container, false);

        Spinner spinner = root.findViewById(R.id.spinner1);

        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, choices);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this); // Set the listener

        flContent = root.findViewById(R.id.fragment_container);


        return root;
    }

    private void navigateToFragment(Fragment fragment) {
        flContent.removeAllViews();

        FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedItem = (String) parent.getItemAtPosition(position);

        switch (selectedItem) {
            case "List your rooms":
                navigateToFragment(f1);
                break;
            case "List room by ID":
                navigateToFragment(f2);
                break;
            case "List my Bookings For specific room ID":
                navigateToFragment(f3);
                break;
            default:
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing if nothing is selected
    }
}

