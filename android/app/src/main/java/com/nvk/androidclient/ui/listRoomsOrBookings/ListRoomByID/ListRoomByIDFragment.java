package com.nvk.androidclient.ui.listRoomsOrBookings.ListRoomByID;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nvk.androidclient.R;

import org.nvk.structures.AvailableSpan;
import org.nvk.structures.Booking;
import org.nvk.structures.Room;

public class ListRoomByIDFragment extends Fragment implements OnItemSelectedListener {
    private ListRoomByIDViewModel viewModel;
    private Button buttonListRoomByID;
    private EditText textViewID;
    private ListView listMyRooms;

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String json = message.getData().getString("result");

            if (json != null) {
//                Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Room does not exist", Toast.LENGTH_SHORT).show();
                return false;
            }

//            Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();

            buttonListRoomByID.setEnabled(true);

            ObjectMapper objectMapper = createObjectMapper();

            try {
                Room room = objectMapper.readValue(json, Room.class);
                viewModel.getRoom().postValue(room);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_room_by_id, container, false);

        viewModel = new ViewModelProvider(this).get(ListRoomByIDViewModel.class);
        textViewID = root.findViewById(R.id.textViewID);
        buttonListRoomByID = root.findViewById(R.id.buttonFetchMyRoom);

        textViewID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                buttonListRoomByID.setEnabled(s.toString().trim().length() > 0);

                try {
                    Integer value = Integer.parseInt(s.toString().trim());

                    viewModel.getRoomId().setValue(value);
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });
        buttonListRoomByID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                Toast.makeText(getActivity(), "Fetching room from server", Toast.LENGTH_SHORT).show();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.findById(myHandler);
                    }
                });
                t.start();
            }
        });

        viewModel.getRoom().observe(this.getViewLifecycleOwner(), new Observer<Room>() {
            @Override
            public void onChanged(Room room) {
                if (room == null) {
                    ((TextView) root.findViewById(R.id.room_name)).setText("");
                    ((TextView) root.findViewById(R.id.room_area)).setText("");
                    ((TextView) root.findViewById(R.id.stars)).setText("");
                    ((TextView) root.findViewById(R.id.price)).setText("");
                    ((TextView) root.findViewById(R.id.persons)).setText("");
                    ((TextView) root.findViewById(R.id.spans)).setText("");
                } else {
                    String stars = "";

                    for (int i=0;i<room.getStars();i++) {
                        stars += "*";
                    }

                    if (room.getStars() == 0) {
                        stars = "No rating yet.";
                    }

                    String spans = " - Spans: \n\n";

                    for (AvailableSpan s : room.getSpans()) {
                        String row = "From " + s.getFrom().toString() + " to " + s.getTo().toString();
                        spans += row + "\n";
                    }

                    spans += "\n - Bookings: \n\n";

                    for (Booking b : room.getBookings()) {
                        String row = b.getUsername() + " " + b.getFrom().toString() + " to " + b.getTo().toString();
                        spans += row + "\n";
                    }

                    if (room.getBookings().isEmpty()) {
                        spans += "No bookings till now.";
                    }

                    ((TextView) root.findViewById(R.id.room_name)).setText(room.getRoomname());
                    ((TextView) root.findViewById(R.id.room_area)).setText(room.getArea());
                    ((TextView) root.findViewById(R.id.stars)).setText(stars);
                    ((TextView) root.findViewById(R.id.price)).setText("$" + room.getPrice());
                    ((TextView) root.findViewById(R.id.persons)).setText(room.getPersons() + " persons");
                    ((TextView) root.findViewById(R.id.spans)).setText(spans);
                }
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
