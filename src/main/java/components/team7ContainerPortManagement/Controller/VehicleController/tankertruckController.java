package src.main.java.components.team7ContainerPortManagement.Controller.VehicleController;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Math.round;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.calculateTotalWeightContainerPort;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.getBasicTruckLineBybasictruckID;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.isShipIDAlreadyExists;
import static src.main.java.components.team7ContainerPortManagement.Controller.portController.generateRandomID;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getContainerIDInPort;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.readContainersFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeContainersToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeVehicleContainerMapToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByID;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByOrderNumber;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portWriteFile.writeVehicleToPort;
import static src.main.java.components.team7ContainerPortManagement.utils.TankerTruckFileUtils.tankertruckReadFile.getTankerTruckTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.TankerTruckFileUtils.tankertruckReadFile.readTankerTruckFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.TankerTruckFileUtils.tankertruckWriteFile.writeTankerTruckToFile;

public class tankertruckController {
    public static void createTankerTruck(Port selectedPort) throws IOException {
        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt", true);
        Scanner scanner = new Scanner(System.in);
        String tankertruckID;
        boolean idExists;

//        do {
//            // Collect input values
//            System.out.println("Enter Tanker Truck ID:");
//            tankertruckID = "ttr-" + scanner.next();
//            scanner.nextLine();
//
//            // Check if the ship ID already exists in the file
//            idExists = isShipIDAlreadyExists(tankertruckID);
//
//            if (idExists) {
//                System.out.println("Error: Ship ID already exists. Please enter a different ID.");
//            }
//        } while (idExists);
        tankertruckID = "ttr-" + generateRandomID();
        System.out.println("Enter Tanker Truck name:");
        String shipName = scanner.nextLine();

        System.out.println("Enter Tanker Truck carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Tanker Truck fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        scanner.nextLine();
        double currentFuel = fuelCapacity;
        if (selectedPort != null) {
            TankerTruck newTankerTruck = new TankerTruck(tankertruckID, shipName, currentFuel, carryingCapacity, fuelCapacity, 3.5,selectedPort);
            selectedPort.addVehicle(newTankerTruck);
            newTankerTruck.setCurrentPort(selectedPort);
            shipWriter.write(newTankerTruck.toString() +"\n");
            writeVehicleToPort(selectedPort, newTankerTruck);
//            System.out.println("Tanker Truck created and added to selected port.");
            String ANSI_RESET = "\u001B[0m";
            String ANSI_GREEN = "\u001B[32m";
            String ANSI_BLUE = "\u001B[34m";
            String ANSI_CYAN = "\u001B[36m";
            String ANSI_RED = "\u001B[31m";
            String yellow = "\u001B[33m";
            String reset = "\u001B[0m";
            System.out.println(      ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 CREATE TANKER TRUCK SUCCESSFULLY" + "       ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            ID            : " + tankertruckID);
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
    public static String getTankerTruckLineBytankertruckID(String shipID, String filePath) throws IOException {
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
    public static TankerTruck getTankerTruckByLine(String line) throws IOException {
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
        TankerTruck tankerTruck = new TankerTruck(id,name,currentFuel,carryingCapacity,fuelCapacity,3.5,currentPort);
        tankerTruck.setID(id);
        tankerTruck.setName(name);
        tankerTruck.setCurrentFuel(currentFuel);
        tankerTruck.setCarryingCapacity(carryingCapacity);
        tankerTruck.setFuelCapacity(fuelCapacity);
        // Assuming you have a method to get a Port object by its ID
        tankerTruck.setCurrentPort(getPortByID(currentPortID));
        return tankerTruck;
    }
    public static void loadContainerTankerTruckMenu(Port selectedPort) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";

        Scanner scanner = new Scanner(System.in);
        List<String> availableTankerTruckIDs = selectedPort.getTankerTrucksInPort();
        if (availableTankerTruckIDs.isEmpty()) {
            System.out.println("There is no Tanker Truck in " + selectedPort.getID() + " port!");
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + ANSI_RESET);
            System.out.println("                                              ");
            System.out.println("      No Tanker Truck in " + selectedPort.getID() + " port!         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("Press any key to return...");
            scanner.nextLine();  // Wait for the user to press Enter
            return;
        }
        System.out.println("Available tanker truck in port " + selectedPort.getName() + ":");
        for (int i = 0; i < availableTankerTruckIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableTankerTruckIDs.get(i));
        }
        int selectedTankerTruckOrderID;
        TankerTruck selectedTankerTruck;
        while(true) {
            System.out.print("Choose a tanker truck by order number: ");
            selectedTankerTruckOrderID = scanner.nextInt();
            if (selectedTankerTruckOrderID < 1 || selectedTankerTruckOrderID > availableTankerTruckIDs.size()) {
                System.out.println("Wrong number. Please choose a valid ship order number.");
            } else {
                String selectedBasicTruckNumber = availableTankerTruckIDs.get(selectedTankerTruckOrderID - 1);
                String tankertruckLine = getTankerTruckLineBytankertruckID(selectedBasicTruckNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
                selectedTankerTruck = getTankerTruckByLine(tankertruckLine);
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
        int selectedContainerOrderNumber = -1;
        System.out.println("╔═══════════════════════════════════════════════════╗");
        System.out.println("║                List of Container                  ║");
        System.out.println("╚═══════════════════════════════════════════════════╝");
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
            String status = getStatusContainerbyID(containerID);
            if (container == null) {

                System.out.println(ANSI_RED+"Error! "+reset+"No available container");
                System.out.println("Press any key to return...");
                scanner.next();
                return;
            }
//            if (status.equals("isLoaded=false")) {
                System.out.println(" ["+(i + 1) + "]" + availableContainerIDs.get(i) + " |" + " Type: " + container.getContainerType() + " | " + status);
//            }

//            else if (container.isLoaded()) {
//                System.out.println("No available container");
//                System.out.println("Press any key to return...");
//                scanner.next();
//                return;
//            }
        }
        System.out.println("╚═══════════════════════════════════════════════════╝");
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

        double totalWeight = round(getTankerTruckTotalContainerWeight(selectedTankerTruck,selectedPort) + selectedContainer.getWeight());
        double totalConweigthinPort = round(calculateTotalWeightContainerPort(selectedPort)) + selectedContainer.getWeight();
        System.out.println("Debug total weight: " + totalWeight);
        System.out.println("Debug vehicle capacity: " + selectedTankerTruck.getCarryingCapacity());
        if (selectedTankerTruck.loadContainer(selectedContainer) && totalConweigthinPort <= selectedPort.getStoringCapacity() && selectedContainer.getContainerType().equals(ContainerType.LIQUID))
        {

            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 LOAD CONTAINER SUCCESSFULLY" + "              ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            " + selectedTankerTruck.getID() + " LOAD ON  " + " =======> " + selectedContainer.getID() + " " + "   " + ANSI_RESET);
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
//            System.out.println("Successfully loaded container " + selectedContainer.getID() + " onto vehicle " + selectedTankerTruck.getID());

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(true);
            selectedContainer.setPort(selectedTankerTruck.getCurrentPort());
            selectedContainer.updateStatusContainer(true,selectedPort);

            // Update the vehicleContainerMap
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            vehicleContainerMap.computeIfAbsent(selectedTankerTruck.getID(), k -> new ArrayList<>()).add(selectedContainer.getID());
            List<TankerTruck> TankerTruck = readTankerTruckFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            // Write the updated data back to the files
            writeTankerTruckToFile(TankerTruck, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            System.out.println("Press any key to return...");
            scanner.next();
        } else if (selectedContainer.getContainerType().equals(ContainerType.REFRIGERATED)){
            System.out.println("The total container weight in Basic Truck is larger than it capacity | " + "Total weight: "+ totalWeight + " Weight limit: " + selectedTankerTruck.getCarryingCapacity());
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          This Tanker truck can not carry Refrigerated container!");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;

        } else if (selectedContainer.getContainerType().equals(ContainerType.OPEN_SIDE)) {
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          This Tanker truck can not carry Open Side container!");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        } else if (selectedContainer.getContainerType().equals(ContainerType.OPEN_TOP)) {
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          This Tanker truck can not carry Open Top container!");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
        else if (selectedContainer.getContainerType().equals(ContainerType.DRY_STORAGE)) {

            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          This Tanker truck can not carry Dry Storage container!");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
        else if (totalWeight > selectedTankerTruck.getCarryingCapacity()) {

            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          The total container weight in Vehicle is larger than it capacity");
            System.out.println("          Total weight: "+ totalWeight + " Weight limit: " + selectedTankerTruck.getCarryingCapacity());
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
            System.out.println("                 Fail to load!");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }



    }
    public static void unloadContainerTankerTruckMenu(Port selectedPort) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);


        List<String> availableTankerTruckIDs = selectedPort.getTankerTrucksInPort();
        if (availableTankerTruckIDs.isEmpty()) {
            System.out.println("There is no Tanker Truck in " + selectedPort.getID() + " port!");
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("      No Tanker Truck in " + selectedPort.getID() + " port!         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("Press any key to return...");
            scanner.nextLine();  // Wait for the user to press Enter
            return;
        }
        System.out.println("Available tanker truck in port " + selectedPort.getName() + ":");
        for (int i = 0; i < availableTankerTruckIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableTankerTruckIDs.get(i));
        }
        int selectedTankerTruckOrderID;
        TankerTruck selectedTankerTruck;
        String selectedTankerTruckNumber;
        while (true) {
            System.out.print("Choose a ship by order number: ");
            selectedTankerTruckOrderID = scanner.nextInt();
            if (selectedTankerTruckOrderID < 1 || selectedTankerTruckOrderID > availableTankerTruckIDs.size()) {
                System.out.println("Wrong number. Please choose a valid ship order number.");
            } else {
                selectedTankerTruckNumber = availableTankerTruckIDs.get(selectedTankerTruckOrderID - 1);
                String tankertruckLine = getBasicTruckLineBybasictruckID(selectedTankerTruckNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
                selectedTankerTruck = getTankerTruckByLine(tankertruckLine);
                break; // Exit the loop if a valid ship is selected
            }
        }

        // Filter out the containers that are loaded on the selected ship
        Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        List<String> loadedContainerIDs = vehicleContainerMap.get(selectedTankerTruck.getID());
        if (loadedContainerIDs == null) {
            System.out.println("There is no container is loaded on this vehicle");
            System.out.println(ANSI_RED+"╔══════════════════════════════════════════════╗");
            System.out.println(ANSI_RED+"║                    Error                     ║");
            System.out.println(ANSI_RED+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("               There is no container is loaded on this vehicle");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.print("Press any key to return...");
            scanner.nextLine();  // Wait for the user to press Enter
            return;
        }
        // Filter the availableContainerIDs to keep only those that are loaded on the selected ship
        List<String> availableContainerIDs = new ArrayList<>(loadedContainerIDs);

// Display only the containers that are loaded on the selected ship
        System.out.println("Available containers loaded on ship " + selectedTankerTruckNumber + ":");
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
        if (selectedTankerTruck.unloadContainer(selectedContainer)) {
            System.out.println("Successfully unloaded container " + selectedContainer.getID() + " onto vehicle " + selectedTankerTruck.getID());
            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 UNLOAD CONTAINER SUCCESSFULLY" + "              ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            " + selectedTankerTruck.getID() + " UNLOAD ON  " + " =======> " + selectedContainer.getID() + " " + "   " + ANSI_RESET);
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(false);
            selectedContainer.updateStatusContainer(false,selectedPort);

            // Update the vehicleContainerMap
            vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
//            vehicleContainerMap.get(selectedShip.getID()).remove(selectedContainer.getID());
            List<TankerTruck> Tankertruck = readTankerTruckFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            if (vehicleContainerMap.containsKey(selectedTankerTruck.getID())) {
                vehicleContainerMap.get(selectedTankerTruck.getID()).remove(selectedContainer.getID());
                if (vehicleContainerMap.get(selectedTankerTruck.getID()).isEmpty()) {
                    vehicleContainerMap.remove(selectedTankerTruck.getID());
                }
            }
            // Write the updated data back to the files
            writeTankerTruckToFile(Tankertruck, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            System.out.println("Press any key to return...");
            scanner.next();
        } else {
            System.out.println("Failed to unload container.");
        }
    }
}
