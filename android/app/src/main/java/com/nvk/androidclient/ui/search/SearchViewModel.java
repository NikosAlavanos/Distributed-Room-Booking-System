package com.nvk.androidclient.ui.search;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.nvk.configuration.Config;
import org.nvk.structures.Profile;
import org.nvk.structures.SearchTraits;
import org.nvk.users.Manager;

public class SearchViewModel extends ViewModel {

   private final MutableLiveData<String> roomArea = new MutableLiveData<>();
   private final MutableLiveData<String> From = new MutableLiveData<>();
   private final MutableLiveData<String> To = new MutableLiveData<>();
   private final MutableLiveData<Integer> Capacity = new MutableLiveData<>();
   private final MutableLiveData<Integer> Price = new MutableLiveData<>();
   private final MutableLiveData<Integer> Stars = new MutableLiveData<>();

   public MutableLiveData<String> getArea() {
        return roomArea;
   }

   public MutableLiveData<String> getFrom() {
        return From;
    }

   public MutableLiveData<String> getTo() {
        return To;
    }

   public MutableLiveData<Integer> getCapacity() {
        return Capacity;
    }

   public MutableLiveData<Integer> getPrice() {
        return Price;
    }

   public MutableLiveData<Integer> getStars() {
        return Stars;
    }

   public void Search(Handler myHandler) {
        try {
            Profile profile = Config.PROFILE;
            Manager manager = new Manager(profile);
            SearchTraits traits = new SearchTraits();

            if (this.roomArea.getValue() != null && this.roomArea.getValue().isEmpty() == false) {
                traits.setArea(this.roomArea.getValue());
            } else {
                traits.setArea(null);
            }

            if (this.From.getValue() != null && this.From.getValue().isEmpty() == false) {
                traits.setDateFrom(this.From.getValue());
            } else {
                traits.setDateFrom(null);
            }

            if (this.To.getValue() != null && this.To.getValue().isEmpty() == false) {
                traits.setDateTo(this.To.getValue());
            } else {
                traits.setDateTo(null);
            }

            if (this.Capacity.getValue() != null) {
                traits.setRoomcap(this.Capacity.getValue());
            } else {
                traits.setRoomcap(null);
            }

            if (this.Price.getValue() != null) {
                traits.setPrice(this.Price.getValue());
            } else {
                traits.setPrice(null);
            }

            if (this.Stars.getValue() != null) {
                traits.setStars(this.Stars.getValue());
            } else {
                traits.setStars(null);
            }

            String result = manager.searchBy(traits);

            Message msg = new Message();
            Bundle bundle = new Bundle();
            bundle.putString("result",result);
            msg.setData(bundle);

            myHandler.sendMessage(msg);
        } catch (Exception ex) {
        }
    }


}