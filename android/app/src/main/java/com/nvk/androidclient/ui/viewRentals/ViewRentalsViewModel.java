package com.nvk.androidclient.ui.viewRentals;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.nvk.configuration.Config;
import org.nvk.structures.Profile;
import org.nvk.users.Renter;

public class ViewRentalsViewModel extends ViewModel {

    private final MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> getUsername() {
        return username;
    }

    public void viewRentals(Handler myHandler) {
        try {
            Profile profile = Config.PROFILE;
            Renter renter = new Renter(profile);
            String result = renter.viewRentals();


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