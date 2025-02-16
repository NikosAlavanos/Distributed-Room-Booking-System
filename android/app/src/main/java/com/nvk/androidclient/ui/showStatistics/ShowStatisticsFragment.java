package com.nvk.androidclient.ui.showStatistics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.nvk.androidclient.R;
import com.nvk.androidclient.ui.showStatistics.AllRooms.AllRoomsFragment;
import com.nvk.androidclient.ui.showStatistics.BookingInDateSpan.BookingsInDateSpanFragment;
import com.nvk.androidclient.ui.showStatistics.MyRooms.MyRoomsFragment;

public class ShowStatisticsFragment extends Fragment implements OnItemSelectedListener {

    String[] choices = {"All rooms", "My rooms", "Bookings"};
    ArrayAdapter<String> adapter;
    private AllRoomsFragment f1 = new AllRoomsFragment();
    private MyRoomsFragment f2 = new MyRoomsFragment();
    private BookingsInDateSpanFragment f3 = new BookingsInDateSpanFragment();
    private ViewGroup flContent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_show_statistics, container, false);

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
            case "All rooms":
                navigateToFragment(f1);
                break;
            case "My rooms":
                navigateToFragment(f2);
                break;
            case "Bookings":
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

