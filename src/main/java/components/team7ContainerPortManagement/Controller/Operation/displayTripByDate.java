package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.*;

public class displayTripByDate {
    public static void listTripsInDay(String portID) {
        Map<String, List<String>> datesByPort = new HashMap<>();
        double arrivalFuelConsumption = 0.0;
        double departureFuelConsumption = 0.0;
        StringBuilder arrivalSection = new StringBuilder("Arrival:\n");
        StringBuilder departureSection = new StringBuilder("Departure:\n");

        String selectedDate = getSelectedDate(portID, datesByPort);

        if (selectedDate == null) {
            System.out.println("No valid date selected.");
            return;
        }

        displayListTripForDate(portID, selectedDate, arrivalSection, departureSection, arrivalFuelConsumption, departureFuelConsumption);
    }
    public static void ListTripInDayAdmin() {
        Map<String, List<String>> datesByPort = new HashMap<>();
        double arrivalFuelConsumption = 0.0;
        double departureFuelConsumption = 0.0;

        StringBuilder arrivalSection = new StringBuilder("Arrival:\n");
        StringBuilder departureSection = new StringBuilder("Departure:\n");

        String selectedDate = selectDate(datesByPort);

        if (selectedDate == null) {
            System.out.println("No valid date selected.");
            return;
        }

        displayListTripsForAdmin(selectedDate, arrivalSection, departureSection, arrivalFuelConsumption, departureFuelConsumption);
    }


    static void displayListTripForDate(String portID, String selectedDate, StringBuilder arrivalSection,
                                       StringBuilder departureSection, double arrivalFuelConsumption, double departureFuelConsumption) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/TripData/trip.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parse = line.split(", ");

                if (parse.length >= 7) {
                    String arrivalPortID = parse[3];
                    String departurePortID = parse[4];
                    String date = parse[1].split("T")[0];

                    if (date.equals(selectedDate)) {
                        StringBuilder section = (arrivalPortID.equals(portID)) ? arrivalSection : departureSection;
                        section.append(" Vehicle ID: ").append(parse[0])
                                .append(" | Date: ").append(date)
                                .append(" | Arrival Port: ").append(arrivalPortID)
                                .append(" | Departure Port: ").append(departurePortID)
                                .append(" | Fuel Consumption: ").append(parse[6]).append("\n");


                    }
                }
            }

            System.out.println(arrivalSection.toString());

            if (departureSection.toString().isEmpty()) {
                System.out.println("Departure:\nNo trip");
            } else {
                System.out.println(departureSection.toString());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    static void displayListTripsForAdmin(String selectedDate, StringBuilder arrivalSection,
                                     StringBuilder departureSection, double arrivalFuelConsumption, double departureFuelConsumption) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/TripData/trip.txt"))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parse = line.split(", ");

                if (parse.length >= 7) {
                    String date = parse[1].split("T")[0];

                    if (date.equals(selectedDate)) {
                        String arrivalPortID = parse[3];
                        String departurePortID = parse[4];

                        StringBuilder section = (arrivalPortID.equals(departurePortID)) ? arrivalSection : departureSection;
                        section.append(" Vehicle ID: ").append(parse[0])
                                .append(" | Date: ").append(date)
                                .append(" | Arrival Port: ").append(arrivalPortID)
                                .append(" | Departure Port: ").append(departurePortID)
                                .append(" | Fuel Consumption: ").append(parse[6]).append("\n");

                        if (arrivalPortID.equals("p-StartPort")) {
                            arrivalFuelConsumption += Double.parseDouble(parse[6]);
                        } else {
                            departureFuelConsumption += Double.parseDouble(parse[6]);
                        }
                    }
                }
            }

            System.out.println(arrivalSection.toString());

            if (departureSection.toString().isEmpty()) {
                System.out.println("Departure:\nNo trip");
            } else {
                System.out.println(departureSection.toString());
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
