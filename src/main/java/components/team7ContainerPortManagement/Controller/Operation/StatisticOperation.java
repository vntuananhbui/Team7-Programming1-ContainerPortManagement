package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.displayTripsForDate;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.getSelectedDate;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.tripDateSelector.getAvailableDatesBetweenDates;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.tripDateSelector.listTripsBetweenSelectedDates;

public class StatisticOperation {
    //Operation 1
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
    public static final Pattern PORT_ID_PATTERN = Pattern.compile("port=Port\\{ID='(.*?)'");
    private static final Pattern WEIGHT_PATTERN = Pattern.compile("weight=(.*?),");
    private static final Pattern CONTAINER_TYPE_PATTERN = Pattern.compile("containerType=(.*?),");
    //operation 2
    public static double getTotalTypeContainerWeight(String portID) throws IOException {

        Map<String, Double> totalWeights = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher portMatcher = PORT_ID_PATTERN.matcher(line);
                Matcher weightMatcher = WEIGHT_PATTERN.matcher(line);
                Matcher containerTypeMatcher = CONTAINER_TYPE_PATTERN.matcher(line);

                if (portMatcher.find() && weightMatcher.find() && containerTypeMatcher.find()) {
                    String extractedPortId = portMatcher.group(1);
                    double weight = Double.parseDouble(weightMatcher.group(1));
                    String containerType = containerTypeMatcher.group(1);

                    if (extractedPortId.equals(portID)) {
                        totalWeights.put(containerType, totalWeights.getOrDefault(containerType, 0.0) + weight);
                    }
                }
            }
        }

        // Display container types and their order numbers for the user to choose
        System.out.println("Available Container Types for Port " + portID + ":");
        List<String> containerTypes = new ArrayList<>(totalWeights.keySet());
        for (int i = 0; i < containerTypes.size(); i++) {
            System.out.println((i + 1) + ". " + containerTypes.get(i));
        }

        // Prompt the user to choose a container type by order number
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the order number of the Container Type: ");
        int selectedOrder = Integer.parseInt(scanner.nextLine());

        if (selectedOrder > 0 && selectedOrder <= containerTypes.size()) {
            String selectedContainerType = containerTypes.get(selectedOrder - 1);
            double totalWeight = totalWeights.getOrDefault(selectedContainerType, 0.0);
            System.out.println("Total Weight of " + selectedContainerType + " Containers at Port " + portID + ": " + totalWeight);
            return totalWeight;
        } else {
            System.out.println("Invalid order number.");
            return 0.0;
        }
    }

    //statistic 3
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

        listTripsBetweenSelectedDates(portID, selectedDate, selectedDate);

        displayListTripForDate(portID, selectedDate, arrivalSection, departureSection, arrivalFuelConsumption, departureFuelConsumption);
    }

    private static void displayListTripForDate(String portID, String selectedDate, StringBuilder arrivalSection, StringBuilder departureSection, double arrivalFuelConsumption, double departureFuelConsumption) {

    }

    //statistic 4
    public static void listTripsBetweenDates(String portID) {
        String ANSI_RED = "\u001B[31m";
        String ANSI_RESET = "\u001B[0m";
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

            System.out.println(ANSI_RED+"Invalid end date order number or end date must be after the start date." + ANSI_RESET);
            return;
        }

        String dayB = availableDates.get(endOrder - 1);

        // List trips between selected dates
        listTripsBetweenSelectedDates(portID, dayA, dayB);
        System.out.println("Press any key to return...");
        scanner.next();
    }
}
