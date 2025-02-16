package com.nvk.androidclient.ui.showStatistics.AllRooms;

import static org.nvk.network.Mapper.createObjectMapper;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nvk.androidclient.R;
import com.nvk.androidclient.ui.search.SearchAdapter;
import com.nvk.androidclient.ui.showStatistics.StatisticsAdapter;
import com.nvk.androidclient.ui.showStatistics.structures.Area;

import org.nvk.structures.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class AllRoomsFragment extends Fragment implements OnItemSelectedListener {
    private AllRoomsViewModel viewModel;
    private Button buttonStatisticsAllRooms;
    private ListView listStatisticsAllRooms;

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String json = message.getData().getString("result");

            Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();

            buttonStatisticsAllRooms.setEnabled(true);

            ObjectMapper objectMapper = createObjectMapper();

            List<Area> areas = new ArrayList<>();

            try {
                JsonNode jsonNode = objectMapper.readTree(json);

                int n = jsonNode.size();

                for (int i=0;i<n;i++) {
                    JsonNode child = jsonNode.get(i);

                }

                // Ensure the JsonNode is an ObjectNode
                if (jsonNode.isObject()) {
                    ObjectNode objectNode = (ObjectNode) jsonNode;



                    objectNode.fields().forEachRemaining(field -> {
                        String fieldName = field.getKey();
                        JsonNode fieldValue = field.getValue();

                        // Process each field (e.g., print field name and value)
                        System.out.println("Field Name: " + fieldName);
                        System.out.println("Field Value: " + fieldValue);


                        areas.add(new Area(fieldName, fieldValue.asText()));
                    });

                    Collections.sort(areas, Comparator.comparing(Area::getName));

                    StatisticsAdapter adapter = new StatisticsAdapter(getLayoutInflater(),areas);
                    listStatisticsAllRooms.setAdapter(adapter);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_statistics_all_rooms, container, false);

        viewModel = new ViewModelProvider(this).get(AllRoomsViewModel.class);

        buttonStatisticsAllRooms = root.findViewById(R.id.buttonStatisticsAllRooms);

        listStatisticsAllRooms = root.findViewById(R.id.listStatisticsAllRooms);

        buttonStatisticsAllRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                Toast.makeText(getActivity(), "Fetching statistics from server", Toast.LENGTH_SHORT).show();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.statisticsForAllRooms(myHandler);
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
        navigateToFragment(new AllRoomsFragment());
    }
}
