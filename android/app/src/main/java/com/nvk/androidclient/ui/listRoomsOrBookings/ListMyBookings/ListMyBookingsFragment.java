package com.nvk.androidclient.ui.listRoomsOrBookings.ListMyBookings;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvk.androidclient.R;

import com.nvk.androidclient.ui.listRoomsOrBookings.ListRoomsWithBookingsAdapter;

import org.nvk.structures.Room;
import org.nvk.structures.RoomCollection;

import java.util.ArrayList;

public class ListMyBookingsFragment extends Fragment implements OnItemSelectedListener {
    private ListMyBookingsViewModel viewModel;
    private Button buttonListMyBookings;
    private EditText textViewID;
    private ListView listMyRoomsWithBookings;

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String json = message.getData().getString("result");

            if (json != null) {
                Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Room does not exist", Toast.LENGTH_SHORT).show();
                return false;
            }

            buttonListMyBookings.setEnabled(true);

            ObjectMapper objectMapper = createObjectMapper();

            try {
                RoomCollection data = objectMapper.readValue(json, RoomCollection.class);

                ArrayList<Room> items = data.rooms;

                ListRoomsWithBookingsAdapter adapter = new ListRoomsWithBookingsAdapter(getLayoutInflater(),items);

                listMyRoomsWithBookings.setAdapter(adapter);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_my_bookings, container, false);

        viewModel = new ViewModelProvider(this).get(ListMyBookingsViewModel.class);
        textViewID = root.findViewById(R.id.textViewLabelSpecificID);
        buttonListMyBookings = root.findViewById(R.id.buttonListMyBookings);

        listMyRoomsWithBookings = root.findViewById(R.id.listMyRoomsWithBookings);

        textViewID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    String text = s.toString().trim();
                    if (text.isEmpty()) {
                        return;
                    }

                    int roomId = Integer.parseInt(text);
                    viewModel.getRoomId().setValue(roomId);
                    buttonListMyBookings.setEnabled(s.toString().trim().length() > 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
        buttonListMyBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                Toast.makeText(getActivity(), "Fetching rooms from server", Toast.LENGTH_SHORT).show();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.listBookings(myHandler);
                    }
                });
                t.start();
            }
        });

        return root;
    }

    private void navigateToFragment(Fragment fragment) {
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        navigateToFragment(new com.nvk.androidclient.ui.listRoomsOrBookings.ListRooms.ListRoomsFragment());
    }
}
