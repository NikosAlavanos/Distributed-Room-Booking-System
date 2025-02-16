package org.nvk.network;

import org.nvk.configuration.Config;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class NetworkCommunication {
    public String send(String ip, int port, String json) {
        return send(null, ip, port,json);
    }

    public String send(String nodetype, String ip, int port, String json) {
        Socket requestSocket = null;
        ObjectOutputStream out = null;
        ObjectInputStream in = null;

        try {
            requestSocket = new Socket(ip, port);
            out = new ObjectOutputStream(requestSocket.getOutputStream());
            in = new ObjectInputStream(requestSocket.getInputStream());

            if (nodetype != null) {
                out.writeUTF(nodetype);
            }
            out.writeUTF(json);
            out.flush();

            String response = in.readUTF();

            return response;
        } catch (Exception unknownHost) {
            System.err.println("You are trying to connect to an unknown host!");
        } finally {
            try {
                in.close();
                out.close();
                requestSocket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

        return null;
    }
}
