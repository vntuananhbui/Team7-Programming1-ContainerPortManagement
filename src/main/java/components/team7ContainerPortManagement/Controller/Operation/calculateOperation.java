package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Math.round;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.getBasicTruckLineBybasictruckID;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.getTankerTruckByLine;
import static src.main.java.components.team7ContainerPortManagement.Controller.containerController.*;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getVehiclesByPortID;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle.getVehicleByVehicleID;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readAvailablePortsFromFile;

public class calculateOperation {
    public static double calculateFuelConsumption(Port currentPort, Port selectedPort ,String selectedVehicleID, Vehicle selectedVehicle) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//// Load available ports from port.txt and display them here
//        List<Port> availablePorts = null;
//        //Read all available port except current port
//        availablePorts = readAvailablePortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt", currentPort);
//        System.out.println("Choose a port by order number: ");
//        int selectedPortOrderNumber = 1;
//        for (Port port : availablePorts) {
//            if (!port.equals(currentPort)) {
//                System.out.println((selectedPortOrderNumber++) + ". Port ID: '" + port.getID() + "', Port Name: '" + port.getName() + "'");
//            }
//        }
//        int selectedPortIndex = scanner.nextInt() - 1;
//        Port selectedPort = availablePorts.get(selectedPortIndex);
//        System.out.println("Selected Port: " + selectedPort);
//
//        List<String> availableVehicleIDs = getVehiclesByPortID(currentPort.getID());
//        System.out.println("Available vehicle in port " + currentPort.getName() + ":");
//
//        for (int i = 0; i < availableVehicleIDs.size(); i++) {
//            System.out.println((i + 1) + ": " + availableVehicleIDs.get(i));
//        }
//        int selectedVehicleOrderID;
//        Vehicle selectedVehicle;
//        String selectedVehicleNumber;
//        while (true) {
//            System.out.print("Choose a Vehicle by order number: ");
//            selectedVehicleOrderID = scanner.nextInt();
//            if (selectedVehicleOrderID < 1 || selectedVehicleOrderID > availableVehicleIDs.size()) {
//                System.out.println("Wrong number. Please choose a valid ship order number.");
//            } else {
//                selectedVehicleNumber = availableVehicleIDs.get(selectedVehicleOrderID - 1);
//                String vehicleline = getBasicTruckLineBybasictruckID(selectedVehicleNumber, "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
//                selectedVehicle = Vehicle.getVehicleByLine(vehicleline);
//                break; // Exit the loop if a valid ship is selected
//            }
//        }

        double fuelConsumption;
//        String selectedVehicleID = selectedVehicle.getID();
        if (selectedVehicleID.startsWith("sh-")) {
//            System.out.println("Selected Vehicle: " + selectedVehicle.getID());
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            double distancePort = currentPort.calculateDistanceTo(selectedPort);
            List<String> containerIDs = vehicleContainerMap.get(selectedVehicle.getID());
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            if (containerIDs == null || containerIDs.isEmpty()) {
                fuelConsumption = 3.5;
                return fuelConsumption * distancePort;
            }
//            System.out.println("container in vehiclecontainermap: " + containerIDs);

//            System.out.println("All container " + containers);
            System.out.println("Distance from: " + currentPort.getID() + " to " + selectedPort.getID() + " is: " + distancePort);
            double finalConsumption = getFinalShipConsumption(containerIDs);

            System.out.println("Consumption: " + finalConsumption);
            fuelConsumption = round(finalConsumption * distancePort);
            System.out.println("Fuel Consumption: " + fuelConsumption);
        } else {
            System.out.println("Selected Vehicle: " + selectedVehicle.getID());
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
            double distancePort = currentPort.calculateDistanceTo(selectedPort);
            List<String> containerIDs = vehicleContainerMap.get(selectedVehicle.getID());
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            if (containerIDs == null || containerIDs.isEmpty()) {
                fuelConsumption = 3.5;
                return fuelConsumption * distancePort;
            }
//            System.out.println("container in vehiclecontainermap: " + containerIDs);

//            System.out.println("All container " + containers);
            System.out.println("Distance from: " + currentPort.getID() + " to " + selectedPort.getID() + " is: " + distancePort);

            double finalConsumption = getFinalTruckConsumption(containerIDs);

            System.out.println("Consumption: " + finalConsumption);
            fuelConsumption = round(finalConsumption * distancePort);
            System.out.println("Fuel Consumption: " + fuelConsumption);

        }
        return fuelConsumption;
    }

    public static double calculateTotalWeightContainerPort(Port currentPort) throws IOException {
        double totalWeightContainerInPort = 0;
        List<String> availableVehicleIDs = getVehiclesByPortID(currentPort.getID());
        Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        System.out.println("Available vehicle in port: "+availableVehicleIDs);
        for (String vehicle : availableVehicleIDs) {
            Vehicle selectedVehicle = getVehicleByVehicleID(vehicle);
            List<String> containerIDs = vehicleContainerMap.get(selectedVehicle.getID());
            if (containerIDs == null || containerIDs.isEmpty()) {
                totalWeightContainerInPort += 0;
            } else {
                for (String container : containerIDs) {
                    Container ObjectContainerList = getContainerByID(container);
                    totalWeightContainerInPort += ObjectContainerList.getWeight();
                }
            }
        }
        System.out.println("Total weight"+totalWeightContainerInPort);
        return totalWeightContainerInPort;
    }



    }
