package org.nvk.network;

import org.nvk.configuration.Config;

public class NetworkCommunicationForWorker extends  NetworkCommunication{
    public NetworkCommunicationForWorker() {
    }

    public String sendToReducer(String json) {
        return send(Config.NODE_TYPE, Config.REDUCER_IP, Config.REDUCER_PORT, json);
    }
}
