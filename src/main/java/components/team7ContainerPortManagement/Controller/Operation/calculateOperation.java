package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;

import static java.lang.Math.round;
import static src.main.java.components.team7ContainerPortManagement.Controller.containerController.*;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getVehiclesByPortID;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle.getVehicleByVehicleID;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readAvailablePortsFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortContainerMapFromFile;

public class calculateOperation {
    public calculateOperation() throws FileNotFoundException {
    }

    public static double calculateFuelConsumption(Port currentPort, Port selectedPort, String selectedVehicleID, Vehicle selectedVehicle) throws IOException {

        double fuelConsumption;
//        String selectedVehicleID = selectedVehicle.getID();
        if (selectedVehicleID.startsWith("sh-")) {
//            System.out.println("Selected Vehicle: " + selectedVehicle.getID());
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            double distancePort = currentPort.calculateDistanceTo(selectedPort);
            List<String> containerIDs = vehicleContainerMap.get(selectedVehicle.getID());
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            if (containerIDs == null || containerIDs.isEmpty()) {
                fuelConsumption = 3.5;
                return fuelConsumption * distancePort;
            }
//            System.out.println("container in vehiclecontainermap: " + containerIDs);

//            System.out.println("All container " + containers);
//            System.out.println("Distance from: " + currentPort.getID() + " to " + selectedPort.getID() + " is: " + distancePort);
            double finalConsumption = getFinalShipConsumption(containerIDs);

//            System.out.println("Consumption: " + finalConsumption);
            fuelConsumption = round(finalConsumption * distancePort);
//            System.out.println("Fuel Consumption: " + fuelConsumption);
        } else {
//            System.out.println("Selected Vehicle: " + selectedVehicle.getID());
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            double distancePort = currentPort.calculateDistanceTo(selectedPort);
            List<String> containerIDs = vehicleContainerMap.get(selectedVehicle.getID());
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            if (containerIDs == null || containerIDs.isEmpty()) {
                fuelConsumption = 3.5;
                return fuelConsumption * distancePort;
            }
//            System.out.println("container in vehiclecontainermap: " + containerIDs);

//            System.out.println("All container " + containers);
//            System.out.println("Distance from: " + currentPort.getID() + " to " + selectedPort.getID() + " is: " + distancePort);

            double finalConsumption = getFinalTruckConsumption(containerIDs);

//            System.out.println("Consumption: " + finalConsumption);
            fuelConsumption = round(finalConsumption * distancePort);
//            System.out.println("Fuel Consumption: " + fuelConsumption);

        }
        return fuelConsumption;
    }

    public static double calculateTotalWeightContainerPort(Port currentPort) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        double totalWeightContainerInPort = 0;
//        List<String> availableVehicleIDs = getVehiclesByPortID(currentPort.getID());
        Map<String, List<String>> portContainerMap = readPortContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt");
//        System.out.println("Debug portContainermap: "+portContainerMap);
        if (portContainerMap.containsKey(currentPort.getID())) {
            List<String> containerList = portContainerMap.get(currentPort.getID());
            System.out.println(ANSI_CYAN + "╔══════════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                    Total Container Weight" + "                    ║");
            System.out.println("╟──────────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println("   Total Container: "+containerList);
            for (String container : containerList) {

                Container getContainer = getContainerByID(container);
                totalWeightContainerInPort += getContainer.getWeight();
            }
        }
        //        System.out.println("Available vehicle in port: " + availableVehicleIDs);
//        for (String vehicle : availableVehicleIDs) {
//            Vehicle selectedVehicle = getVehicleByVehicleID(vehicle);
//
//            List<String> containerIDs = vehicleContainerMap.get(selectedVehicle.getID());
//            if (containerIDs == null || containerIDs.isEmpty()) {
//                totalWeightContainerInPort += 0;
//            } else {
//                for (String container : containerIDs) {
//                    Container ObjectContainerList = getContainerByID(container);
//                    totalWeightContainerInPort += ObjectContainerList.getWeight();
//                }
//            }
//        }
        System.out.println("   Total weight: " + totalWeightContainerInPort);
        System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
        System.out.println(ANSI_CYAN + "╚══════════════════════════════════════════════════════════════╝" + ANSI_RESET);
        return totalWeightContainerInPort;
    }







    static String getSelectedDate(String portID, Map<String, List<String>> datesByPort) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/TripData/trip.txt"))) {
            String line;
            int orderNumber = 1;

            while ((line = reader.readLine()) != null) {
                String[] parse = line.split(", ");

                if (parse.length >= 7) {
                    String arrivalPortID = parse[3];
                    String departurePortID = parse[4];

                    if (arrivalPortID.equals(portID) || departurePortID.equals(portID)) {
                        String date = parse[1].split("T")[0];

                        datesByPort.computeIfAbsent(portID, k -> new ArrayList<>());

                        if (!datesByPort.get(portID).contains(date)) {
                            datesByPort.get(portID).add(date);
                        }

                        orderNumber++;
                    }
                }
            }

            List<String> availableDates = datesByPort.get(portID);

            System.out.println("Available Dates for Port " + portID + ":");
            for (int i = 0; i < availableDates.size(); i++) {
                System.out.println((i + 1) + ". " + availableDates.get(i));
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the order number of the date to view trips: ");
            int selectedOrder = scanner.nextInt();

            if (selectedOrder > 0 && selectedOrder <= availableDates.size()) {
                return availableDates.get(selectedOrder - 1);
            } else {
                System.out.println("Invalid order number.");
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void displayTripsForDate(String portID, String selectedDate, StringBuilder arrivalSection,
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
//                        StringBuilder section = (arrivalPortID.equals(portID)) ? arrivalSection : departureSection;
                        StringBuilder section = null;
                        if (arrivalPortID.equals(portID)) {
                             section = arrivalSection;
                            section.append(" Vehicle ID: ").append(parse[0])
                                    .append(" | Date: ").append(date)
                                    .append(" | Fuel Consumption: ").append(parse[6]).append("\n");
                        } else if(departurePortID.equals(portID)) {
                             section = departureSection;
                            section.append(" Vehicle ID: ").append(parse[0])
                                    .append(" | Date: ").append(date)
                                    .append(" | Fuel Consumption: ").append(parse[6]).append("\n");
                        }

                        if (arrivalPortID.equals(portID)) {
                            arrivalFuelConsumption += Double.parseDouble(parse[6]);
                        } if(departurePortID.equals(portID)) {
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

            double totalFuelConsumption = arrivalFuelConsumption + departureFuelConsumption;
            System.out.println("Total Fuel Consumption for " + selectedDate + ": " + totalFuelConsumption);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    static String selectDate(Map<String, List<String>> datesByPort) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/TripData/trip.txt"))) {
            String line;
            int orderNumber = 1;
            List<String> allDates = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String[] parse = line.split(", ");

                if (parse.length >= 7) {
                    String date = parse[1].split("T")[0];

                    if (!allDates.contains(date)) {
                        allDates.add(date);
                    }

                    orderNumber++;
                }
            }

            System.out.println("Available Dates:");
            for (int i = 0; i < allDates.size(); i++) {
                System.out.println((i + 1) + ". " + allDates.get(i));
            }

            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter the order number of the date to view trips: ");
            int selectedDateOrder = scanner.nextInt();

            if (selectedDateOrder > 0 && selectedDateOrder <= allDates.size()) {
                return allDates.get(selectedDateOrder - 1);
            } else {
                System.out.println("Invalid order number.");
                return null;
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static void displayTripsForAdmin(String selectedDate, StringBuilder arrivalSection,
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

            double totalFuelConsumption = arrivalFuelConsumption + departureFuelConsumption;
            System.out.println("Total Fuel Consumption for " + selectedDate + ": " + totalFuelConsumption);

        } catch (IOException e) {
            e.printStackTrace();
        }
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
//                    String date = parse[1].split("T")[0];
                    String timestamp = parse[1];

                    // Split the timestamp string by "T" to separate date and time
                    String[] timestampParts = timestamp.split("T");

                    if (timestampParts.length == 2) {
                        String date = timestampParts[0]; // Date component
                        String timeWithMilliseconds = timestampParts[1]; // Time component with milliseconds

                        // Split the time component by "." to separate hours and milliseconds
                        String[] timeParts = timeWithMilliseconds.split("\\.");
                        String hour = timeParts[0]; // Hour component

                        //----
                        if (date.equals(selectedDate)) {
//                            StringBuilder section = (arrivalPortID.equals(portID)) ? arrivalSection : departureSection;
                            StringBuilder section = null;
                            if (arrivalPortID.equals(portID)) {
                                section = arrivalSection;
                                section.append(" Vehicle ID: ").append(parse[0])
                                        .append(" | Date: ").append(date)
                                        .append(" | Hour: ").append(hour)
                                        .append(" | Arrival Port: ").append(arrivalPortID)
                                        .append(" | Departure Port: ").append(departurePortID)
                                        .append(" | Fuel Consumption: ").append(parse[6]).append("\n");
                            } else if(departurePortID.equals(portID)) {
                                section = departureSection;
                                section.append(" Vehicle ID: ").append(parse[0])
                                        .append(" | Date: ").append(date)
                                        .append(" | Hour: ").append(hour)
                                        .append(" | Arrival Port: ").append(arrivalPortID)
                                        .append(" | Departure Port: ").append(departurePortID)
                                        .append(" | Fuel Consumption: ").append(parse[6]).append("\n");
                            }
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
            System.out.println("Press any key to return...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
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
                    String timestamp = parse[1];

                    // Split the timestamp string by "T" to separate date and time
                    String[] timestampParts = timestamp.split("T");

                    if (timestampParts.length == 2) {
                        String date = timestampParts[0]; // Date component
                        String timeWithMilliseconds = timestampParts[1]; // Time component with milliseconds

                        // Split the time component by "." to separate hours and milliseconds
                        String[] timeParts = timeWithMilliseconds.split("\\.");
                        String hour = timeParts[0]; // Hour component

                        if (date.equals(selectedDate)) {
                            String arrivalPortID = parse[3];
                            String departurePortID = parse[4];

                            StringBuilder section = (arrivalPortID.equals(departurePortID)) ? arrivalSection : departureSection;
                            section.append(" Vehicle ID: ").append(parse[0])
                                    .append(" | Date: ").append(date)
                                    .append(" | Hour: ").append(hour)
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
            }
            if (departureSection.toString().isEmpty()) {
                System.out.println("Departure:\nNo trip");
            } else {
                System.out.println(departureSection.toString());
            }
            System.out.println(arrivalSection.toString());
            System.out.println("Press any key to return...");
            Scanner scanner = new Scanner(System.in);
            scanner.nextLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}//END
