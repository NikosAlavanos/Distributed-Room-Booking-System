package com.nvk.androidclient.ui.rateRoom;

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

public class RateRoomFragment extends Fragment {

    private Button buttonRateRoom;

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String json = message.getData().getString("result");

            if (json == null) {
                Toast.makeText(getActivity(), "Invalid ID", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();
            }

            buttonRateRoom.setEnabled(true);

            return false;
        }
    });


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RateRoomViewModel viewModel = new ViewModelProvider(this).get(RateRoomViewModel.class);

        View root = inflater.inflate(R.layout.fragment_rate_room, container, false);

        buttonRateRoom = root.findViewById(R.id.buttonRateRoom);

        EditText roomId = root.findViewById(R.id.editTextRoomID);
        EditText stars = root.findViewById(R.id.editTextStars);


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
                    viewModel.getStars().setValue(0);
                    ex.printStackTrace();
                }
            }
        });



        buttonRateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewModel.getStars().getValue() == null || viewModel.getStars().getValue() < 0 || viewModel.getStars().getValue() > 5) {
                    Toast.makeText(getActivity(), "Invalid rate value", Toast.LENGTH_SHORT).show();
                    return;
                }
                v.setEnabled(false);

                Toast.makeText(getActivity(), "Sending the rate room to server...", Toast.LENGTH_SHORT).show();

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