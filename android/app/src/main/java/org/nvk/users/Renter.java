package org.nvk.users;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.nvk.network.Mapper;
//import org.nvk.network.WorkerCollection;
import org.nvk.structures.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Renter extends Client {


    public Renter(Profile profile) {
        super(profile);
    }

    public void searchBy(SearchTraits traits)  {

        SearchRoomRequest request = new SearchRoomRequest(profile.getUsername(), traits.getArea(), traits.getDateFrom(), traits.getDateTo(), traits.getRoomcap(), traits.getPrice(), traits.getStars());

        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            processRequest(json);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String rateRoom(int room_id,Integer stars){
        RateRoomRequest request=new RateRoomRequest(profile.getUsername(),room_id,stars);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json  = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    public String rentRoom(int room_id,String renter_username,String dateFrom,String dateTo){
        RentRoomRequest request=new RentRoomRequest(room_id,renter_username, dateFrom,dateTo);
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json  = objectMapper.writeValueAsString(request);

            System.out.println(" * SENT:");
            System.out.println(json);

            String result = processRequest(json);

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public String viewRentals(){
        ViewRentalsRequest request = new ViewRentalsRequest(profile.getUsername());
        try {
            ObjectMapper objectMapper = Mapper.createObjectMapper();
            String json = objectMapper.writeValueAsString(request);
            System.out.println(" * SENT:");
            System.out.println(json);

            String result =  processRequest(json);

            return result;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }



}
