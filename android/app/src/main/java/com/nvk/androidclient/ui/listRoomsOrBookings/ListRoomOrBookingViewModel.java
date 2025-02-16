package com.nvk.androidclient.ui.listRoomsOrBookings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ListRoomOrBookingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ListRoomOrBookingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Create Room");
    }

    public LiveData<String> getText() {
        return mText;
    }
}