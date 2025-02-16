package org.nvk.users;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.nvk.configuration.Config;
import org.nvk.network.NetworkCommunication;
import org.nvk.network.NetworkCommunicationForClients;
import org.nvk.structures.Profile;


public abstract class Client {
    public Profile profile;
    private NetworkCommunicationForClients communication = new NetworkCommunicationForClients();


    public Client(Profile profile) {
        this.profile = profile;

    }

    protected void processRequest(String json) {
        String result = null;

        if (Config.NETWORK_MODE) {
            result = communication.sendToMaster(json);
        }


        System.out.println(" * RECEIVE: ");
        System.out.println(result);

        if (result.startsWith("ERROR")) {
            System.out.println("Request failed from server");
        } else {
            System.out.println("Success");
        }
    }
}
