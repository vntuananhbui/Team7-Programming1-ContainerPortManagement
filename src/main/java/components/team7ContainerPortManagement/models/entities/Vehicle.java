package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;
import src.main.java.components.team7ContainerPortManagement.models.interfaces.VehicleOperations;

import java.io.*;
import java.util.*;

import static java.awt.Color.red;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.calculateFuelConsumption;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.getBasicTruckLineBybasictruckID;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.getReeferTruckByLine;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Container.getTotalContainerWeightByPort;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Container.getTotalWeightOfContainersInVehicle;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getVehiclesByPortID;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.readVehicleContainerMapFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portWriteFile.writeVehiclePortMapInFile;

public abstract class Vehicle implements VehicleOperations {
    protected String ID;
    protected String name;
    protected double currentFuel;
    protected double carryingCapacity;
    protected double fuelCapacity;
    protected Port currentPort;
    protected  double fuelConsumtion;
    protected List<Container> containers;
    protected double currentLoad;




    public Vehicle(String ID, String name, double currentFuel, double carryingCapacity, double fuelCapacity,double fuelConsumtion, Port currentPort) {
        this.ID = ID;
        this.name = name;
        this.currentFuel = currentFuel;
        this.carryingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = currentPort;
        this.fuelConsumtion = fuelConsumtion;

    }


    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getCurrentFuel() {
        return currentFuel;
    }

    public double getCarryingCapacity() {
        return carryingCapacity;
    }

    public double getFuelCapacity() {
        return fuelCapacity;
    }

    public Port getCurrentPort() {
        return currentPort;
    }

    public List<Container> getContainers() {
        return containers;
    }

//    @Override
//    public abstract boolean canLoadContainer(Container container);


//    public boolean loadContainer(Container container) {
//        if(canLoadContainer(container) && !container.isLoaded() && container.getPort() == this.currentPort || container.getPort() == null) {
//            containers.add(container);
//            container.setLoaded(true);
//            container.setPort(this.currentPort);
//            System.out.println(container.getID() + ", Type: " + container.getContainerType() + " has been added");
//            return true;
//        }
//        System.out.println("The container Type: " + container.getContainerType() + " is currently onload, please unload first");
//        return false;
//    }

    public boolean loadContainer(Container container) {


        if (container.isLoaded()) {
            System.out.println("This container is already loaded onto another vehicle.");
            return false;
        }
        if (this.currentLoad + container.getWeight() > this.carryingCapacity) {
            System.out.println("Loading this container would exceed the vehicle's carrying capacity.");
            return false;
        }


        if (!this.currentPort.getID().equals(container.getPort().getID())) {
            System.out.println("ship port: " +this.currentPort.getID());
            System.out.println("Container port"+container.getPort().getID());
            System.out.println("The container and the vehicle are not in the same port.");
            return false;
        }
        container.setLoaded(true);
        this.currentLoad += container.getWeight();
        return true;
    }




    @Override
    public boolean unloadContainer(Container container) {
//        if (containers.contains(container)) {
//            // Unload the container from this vehicle
//            currentPort.addContainer(container);
//            containers.remove(container);
//            container.setLoaded(false); //container setload is false so it can load to another vehicle
//        } else {
//            // The container is not loaded onto this vehicle
//            System.out.println("Container is not be loaded in this vehicle.");
//            return false;
//        }
        if(!container.isLoaded()) {
            System.out.println("This container is not currently load on this vehicle");
            return false;
        }
        return true;
    }


    @Override
    public boolean canMoveTo(Port destination) {
        if (this.getID().startsWith("sh-")) {
            return true;
        }
        if (!(this.getID().startsWith("sh-")) && (!destination.isLandingAbility() || !this.currentPort.isLandingAbility())) {
            // The vehicle can move because it's not a ship (doesn't start with "sh-")
            // and either the destination port or the current port has landing ability.
            System.out.println("This vehicle cannot move to a port that does not have landing ability!");
            return false;
        }
        try {
            double fuelRequire = calculateFuelConsumption(currentPort,destination,this.getID(),this);
            if(fuelRequire > this.getCurrentFuel()) {
                System.out.println("Error!!!!");
                System.out.println("Fuel require is larger than current fuel");
                System.out.println("Fuel require: " + fuelRequire);
                System.out.println("Current fuel: " + this.getCurrentFuel());
                return false;
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;

    }


//        double fuelRequired = 0;
//
//        double distance = currentPort.calculateDistanceTo(destination);
//
//        for (Container container : this.containers) {
//            fuelRequired += getFuelConsumptionPerKm(container) * distance;
//
//        }
//        System.out.println("Distance: " + distance);
//        //Check if container is empty so use the consumption of vehicle if container is loaded it use container consumption
//        if (this.containers.isEmpty()) {
//            fuelRequired = this.fuelConsumtion * distance;
//        } else {
//            for (Container container : this.containers) {
//                fuelRequired += getFuelConsumptionPerKm(container) * distance;
//            }
//        }
//        System.out.println("Fuel Required: " +fuelRequired);
//        if (canMoveTo(destination) && fuelRequired <= this.currentFuel) {
//            // Start a new trip
//            Trip trip = new Trip(this, this.currentPort, destination);
//            trip.start();
//            this.currentPort.addTrip(trip);
//            this.currentFuel -= fuelRequired;
//            // Move the vehicle to the destination port
//            this.currentPort = destination;
////            System.out.println("fuel require "+fuelRequired);
//            // Complete the trip
//            for (Container container : containers) {
//                container.setPort(currentPort);
//            }
//            System.out.println("Vehicle move to " + destination.getName() + " successfully");
//
//            destination.addVehicle(this);
//            destination.addTrip(trip);
//
//            trip.complete();
//
//        } else {
//            System.out.println("Current fuel : " + currentFuel + " is not enough to move to destination , require are: " + fuelRequired);
//        }


//    @Override
//    public void refuel(double amount) {
//        this.currentFuel += Math.min(amount, this.fuelCapacity - this.currentFuel);
//    }
//    public void showLoadedContainers() {
//        if(containers.isEmpty()) {
//            System.out.println(this.name + " is currently not carrying any containers.");
//            return;
//        }
//
//        System.out.println(this.name + " is carrying the following containers:");
//        for(Container container : containers) {
//            System.out.println("Container ID: " + container.getID() + ", Type: " + container.getContainerType());
//        }
//    }

    @Override
    public String toString() {
        return "Vehicle{ID='" + ID + "', name='" + name + "', currentFuel=" + currentFuel
                + ", carryingCapacity=" + carryingCapacity + ", fuelCapacity=" + fuelCapacity
                + ", currentPort=" + (currentPort != null ? currentPort.getID() : "null") + "}";
    }

    public abstract double getFuelConsumptionPerKm(Container container);

//SET CURRENT PORT
    public void setCurrentPort(Port newPort) {
        if (currentPort != null) {
            currentPort.removeVehicle(this); // Remove the vehicle from the current port's list
        }
        currentPort = newPort;
        if (currentPort != null) {
            currentPort.addVehicle(this); // Add the vehicle to the new port's list
            this.currentPort = currentPort;
        }
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCurrentFuel(double currentFuel) {
        this.currentFuel = currentFuel;
    }

    public void setCarryingCapacity(double carryingCapacity) {
        this.carryingCapacity = carryingCapacity;
    }

    public void setFuelCapacity(double fuelCapacity) {
        this.fuelCapacity = fuelCapacity;
    }

    public void setFuelConsumtion(double fuelConsumtion) {
        this.fuelConsumtion = fuelConsumtion;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public static Vehicle getVehicleByLine(String line) throws IOException {
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
        Vehicle vehicle = new Vehicle(id, name, currentFuel, carryingCapacity, fuelCapacity, 3.5, currentPort) {
//            @Override
//            public boolean canLoadContainer(Container container) {
//                return false;
//            }

            @Override
            public double getFuelConsumptionPerKm(Container container) {
                return 0;
            }
        };
        //Vehicle(String ID, String name, double currentFuel, double carryingCapacity, double fuelCapacity,double fuelConsumtion, Port currentPort)
        vehicle.setID(id);
        vehicle.setName(name);
        vehicle.setCurrentFuel(currentFuel);
        vehicle.setCarryingCapacity(carryingCapacity);
        vehicle.setFuelCapacity(fuelCapacity);
        // Assuming you have a method to get a Port object by its ID
        vehicle.setCurrentPort(getPortByID(currentPortID));

        return vehicle;
    }

    public static void writeVehicleToFile(List<Vehicle> vehicles, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Vehicle vehicle : vehicles) {
                writer.write(vehicle.toString());
                writer.newLine();
            }
        }
    }
    public static List<Vehicle> readVehiclesFromFile(String fileName) throws IOException {
        List<Vehicle> vehicles = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Vehicle vehicle = getReeferTruckByLine(line);
                vehicles.add(vehicle);
            }
        }
        return vehicles;
    }
    public static void writeVehiclePortMapToFile(Map<String, List<String>> map, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                writer.write("{" + entry.getKey() + ": " + String.join(", ", entry.getValue()) + "}");
                writer.newLine();
            }
        }
    }
    public static void updateVehiclePort(String vehicleID, String newPortID) throws IOException {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt";

        // Read the contents of the file into memory
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // Find the line corresponding to the vehicle and update the port
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("Vehicle{ID='" + vehicleID + "'")) {
                lines.set(i, lines.get(i).replace("currentPort=" + getCurrentPort(vehicleID, lines), "currentPort=" + newPortID));
                break;
            }
        }

        // Write the updated contents back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
    private static String getCurrentPort(String vehicleID, List<String> lines) {
        for (String line : lines) {
            if (line.contains("Vehicle{ID='" + vehicleID + "'")) {
                int startIndex = line.indexOf("currentPort=") + "currentPort=".length();
                int endIndex = line.indexOf(",", startIndex);
                if (endIndex == -1) {
                    endIndex = line.indexOf("}", startIndex);
                }
                return line.substring(startIndex, endIndex).trim();
            }
        }
        return null;
    }
    public static Vehicle getVehicleByVehicleID(String vehicleID) throws IOException {
        List<Vehicle> vehicles = readVehiclesFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt");
        for (Vehicle vehicle : vehicles) {
            if (vehicle.getID().equals(vehicleID)) {
                return vehicle;
            }
        }
        return null; // Return null if no vehicle is found with the given ID
    }
    public static void updateFuel(String vehicleID, double newFuel) throws IOException {
        List<String> lines = new ArrayList<>();

        // Read the entire file
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Check if the line contains the vehicleID we want to update
                if (line.contains("ID='" + vehicleID + "'")) {
                    // Replace the currentFuel value
                    line = replaceFuelValue(line, newFuel);
                }
                lines.add(line);
//                System.out.println("Update fuel complete!");
            }
        }

        // Write the modified content back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt"))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private static String replaceFuelValue(String line, double newFuel) {
        // Using regex to replace the currentFuel value. Assumes there's only one instance of "currentFuel=..." in the line.
        return line.replaceAll("currentFuel=[0-9]+\\.?[0-9]*", "currentFuel=" + newFuel);
    }
    public static void moveToMenu(Port currentPort) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String yellow = "\u001B[33m";
        String VEHICLE_ICON = "\uD83D\uDE97";  // 🚗
        String VEHICLE_Truck = "\uD83D\uDE9A";
        String reset = "\u001B[0m";
        String VEHICLE_ship = "\uD83D\uDEA2";
        String PORT_ICON = "\uD83C\uDFED";
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
        // Handling invalid port choice
        int selectedPortIndex;
        while (true) {
            try {
                selectedPortIndex = scanner.nextInt() - 1;
                if (selectedPortIndex >= 0 && selectedPortIndex < availablePorts.size()) {
                    break;
                } else {
                    System.out.println("Invalid choice. Please select a number between 1 and " + availablePorts.size());
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        Port selectedPort = availablePorts.get(selectedPortIndex);
        System.out.println("Selected Port: " + selectedPort);
        List<String> availableVehicleIDs = getVehiclesByPortID(currentPort.getID());
        System.out.println("Available vehicle in port " + currentPort.getName());
        if (availableVehicleIDs == null || availableVehicleIDs.isEmpty()) {
//            System.out.println("There is no Basic Truck in " + selectedPort.getID() + " port!");
            System.out.println(red+"╔══════════════════════════════════════════════╗");
            System.out.println(red+"║                    Error                     ║");
            System.out.println(red+"║──────────────────────────────────────────────║" + reset);
            System.out.println("                                              ");
            System.out.println("          No Vehicle in " + selectedPort.getID() + " port!         ");
            System.out.println("                                              ");
            System.out.println("╚══════════════════════════════════════════════╝");
            System.out.println("Press any key to return...: ");
            scanner.next();
            return;
        }
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
        double storageRequire = getTotalContainerWeightByPort(selectedPort.getID());
        double fuelRequire = calculateFuelConsumption(currentPort,selectedPort,selectedVehicle.getID(),selectedVehicle);
//        if (selectedVehicle.canMoveTo(selectedPort) &&  fuelRequire <= selectedVehicle.getCurrentFuel()) {
        if (selectedVehicle.canMoveTo(selectedPort)) {
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
            List<String> currentContainerVehicle = vehicleContainerMap.get(selectedVehicle.getID());
            System.out.println("currentport container: " + currentPortContainers);
            System.out.println("current container vehicle: " + currentContainerVehicle);
            double totalWeightInVehicle = getTotalWeightOfContainersInVehicle(currentContainerVehicle);
            double weightNeedtoMove = selectedPort.getStoringCapacity() -getTotalContainerWeightByPort(selectedPort.getID());
            System.out.println("Total Weight in Vehicle: " + totalWeightInVehicle);
            System.out.println("Selected port avaialble weight: " + getTotalContainerWeightByPort(selectedPort.getID()));
            System.out.println("Selected port available weight to move: " + weightNeedtoMove);
            if (currentContainerVehicle != null) {
                if (weightNeedtoMove < totalWeightInVehicle) {
                    System.out.println("This vehicle carry very large weight of Container, move Cancel!");
                    System.out.println("Limit weight: " + weightNeedtoMove);
                    System.out.println("Current total weight: " + totalWeightInVehicle);
                    return;
                }
            }
            if (currentContainerVehicle != null) {
                if (currentPortContainers != null) {
                    currentPortContainers = new ArrayList<>(currentPortContainers);
                } else {
                    currentPortContainers = new ArrayList<>();
                }
                currentPortContainers.removeAll(containerIDs);

//                containerPortMap.computeIfAbsent(selectedPort.getID(), k -> new ArrayList<>()).addAll(containerIDs);
//                containerPortMap.put(currentPort.getID(), currentPortContainers);
                // Check if the port already exists in the map
                // Check if the port already exists in the map
                for (String containerID : containerIDs) {
                    currentPortContainers.remove(containerID);
                }
                System.out.println("*****After remove!"+currentPortContainers);
                System.out.println("Before Container Port map: " + containerPortMap);

                writePortContainersToFile(currentPort.getID(), currentPortContainers);
                // Add containers to the new port's container list
                if (containerPortMap.containsKey(selectedPort.getID())) {
                    List<String> existingContainers = containerPortMap.get(selectedPort.getID());
                    List<String> mutableContainers = new ArrayList<>(existingContainers); // Create a modifiable list
                    mutableContainers.addAll(containerIDs);
                    containerPortMap.put(selectedPort.getID(), mutableContainers);
                    System.out.println("IF: Container Port map: " + containerPortMap);
                } else {
                    // Port doesn't exist, create a new entry
                    containerPortMap.put(selectedPort.getID(), new ArrayList<>(containerIDs));
                    System.out.println("ELSE: Container Port map: " + containerPortMap);
                }
                if (containerPortMap.containsKey(currentPort.getID())) {
                    List<String> currentPortContainersMap = containerPortMap.get(currentPort.getID());

                    // Create a modifiable copy of the list
                    List<String> modifiableContainersMap = new ArrayList<>(currentPortContainersMap);

                    // Remove containers from the modifiable list
                    modifiableContainersMap.removeAll(containerIDs);

                    // Update the containerPortMap with the modified list
                    containerPortMap.put(currentPort.getID(), modifiableContainersMap);
                }
                writePortContainersToFile(selectedPort.getID(), containerPortMap.get(selectedPort.getID()));
                for (String containerID : containerIDs) {
                    try {
                        updateContainerPort(containerID, selectedPort.getID(), selectedPort);
                        System.out.println("Updated container " + containerID + " to port " + selectedPort.getID());
                    } catch (IOException e) {
                        System.out.println("Error updating container " + containerID + " port: " + e.getMessage());
                    }
                }
            }
//            System.out.println("currentport vehicle: " + currentPortVehicles);
//                System.out.println("selected vehicle: "+selectedVehicle.getID());
            currentPortVehicles.remove(selectedVehicle.getID());
//            System.out.println("Map1 :" + vehiclePortMap);
//            System.out.println("After remove port vehicle: " + currentPortVehicles);
            vehiclePortMap.put(currentPort.getID(),currentPortVehicles);
//            System.out.println("Map2 :" + vehiclePortMap);
//            System.out.println("After remove port vehicle: " + currentPortVehicles);
            if (vehiclePortMap.get(currentPort.getID()).isEmpty()) {
                vehiclePortMap.remove(currentPort.getID());
            }
            if (selectedPortIDs == null) {
                selectedPortIDs = new ArrayList<>();
                selectedPortIDs.add(selectedVehicle.getID());
                vehiclePortMap.put(selectedPort.getID(), selectedPortIDs);
//                System.out.println("in if vehicleport : " + currentPortVehicles);
//                System.out.println("IF: Selected Port ID: "+selectedPortIDs);
            } else {
                try {
                    selectedPortIDs.add(selectedVehicle.getID());
//                    System.out.println("ELSE: Selected Port ID: "+selectedPortIDs);
                } catch (UnsupportedOperationException e) {
                    List<String> mutableList = new ArrayList<>(selectedPortIDs);
                    mutableList.add(selectedVehicle.getID());
                    vehiclePortMap.put(selectedPort.getID(), mutableList);
                }
            }
            double afterMoveFuel = selectedVehicle.getCurrentFuel() -fuelRequire;



//            Trip newTrip = new Trip(selectedVehicle, selectedVehicle.getCurrentPort(), selectedPort);
            Trip newTrip = new Trip(selectedVehicle,selectedVehicle.getCurrentPort(),selectedPort,fuelRequire);
            newTrip.start();
            newTrip.tripComplete();
            System.out.println("After map: " + vehiclePortMap);
            System.out.println();

            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 VEHICLE MOVE CONFRIMATION" + "              ║" + ANSI_RESET);
            System.out.println("╟────────────────────────────────────────────────────────╢");
            System.out.println( "            " + selectedVehicle.getID() + " " + VEHICLE_ship + yellow + " =======> " + ANSI_GREEN + selectedPort.getID() + " " + PORT_ICON + "   " + ANSI_RESET);
            System.out.println("  Fuel Consumption:    " + fuelRequire);
            System.out.println("  Time of Departure:   " + newTrip.getDepartureDate() + "        " );
            System.out.println("  Time of Arrival:     " + newTrip.getArrivalDate() + "        " );
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝");

            updateVehiclePort(selectedVehicle.getID(),selectedPort.getID());
            writeVehiclePortMapInFile(vehiclePortMap, "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_vehicles.txt");
            writePortContainerMapToFile(containerPortMap,"src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt");

//            System.out.println("Fuel require: " + fuelRequire);
//            System.out.println("Current fuel: " + selectedVehicle.getCurrentFuel());
            updateFuel(selectedVehicle.getID(),afterMoveFuel);

        }
//        else if (fuelRequire > selectedVehicle.getCurrentFuel()){
//            System.out.println("Fuel require is larger than current fuel");
//            System.out.println("Fuel require: " + fuelRequire);
//            System.out.println("Current fuel: " + selectedVehicle.getCurrentFuel());
//
//        }
        else {
            System.out.println("Fail to move");
            System.out.println("Fuel require: " + fuelRequire);
            System.out.println("Current fuel: " + selectedVehicle.getCurrentFuel());

        }
    }
}
