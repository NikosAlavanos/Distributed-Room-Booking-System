package com.nvk.androidclient.ui.showStatistics;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShowStatisticsModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ShowStatisticsModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Create Room");
    }

    public LiveData<String> getText() {
        return mText;
    }
}