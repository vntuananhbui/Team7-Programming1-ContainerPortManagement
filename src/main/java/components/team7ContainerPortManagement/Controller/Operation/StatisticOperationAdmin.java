package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.*;

public class StatisticOperationAdmin {
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

    //statistic2
    public static final Pattern PORT_ID_PATTERN = Pattern.compile("port=Port\\{ID='(.*?)'");
    private static final Pattern WEIGHT_PATTERN = Pattern.compile("weight=(.*?),");
    private static final Pattern CONTAINER_TYPE_PATTERN = Pattern.compile("containerType=(.*?),");



    public static double getTotalTypeContainerWeightAdmin() throws IOException {
        Map<String, Double> totalWeights = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher weightMatcher = WEIGHT_PATTERN.matcher(line);
                Matcher containerTypeMatcher = CONTAINER_TYPE_PATTERN.matcher(line);

                if (weightMatcher.find() && containerTypeMatcher.find()) {
                    double weight = Double.parseDouble(weightMatcher.group(1));
                    String containerType = containerTypeMatcher.group(1);

                    totalWeights.put(containerType, totalWeights.getOrDefault(containerType, 0.0) + weight);
                }
            }
        }

        // Display container types and their order numbers for the user to choose
        System.out.println("Available Container Types:");
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
            System.out.println("Total Weight of " + selectedContainerType + " Containers: " + totalWeight);
            return totalWeight;

        } else {
            System.out.println("Invalid order number.");
            return 0.0;
        }
    }
//Statistic 3
public static void ListTripInDayAdmin() {
    Map<String, List<String>> datesByPort = new HashMap<>();
    double arrivalFuelConsumption = 0.0;
    double departureFuelConsumption = 0.0;

//        StringBuilder arrivalSection = new StringBuilder("Arrival:\n");
//        StringBuilder departureSection = new StringBuilder("Departure:\n");
    StringBuilder arrivalSection = new StringBuilder("\n");
    StringBuilder departureSection = new StringBuilder("\n");

    String selectedDate = selectDate(datesByPort);

    if (selectedDate == null) {
        System.out.println("No valid date selected.");
        return;
    }

    displayListTripsForAdmin(selectedDate, arrivalSection, departureSection, arrivalFuelConsumption, departureFuelConsumption);
}
//statistic 4
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
//                        String date = parse[1].split("T")[0];
                        String timestamp = parse[1];

                        // Split the timestamp string by "T" to separate date and time
                        String[] timestampParts = timestamp.split("T");

                        if (timestampParts.length == 2) {
                            String date = timestampParts[0]; // Date component
                            String timeWithMilliseconds = timestampParts[1]; // Time component with milliseconds

                            // Split the time component by "." to separate hours and milliseconds
                            String[] timeParts = timeWithMilliseconds.split("\\.");
                            String hour = timeParts[0]; // Hour component
                            // Check if the trip falls within the specified date range
                            if (date.compareTo(dayA) >= 0 && date.compareTo(dayB) <= 0) {
                                // Print the trip details in one line
                                System.out.println("Order Number: " + orderNumber +
                                        " | Vehicle ID: " + parse[0] +
                                        " | Date: " + date +
                                        " | Hour: " + hour +
                                        " | Fuel Consumption: " + parse[6] +
                                        " | Arrival Port: " + parse[3] +
                                        " | Departure Port: " + parse[4]);
                            }

                            orderNumber++;

                        }
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
