package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Math.round;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.getReeferTruckByLine;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.getReeferTruckLineByreefertruckID;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.getShipByLine;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.getShipLineByShipID;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.*;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getContainerIDInPort;
import static src.main.java.components.team7ContainerPortManagement.utils.BasicTruckFileUtils.basictruckReadFile.getBasicTruckTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.BasicTruckFileUtils.basictruckReadFile.readBasicTruckFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.BasicTruckFileUtils.basictruckWriteFile.writeBasicTruckToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeContainersToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeVehicleContainerMapToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ReeferTruckFileUtils.reefertruckReadFile.getReeferTruckTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.ReeferTruckFileUtils.reefertruckReadFile.readReeferTruckFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ReeferTruckFileUtils.reefertruckWriteFile.writeReeferTruckToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipReadFile.getShipTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipReadFile.readShipFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipWriteFile.writeShipToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.TankerTruckFileUtils.tankertruckReadFile.getTankerTruckTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.TankerTruckFileUtils.tankertruckReadFile.readTankerTruckFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.TankerTruckFileUtils.tankertruckWriteFile.writeTankerTruckToFile;

public class loadAndunloadContainer {
    //vehicle Load container
    public static void loadContainerShipMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableShipIDs = selectedPort.getShipsInPort();

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

        System.out.println("Available container in port: " + selectedPort.getID());
        if (availableContainerIDs.isEmpty()) {
            System.out.println("No available container!");
            return;
        }
        int selectedContainerOrderNumber = -1;
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
            String status = getStatusContainerbyID(containerID);

            if (status.equals("isLoaded=false")) {
                System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + "|" + status);
            }
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


        if (selectedShip.loadContainer(selectedContainer) && totalWeight < selectedShip.getCarryingCapacity()) {
            System.out.println("Successfully loaded container " + selectedContainer.getID() + " onto vehicle " + selectedShip.getID());

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(true);
            selectedContainer.setPort(selectedShip.getCurrentPort());
            selectedContainer.updateStatusContainer(true);

            // Update the vehicleContainerMap
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            vehicleContainerMap.computeIfAbsent(selectedShip.getID(), k -> new ArrayList<>()).add(selectedContainer.getID());
            List<Ship> Ship = readShipFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            // Write the updated data back to the files
            writeShipToFile(Ship, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        } else if (totalWeight > selectedShip.getCarryingCapacity()) {
            System.out.println("The total container weight in Ship is larger than it capacity | " + "Total weight: "+ totalWeight + " Weight limit: " + selectedShip.getCarryingCapacity());
        }
        else {
            System.out.println("Failed to load container.");
        }

    }


    //========================
    public static void loadContainerBasicTruckMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableBasicTruckIDs = selectedPort.getBasicTrucksInPort();

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
        if (availableContainerIDs.isEmpty()) {
            System.out.println("No available container!");
            return;
        }
        int selectedContainerOrderNumber = -1;
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
            String status = getStatusContainerbyID(containerID);

            if (status.equals("isLoaded=false")) {
                System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + "|" + status);
            }
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
        if (selectedBasicTruck.loadContainer(selectedContainer) && selectedContainer.getContainerType().equals(ContainerType.OPEN_TOP) || selectedContainer.getContainerType().equals(ContainerType.OPEN_SIDE) || selectedContainer.getContainerType().equals(ContainerType.DRY_STORAGE))
        {

            System.out.println("Successfully loaded container " + selectedContainer.getID() + " onto vehicle " + selectedBasicTruck.getID());

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(true);
            selectedContainer.setPort(selectedBasicTruck.getCurrentPort());
            selectedContainer.updateStatusContainer(true);

            // Update the vehicleContainerMap
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            vehicleContainerMap.computeIfAbsent(selectedBasicTruck.getID(), k -> new ArrayList<>()).add(selectedContainer.getID());
            List<BasicTruck> BasicTruck = readBasicTruckFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            // Write the updated data back to the files
            writeBasicTruckToFile(BasicTruck, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        } else if (selectedContainer.getContainerType().equals(ContainerType.REFRIGERATED)){
            System.out.println("This Basic truck can not carry Refrigerated Container!");

        } else if (selectedContainer.getContainerType().equals(ContainerType.LIQUID)) {
            System.out.println("This Basic truck can not carry Liquid container!");
        } else if (totalWeight < selectedBasicTruck.getCarryingCapacity()) {
            System.out.println("The total container weight in Basic Truck is larger than it capacity | " + "Total weight: "+ totalWeight + " Weight limit: " + selectedBasicTruck.getCarryingCapacity());
        }
        else {
            System.out.println("Fail to load! ");
        }


        // Define other methods (e.g., readPortsFromFile, displayAvailablePorts) here
    }

    //===================================================================================================================
    public static void loadContainerTankerTruckMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableTankerTruckIDs = selectedPort.getTankerTrucksInPort();

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

        System.out.println("Available container in port: " + selectedPort.getID());
        if (availableContainerIDs.isEmpty()) {
            System.out.println("No available container!");
            return;
        }
        int selectedContainerOrderNumber = -1;
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
            String status = getStatusContainerbyID(containerID);

            if (status.equals("isLoaded=false")) {
                System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + "|" + status);
            }
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

        double totalWeight = round(getTankerTruckTotalContainerWeight(selectedTankerTruck,selectedPort) + selectedContainer.getWeight());
        if (selectedTankerTruck.loadContainer(selectedContainer) && selectedContainer.getContainerType().equals(ContainerType.LIQUID))
        {

            System.out.println("Successfully loaded container " + selectedContainer.getID() + " onto vehicle " + selectedTankerTruck.getID());

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(true);
            selectedContainer.setPort(selectedTankerTruck.getCurrentPort());
            selectedContainer.updateStatusContainer(true);

            // Update the vehicleContainerMap
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            vehicleContainerMap.computeIfAbsent(selectedTankerTruck.getID(), k -> new ArrayList<>()).add(selectedContainer.getID());
            List<TankerTruck> TankerTruck = readTankerTruckFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            // Write the updated data back to the files
            writeTankerTruckToFile(TankerTruck, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        } else if (selectedContainer.getContainerType().equals(ContainerType.REFRIGERATED)){
            System.out.println("This Basic truck can not carry Refrigerated Container!");

        } else if (selectedContainer.getContainerType().equals(ContainerType.OPEN_SIDE)) {
            System.out.println("This Basic truck can not carry Open Side container!");
        } else if (selectedContainer.getContainerType().equals(ContainerType.OPEN_TOP)) {
            System.out.println("This Basic truck can not carry Open Top container!");
        }
        else if (selectedContainer.getContainerType().equals(ContainerType.DRY_STORAGE)) {
            System.out.println("This Basic truck can not carry Dry Storage container!");
        }
        else if (totalWeight < selectedTankerTruck.getCarryingCapacity()) {
            System.out.println("The total container weight in Basic Truck is larger than it capacity | " + "Total weight: "+ totalWeight + " Weight limit: " + selectedTankerTruck.getCarryingCapacity());
        }
        else {
            System.out.println("Fail to load! ");
        }



    }
    //======================================================================================
    public static void loadContainerReeferTruckMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableReeferTruckIDs = selectedPort.getReeferTrucksInPort();

        System.out.println("Available reefer truck in port " + selectedPort.getName() + ":");
        for (int i = 0; i < availableReeferTruckIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableReeferTruckIDs.get(i));
        }
        int selectedReeferTruckOrderID;
        ReeferTrucks selectedReeferTruck;
        while(true) {
            System.out.print("Choose a reefer truck by order number: ");
            selectedReeferTruckOrderID = scanner.nextInt();
            if (selectedReeferTruckOrderID < 1 || selectedReeferTruckOrderID > availableReeferTruckIDs.size()) {
                System.out.println("Wrong number. Please choose a valid ship order number.");
            } else {
                String selectedReeferTruckNumber = availableReeferTruckIDs.get(selectedReeferTruckOrderID - 1);
                String reefertruckLine = getReeferTruckLineByreefertruckID(selectedReeferTruckNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
                selectedReeferTruck = getReeferTruckByLine(reefertruckLine);
                break; // Exit the loop if a valid ship is selected
            }
        }

        List<String> availableContainerIDs = getContainerIDInPort(selectedPort);

        System.out.println("Available container in port: " + selectedPort.getID());
        if (availableContainerIDs.isEmpty()) {
            System.out.println("No available container!");
            return;
        }
        int selectedContainerOrderNumber = -1;
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
            String status = getStatusContainerbyID(containerID);

            if (status.equals("isLoaded=false")) {
                System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + "|" + status);
            }
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

        double totalWeight = round(getReeferTruckTotalContainerWeight(selectedReeferTruck,selectedPort) + selectedContainer.getWeight());
        if (selectedReeferTruck.loadContainer(selectedContainer) && selectedContainer.getContainerType().equals(ContainerType.REFRIGERATED))
        {

            System.out.println("Successfully loaded container " + selectedContainer.getID() + " onto vehicle " + selectedReeferTruck.getID());

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(true);
            selectedContainer.setPort(selectedReeferTruck.getCurrentPort());
            selectedContainer.updateStatusContainer(true);

            // Update the vehicleContainerMap
            //Read map
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            //Add container to truck
            vehicleContainerMap.computeIfAbsent(selectedReeferTruck.getID(), k -> new ArrayList<>()).add(selectedContainer.getID());
            List<ReeferTrucks> reeferTrucks = readReeferTruckFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            // Write the updated data back to the files
            writeReeferTruckToFile(reeferTrucks, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        } else if (selectedContainer.getContainerType().equals(ContainerType.LIQUID)){
            System.out.println("This Basic truck can not carry Liquid Container!");

        } else if (selectedContainer.getContainerType().equals(ContainerType.OPEN_SIDE)) {
            System.out.println("This Basic truck can not carry Open Side container!");
        } else if (selectedContainer.getContainerType().equals(ContainerType.OPEN_TOP)) {
            System.out.println("This Basic truck can not carry Open Top container!");
        }
        else if (selectedContainer.getContainerType().equals(ContainerType.DRY_STORAGE)) {
            System.out.println("This Basic truck can not carry Dry Storage container!");
        }
        else if (totalWeight < selectedReeferTruck.getCarryingCapacity()) {
            System.out.println("The total container weight in Basic Truck is larger than it capacity | " + "Total weight: "+ totalWeight + " Weight limit: " + selectedReeferTruck.getCarryingCapacity());
        }
        else {
            System.out.println("Fail to load! ");
        }


        // Define other methods (e.g., readPortsFromFile, displayAvailablePorts) here
    }







    //vehicle unload Container
    public static void unloadContainerShipMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableShipIDs = selectedPort.getShipsInPort();

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
            System.out.println("Successfully unloaded container " + selectedContainer.getID() + " onto vehicle " + selectedShip.getID());

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(false);
            selectedContainer.updateStatusContainer(false);

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
        } else {
            System.out.println("Failed to unload container.");
        }
    }

    public static void unloadContainerBasicTruckMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableBasicTruckIDs = selectedPort.getBasicTrucksInPort();
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
            System.out.println("Successfully unloaded container " + selectedContainer.getID() + " onto vehicle " + selectedBasicTruck.getID());

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(false);
            selectedContainer.updateStatusContainer(false);

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
        } else {
            System.out.println("Failed to unload container.");
        }
    }
    public static void unloadContainerTankerTruckMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableTankerTruckIDs = selectedPort.getTankerTrucksInPort();
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

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(false);
            selectedContainer.updateStatusContainer(false);

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
        } else {
            System.out.println("Failed to unload container.");
        }
    }

    public static void unloadContainerReeferTruckMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableReeferTruckIDs = selectedPort.getReeferTrucksInPort();
        System.out.println("Available reefer truck in port " + selectedPort.getName());
        for (int i = 0; i < availableReeferTruckIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableReeferTruckIDs.get(i));
        }
        int selectedReeferTruckOrderID;
        ReeferTrucks selectedReeferTruck;
        String selectedBasicTruckNumber;
        while (true) {
            System.out.print("Choose a reefer by order number: ");
            selectedReeferTruckOrderID = scanner.nextInt();
            if (selectedReeferTruckOrderID < 1 || selectedReeferTruckOrderID > availableReeferTruckIDs.size()) {
                System.out.println("Wrong number. Please choose a valid ship order number.");
            } else {
                selectedBasicTruckNumber = availableReeferTruckIDs.get(selectedReeferTruckOrderID - 1);
                String reefertruckLine = getReeferTruckLineByreefertruckID(selectedBasicTruckNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
                selectedReeferTruck = getReeferTruckByLine(reefertruckLine);
                break; // Exit the loop if a valid ship is selected
            }
        }

        // Filter out the containers that are loaded on the selected ship
        Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        List<String> loadedContainerIDs = vehicleContainerMap.get(selectedReeferTruck.getID());
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
        if (selectedReeferTruck.unloadContainer(selectedContainer)) {
            System.out.println("Successfully unloaded container " + selectedContainer.getID() + " onto vehicle " + selectedReeferTruck.getID());

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(false);
            selectedContainer.updateStatusContainer(false);

            // Update the vehicleContainerMap
            vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
//            vehicleContainerMap.get(selectedShip.getID()).remove(selectedContainer.getID());
            List<ReeferTrucks> reeferTrucks = readReeferTruckFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            if (vehicleContainerMap.containsKey(selectedReeferTruck.getID())) {
                vehicleContainerMap.get(selectedReeferTruck.getID()).remove(selectedContainer.getID());
                if (vehicleContainerMap.get(selectedReeferTruck.getID()).isEmpty()) {
                    vehicleContainerMap.remove(selectedReeferTruck.getID());
                }
            }
            // Write the updated data back to the files
            writeReeferTruckToFile(reeferTrucks, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        } else {
            System.out.println("Failed to unload container.");
        }
    }

}
