package src.main.java.components.team7ContainerPortManagement.Controller.VehicleController;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Math.round;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.calculateTotalWeightContainerPort;
import static src.main.java.components.team7ContainerPortManagement.Controller.portController.generateRandomID;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getContainerIDInPort;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.readContainersFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeContainersToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeVehicleContainerMapToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByID;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByOrderNumber;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portWriteFile.writeVehicleToPort;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipReadFile.getShipTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipReadFile.readShipFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipWriteFile.writeShipToFile;

public class shipController {
    //===================================================================================================================
    //===================================================================================================================
    public static void createShip(Port selectedPort) throws IOException {
        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt", true);
        Scanner scanner = new Scanner(System.in);
        String shipID;
        boolean idExists;

//        do {
//            // Collect input values
//            System.out.println("Enter ship ID:");
//            shipID = "sh-" + scanner.next();
//            scanner.nextLine();
//
//            // Check if the ship ID already exists in the file
//            idExists = isShipIDAlreadyExists(shipID);
//
//            if (idExists) {
//                System.out.println("Error: Ship ID already exists. Please enter a different ID.");
//            }
//        } while (idExists);
        shipID = "sh-" + generateRandomID();

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
            String ANSI_RESET = "\u001B[0m";
            String ANSI_GREEN = "\u001B[32m";
            String ANSI_BLUE = "\u001B[34m";
            String ANSI_CYAN = "\u001B[36m";
            String ANSI_RED = "\u001B[31m";
            String yellow = "\u001B[33m";
            String reset = "\u001B[0m";
            System.out.println(      ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 CREATE SHIP SUCCESSFULLY" + "           ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            ID            : " + shipID);
            System.out.println( "            NAME          : " + shipName);
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
    static boolean isShipIDAlreadyExists(String shipID) throws IOException {
        File file = new File("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("ID='" + shipID + "'")) {
                scanner.close();
                return true; // ID already exists
            }
        }
        scanner.close();
        return false; // ID does not exist
    }
    //===================================================================================================================
    //===================================================================================================================
    //GET SHIP LINE BY ID
    public static String getShipLineByShipID(String shipID, String filePath) throws IOException {
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
    //AFTER GET SHIP LINE, USE THIS METHOD TO FIND THE SHIP OBJECT
    public static Ship getShipByLine(String line) throws IOException {
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
        Ship ship = new Ship(id,name,currentFuel,carryingCapacity,fuelCapacity,3.5,currentPort);
        ship.setID(id);
        ship.setName(name);
        ship.setCurrentFuel(currentFuel);
        ship.setCarryingCapacity(carryingCapacity);
        ship.setFuelCapacity(fuelCapacity);
        // Assuming you have a method to get a Port object by its ID
        ship.setCurrentPort(getPortByID(currentPortID));

        return ship;
    }
    public static void loadContainerShipMenu(Port selectedPort) throws IOException {
        String reset = "\u001B[0m";
        String red = "\u001B[31m";
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";

        Scanner scanner = new Scanner(System.in);
        List<String> availableShipIDs = selectedPort.getShipsInPort();
        if (availableShipIDs.isEmpty()) {
            System.out.println("There is no Ship in " + selectedPort.getID() + " port!");
            System.out.println(red+"╔══════════════════════════════════════════════╗");
            System.out.println(red+"║                    Error                     ║");
            System.out.println(red+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          No Ship in " + selectedPort.getID() + " port!         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
        System.out.println("Available ships in port " + selectedPort.getName() + ":");
        for (int i = 0; i < availableShipIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableShipIDs.get(i));
        }
        int selectedShipOrderID;
        Ship selectedShip;
        while(true) {
            System.out.print("Choose a ship by order number: ");
            selectedShipOrderID = scanner.nextInt();
            if (selectedShipOrderID < 1 || selectedShipOrderID > availableShipIDs.size()) {
                System.out.println("Wrong number. Please choose a valid ship order number.");
            } else {
                String selectedShipNumber = availableShipIDs.get(selectedShipOrderID - 1);
                String shipLine = getShipLineByShipID(selectedShipNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
                selectedShip = getShipByLine(shipLine);
                break; // Exit the loop if a valid ship is selected
            }
        }

        List<String> availableContainerIDs = getContainerIDInPort(selectedPort);
        if (availableContainerIDs.isEmpty()) {
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          No available container in " + selectedPort.getID() + " port!         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
        System.out.println("Available container in port: " + selectedPort.getID());
        System.out.println(ANSI_RED + "isLoaded = true is UNAVAILABLE "+ ANSI_GREEN+" | ISLOADED = FALSE IS AVAILABLE" + reset);
//        System.out.println("debug: " + availableContainerIDs);
        int selectedContainerOrderNumber = -1;
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
            String status = getStatusContainerbyID(containerID);
//            System.out.println("debug: " + status);
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
        String selectedContainerNumber = availableContainerIDs.get(selectedContainerOrderNumber - 1);
        System.out.println();

        String containerLine = getContainerLineByContainerID(selectedContainerNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
        Container selectedContainer = getContainerByLine(containerLine);
        //Load function
        //Calculate total weight
        double totalWeight = round(getShipTotalContainerWeight(selectedShip,selectedPort) + selectedContainer.getWeight());
        double totalConweigthinPort = round(calculateTotalWeightContainerPort(selectedPort)) + selectedContainer.getWeight();

        if (selectedShip.loadContainer(selectedContainer) && totalConweigthinPort < selectedPort.getStoringCapacity()) {
//            System.out.println("Successfully loaded container " + selectedContainer.getID() + " onto vehicle " + selectedShip.getID());

            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 LOAD CONTAINER SUCCESSFULLY" + "            ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "                   " + selectedShip.getID() + " LOAD ON  " + selectedContainer.getID() + " " + "   " + ANSI_RESET);
            System.out.println(yellow + "                         ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(true);
            selectedContainer.setPort(selectedPort);
            selectedContainer.updateStatusContainer(true,selectedPort);

            // Update the vehicleContainerMap
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            vehicleContainerMap.computeIfAbsent(selectedShip.getID(), k -> new ArrayList<>()).add(selectedContainer.getID());
            List<Ship> Ship = readShipFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
//            System.out.println("debug readbefore: " + containers);

            // Write the updated data back to the files
            writeShipToFile(Ship, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            System.out.println("Press any key to return...");
            scanner.next();
        }
        else if (totalWeight > selectedShip.getCarryingCapacity()) {
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          The total container weight in Tanker Truck is larger than it capacity");
            System.out.println("         Total Weight: " + totalWeight +" | " + selectedShip.getCarryingCapacity());
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
            System.out.println("          The total container weight in Port is larger than it capacity");
            System.out.println("          Total weight: "+ totalWeight + " Weight limit: " + selectedShip.getCarryingCapacity());
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
        else{
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("                 Fail to load!");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }

    }
    public static void unloadContainerShipMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        String red = "\u001B[31m";

        List<String> availableShipIDs = selectedPort.getShipsInPort();
        if (availableShipIDs.isEmpty()) {
            System.out.println("There is no Ship in " + selectedPort.getID() + " port!");
            System.out.println(red+"╔══════════════════════════════════════════════╗");
            System.out.println(red+"║                    Error                     ║");
            System.out.println(red+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("      No Ship in " + selectedPort.getID() + " port!         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("Press any key to return...");
            scanner.nextLine();  // Wait for the user to press Enter
            return;
        }
        System.out.println("Available ships in port " + selectedPort.getName() + ":");
        for (int i = 0; i < availableShipIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableShipIDs.get(i));
        }

        int selectedShipOrderID;
        Ship selectedShip;
        String selectedShipNumber;
        while (true) {
            System.out.print("Choose a ship by order number: ");
            selectedShipOrderID = scanner.nextInt();

            if (selectedShipOrderID < 1 || selectedShipOrderID > availableShipIDs.size()) {
                System.out.println("Wrong number. Please choose a valid ship order number.");
            } else {
                selectedShipNumber = availableShipIDs.get(selectedShipOrderID - 1);
                String shipLine = getShipLineByShipID(selectedShipNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
                selectedShip = getShipByLine(shipLine);
                break; // Exit the loop if a valid ship is selected
            }
        }

        // Filter out the containers that are loaded on the selected ship
        Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        List<String> loadedContainerIDs = vehicleContainerMap.get(selectedShip.getID());
        // Filter the availableContainerIDs to keep only those that are loaded on the selected ship
        if (loadedContainerIDs == null) {
            System.out.println(ANSI_RED+"╔═══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                      ║");
            System.out.println(ANSI_RED+"║───────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("There is no container is loaded on this vehicle");
            System.out.println("                                              ");
            System.out.println("╚═══════════════════════════════════════════════╝");
            System.out.print("Press any key to return...");
            scanner.next();  // Wait for the user to press Enter
            return;
        }
        List<String> availableContainerIDs = new ArrayList<>(loadedContainerIDs);

// Display only the containers that are loaded on the selected ship
        System.out.println("Available containers loaded on ship " + selectedShipNumber + ":");
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
        if (selectedShip.unloadContainer(selectedContainer)) {
            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 UNLOAD CONTAINER SUCCESSFULLY" + "           ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            " + selectedShip.getID() + " UNLOAD ON  " + " =======> " + selectedContainer.getID() + " " + "   " + ANSI_RESET);
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(false);
            selectedContainer.updateStatusContainer(false,selectedPort);

            // Update the vehicleContainerMap
            vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
//            vehicleContainerMap.get(selectedShip.getID()).remove(selectedContainer.getID());
            List<Ship> Ship = readShipFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            if (vehicleContainerMap.containsKey(selectedShip.getID())) {
                vehicleContainerMap.get(selectedShip.getID()).remove(selectedContainer.getID());
                if (vehicleContainerMap.get(selectedShip.getID()).isEmpty()) {
                    vehicleContainerMap.remove(selectedShip.getID());
                }
            }
            // Write the updated data back to the files
            writeShipToFile(Ship, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            System.out.println("Press any key to return...");
            scanner.next();
        } else {
            System.out.println("Failed to unload container.");
        }
    }

}
