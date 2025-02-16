package com.nvk.androidclient.ui.create;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.nvk.configuration.Config;
import org.nvk.structures.Profile;
import org.nvk.users.Manager;

public class CreateRoomViewModel extends ViewModel {

    private final MutableLiveData<String> roomName;
    private final MutableLiveData<String> area;
    private final MutableLiveData<Integer> persons;
    private final MutableLiveData<Integer> price;


    public CreateRoomViewModel() {
        roomName = new MutableLiveData<>();
        area = new MutableLiveData<>();
        persons = new MutableLiveData<>();
        price = new MutableLiveData<>();
    }

    public MutableLiveData<String> getRoomName() {
        return roomName;
    }

    public MutableLiveData<String> getArea() {
        return area;
    }

    public MutableLiveData<Integer> getPersons() {
        return persons;
    }

    public MutableLiveData<Integer> getPrice() {
        return price;
    }

    public String createRoom(String roomName, String area, Integer persons, Integer price) {
        Profile profile = Config.PROFILE;
        Manager manager = new Manager(profile);
        String result = manager.insertRoom(roomName, area, persons, price, 0);
        return result;
    }
}