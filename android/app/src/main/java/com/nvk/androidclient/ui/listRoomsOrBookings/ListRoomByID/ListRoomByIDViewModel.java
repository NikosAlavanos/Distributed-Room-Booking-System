package com.nvk.androidclient.ui.listRoomsOrBookings.ListRoomByID;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.nvk.configuration.Config;
import org.nvk.structures.Profile;
import org.nvk.structures.Room;
import org.nvk.users.Manager;

public class ListRoomByIDViewModel extends ViewModel {

    private final MutableLiveData<Integer> roomId = new MutableLiveData<>();
    private final MutableLiveData<Room> room = new MutableLiveData<>(null);

    public ListRoomByIDViewModel() {
    }

    public MutableLiveData<Integer> getRoomId() {
        return roomId;
    }

    public MutableLiveData<Room> getRoom() {
        return room;
    }

    public void findById(Handler myHandler) {
        try {
            Profile profile = Config.PROFILE;
            Manager manager = new Manager(profile);
            String result = manager.findRoomByID(roomId.getValue().intValue());

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
