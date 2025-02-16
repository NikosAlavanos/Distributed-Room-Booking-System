package com.nvk.androidclient.ui.listRoomsOrBookings.ListRooms;

import static org.nvk.network.Mapper.createObjectMapper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.nvk.androidclient.ui.listRoomsOrBookings.ListRoomsAdapter;

import org.nvk.structures.Room;
import org.nvk.structures.RoomCollection;

import java.util.ArrayList;

public class ListRoomsFragment extends Fragment implements OnItemSelectedListener {
    private ListRoomsViewModel viewModel;
    private Button buttonListRooms;
    private ListView listMyRooms;

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String json = message.getData().getString("result");

//            Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();

            buttonListRooms.setEnabled(true);

            ObjectMapper objectMapper = createObjectMapper();

            try {
                RoomCollection data = objectMapper.readValue(json, RoomCollection.class);

                ArrayList<Room> items = data.rooms;

                ListRoomsAdapter adapter = new ListRoomsAdapter(getLayoutInflater(),items);

                listMyRooms.setAdapter(adapter);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_list_rooms, container, false);

        viewModel = new ViewModelProvider(this).get(ListRoomsViewModel.class);

        buttonListRooms = root.findViewById(R.id.buttonFetchMyRooms);

        listMyRooms = root.findViewById(R.id.listMyRooms);

        listMyRooms.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "Clicked: "+i, Toast.LENGTH_SHORT).show();
            }
        });

        buttonListRooms.setOnClickListener(new View.OnClickListener() {
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
        navigateToFragment(new ListRoomsFragment());
    }
}
