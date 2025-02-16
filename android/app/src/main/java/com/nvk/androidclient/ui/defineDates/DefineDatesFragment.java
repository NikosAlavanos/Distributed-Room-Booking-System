package com.nvk.androidclient.ui.defineDates;

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

public class DefineDatesFragment extends Fragment {

    private FragmentDefineDatesBinding binding;
    private Button buttonDefineDates;

    public Handler myHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            String json = message.getData().getString("result");

            if (json != null) {
                Toast.makeText(getActivity(), json, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Room does not exist", Toast.LENGTH_SHORT).show();
            }

            buttonDefineDates.setEnabled(true);

            return false;
        }
    });


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DefineDatesViewModel viewModel = new ViewModelProvider(this).get(DefineDatesViewModel.class);

        binding = FragmentDefineDatesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        buttonDefineDates = root.findViewById(R.id.buttonDefineDates);

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


        buttonDefineDates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setEnabled(false);

                Toast.makeText(getActivity(), "Changing dates from server", Toast.LENGTH_SHORT).show();

                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.defineDates(myHandler);
                    }
                });
                t.start();
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}