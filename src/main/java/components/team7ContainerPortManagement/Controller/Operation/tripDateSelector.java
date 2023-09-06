package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class tripDateSelector {


    static List<String> getAvailableDatesBetweenDates(String portID) {
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

    static void listTripsBetweenSelectedDates(String portID, String dayA, String dayB) {
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



}
