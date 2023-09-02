package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class tripDateSelector {
    public static void listTripsBetweenDates(String portID) {
        Scanner scanner = new Scanner(System.in);

        // Get available dates
        List<String> availableDates = getAvailableDatesBetweenDates(portID);

        if (availableDates.isEmpty()) {
            System.out.println("No valid dates found in the trip file.");
            return;
        }

        // Display available dates
        System.out.println("Available Dates:");
        for (int i = 0; i < availableDates.size(); i++) {
            System.out.println((i + 1) + ". " + availableDates.get(i));
        }

        // Prompt the user to choose the start date
        System.out.print("Enter the order number of the start date: ");
        int startOrder = scanner.nextInt();

        if (startOrder < 1 || startOrder > availableDates.size()) {
            System.out.println("Invalid start date order number.");
            return;
        }

        String dayA = availableDates.get(startOrder - 1);

        // Prompt the user to choose the end date
        System.out.print("Enter the order number of the end date: ");
        int endOrder = scanner.nextInt();

        if (endOrder < 1 || endOrder > availableDates.size() || endOrder < startOrder) {
            System.out.println("Invalid end date order number or end date must be after the start date.");
            return;
        }

        String dayB = availableDates.get(endOrder - 1);

        // List trips between selected dates
        listTripsBetweenSelectedDates(portID, dayA, dayB);
    }

    private static List<String> getAvailableDatesBetweenDates(String portID) {
        List<String> availableDates = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/TripData/trip.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parse = line.split(", ");

                if (parse.length >= 7) {
                    String arrivalPortID = parse[3];
                    String departurePortID = parse[4];
                    String date = parse[1].split("T")[0];

                    if ((arrivalPortID.equals(portID) || departurePortID.equals(portID)) &&
                            !availableDates.contains(date)) {
                        availableDates.add(date);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(availableDates); // Sort dates in ascending order
        return availableDates;
    }

    private static void listTripsBetweenSelectedDates(String portID, String dayA, String dayB) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/TripData/trip.txt"))) {
            String line;
            int orderNumber = 1;

            while ((line = reader.readLine()) != null) {
                String[] parse = line.split(", ");

                if (parse.length >= 7) {
                    String arrivalPortID = parse[3];
                    String departurePortID = parse[4];
                    String date = parse[1].split("T")[0];

                    // Check if the trip falls within the specified date range
                    if (date.compareTo(dayA) >= 0 && date.compareTo(dayB) <= 0 &&
                            (arrivalPortID.equals(portID) || departurePortID.equals(portID))) {
                        // Print the trip details
                        System.out.println("Vehicle ID: " + parse[0] +
                                " | Date: " + date +
                                " | Fuel Consumption: " + parse[6] +
                                " | Arrival Port: " + arrivalPortID +
                                " | Departure Port: " + departurePortID);
                    }

                    orderNumber++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void listTripsBetweenDatesAdmin() {
        Scanner scanner = new Scanner(System.in);

        // Create a list to store available dates
        List<String> availableDates = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/TripData/trip.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parse = line.split(", ");

                if (parse.length >= 7) {
                    String date = parse[1].split("T")[0];

                    // Check if the date is not already in the list, then add it
                    if (!availableDates.contains(date)) {
                        availableDates.add(date);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Display available dates with order numbers
        System.out.println("Available Dates:");
        for (int i = 0; i < availableDates.size(); i++) {
            System.out.println((i + 1) + ". " + availableDates.get(i));
        }

        // Prompt the admin to choose day A and day B by order number
        System.out.print("Enter the order number of day A: ");
        int dayAOrder = scanner.nextInt();
        System.out.print("Enter the order number of day B: ");
        int dayBOrder = scanner.nextInt();

        // Retrieve the selected dates based on the order numbers

        if (dayAOrder > 0 && dayAOrder <= availableDates.size() && dayBOrder > 0 && dayBOrder <= availableDates.size()) {
            String dayA = availableDates.get(dayAOrder - 1);
            String dayB = availableDates.get(dayBOrder - 1);
            // Check if day A is not after day B
            if (dayA.compareTo(dayB) <= 0) {
            try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/TripData/trip.txt"))) {
                String line;
                int orderNumber = 1;

                while ((line = reader.readLine()) != null) {
                    String[] parse = line.split(", ");

                    if (parse.length >= 7) {
                        String date = parse[1].split("T")[0];

                        // Check if the trip falls within the specified date range
                        if (date.compareTo(dayA) >= 0 && date.compareTo(dayB) <= 0) {
                            // Print the trip details in one line
                            System.out.println("Order Number: " + orderNumber +
                                    " | Vehicle ID: " + parse[0] +
                                    " | Date: " + date +
                                    " | Fuel Consumption: " + parse[6] +
                                    " | Arrival Port: " + parse[3] +
                                    " | Departure Port: " + parse[4]);
                        }

                        orderNumber++;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
                System.out.println("Day A cannot be after Day B.");
            }
        }
    }

}
