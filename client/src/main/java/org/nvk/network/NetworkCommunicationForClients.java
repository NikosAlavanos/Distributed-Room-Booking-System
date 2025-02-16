package org.nvk.network;

import org.nvk.configuration.Config;

public class NetworkCommunicationForClients extends  NetworkCommunication{
    public NetworkCommunicationForClients() {
    }

    public String sendToMaster(String json) {
        return send(Config.NODE_TYPE, Config.MASTER_IP, Config.MASTER_PORT, json);
    }
}
