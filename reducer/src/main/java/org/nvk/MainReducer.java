package org.nvk;

import org.nvk.configuration.Config;

public class MainReducer {
    public static void main(String[] args) {
        Reducer reducer = new Reducer();

        System.out.println("Starting reducer at: " + Config.REDUCER_PORT);

        try {
            reducer.openServer(Config.REDUCER_PORT);

            reducer.waitForClients();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}