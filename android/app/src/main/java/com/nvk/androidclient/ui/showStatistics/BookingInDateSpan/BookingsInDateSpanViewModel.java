package com.nvk.androidclient.ui.showStatistics.BookingInDateSpan;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.nvk.configuration.Config;
import org.nvk.structures.Profile;
import org.nvk.users.Manager;

public class BookingsInDateSpanViewModel extends ViewModel {

    private final MutableLiveData<String> From = new MutableLiveData<>();
    private final MutableLiveData<String> To = new MutableLiveData<>();

    public BookingsInDateSpanViewModel() {
    }

    public MutableLiveData<String> getFrom() {
        return From;
    }

    public MutableLiveData<String> getTo() {
        return To;
    }


    public void listMyBookings(Handler myHandler) {
        try {
            Profile profile = Config.PROFILE;
            Manager manager = new Manager(profile);



            String result = manager.showStatisticsForBookings(From.getValue(),To.getValue());

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("result",result);
            msg.setData(bundle);

            myHandler.sendMessage(msg);
        } catch (Exception ex) {
        }
    }
}