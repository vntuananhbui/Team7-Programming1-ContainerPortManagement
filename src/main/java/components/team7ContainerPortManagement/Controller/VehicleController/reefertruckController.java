package src.main.java.components.team7ContainerPortManagement.Controller.VehicleController;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;

import java.io.*;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByID;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByOrderNumber;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portWriteFile.writeVehicleToPort;

public class reefertruckController {
    public static void createReeferTruck(Port selectedPort) throws IOException {
        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt", true);
        Scanner scanner = new Scanner(System.in);
        // Collect input values
        System.out.println("Enter Reefer Truck ID:");
        String shipID = "rtr-" + scanner.next();
        scanner.nextLine();
        System.out.println("Enter Reefer Truck name:");
        String shipName = scanner.nextLine();

        System.out.println("Enter Reefer Truck carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Tanker Truck fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        scanner.nextLine();
        double currentFuel = fuelCapacity;
        if (selectedPort != null) {
            ReeferTrucks newReeferTruck = new ReeferTrucks(shipID, shipName, currentFuel, carryingCapacity, fuelCapacity, 3.5,selectedPort);
            selectedPort.addVehicle(newReeferTruck);
            newReeferTruck.setCurrentPort(selectedPort);
            shipWriter.write(newReeferTruck.toString() + "\n");
            writeVehicleToPort(selectedPort, newReeferTruck);
            System.out.println("Reefer Truck created and added to selected port.");
        } else {
            System.out.println("Invalid port choice.");
        }

        shipWriter.close();
    }
    public static String getReeferTruckLineByreefertruckID(String shipID, String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ID='" + shipID + "'")) {
                    return line;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Vehicle file not found.");
            e.printStackTrace();
        }
        return null; // Ship line not found
    }
    //===================================================================================================================
    //===================================================================================================================
    public static ReeferTrucks getReeferTruckByLine(String line) throws IOException {
        // Parse the line to extract the fields
        // Assuming the line format is: Vehicle{ID='sh-3ww2', name='1212', currentFuel=1212.0, carryingCapacity=1212.0, fuelCapacity=1212.0, currentPort=p-uui8}
        String[] parts = line.split(", ");
        String id = parts[0].split("'")[1];
        String name = parts[1].split("'")[1];
        double currentFuel = Double.parseDouble(parts[2].split("=")[1]);
        double carryingCapacity = Double.parseDouble(parts[3].split("=")[1]);
        double fuelCapacity = Double.parseDouble(parts[4].split("=")[1]);
        String currentPortID = parts[5].split("=")[1].substring(0, parts[5].split("=")[1].length() - 1);
//        System.out.println(currentPortID);
        Port currentPort = getPortByOrderNumber(id, "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
        // Create a new Vehicle object
        ReeferTrucks reeferTruck = new ReeferTrucks(id,name,currentFuel,carryingCapacity,fuelCapacity,3.5,currentPort);
        reeferTruck.setID(id);
        reeferTruck.setName(name);
        reeferTruck.setCurrentFuel(currentFuel);
        reeferTruck.setCarryingCapacity(carryingCapacity);
        reeferTruck.setFuelCapacity(fuelCapacity);
        // Assuming you have a method to get a Port object by its ID
        reeferTruck.setCurrentPort(getPortByID(currentPortID));

        return reeferTruck;
    }
}
