package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.getShipByLine;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.getShipLineByShipID;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getContainerIDInPort;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeContainersToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeVehicleContainerMapToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipReadFile.readShipFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipWriteFile.writeShipToFile;

public class vehicleOperation {
    //vehicle Load container
    public static void loadContainerShipMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableShipIDs = selectedPort.getShipsInPort();

        System.out.println("Available ships in port " + selectedPort.getName() + ":");
        for (int i = 0; i < availableShipIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableShipIDs.get(i));
        }

        System.out.print("Choose a ship by order number: ");
        int selectedShipOrderID = scanner.nextInt();
        String selectedShipNumber = availableShipIDs.get(selectedShipOrderID - 1);
        String shipLine = getShipLineByShipID(selectedShipNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
//        System.out.println("Ship line: " + shipLine);

//        System.out.println(getShipByLine(shipLine));
        Ship selectedShip = getShipByLine(shipLine);
//        System.out.println("Ship :" + selectedShip);
        //container select
        List<String> availableContainerIDs = getContainerIDInPort(selectedPort);
        System.out.println("Available container in port: " + selectedPort.getID() + ":");
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
            String status;
            if (getStatusContainerbyID(containerID).equals("isLoaded=false")) {
                status = "Available";
            } else {
                status = "Unavailable";
            }

            System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + "| Can load? : "+ status);
        }
        int selectedContainerOrderNumber = scanner.nextInt();
        String selectedContainerNumber = availableContainerIDs.get(selectedContainerOrderNumber - 1);
//        System.out.println("Container Number: " + selectedContainerNumber);
        System.out.println();
        String containerLine = getContainerLineByContainerID(selectedContainerNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
//        System.out.println("Container Line" + containerLine);

//        System.out.println("Get container: " + getContainerByLine(containerLine));

        Container selectedContainer = getContainerByLine(containerLine);

        //Load function
        // Try to load the container onto the vehicle

        if (selectedShip.loadContainer(selectedContainer)) {
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
        } else {
            System.out.println("Failed to load container.");
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

        System.out.print("Choose a ship by order number: ");
        int selectedShipOrderNumber = scanner.nextInt();
        String selectedShipNumber = availableShipIDs.get(selectedShipOrderNumber - 1);
        String shipLine = getShipLineByShipID(selectedShipNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
        System.out.println("Ship line: " + shipLine);

//        System.out.println(getShipByLine(shipLine));
        Ship selectedShip = getShipByLine(shipLine);
        System.out.println("Ship :" + selectedShip);

        //container select

        List<String> availableContainerIDs = getContainerIDInPort(selectedPort);
        System.out.println("Available container in port: " + selectedPort.getID() + ":");
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
            String status;
            if (getStatusContainerbyID(containerID).equals("isLoaded=true")) {
                status = "Available";
            } else {
                status = "Unavailable";
            }

            System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + "| Can unload? : "+ status);
        }
        System.out.println("Choose a container by order number: ");
        int selectedContainerOrderNumber = scanner.nextInt();
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
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            vehicleContainerMap.get(selectedShip.getID()).remove(selectedContainer.getID());
            List<Ship> Ship = readShipFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            // Write the updated data back to the files
            writeShipToFile(Ship, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        } else {
            System.out.println("Failed to unload container.");
        }
    }


}
