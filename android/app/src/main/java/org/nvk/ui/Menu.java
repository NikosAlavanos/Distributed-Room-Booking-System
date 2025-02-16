package org.nvk.ui;

import org.apache.commons.lang3.StringUtils;
import org.nvk.users.Manager;
import org.nvk.structures.*;
import org.nvk.users.Renter;


import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    private Scanner scanner = new Scanner(System.in);
    private Profile profile;
    private Manager manager;
    private Renter renter;


    public Menu(Profile profile) {
        this.profile = profile;

        renter = new Renter(profile);
        manager = new Manager(profile);
    }

    private String askUserForString(String question) {
        System.out.print(question + ": ");
        return scanner.nextLine();
    }

    private Integer askUserForInteger(String question) {
        while (true) {
            try {
                System.out.print(question + ": ");
                String answer = scanner.nextLine();
                return Integer.parseInt(answer);
            } catch (Exception ex) {
                System.err.println("Please type a valid integer");
            }
        }
    }


    public void print() {
        System.out.println("==========================================================");
        System.out.println("                        Main  menu");
        System.out.println("==========================================================");

        if (profile.isOwner()) {
            System.out.println("  Manager choices: ");
            System.out.println("    1. Insert a room");
            System.out.println("    2. Define dates for a room");
            System.out.println("    3. List my rooms");
            System.out.println("    4. List my bookings for specific room ID");
            System.out.println("    5. List room by room ID");
            System.out.println("    6. Show statistics for your rooms");
            System.out.println("    7. Show statistics for all rooms");
            System.out.println("    8. Search");
            System.out.println("    13. Show statistics for bookings in date span");

            System.out.println("  Renter choices: ");
            System.out.println("    9. Search");
            System.out.println("    10. Rent a room");
            System.out.println("    11. Rate a room");
            System.out.println("    12. View rentals");
        }

        if (profile.isRenter()) {
            System.out.println("  Renter choices: ");
            System.out.println("    1. Search");
            System.out.println("    2. Rent a room");
            System.out.println("    3. Rate a room");
            System.out.println("    4. View rentals");
        }
    }

    public int getChoice() {
        while (true) {
            try {
                System.out.print(StringUtils.capitalize(profile.username) + ", Type your choice:");
                String s = scanner.nextLine();
                int x = Integer.parseInt(s);
                return x;
            } catch (Exception ex) {
                System.err.println("Please type a valid integer");

            }
        }
    }

    //
    // Manager choices
    //

    public void insertRoom() {
        String roomname = askUserForString("The room name");
        String area = askUserForString("Type the area");
        Integer persons = askUserForInteger("Type the number of persons");
        Integer price = askUserForInteger("Type price");
        manager.insertRoom(roomname, area, persons, price, 0);
    }


    public void listBookings() {
        System.out.print("Type the room id:");

        String answer = scanner.nextLine();
        int id = Integer.parseInt(answer);
        manager.listBookings(id);
    }

    public void findRoomByID() {
        System.out.print("Type the room id:");

        String answer = scanner.nextLine();
        int id = Integer.parseInt(answer);
        manager.findRoomByID(id);
    }


    public void listRooms() {
        manager.listRooms();
    }

    public void defineRoomDates() {
        System.out.print("Type the room id:");

        String answer = scanner.nextLine();
        int id = Integer.parseInt(answer);

        System.out.print("Type the date from: ");
        String dateFrom = scanner.nextLine();

        System.out.print("Type the date to: ");
        String dateTo = scanner.nextLine();

        manager.setDatesForRoom(id, dateFrom, dateTo);
    }
    public void  showStatisticsForRooms(){
        manager.showStatisticsForRooms();
    }

    public void  showStatistics(){
        manager.showStatistics();
    }

    //
    // Renter choices
    //except search (both manager and renter)

    public void search() {
        System.out.println("    0. End of entering criteria");
        System.out.println("    1. By area");
        System.out.println("    2. By date");
        System.out.println("    3. By room capacity");
        System.out.println("    4. By price");
        System.out.println("    5. By stars");


        try {
            ArrayList<Integer> chosenCriteria = new ArrayList<>();
            SearchTraits traits = new SearchTraits();
            while (true) {
                System.out.print("Enter a number between 0 and 5: ");
                Integer choice = scanner.nextInt();
                if (choice >= 0 && choice <= 5) {
                    scanner.nextLine();

                    if (choice == 0) {
                        break;

                    }

                    if (chosenCriteria.contains(choice)) {
                        System.out.println("You have already chosen this criteria. Please choose another choice.");
                        continue;
                    }
                    chosenCriteria.add(choice);
                    switch (choice) {
                        case 1:
                            String areaName = askUserForString("Type the area");
                            traits.setArea(areaName);
                            break;
                        case 2:
                            System.out.print("Type the date from: ");
                            String dateFrom = scanner.nextLine();
                            System.out.print("Type the date to: ");
                            String dateTo = scanner.nextLine();
                            traits.setDateTo(dateTo);
                            traits.setDateFrom(dateFrom);
                            break;

                        case 3:
                            Integer roomcap = askUserForInteger("Type the room capacity");
                            traits.setRoomcap(roomcap);
                            break;

                        case 4:
                            Integer price = askUserForInteger("Type the price");
                            traits.setPrice(price);
                            break;
                        case 5:
                            Integer stars = askUserForInteger("Type the stars");
                            traits.setStars(stars);
                            break;

                    }


                } else {
                    System.out.println("Number must be between 0 and 5.");
                }
            }
            for(int i=1;i<=5;i++){
                if (!chosenCriteria.contains(i)){
                    switch(i){
                        case 1:
                            traits.setArea(null);
                            break;
                        case 2:
                            traits.setDateFrom(null);
                            traits.setDateTo(null);
                            break;
                        case 3:
                            traits.setRoomcap(null);
                            break;
                        case 4:
                            traits.setPrice(null);
                            break;
                        case 5:
                            traits.setStars(null);
                            break;
                    }
                }
            }
            if (profile.isOwner()){
                manager.searchBy(traits);
            }else{
                renter.searchBy(traits);
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a valid number between 0 and 5.");
            scanner.nextLine();
        }

    }

    public void rateRoom() {
        System.out.print("Type the room id:");

        String answer = scanner.nextLine();
        int id = Integer.parseInt(answer);
        Integer stars = askUserForInteger("Type the stars for the room");
        renter.rateRoom(id, stars);
    }

    public void rentRoom() {//Menu.java
        System.out.print("Type the room id:");

        String answer = scanner.nextLine();
        int id = Integer.parseInt(answer);


        System.out.print("Type the date from: ");
        String dateFrom = scanner.nextLine();

        System.out.print("Type the date to: ");
        String dateTo = scanner.nextLine();

        renter.rentRoom(id, profile.getUsername(), dateFrom, dateTo);
    }

    public void viewRentals() {
        renter.viewRentals();
    }

    public void showStatisticsForBookings() {
        System.out.print("Type the date from: ");
        String dateFrom = scanner.nextLine();

        System.out.print("Type the date to: ");
        String dateTo = scanner.nextLine();

        manager.showStatisticsForBookings(dateFrom, dateTo);
    }
}
