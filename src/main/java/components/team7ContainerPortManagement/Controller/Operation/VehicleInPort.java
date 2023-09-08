package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VehicleInPort {
    public static void listAllShipInPort(String portID) {
        boolean foundVehicles = false;
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt"))) {
            String line;
            Pattern pattern = Pattern.compile("Vehicle\\{ID='(sh-[^']+)', name='([^']+)', currentFuel=([^,]+), carryingCapacity=([^,]+), fuelCapacity=([^,]+), currentPort=(p-[^']+)}");
            foundVehicles = false;
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String vehicleID = matcher.group(1);
                    String name = matcher.group(2);
                    String currentFuel = matcher.group(3);
                    String carryingCapacity = matcher.group(4);
                    String fuelCapacity = matcher.group(5);
                    String currentPort = matcher.group(6);

                    if (currentPort.equals(portID)) {
                        foundVehicles = true;
                        System.out.println("Vehicle ID: " + vehicleID + " | Name: " + name + " | Current Fuel: " + currentFuel +
                                " | Carrying Capacity: " + carryingCapacity + " | Fuel Capacity: " + fuelCapacity);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!foundVehicles) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("No vehicle in port"); // Print this message if no vehicles were found
            System.out.println("Type any key to return....");
            scanner.nextLine();
            return;
        }
    }
    public static void listAllShipInPortAdmin() {
        // Get the list of available ports
        List<String> availablePorts = getAvailablePorts();
        if (availablePorts.isEmpty()) {
            System.out.println("No ship in ports found!");
            return;
        }

        // Print available ports with order numbers
        System.out.println("Available Ports:");
        for (int i = 0; i < availablePorts.size(); i++) {
            System.out.println((i + 1) + ". " + availablePorts.get(i));
        }

        // Prompt the admin to choose a port by order number
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the order number of the Port to view ships: ");
        int selectedPortOrder = scanner.nextInt();

        if (selectedPortOrder < 1 || selectedPortOrder > availablePorts.size()) {
            System.out.println("Invalid order number.");
            return;
        }

        // Get the selected port ID based on the order number
        String selectedPortID = availablePorts.get(selectedPortOrder - 1);

        // List ships in the selected port
        listAllShipInPort(selectedPortID);
    }
    private static List<String> getAvailablePorts() {
        List<String> availablePorts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt"))) {
            String line;
            Pattern pattern = Pattern.compile("Vehicle\\{ID='sh-[^']+', name='[^']+', currentFuel=[^,]+, carryingCapacity=[^,]+, fuelCapacity=[^,]+, currentPort=(p-[^']+)}");

            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String currentPort = matcher.group(1);

                    if (!availablePorts.contains(currentPort)) {
                        availablePorts.add(currentPort);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return availablePorts;
    }
}
