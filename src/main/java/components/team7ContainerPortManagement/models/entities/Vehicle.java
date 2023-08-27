package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;
import src.main.java.components.team7ContainerPortManagement.models.interfaces.VehicleOperations;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.calculateFuelConsumption;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.getReeferTruckByLine;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByID;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByOrderNumber;

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

//        if (!currentPort.hasLandingAbility() || !destination.hasLandingAbility()) {
//            return false;
//        }
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
                System.out.println("Update fuel complete!");
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

}
