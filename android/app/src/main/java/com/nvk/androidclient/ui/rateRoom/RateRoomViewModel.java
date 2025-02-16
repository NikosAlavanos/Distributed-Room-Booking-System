package com.nvk.androidclient.ui.rateRoom;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.nvk.configuration.Config;
import org.nvk.structures.Profile;
import org.nvk.users.Renter;

public class RateRoomViewModel extends ViewModel {

    private final MutableLiveData<Integer> roomId = new MutableLiveData<>();
    private final MutableLiveData<Integer> stars = new MutableLiveData<>();

    public MutableLiveData<Integer> getRoomId() {
        return roomId;
    }

    public MutableLiveData<Integer> getStars() {
        return stars;
    }

    public void rentRoom(Handler myHandler) {
        try {
            Profile profile = Config.PROFILE;
            Renter renter = new Renter(profile);
            String result = renter.rateRoom(roomId.getValue().intValue(), stars.getValue().intValue());


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