package com.nvk.androidclient.ui.listRoomsOrBookings.ListMyBookings;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.nvk.configuration.Config;
import org.nvk.structures.Profile;
import org.nvk.users.Manager;

public class ListMyBookingsViewModel extends ViewModel {

    private final MutableLiveData<Integer> roomId = new MutableLiveData<>(0);

    public ListMyBookingsViewModel() {
    }

    public MutableLiveData<Integer> getRoomId() {
        return roomId;
    }

    public void listBookings(Handler myHandler) {
        try {
            if (this.roomId.getValue() == 0) {
                return;
            }
            Profile profile = Config.PROFILE;
            Manager manager = new Manager(profile);

            int roomId = this.roomId.getValue();

            String result = manager.listBookings(roomId);

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("result",result);
            msg.setData(bundle);

            myHandler.sendMessage(msg);
        } catch (Exception ex) {
        }
    }
}