package com.nvk.androidclient.ui.viewRentals;

import static org.nvk.network.Mapper.createObjectMapper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvk.androidclient.R;
import com.nvk.androidclient.ui.listRoomsOrBookings.ListRoomsAdapter;

import org.nvk.structures.Room;
import org.nvk.structures.RoomCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class ViewRentalsFragment extends Fragment {

    private Button buttonViewRentals;
    private ListView viewRentals;

    public static ArrayList<Room> removeDuplicates(List<Room> rooms) {
        HashSet<Integer> seenIds = new HashSet<>();
        ArrayList<Room> uniqueRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (!seenIds.contains(room.getId())) {
                seenIds.add(room.getId());
                uniqueRooms.add(room);
            }
        }

        return uniqueRooms;
    }

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String json = message.getData().getString("result");

            //Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();

            buttonViewRentals.setEnabled(true);

            ObjectMapper objectMapper = createObjectMapper();

            try {
                RoomCollection data = objectMapper.readValue(json, RoomCollection.class);

                ArrayList<Room> items = removeDuplicates(data.rooms);

                ViewRentalsAdapter adapter = new ViewRentalsAdapter(getLayoutInflater(),items);

                viewRentals.setAdapter(adapter);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
    });


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewRentalsViewModel viewModel = new ViewModelProvider(this).get(ViewRentalsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_view_rentals, container, false);

        buttonViewRentals = root.findViewById(R.id.buttonViewRentals);

        viewRentals = root.findViewById(R.id.viewRentals);


//        EditText valueName = root.findViewById(R.id.valueName);
//
//
//        valueName.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                try {
//                    viewModel.getUsername().postValue(s.toString().trim());
//                }catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//            }
//        });

        buttonViewRentals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                Toast.makeText(getActivity(), "Fetcing my rentals...", Toast.LENGTH_SHORT).show();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.viewRentals(myHandler);
                    }
                });
                t.start();
            }
        });

        return root;
    }
}