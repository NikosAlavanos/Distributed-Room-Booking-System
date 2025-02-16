package com.nvk.androidclient.ui.rentRoom;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nvk.androidclient.R;
import com.nvk.androidclient.databinding.FragmentDefineDatesBinding;

public class RentRoomFragment extends Fragment {

    private Button buttonRentRoom;

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String json = message.getData().getString("result");

            if (json != null) {
                Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Room does not exist", Toast.LENGTH_SHORT).show();
            }

            buttonRentRoom.setEnabled(true);

            return false;
        }
    });


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RentRoomViewModel viewModel = new ViewModelProvider(this).get(RentRoomViewModel.class);

        View root = inflater.inflate(R.layout.fragment_rent_room, container, false);

        buttonRentRoom = root.findViewById(R.id.buttonRentRoom);

        EditText roomId = root.findViewById(R.id.editTextRoomID);
        EditText dateFrom = root.findViewById(R.id.editTextDateFrom);
        EditText dateTo = root.findViewById(R.id.editTextDateTo);

        roomId.addTextChangedListener(new TextWatcher() {
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

                    viewModel.getRoomId().setValue(value);
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
                    viewModel.getDateFrom().setValue(s.toString().trim());
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
                    viewModel.getDateTo().setValue(s.toString().trim());
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });


        buttonRentRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                Toast.makeText(getActivity(), "Booking the room...", Toast.LENGTH_SHORT).show();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.rentRoom(myHandler);
                    }
                });
                t.start();
            }
        });

        return root;
    }
}