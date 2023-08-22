package src.main.java.components.team7ContainerPortManagement.views;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputControl {

    //Create port
//    public static void inputPort() throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        FileWriter portWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt", true);
//
//        // Collect input values
//        System.out.println("Enter port ID:");
//        String portID = "p-" + scanner.next();
//        scanner.nextLine();
//        System.out.println("Enter port name:");
//        String portName = scanner.nextLine();
//        System.out.println("Enter latitude:");
//        double latitude = scanner.nextDouble();
//        scanner.nextLine();
//        System.out.println("Enter longitude:");
//        double longitude = scanner.nextDouble();
//        scanner.nextLine();
//        System.out.println("Enter storing capacity:");
//        int capacity = scanner.nextInt();
//        scanner.nextLine();
//        System.out.println("Enter landing ability (true or false):");
//        boolean landingAbility = scanner.nextBoolean();
//
//        // Create an instance of Port using the input values
//        Port newPort = new Port(portID, portName, latitude, longitude, capacity, landingAbility);
//
//        // Write the input values to the file
//        portWriter.write(newPort.toString() + "\n");
//
//        portWriter.close();
//    }
    //Create ship
//    public static void createShip(Port selectedPort) throws IOException {
//        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt", true);
//        Scanner scanner = new Scanner(System.in);
//        // Collect input values
//        System.out.println("Enter ship ID:");
//        String shipID = "sh-" + scanner.next();
//        scanner.nextLine();
//        System.out.println("Enter ship name:");
//        String shipName = scanner.nextLine();
//
//        System.out.println("Enter ship carrying capacity:");
//        double carryingCapacity = scanner.nextDouble();
//        scanner.nextLine();
//
//        System.out.println("Enter ship fuel capacity:");
//        double fuelCapacity = scanner.nextDouble();
//        scanner.nextLine();
//        double currentFuel = fuelCapacity;
//        if (selectedPort != null) {
//            Ship newShip = new Ship(shipID, shipName, currentFuel, carryingCapacity, fuelCapacity, 3.5,selectedPort);
//            selectedPort.addVehicle(newShip);
//            newShip.setCurrentPort(selectedPort);
//            shipWriter.write(newShip.toString() + ", Port=" + selectedPort.toString() + "\n");
//            writeVehicleToPort(selectedPort, newShip);
//            System.out.println("Ship created and added to selected port.");
//        } else {
//            System.out.println("Invalid port choice.");
//        }
//
//        shipWriter.close();
//    }
//
//    //Write vehicle to port
//    public static void writeVehicleToPort(Port port, Vehicle vehicle) throws IOException {
//        File file = new File("src/main/java/components/team7ContainerPortManagement/models/utils/port_vehicles.txt");
//        List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));
//
//        boolean found = false;
//        for (int i = 0; i < lines.size(); i++) {
//            String line = lines.get(i);
//            if (line.startsWith("{Port :" + port.getID())) {
//                line = line.substring(0, line.lastIndexOf('}')) + ", " + vehicle.getID() + "}";
//                lines.set(i, line);
//                found = true;
//                break;
//            }
//        }
//
//        if (!found) {
//            lines.add("{Port :" + port.getID() + ", Vehicles: " + vehicle.getID() + "}");
//        }
//
//        Files.write(file.toPath(), lines);
//    }



    //Display all port
//    public static void displayAllPorts() throws IOException {
//        // Path to the ports file
//        String filePath = "src/main/java/components/team7ContainerPortManagement/models/utils/port.txt";
//
//        // Try-with-resources to automatically close BufferedReader
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            System.out.println("Current Ports:\n------------");
//            int orderNumber = 1;
//
//            while ((line = reader.readLine()) != null) {
//                // Extract port ID and name from the line using regex
//                String portID = extractDataFromLine(line, "ID='(.*?)'");
//                String portName = extractDataFromLine(line, "name='(.*?)'");
//
//                if (portID != null && portName != null) {
//                    System.out.printf("%d. Port ID: '%s' , Port Name: '%s'\n", orderNumber++, portID, portName);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Port file not found.");
//            e.printStackTrace();
//        }
//    }
//    //Belong to displayAllPort
//    private static String extractDataFromLine(String line, String patternStr) {
//        Pattern pattern = Pattern.compile(patternStr);
//        Matcher matcher = pattern.matcher(line);
//
//        if (matcher.find()) {
//            return matcher.group(1);
//        }
//
//        return null;
//    }





}
