package com.nvk.androidclient.ui.defineDates;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.nvk.configuration.Config;
import org.nvk.structures.Profile;
import org.nvk.users.Manager;

public class DefineDatesViewModel extends ViewModel {

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

    public void defineDates(Handler myHandler) {
        try {
            Profile profile = Config.PROFILE;
            Manager manager = new Manager(profile);
            String result = manager.setDatesForRoom(roomId.getValue().intValue(), dateFrom.getValue(), dateTo.getValue());

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