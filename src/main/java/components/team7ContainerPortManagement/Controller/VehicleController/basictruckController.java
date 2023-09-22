package src.main.java.components.team7ContainerPortManagement.Controller.VehicleController;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static java.lang.Math.round;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.calculateTotalWeightContainerPort;

import static src.main.java.components.team7ContainerPortManagement.Controller.portController.generateRandomID;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getContainerIDInPort;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle.readVehiclesFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.BasicTruckFileUtils.basictruckReadFile.getBasicTruckTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.BasicTruckFileUtils.basictruckReadFile.readBasicTruckFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.BasicTruckFileUtils.basictruckWriteFile.writeBasicTruckToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.readContainersFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeContainersToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeVehicleContainerMapToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByID;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByOrderNumber;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portWriteFile.writeVehicleToPort;

public class basictruckController {
    public static void createBasicTruck(Port selectedPort) throws IOException {
        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt", true);
        Scanner scanner = new Scanner(System.in);
        String basictruckID;
        boolean idExists;

//        do {
//            // Collect input values
//            System.out.println("Enter Basic Truck ID:");
//            basictruckID = "btr-" + scanner.next();
//            scanner.nextLine();
//
//            // Check if the ship ID already exists in the file
//            idExists = isShipIDAlreadyExists(basictruckID);
//
//            if (idExists) {
//                System.out.println("Error: Ship ID already exists. Please enter a different ID.");
//            }
//        } while (idExists);
        basictruckID = "btr-" + generateRandomID();
//        scanner.nextLine();
        System.out.println("Enter Basic Truck name:");
        String BaiscTruckName = scanner.nextLine();

        System.out.println("Enter Basic Truck carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Baisc Truck fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        scanner.nextLine();
        double currentFuel = fuelCapacity;
        if (selectedPort != null) {
            BasicTruck newBasicTruck = new BasicTruck(basictruckID, BaiscTruckName, currentFuel, carryingCapacity, fuelCapacity, 3.5,selectedPort);
            selectedPort.addVehicle(newBasicTruck);
            newBasicTruck.setCurrentPort(selectedPort);
            shipWriter.write(newBasicTruck +"\n");
            writeVehicleToPort(selectedPort, newBasicTruck);
            String ANSI_RESET = "\u001B[0m";
            String ANSI_GREEN = "\u001B[32m";
            String ANSI_BLUE = "\u001B[34m";
            String ANSI_CYAN = "\u001B[36m";
            String ANSI_RED = "\u001B[31m";
            String yellow = "\u001B[33m";
            String reset = "\u001B[0m";
            System.out.println(      ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 CREATE BASIC TRUCK SUCCESSFULLY" + "        ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            ID            : " + basictruckID);
            System.out.println( "            NAME          : " + BaiscTruckName);
            System.out.println( "            CARRY CAPACITY: " + carryingCapacity);
            System.out.println( "            FUEL CAPACITY : " + fuelCapacity);
            System.out.println( "            PORT          : " + selectedPort.getID());
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println("Press any key to return...");
            scanner.next();
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
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
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
            System.out.println(      ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 Refuel SUCCESSFULLY" + "                ║");
            System.out.println(      ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
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
    public static void loadContainerBasicTruckMenu(Port selectedPort) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
        List<String> availableBasicTruckIDs = selectedPort.getBasicTrucksInPort();
        if (availableBasicTruckIDs.isEmpty()) {
            System.out.println("There is no Basic Truck in " + selectedPort.getID() + " port!");
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          No Basic Truck in " + selectedPort.getID() + " port!         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
        System.out.println("Available basic truck in port " + selectedPort.getName() + ":");
        for (int i = 0; i < availableBasicTruckIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableBasicTruckIDs.get(i));
        }
        int selectedBasicTruckOrderID;
        BasicTruck selectedBasicTruck;
        while(true) {
            System.out.print("Choose a basic truck by order number: ");
            selectedBasicTruckOrderID = scanner.nextInt();
            if (selectedBasicTruckOrderID < 1 || selectedBasicTruckOrderID > availableBasicTruckIDs.size()) {
                System.out.println("Wrong number. Please choose a valid ship order number.");
            } else {
                String selectedBasicTruckNumber = availableBasicTruckIDs.get(selectedBasicTruckOrderID - 1);
                String basictruckLine = getBasicTruckLineBybasictruckID(selectedBasicTruckNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
                selectedBasicTruck = getBasicTruckByLine(basictruckLine);
                break; // Exit the loop if a valid ship is selected
            }
        }
        List<String> availableContainerIDs = getContainerIDInPort(selectedPort);
        System.out.println("Available container in port: " + selectedPort.getID());
        System.out.println(ANSI_RED + "isLoaded = true is UNAVAILABLE "+ ANSI_GREEN+" | ISLOADED = FALSE IS AVAILABLE" + reset);
        if (availableContainerIDs.isEmpty()) {
            System.out.println("No available container!");
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          No available Container in " + selectedPort.getID() + " port!         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
        int selectedContainerOrderNumber = -1;
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
            String status = getStatusContainerbyID(containerID);

            if (status.equals("isLoaded=false")) {
//                System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + " |" + " Type: " + container.getContainerType());
            }
            if (container == null) {
                System.out.println("No available container");
                System.out.println("Press any key to return...");
                scanner.next();
                return;
            }
//            if (status.equals("isLoaded=false")) {
            System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + " |" + " Type: " + container.getContainerType() + " | " + status);
//            }
        }
        while (selectedContainerOrderNumber < 1 || selectedContainerOrderNumber > availableContainerIDs.size()) {
            System.out.println("Choose a container by order number: ");
            selectedContainerOrderNumber = scanner.nextInt();
            if (selectedContainerOrderNumber < 1 || selectedContainerOrderNumber > availableContainerIDs.size()) {
                System.out.println("Wrong number. Please choose a valid container order number.");
            }

        }
//        selectedContainerOrderNumber = scanner.nextInt();
        String selectedContainerNumber = availableContainerIDs.get(selectedContainerOrderNumber - 1);
//        System.out.println("Container Number: " + selectedContainerNumber);
        System.out.println();
        String containerLine = getContainerLineByContainerID(selectedContainerNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
//        System.out.println("Container Line" + containerLine);

//        System.out.println("Get container: " + getContainerByLine(containerLine));

        Container selectedContainer = getContainerByLine(containerLine);
        System.out.println("Container Type: "+selectedContainer.getContainerType());
        //Load function

        double totalWeight = round(getBasicTruckTotalContainerWeight(selectedBasicTruck,selectedPort) + selectedContainer.getWeight());
//        System.out.println("debug totalweight: " + totalWeight);
//        System.out.println("debug: container weight: " + selectedContainer.getWeight());
//        System.out.println("debug : truck capacity: " + selectedBasicTruck.getCarryingCapacity()) ;
        double totalConweigthinPort = round(calculateTotalWeightContainerPort(selectedPort)) + selectedContainer.getWeight();

        if (selectedBasicTruck.loadContainer(selectedContainer) && totalWeight <= selectedBasicTruck.getCarryingCapacity() && totalConweigthinPort <= selectedPort.getStoringCapacity() && (selectedContainer.getContainerType().equals(ContainerType.OPEN_TOP) || selectedContainer.getContainerType().equals(ContainerType.OPEN_SIDE) || selectedContainer.getContainerType().equals(ContainerType.DRY_STORAGE)))
        {

            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 LOAD CONTAINER SUCCESSFULLY" + "            ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            " + selectedBasicTruck.getID() + " LOAD ON  " + " =======> " + selectedContainer.getID() + " " + "   " + ANSI_RESET);
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(true);
            selectedContainer.setPort(selectedBasicTruck.getCurrentPort());
            selectedContainer.updateStatusContainer(true,selectedPort);
            // Update the vehicleContainerMap
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            vehicleContainerMap.computeIfAbsent(selectedBasicTruck.getID(), k -> new ArrayList<>()).add(selectedContainer.getID());
            List<BasicTruck> BasicTruck = readBasicTruckFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            // Write the updated data back to the files
            writeBasicTruckToFile(BasicTruck, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            System.out.println("Press any key to return...");
            scanner.next();
        } else if (selectedContainer.getContainerType().equals(ContainerType.REFRIGERATED)){
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          This Basic truck can not carry Refrigerated Container!");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        } else if (selectedContainer.getContainerType().equals(ContainerType.LIQUID)) {
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          This Basic truck can not carry Liquid container!");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
        else if (totalWeight > selectedBasicTruck.getCarryingCapacity()) {
            System.out.println("The total container weight in Basic Truck is larger than it capacity | " + "Total weight: "+ totalWeight + " Weight limit: " + selectedBasicTruck.getCarryingCapacity());
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          The total container weight in Basic Truck is larger than it capacity");
            System.out.println("         Total Weight: " + totalWeight +" | Capacity: " + selectedBasicTruck.getCarryingCapacity() );
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
        else if(totalConweigthinPort > selectedPort.getStoringCapacity()) {
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("         The total container weight in Port is larger than it capacity");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
        else {

            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("                  Fail to load!               ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }


    }
    public static void unloadContainerBasicTruckMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        String red = "\u001B[31m";

        List<String> availableBasicTruckIDs = selectedPort.getBasicTrucksInPort();
        if (availableBasicTruckIDs.isEmpty()) {

            System.out.println(red+"╔══════════════════════════════════════════════╗");
            System.out.println(red+"║                    Error                     ║");
            System.out.println(red+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("      No Basic Truck in " + selectedPort.getID() + " port!         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("Press any key to return...");
            scanner.nextLine();  // Wait for the user to press Enter
            return;
        }
        System.out.println("Available basic truck in port " + selectedPort.getName() + ":");
        for (int i = 0; i < availableBasicTruckIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableBasicTruckIDs.get(i));
        }
        int selectedBasicTruckOrderID;
        BasicTruck selectedBasicTruck;
        String selectedBasicTruckNumber;
        while (true) {
            System.out.print("Choose a ship by order number: ");
            selectedBasicTruckOrderID = scanner.nextInt();
            if (selectedBasicTruckOrderID < 1 || selectedBasicTruckOrderID > availableBasicTruckIDs.size()) {
                System.out.println("Wrong number. Please choose a valid ship order number.");
            } else {
                selectedBasicTruckNumber = availableBasicTruckIDs.get(selectedBasicTruckOrderID - 1);
                String basictruckLine = getBasicTruckLineBybasictruckID(selectedBasicTruckNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
                selectedBasicTruck = getBasicTruckByLine(basictruckLine);
                break; // Exit the loop if a valid ship is selected
            }
        }

        // Filter out the containers that are loaded on the selected ship
        Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        List<String> loadedContainerIDs = vehicleContainerMap.get(selectedBasicTruck.getID());
        if (loadedContainerIDs == null) {

            System.out.println(red+"╔══════════════════════════════════════════════╗");
            System.out.println(red+"║                    Error                     ║");
            System.out.println(red+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("      There is no container is loaded on this vehicle!         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("Press any key to return...");
            scanner.nextLine();  // Wait for the user to press Enter
            return;
        }
        // Filter the availableContainerIDs to keep only those that are loaded on the selected ship
        List<String> availableContainerIDs = new ArrayList<>(loadedContainerIDs);

// Display only the containers that are loaded on the selected ship
        System.out.println("Available containers loaded on ship " + selectedBasicTruckNumber + ":");
        for (int i = 0; i < loadedContainerIDs.size(); i++) {
            String containerID = loadedContainerIDs.get(i);
            String status = getStatusContainerbyID(containerID);
            System.out.println((i + 1) + ": " + containerID + "|" + status);
        }

        int selectedContainerOrderNumber = -1;
        while (selectedContainerOrderNumber < 1 || selectedContainerOrderNumber > availableContainerIDs.size()) {
            System.out.println("Choose a container by order number: ");
            selectedContainerOrderNumber = scanner.nextInt();
            if (selectedContainerOrderNumber < 1 || selectedContainerOrderNumber > availableContainerIDs.size()) {
                System.out.println("Wrong number. Please choose a valid container order number.");
            }
        }
        String selectedContainerNumber = availableContainerIDs.get(selectedContainerOrderNumber - 1);
        System.out.println();
        String containerLine = getContainerLineByContainerID(selectedContainerNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");

        Container selectedContainer = getContainerByLine(containerLine);

        //Unload function
        if (selectedBasicTruck.unloadContainer(selectedContainer)) {

            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 UNLOAD CONTAINER SUCCESSFULLY" + "              ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            " + selectedBasicTruck.getID() + " UNLOAD ON  " + " =======> " + selectedContainer.getID() + " " + "   " + ANSI_RESET);
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(false);
            selectedContainer.updateStatusContainer(false,selectedPort);

            // Update the vehicleContainerMap
            vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
//            vehicleContainerMap.get(selectedShip.getID()).remove(selectedContainer.getID());
            List<BasicTruck> Basictruck = readBasicTruckFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            if (vehicleContainerMap.containsKey(selectedBasicTruck.getID())) {
                vehicleContainerMap.get(selectedBasicTruck.getID()).remove(selectedContainer.getID());
                if (vehicleContainerMap.get(selectedBasicTruck.getID()).isEmpty()) {
                    vehicleContainerMap.remove(selectedBasicTruck.getID());
                }
            }
            // Write the updated data back to the files
            writeBasicTruckToFile(Basictruck, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            System.out.println("Press any key to return...");
            scanner.next();
        } else {
            System.out.println(red+"╔══════════════════════════════════════════════╗");
            System.out.println(red+"║                    Error                     ║");
            System.out.println(red+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("                Failed to unload container.         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("Press any key to return...");
            scanner.nextLine();  // Wait for the user to press Enter
            return;
        }
    }
}
