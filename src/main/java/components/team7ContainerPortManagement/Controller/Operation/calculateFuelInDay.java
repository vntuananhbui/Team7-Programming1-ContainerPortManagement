package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.*;

public class calculateFuelInDay {
    public static void calculateFuelInDay(String portID) {
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

        displayTripsForDate(portID, selectedDate, arrivalSection, departureSection, arrivalFuelConsumption, departureFuelConsumption);
        System.out.println("Press any key to return...");
        Scanner scanner = new Scanner(System.in);
        scanner.next();
    }
    public static void calculateFuelInDayAdmin() {
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

        displayTripsForAdmin(selectedDate, arrivalSection, departureSection, arrivalFuelConsumption, departureFuelConsumption);
    }

}
