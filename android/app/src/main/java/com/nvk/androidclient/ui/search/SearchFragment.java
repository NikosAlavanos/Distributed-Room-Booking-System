package com.nvk.androidclient.ui.search;

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
//import com.nvk.androidclient.databinding.FragmentDefineDatesBinding;

public class SearchFragment extends Fragment {
    private SearchViewModel viewModel;
    private Button buttonSearch;
    private ListView search;

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String json = message.getData().getString("result");

            //Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();

            buttonSearch.setEnabled(true);
            ObjectMapper objectMapper = createObjectMapper();

            try {
                RoomCollection data = objectMapper.readValue(json, RoomCollection.class);

                ArrayList<Room> items = data.rooms;

                SearchAdapter adapter = new SearchAdapter(getLayoutInflater(),items);

                search.setAdapter(adapter);

                if (items.isEmpty()) {
                    Toast.makeText(getActivity(), "No rooms found", Toast.LENGTH_SHORT).show();
                }
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return false;
        }
    });


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SearchViewModel viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        View root = inflater.inflate(R.layout.fragment_search, container, false);


        buttonSearch = root.findViewById(R.id.buttonSearch);
        search = root.findViewById(R.id.search);

        EditText dateFrom = root.findViewById(R.id.fromText);
        EditText roomArea = root.findViewById(R.id.areaText);
        EditText dateTo = root.findViewById(R.id.toText);
        EditText capacity = root.findViewById(R.id.capacityText);
        EditText price = root.findViewById(R.id.priceText);
        EditText stars = root.findViewById(R.id.starsText);


        roomArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    viewModel.getArea().setValue(s.toString().trim());
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        dateFrom.addTextChangedListener(new TextWatcher() {
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
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        dateTo.addTextChangedListener(new TextWatcher() {
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
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        capacity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Integer value = Integer.parseInt(s.toString().trim());

                    viewModel.getCapacity().setValue(value);
                }catch (Exception ex) {
                    ex.printStackTrace();
                    viewModel.getCapacity().setValue(null);
                }
            }
        });

        price.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Integer value = Integer.parseInt(s.toString().trim());

                    viewModel.getPrice().setValue(value);
                }catch (Exception ex) {
                    ex.printStackTrace();
                    viewModel.getPrice().setValue(null);
                }
            }
        });

        stars.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    Integer value = Integer.parseInt(s.toString().trim());

                    viewModel.getStars().setValue(value);
                }catch (Exception ex) {
                    ex.printStackTrace();
                    viewModel.getStars().setValue(null);
                }
            }
        });


        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                Toast.makeText(getActivity(), "Fetching rooms from server", Toast.LENGTH_SHORT).show();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.Search(myHandler);
                    }
                });
                t.start();
            }
        });

        return root;
    }
}