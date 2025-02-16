package com.nvk.androidclient.ui.create;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nvk.androidclient.databinding.FragmentCreateRoomBinding;

public class CreateRoomFragment extends Fragment {

    public class CreateRoomAsyncTask extends AsyncTask<Void, Void, String> {

        private final CreateRoomViewModel viewModel;
        private final TextView textViewResult;
        private final String roomName;
        private final String area;
        private final Integer persons;
        private final Integer price;

        public CreateRoomAsyncTask(CreateRoomViewModel viewModel, TextView textViewResult, String roomName, String area, Integer persons, Integer price) {
            this.viewModel = viewModel;
            this.textViewResult = textViewResult;
            this.roomName = roomName;
            this.area = area;
            this.persons = persons;
            this.price = price;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            textViewResult.setText("Waiting for a response from server ...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                String result = viewModel.createRoom(roomName, area, persons, price);
                return result;
            } catch (Exception e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("AsyncTaskExample", "Result: " + result);

            textViewResult.setText(result);
        }
    }

    private FragmentCreateRoomBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        CreateRoomViewModel viewModel = new ViewModelProvider(this).get(CreateRoomViewModel.class);

        binding = FragmentCreateRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText labelRoomName = binding.labelRoomName;
        EditText labelArea = binding.labelArea;
        EditText labelPersons = binding.labelPersons;
        EditText labelPrice = binding.labelPrice;
        TextView textViewResult = binding.textViewResult;

        labelRoomName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the ViewModel's text value each time the user types
                viewModel.getRoomName().postValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        labelArea.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the ViewModel's text value each time the user types
                viewModel.getArea().postValue(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        labelPersons.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the ViewModel's text value each time the user types
                try {
                    Integer x = Integer.parseInt(s.toString());
                    viewModel.getPersons().postValue(x);
                } catch (Exception ex) {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        labelPrice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Update the ViewModel's text value each time the user types
                try {
                    Integer x = Integer.parseInt(s.toString());
                    viewModel.getPrice().postValue(x);
                } catch (Exception ex) {

                }

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        Button buttonCreateRoom = binding.buttonCreateRoom;

        buttonCreateRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String roomName = viewModel.getRoomName().getValue();
                String area = viewModel.getArea().getValue();
                Integer persons = viewModel.getPersons().getValue();
                Integer price = viewModel.getPrice().getValue();

                Log.i("DEBUG MESSAGE", "Roomname: " + roomName);
                Log.i("DEBUG MESSAGE", "Area: " + area);
                Log.i("DEBUG MESSAGE", "Persons: " + persons);
                Log.i("DEBUG MESSAGE", "Price: " + price);

                if (roomName == null || roomName.isEmpty()) {
                    Toast.makeText(getActivity(), "Missing room name", Toast.LENGTH_LONG).show();
                    return;
                }

                if (area == null || area.isEmpty()) {
                    Toast.makeText(getActivity(), "Missing area", Toast.LENGTH_LONG).show();
                    return;
                }

                if (persons == null) {
                    Toast.makeText(getActivity(), "Missing persons", Toast.LENGTH_LONG).show();
                    return;
                }

                if (price == null) {
                    Toast.makeText(getActivity(), "Missing price", Toast.LENGTH_LONG).show();
                    return;
                }

                try {
                    CreateRoomAsyncTask task = new CreateRoomAsyncTask(viewModel, textViewResult, roomName, area, persons, price);
                    task.execute();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    Toast.makeText(getActivity(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
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