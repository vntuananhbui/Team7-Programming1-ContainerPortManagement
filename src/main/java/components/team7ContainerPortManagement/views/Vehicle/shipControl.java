package src.main.java.components.team7ContainerPortManagement.views.Vehicle;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle.extractValue;
import static src.main.java.components.team7ContainerPortManagement.views.Containers.InputContainer.getPortByID;
import static src.main.java.components.team7ContainerPortManagement.views.Ports.portControl.getPortByOrderNumber;

public class shipControl {
    public static void createShip(Port selectedPort) throws IOException {
        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt", true);
        Scanner scanner = new Scanner(System.in);
        // Collect input values
        System.out.println("Enter ship ID:");
        String shipID = "sh-" + scanner.next();
        scanner.nextLine();
        System.out.println("Enter ship name:");
        String shipName = scanner.nextLine();

        System.out.println("Enter ship carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter ship fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        scanner.nextLine();
        double currentFuel = fuelCapacity;
        if (selectedPort != null) {
            Ship newShip = new Ship(shipID, shipName, currentFuel, carryingCapacity, fuelCapacity, 3.5,selectedPort);
            selectedPort.addVehicle(newShip);
            newShip.setCurrentPort(selectedPort);
//           shipWriter.write(newShip.toString() + ", Port=" + selectedPort.toString() + "\n");
            shipWriter.write(newShip.toString() + "\n");

            writeVehicleToPort(selectedPort, newShip);
            System.out.println("Ship created and added to selected port.");
        } else {
            System.out.println("Invalid port choice.");
        }

        shipWriter.close();
    }

    //Write vehicle to port
    public static void writeVehicleToPort(Port port, Vehicle vehicle) throws IOException {
        File file = new File("src/main/java/components/team7ContainerPortManagement/models/utils/port_vehicles.txt");
        List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("{Port :" + port.getID())) {
                line = line.substring(0, line.lastIndexOf('}')) + ", " + vehicle.getID() + "}";
                lines.set(i, line);
                found = true;
                break;
            }
        }

        if (!found) {
            lines.add("{Port :" + port.getID() + ", Vehicles: " + vehicle.getID() + "}");
        }

        Files.write(file.toPath(), lines);
    }
    public static String getShipLineByShipNumber(String shipNumber, String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ID='" + shipNumber + "'")) {
                    return line;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Vehicle file not found.");
            e.printStackTrace();
        }
        return null; // Ship line not found
    }



    public static Ship parseShipLine(String shipLine) throws IOException {
        String[] parts = shipLine.split(", ");

        String id = parts[0].substring(parts[0].indexOf("'") + 1, parts[0].indexOf("'", parts[0].indexOf("'") + 1));
        String name = parts[1].substring(parts[1].indexOf("'") + 1, parts[1].indexOf("'", parts[1].indexOf("'") + 1));
        double currentFuel = Double.parseDouble(parts[2].substring(parts[2].indexOf("=") + 1));
        double carryingCapacity = Double.parseDouble(parts[3].substring(parts[3].indexOf("=") + 1));
        double fuelCapacity = Double.parseDouble(parts[4].substring(parts[4].indexOf("=") + 1));
        String portID = parts[5].substring(parts[5].indexOf("=") + 1);
        Port currentPort = getPortByOrderNumber(portID, "src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");

        // You might need to add more parsing logic for other attributes of Ship

        return new Ship(id, name, currentFuel, carryingCapacity, fuelCapacity, 3.5,currentPort);
    }
    public static Ship createShipFromShipLine(String shipLine) throws IOException {
        // Parse shipLine and extract required values
        String vehicleID = extractValue(shipLine, "ID='");
        String vehicleName = extractValue(shipLine, "name='");

        String currentFuelString = extractValue(shipLine, "currentFuel=");
        double currentFuel = Double.parseDouble(currentFuelString.split(",")[0].trim());

        double carryingCapacity = Double.parseDouble(extractValue(shipLine, "carryingCapacity="));
        double fuelCapacity = Double.parseDouble(extractValue(shipLine, "fuelCapacity="));
        double fuelConsumtion = Double.parseDouble(extractValue(shipLine, "fuelConsumtion="));
        String portID = extractValue(shipLine, "currentPort=");

        Port currentPort = getPortByID(portID); // Implement this method to get the port by ID

        return new Ship(vehicleID, vehicleName, currentFuel, carryingCapacity, fuelCapacity, fuelConsumtion, currentPort);
    }



}
