package com.nvk.androidclient.ui.showStatistics.BookingInDateSpan;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.nvk.androidclient.R;
import com.nvk.androidclient.ui.showStatistics.StatisticsAdapter;
import com.nvk.androidclient.ui.showStatistics.structures.Area;

import org.nvk.structures.Room;
import org.nvk.structures.RoomCollection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookingsInDateSpanFragment extends Fragment implements OnItemSelectedListener {
    private BookingsInDateSpanViewModel viewModel;
    private Button buttonFetchMyBookings;
    private EditText fromText;
    private EditText toText;
    private ListView listMyBookings;

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String json = message.getData().getString("result");

            Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();

            buttonFetchMyBookings.setEnabled(true);

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



                        String temp = fieldValue.asText();


                        if (!temp.equals("0")) {
                            areas.add(new Area(fieldName, fieldValue.asText()));
                        }
                    });

                    Collections.sort(areas, Comparator.comparing(Area::getName));

                    StatisticsAdapter adapter = new StatisticsAdapter(getLayoutInflater(),areas);
                    listMyBookings.setAdapter(adapter);

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_statistics_bookings, container, false);

        viewModel = new ViewModelProvider(this).get(BookingsInDateSpanViewModel.class);

        fromText = root.findViewById(R.id.fromText);
        toText = root.findViewById(R.id.toText);
        buttonFetchMyBookings = root.findViewById(R.id.buttonFetchMyBookings);

        listMyBookings = root.findViewById(R.id.listMyBookings);

        fromText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    viewModel.getFrom().setValue(s.toString().trim());
                    buttonFetchMyBookings.setEnabled(s.toString().trim().length() > 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        toText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {

                    viewModel.getTo().setValue(s.toString().trim());
                    buttonFetchMyBookings.setEnabled(s.toString().trim().length() > 0);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

        });

        buttonFetchMyBookings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                Toast.makeText(getActivity(), "Fetching Bookings from server", Toast.LENGTH_SHORT).show();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.listMyBookings(myHandler);
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
