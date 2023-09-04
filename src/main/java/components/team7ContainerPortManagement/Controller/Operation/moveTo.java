package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.getVehicleTextLine;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.getTankerTruckByLine;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getVehiclesByPortID;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle.updateVehiclePort;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.readVehicleContainerMapFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.updateContainerPort;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writePortContainerMapToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portWriteFile.writeVehiclePortMapInFile;

public class moveTo {

    public static void moveToMenu(Port currentPort) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Load available ports from port.txt and displays them for selection
        List<Port> availablePorts = null;
        availablePorts = readAvailablePortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt", currentPort);
        System.out.println("Choose a port by order number: ");
        int selectedPortOrderNumber = 1;
        for (Port port : availablePorts) {
            if (!port.equals(currentPort)) {
                System.out.println((selectedPortOrderNumber++) + ". Port ID: '" + port.getID() + "', Port Name: '" + port.getName() + "'");
            }
        }

        // Displays the current selected port
        int selectedPortIndex = scanner.nextInt() - 1;
        Port selectedPort = availablePorts.get(selectedPortIndex);
        System.out.println("Selected Port: " + selectedPort);

        //Gets the available Vehicle ID at current port (via the vehicle text file) and displays them to the console
        List<String> availableVehicleIDs = getVehiclesByPortID(currentPort.getID());
        System.out.println("Available vehicle in port " + currentPort.getName() + ":");
        for (int i = 0; i < availableVehicleIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableVehicleIDs.get(i));
        }

        int selectedVehicleOrderID;
        Vehicle selectedVehicleObject;
        String selectedVehicleID;
        while (true) {
            System.out.print("Choose a Vehicle by order number: ");
            selectedVehicleOrderID = scanner.nextInt();
            // If the input is an invalid integer, print an error message and repeat the loop
            if (selectedVehicleOrderID < 1 || selectedVehicleOrderID > availableVehicleIDs.size()) {
                System.out.println("Invalid number. Please choose a valid vehicle number");
            } else {
                selectedVehicleID = availableVehicleIDs.get(selectedVehicleOrderID - 1);
                String vehicleTextLine = getVehicleTextLine(selectedVehicleID, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
                selectedVehicleObject = getTankerTruckByLine(vehicleTextLine);
                break; // Exit the loop if a valid ship is selected
            }
        }
        if (selectedVehicleObject.canMoveTo(selectedPort)) {
//        System.out.println("Vehicle current port: "+selectedVehicle.getCurrentPort());
//        System.out.println("Destination port: " + selectedPort);
//        selectedVehicle.setCurrentPort(selectedPort); //Change the file vehicle.txt
            Map<String, List<String>> vehiclePortMap = readVehiclePortMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_vehicles.txt");
            Map<String, List<String>> containerPortMap = readPortContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt");
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            System.out.println("Before map: " + vehiclePortMap);
            // Retrieve the list of containers associated with the vehicle
            List<String> containerIDs = vehicleContainerMap.get(selectedVehicleObject.getID());
            List<String> currentPortVehicles = new ArrayList<>(vehiclePortMap.get(currentPort.getID()));
            List<String> currentPortContainers = containerPortMap.get(currentPort.getID());
            List<String> selectedPortIDs = vehiclePortMap.get(selectedPort.getID());
            System.out.println("currentport container: " + currentPortContainers);
            if (currentPortContainers != null) {
                currentPortContainers = new ArrayList<>(currentPortContainers);
                currentPortContainers.removeAll(containerIDs);
                containerPortMap.computeIfAbsent(selectedPort.getID(), k -> new ArrayList<>()).addAll(containerIDs);
                containerPortMap.put(currentPort.getID(), currentPortContainers);
                for (String containerID : containerIDs) {
                    try {
                        updateContainerPort(containerID, selectedPort.getID(), selectedPort);
                        System.out.println("Updated container " + containerID + " to port " + selectedPort.getID());
                    } catch (IOException e) {
                        System.out.println("Error updating container " + containerID + " port: " + e.getMessage());
                    }
                }
            }
            System.out.println("currentport vehicle: " + currentPortVehicles);
            System.out.println("selected vehicle: " + selectedVehicleObject.getID());
            currentPortVehicles.remove(selectedVehicleObject.getID());
            vehiclePortMap.put(currentPort.getID(), currentPortVehicles);
            if (selectedPortIDs == null) {
                selectedPortIDs = new ArrayList<>();
                vehiclePortMap.put(selectedPort.getID(), currentPortVehicles);
                System.out.println("IF: Selected Port ID: " + selectedPortIDs);
            } else {
                try {
                    selectedPortIDs.add(selectedVehicleObject.getID());
                    System.out.println("ELSE: Selected Port ID: " + selectedPortIDs);
                } catch (UnsupportedOperationException e) {
                    List<String> mutableList = new ArrayList<>(selectedPortIDs);
                    mutableList.add(selectedVehicleObject.getID());
                    vehiclePortMap.put(selectedPort.getID(), mutableList);
                }
            }
//                vehiclePortMap.computeIfAbsent(selectedPort.getID(), k -> new ArrayList<>()).add(selectedVehicle.getID());

            System.out.println("After map: " + vehiclePortMap);

            updateVehiclePort(selectedVehicleObject.getID(), selectedPort.getID());
            writeVehiclePortMapInFile(vehiclePortMap, "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_vehicles.txt");
            writePortContainerMapToFile(containerPortMap, "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt");

            //
//            Map<String, List<String>> newVehiclePortMap = processDataFromVehicleFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt", vehiclePortMap);
//            writeVehiclePortMapInFile(newVehiclePortMap, "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_vehicles.txt");
            // Remove the vehicle and its containers from the current port
//                System.out.println("current port: "+currentPort.getID());
//                System.out.println("selected vehicle: "+selectedVehicle.getID());
//                System.out.println("selected port: "+selectedPort.getID());
//                System.out.println("Vehicle Port Map: " + vehiclePortMap);
//                System.out.println("Current port vehicle: "+ currentPortVehicles);


//                selectedVehicle.setCurrentPort(selectedPort);

            // Add the vehicle and its containers to the selected port
//                currentPortVehicles.add(selectedVehicle.getID());

//                vehiclePortMap.computeIfAbsent(selectedPort.getID(), k -> new ArrayList<>()).add(selectedVehicle.getID());


        }


//        Test HashMap of containerPortMap
//        System.out.println("Container Port map: "  + containerPortMap);
//        System.out.println("Contains key: " + containerPortMap.keySet());
//        System.out.println(containerPortMap.containsKey(selectedPort.getID()));
//        System.out.println("value: " + containerPortMap.values());
//        Test Hashmap of vehiclePort map
//        System.out.println("Vehicle port map: " + vehiclePortMap);
//        System.out.println("Vehicle container map: " + vehicleContainerMap);
//        System.out.println("Contains Key: "+vehiclePortMap.keySet());
//        System.out.println(vehiclePortMap.containsKey(selectedPort.getID()));
//        System.out.println("Selected Port: "+selectedPort.getID());
//        System.out.println("Value: " + vehiclePortMap.values());
//        System.out.println("Vehicle ID: " + selectedVehicle.getID() );
//            if (vehiclePortMap.containsKey(selectedPort.getID())) {
//                vehiclePortMap.get(selectedPort.getID()).remove(selectedVehicle.getID());
//                if (vehiclePortMap.get(selectedPort.getID()).isEmpty()) {
//                    vehiclePortMap.remove(selectedPort.getID());
//                }
//            }
//            vehiclePortMap.computeIfAbsent(selectedPort.getID(), k -> new ArrayList<>()).add(selectedVehicle.getID());

        //Add vehicle to port_vehicles.txt
//            List<String> containerIDsInCurrentPort = containerPortMap.getOrDefault(currentPort.getID(), new ArrayList<>());
//            System.out.println(containerIDsInCurrentPort);
//            containerPortMap.put(selectedPort.getID(), containerIDsInCurrentPort); //port_container.txt
//            containerPortMap.put(currentPort.getID(), new ArrayList<>());
//            List<String> selectedPortContainerIDs = containerPortMap.computeIfAbsent(selectedPort.getID(), k -> new ArrayList<>());
        //
//            containerPortMap.put(selectedPort.getID(), selectedPortContainerIDs);
// Clear the list of container IDs for the current port
//            containerPortMap.put(currentPort.getID(), new ArrayList<>());


        else {
            System.out.println("Fail to move");
        }
    }

    public static boolean canMoveTo(Port currentPort, Port destinationPort) throws IOException {
        return true;
    }
}