package src.main.java.components.team7ContainerPortManagement.Controller.VehicleController;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle.readVehiclesFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByID;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByOrderNumber;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portWriteFile.writeVehicleToPort;

public class basictruckController {
    public static void createBasicTruck(Port selectedPort) throws IOException {
        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt", true);
        Scanner scanner = new Scanner(System.in);
        // Collect input values
        System.out.println("Enter Basic Truck ID:");
        String shipID = "btr-" + scanner.next();
        scanner.nextLine();
        System.out.println("Enter Basic Truck name:");
        String shipName = scanner.nextLine();

        System.out.println("Enter Basic Truck carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Baisc Truck fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        scanner.nextLine();
        double currentFuel = fuelCapacity;
        if (selectedPort != null) {
            BasicTruck newBasicTruck = new BasicTruck(shipID, shipName, currentFuel, carryingCapacity, fuelCapacity, 3.5,selectedPort);
            selectedPort.addVehicle(newBasicTruck);
            newBasicTruck.setCurrentPort(selectedPort);
            shipWriter.write(newBasicTruck +"\n");
            writeVehicleToPort(selectedPort, newBasicTruck);
            System.out.println("Ship created and added to selected port.");
        } else {
            System.out.println("Invalid port choice.");
        }

        shipWriter.close();
    }
    public static void updateVehicle(String portID) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Vehicle> vehicles = readVehiclesFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");

        // Filter vehicles belonging to the same port
        List<Vehicle> filteredVehicles = vehicles.stream()
                .filter(vehicle -> vehicle.getCurrentPort().getID().equals(portID))
                .collect(Collectors.toList());

        // Show the list of vehicles
        System.out.println("Select a vehicle to update from port " + portID + ":");
        if (filteredVehicles.isEmpty()) {
            System.out.println("No vehicles found for this port.");
            return;
        }
//        for (int i = 0; i < filteredVehicles.size(); i++) {
//            System.out.println((i + 1) + ". " + filteredVehicles.get(i).getID());
//        }
        displayVehiclesInColumns(filteredVehicles);
        // User selects a vehicle
        System.out.println("Enter the order number of the vehicle you want to update:");
        int selectedIndex = scanner.nextInt() - 1;

        if (selectedIndex >= 0 && selectedIndex < filteredVehicles.size()) {
            Vehicle selectedVehicle = filteredVehicles.get(selectedIndex);

            // Update vehicle's name
            scanner.nextLine();
            System.out.println("Enter new vehicle name (leave empty to skip):");
            String newName = scanner.nextLine();
            if (!newName.isEmpty()) {
                selectedVehicle.setName(newName);
            }

            // Update vehicle's carrying capacity
            System.out.println("Enter new carrying capacity (-1 to skip):");
            double newCarryingCapacity = scanner.nextDouble();
            if (newCarryingCapacity != -1) {
                selectedVehicle.setCarryingCapacity(newCarryingCapacity);
            }

            // Update vehicle's fuel capacity
            System.out.println("Enter new fuel capacity (-1 to skip):");
            double newFuelCapacity = scanner.nextDouble();
            if (newFuelCapacity != -1) {
                selectedVehicle.setFuelCapacity(newFuelCapacity);
            }

            saveVehiclesToFile(vehicles);
            System.out.println("Vehicle updated successfully!");
        } else {
            System.out.println("Invalid selection!");
        }
    }
    public static void deleteVehicle(String portID) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Vehicle> vehicles = readVehiclesFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");

        // Filter vehicles belonging to the same port
        List<Vehicle> filteredVehicles = vehicles.stream()
                .filter(vehicle -> vehicle.getCurrentPort().getID().equals(portID))
                .collect(Collectors.toList());

        System.out.println("Select a vehicle to delete from port " + portID + ":");
        if (filteredVehicles.isEmpty()) {
            System.out.println("No vehicles found for this port.");
            return;
        }
//        for (int i = 0; i < filteredVehicles.size(); i++) {
//            System.out.println((i + 1) + ". " + filteredVehicles.get(i).toString());
//        }
        displayVehiclesInColumns(filteredVehicles);
        System.out.println("Enter the order number of the vehicle you want to delete:");
        int selectedIndex = scanner.nextInt() - 1;

        if (selectedIndex >= 0 && selectedIndex < filteredVehicles.size()) {
            Vehicle selectedVehicle = filteredVehicles.get(selectedIndex);
            vehicles.remove(selectedVehicle);

            saveVehiclesToFile(vehicles);
            System.out.println("Vehicle deleted successfully!");
        } else {
            System.out.println("Invalid selection!");
        }
    }

    public static void refuelVehicle(String portID) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Vehicle> vehicles = readVehiclesFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");

        // Filter vehicles belonging to the specified port
        List<Vehicle> filteredVehicles = vehicles.stream()
                .filter(vehicle -> vehicle.getCurrentPort().getID().equals(portID))
                .collect(Collectors.toList());

        if (filteredVehicles.isEmpty()) {
            System.out.println("No vehicles found for this port.");
            return;
        }

        // Show the list of vehicles
        System.out.println("Select a vehicle to refuel from port " + portID + ":");
        displayVehiclesFuelInColumns(filteredVehicles);

        // User selects a vehicle
        System.out.println("Enter the order number of the vehicle you want to refuel:");
        int selectedIndex = scanner.nextInt() - 1;

        if (selectedIndex >= 0 && selectedIndex < filteredVehicles.size()) {
            Vehicle selectedVehicle = filteredVehicles.get(selectedIndex);
            selectedVehicle.setCurrentFuel(selectedVehicle.getFuelCapacity());
            saveVehiclesToFile(vehicles);
            System.out.println("Vehicle refueled successfully!");
        } else {
            System.out.println("Invalid selection!");
        }
    }



    public static void saveVehiclesToFile(List<Vehicle> vehicles) throws IOException {
        FileWriter vehicleWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt", false);
        for (Vehicle vehicle : vehicles) {
            vehicleWriter.write(vehicle.toString() + "\n");
        }
        vehicleWriter.close();
    }

    public static void displayVehiclesInColumns(List<Vehicle> vehicles) {
        final int MAX_PER_COLUMN = 5;
        final int COLUMN_WIDTH = 40;
        int totalVehicles = vehicles.size();
        int numberOfColumns = (int) Math.ceil((double) totalVehicles / MAX_PER_COLUMN);

        for (int i = 0; i < MAX_PER_COLUMN; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < numberOfColumns; j++) {
                int index = j * MAX_PER_COLUMN + i;
                if (index < totalVehicles) {
                    String vehicleInfo = String.format("%d. %s", index + 1, vehicles.get(index).getID());
                    row.append(String.format("%-" + COLUMN_WIDTH + "s", vehicleInfo));
                }
            }
            System.out.println(row);
        }
    }
    public static void displayVehiclesFuelInColumns(List<Vehicle> vehicles) {
        final int MAX_PER_COLUMN = 5;
        final int COLUMN_WIDTH = 40;
        int totalVehicles = vehicles.size();
        int numberOfColumns = (int) Math.ceil((double) totalVehicles / MAX_PER_COLUMN);

        for (int i = 0; i < MAX_PER_COLUMN; i++) {
            StringBuilder row = new StringBuilder();
            for (int j = 0; j < numberOfColumns; j++) {
                int index = j * MAX_PER_COLUMN + i;
                if (index < totalVehicles) {
                    Vehicle vehicle = vehicles.get(index);
                    String vehicleInfo = String.format("%d. ID: %s, Current Fuel: %.2f, Capacity: %.2f",
                            index + 1, vehicle.getID(), vehicle.getCurrentFuel(), vehicle.getFuelCapacity());
                    row.append(String.format("%-" + COLUMN_WIDTH + "s", vehicleInfo));
                }
            }
            System.out.println(row);
        }
    }

    //===================================================================================================================
    //===================================================================================================================
    //GET SHIP LINE BY ID
    public static String getBasicTruckLineBybasictruckID(String shipID, String filePath) throws IOException {
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
    public static BasicTruck getBasicTruckByLine(String line) throws IOException {
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
        BasicTruck basictruck = new BasicTruck(id,name,currentFuel,carryingCapacity,fuelCapacity,3.5,currentPort);
        basictruck.setID(id);
        basictruck.setName(name);
        basictruck.setCurrentFuel(currentFuel);
        basictruck.setCarryingCapacity(carryingCapacity);
        basictruck.setFuelCapacity(fuelCapacity);
        // Assuming you have a method to get a Port object by its ID
        basictruck.setCurrentPort(getPortByID(currentPortID));

        return basictruck;
    }
}
