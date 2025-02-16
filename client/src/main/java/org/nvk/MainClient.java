package org.nvk;


import org.nvk.ui.Menu;
import org.nvk.structures.Profile;
//import org.nvk.worker.Worker;

import java.util.Scanner;

public class MainClient {
    public static void main(String[] args) {
        String username;



        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Please login to the system by typing your username?");
            username = scanner.nextLine();
        } else {
            username = args[0];
        }

        boolean isOwner = username.equalsIgnoreCase("bob") || username.equalsIgnoreCase("eva");
        Profile profile = new Profile(username, isOwner);

        Menu menu = new Menu(profile);

        while (true) {
            menu.print();

            int x = menu.getChoice();

            if (profile.isOwner()) {
                switch (x) {
                    case 1:
                        menu.insertRoom();
                        break;
                    case 2:
                        menu.defineRoomDates();
                        break;
                    case 3:
                        menu.listRooms();
                        break;
                    case 4:
                        menu.listBookings();
                        break;
                    case 5:
                        menu.findRoomByID();
                        break;
                    case 6:
                        menu.showStatistics();
                        break;
                    case 7:
                        menu.showStatisticsForRooms();
                        break;
                    case 8:
                        menu.search();
                        break;
                    case 9:
                        menu.search();
                        break;
                    case 10:
                        menu.rentRoom();
                        break;
                    case 11:
                        menu.rateRoom();
                        break;
                    case 12:
                        menu.viewRentals();
                        break;
                    case 13:
                        menu.showStatisticsForBookings();
                        break;
                }
            }

            if (profile.isRenter()) {
                switch (x) {
                    case 1:
                        menu.search();
                        break;
                    case 2:
                        menu.rentRoom();
                        break;
                    case 3:
                        menu.rateRoom();
                        break;
                    case 4:
                        menu.viewRentals();
                        break;
                }
            }
        }
    }
}