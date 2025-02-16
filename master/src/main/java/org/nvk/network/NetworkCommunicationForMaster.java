package org.nvk.network;

import org.nvk.configuration.Config;

public class NetworkCommunicationForMaster extends  NetworkCommunication{
    public NetworkCommunicationForMaster() {
    }

    public String sendToWorker(String ip, int port, String json) {
        return send(Config.NODE_TYPE, ip, port, json);
    }
}