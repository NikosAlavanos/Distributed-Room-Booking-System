package org.nvk;

import org.nvk.multithreading.ActionForClients;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Reducer {
    private ServerSocket providerSocket;

    public void openServer(int port) throws IOException {
        providerSocket = new ServerSocket(port);
    }

    public void waitForClients() throws IOException {
        while (true) {
            System.out.println("[Reducer thread] Waiting for a worker to connect ..." );

            Socket connection = providerSocket.accept();

            System.out.println("[Reducer thread] Client connected. ");

            Thread t = new ActionForClients(connection);
            t.start();
        }
    }
}
