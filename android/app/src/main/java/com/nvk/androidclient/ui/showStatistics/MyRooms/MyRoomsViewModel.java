package com.nvk.androidclient.ui.showStatistics.MyRooms;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.nvk.configuration.Config;
import org.nvk.structures.Profile;
import org.nvk.users.Manager;

public class MyRoomsViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public MyRoomsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public void showMyStatistics(Handler myHandler) {
        try {
            Profile profile = Config.PROFILE;
            Manager manager = new Manager(profile);
            String result = manager.showStatistics();
            mText.postValue(result);

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("result",result);
            msg.setData(bundle);

            myHandler.sendMessage(msg);
        } catch (Exception ex) {
            mText.postValue(ex.getMessage());
        }
    }
}