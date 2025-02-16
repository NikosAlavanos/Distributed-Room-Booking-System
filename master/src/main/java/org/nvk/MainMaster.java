package org.nvk;

import org.nvk.configuration.Config;
import org.nvk.hashing.HashCalculator;

import java.math.BigInteger;
import java.util.UUID;

public class MainMaster {
    public static void main(String[] args) {
        Master master = new Master();

        System.out.println("Starting master at: " + Config.MASTER_PORT);

        try {
            master.openServer(Config.MASTER_PORT);

            master.waitForClients();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}