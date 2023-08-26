package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import src.main.java.components.team7ContainerPortManagement.Controller.portController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.calculateFuelConsumption;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.getBasicTruckLineBybasictruckID;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.getTankerTruckByLine;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getVehiclesByPortID;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle.*;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.readContainersFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.readVehicleContainerMapFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portWriteFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.ReeferTruckFileUtils.reefertruckReadFile.readReeferTruckFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ReeferTruckFileUtils.reefertruckWriteFile.writeReeferTruckToFile;

public class moveTo {

    public static void moveToMenu(Port currentPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
// Load available ports from port.txt and display them here
        List<Port> availablePorts = null;
        //Read all available port except current port
        availablePorts = readAvailablePortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt", currentPort);
        System.out.println("Choose a port by order number: ");
        int selectedPortOrderNumber = 1;
        for (Port port : availablePorts) {
            if (!port.equals(currentPort)) {
                System.out.println((selectedPortOrderNumber++) + ". Port ID: '" + port.getID() + "', Port Name: '" + port.getName() + "'");
            }
        }
        int selectedPortIndex = scanner.nextInt() - 1;
        Port selectedPort = availablePorts.get(selectedPortIndex);
        System.out.println("Selected Port: " + selectedPort);

        List<String> availableVehicleIDs = getVehiclesByPortID(currentPort.getID());
        System.out.println("Available vehicle in port " + currentPort.getName() + ":");

        for (int i = 0; i < availableVehicleIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableVehicleIDs.get(i));
        }
        int selectedVehicleOrderID;
        Vehicle selectedVehicle;
        String selectedVehicleNumber;
        while (true) {
            System.out.print("Choose a Vehicle by order number: ");
            selectedVehicleOrderID = scanner.nextInt();
            if (selectedVehicleOrderID < 1 || selectedVehicleOrderID > availableVehicleIDs.size()) {
                System.out.println("Wrong number. Please choose a valid ship order number.");
            } else {
                selectedVehicleNumber = availableVehicleIDs.get(selectedVehicleOrderID - 1);
                String tankertruckLine = getBasicTruckLineBybasictruckID(selectedVehicleNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
                selectedVehicle = getVehicleByLine(tankertruckLine);
                break; // Exit the loop if a valid ship is selected
            }
        }
        double fuelRequire = calculateFuelConsumption(currentPort,selectedPort,selectedVehicle.getID(),selectedVehicle);
        if (selectedVehicle.canMoveTo(selectedPort) &&  fuelRequire <= selectedVehicle.getFuelCapacity()) {
//        System.out.println("Vehicle current port: "+selectedVehicle.getCurrentPort());
//        System.out.println("Destination port: " + selectedPort);
//        selectedVehicle.setCurrentPort(selectedPort); //Change the file vehicle.txt
            Map<String, List<String>> vehiclePortMap = readVehiclePortMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_vehicles.txt");
            Map<String, List<String>> containerPortMap = readPortContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt");
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            System.out.println("Before map: " +vehiclePortMap);
                // Retrieve the list of containers associated with the vehicle
                List<String> containerIDs = vehicleContainerMap.get(selectedVehicle.getID());
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
                System.out.println("selected vehicle: "+selectedVehicle.getID());
                currentPortVehicles.remove(selectedVehicle.getID());
                vehiclePortMap.put(currentPort.getID(),currentPortVehicles);
            if (selectedPortIDs == null) {
                selectedPortIDs = new ArrayList<>();
                vehiclePortMap.put(selectedPort.getID(), currentPortVehicles);
                System.out.println("IF: Selected Port ID: "+selectedPortIDs);
            } else {
                try {
                    selectedPortIDs.add(selectedVehicle.getID());
                    System.out.println("ELSE: Selected Port ID: "+selectedPortIDs);
                } catch (UnsupportedOperationException e) {
                    List<String> mutableList = new ArrayList<>(selectedPortIDs);
                    mutableList.add(selectedVehicle.getID());
                    vehiclePortMap.put(selectedPort.getID(), mutableList);
                }
            }

            System.out.println("After map: " + vehiclePortMap);
            System.out.println();
            updateVehiclePort(selectedVehicle.getID(),selectedPort.getID());
            writeVehiclePortMapInFile(vehiclePortMap, "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_vehicles.txt");
            writePortContainerMapToFile(containerPortMap,"src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt");

            System.out.println("Fuel require: " + fuelRequire);
            System.out.println("Current fuel: " + selectedVehicle.getFuelCapacity());
            }



//

     else if (fuelRequire > selectedVehicle.getFuelCapacity()){
            System.out.println("Ship current Fuel not enought to move to " + selectedPort.getID());
            System.out.println("Fuel require: " + fuelRequire);
            System.out.println("Current fuel: " + selectedVehicle.getFuelCapacity());

        } else {
        System.out.println("Fail to move");
            System.out.println("Fuel require: " + fuelRequire);
            System.out.println("Current fuel: " + selectedVehicle.getFuelCapacity());

        }
}
}