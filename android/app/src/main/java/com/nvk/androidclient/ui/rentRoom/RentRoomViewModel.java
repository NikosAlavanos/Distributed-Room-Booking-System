package com.nvk.androidclient.ui.rentRoom;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.nvk.configuration.Config;
import org.nvk.structures.Profile;
import org.nvk.users.Renter;

public class RentRoomViewModel extends ViewModel {

    private final MutableLiveData<Integer> roomId = new MutableLiveData<>();
    private final MutableLiveData<String> dateFrom = new MutableLiveData<>();
    private final MutableLiveData<String> dateTo = new MutableLiveData<>();

    public MutableLiveData<Integer> getRoomId() {
        return roomId;
    }

    public MutableLiveData<String> getDateFrom() {
        return dateFrom;
    }

    public MutableLiveData<String> getDateTo() {
        return dateTo;
    }

    public void rentRoom(Handler myHandler) {
        try {
            Profile profile = Config.PROFILE;
            Renter renter = new Renter(profile);
            String result = renter.rentRoom(roomId.getValue().intValue(), profile.username, dateFrom.getValue(), dateTo.getValue());


            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("result",result);
            msg.setData(bundle);

            myHandler.sendMessage(msg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}