package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.interfaces.VehicleOperations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static src.main.java.components.team7ContainerPortManagement.views.Containers.InputContainer.getPortByID;
import static src.main.java.components.team7ContainerPortManagement.views.Containers.InputContainer.updateContainerLoadFile;

public abstract class Vehicle implements VehicleOperations {
    protected String ID;
    protected String name;
    protected double currentFuel;
    protected double carryingCapacity;
    protected double fuelCapacity;
    protected Port currentPort;
    protected  double fuelConsumtion;
    protected List<Container> containers;



    public static List<Vehicle> loadShipDataFromFile(String filePath) throws IOException {
        List<Vehicle> ship = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Vehicle{")) {
                    String vehicleID = extractValue(line, "ID=sh-'");
                    String vehicleName = extractValue(line, "name='");
                    double currentFuel = Double.parseDouble(extractValue(line, "currentFuel="));
                    double carryingCapacity = Double.parseDouble(extractValue(line, "carryingCapacity="));
                    double fuelCapacity = Double.parseDouble(extractValue(line, "fuelCapacity="));
                    double fuelConsumtion = Double.parseDouble(extractValue(line, "fuelConsumtion="));
                    String portID = extractValue(line, "currentPort={ID=");

                    Port currentPort = getPortByID(portID); // Implement this method to get the port by ID

                    ship.add(new Ship(vehicleID, vehicleName, currentFuel, carryingCapacity, fuelCapacity, fuelConsumtion, currentPort));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Vehicle file not found.");
            e.printStackTrace();
        }

        return ship;
    }
    public List<BasicTruck> loadBasicTruckDataFromFile(String filePath) throws IOException {
        List<BasicTruck> basicTruck = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Vehicle{")) {
                    String vehicleID = extractValue(line, "ID=btr'");
                    String vehicleName = extractValue(line, "name='");
                    double currentFuel = Double.parseDouble(extractValue(line, "currentFuel="));
                    double carryingCapacity = Double.parseDouble(extractValue(line, "carryingCapacity="));
                    double fuelCapacity = Double.parseDouble(extractValue(line, "fuelCapacity="));
                    double fuelConsumtion = Double.parseDouble(extractValue(line, "fuelConsumtion="));
                    String portID = extractValue(line, "currentPort=");

                    Port currentPort = getPortByID(portID); // Implement this method to get the port by ID

                    basicTruck.add(new BasicTruck(vehicleID, vehicleName, currentFuel, carryingCapacity, fuelCapacity, fuelConsumtion, currentPort));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Vehicle file not found.");
            e.printStackTrace();
        }

        return basicTruck;
    }

    public static String extractValue(String line, String attribute) {
        int startIndex = line.indexOf(attribute) + attribute.length();
        int endIndex = line.indexOf("'", startIndex);
        return line.substring(startIndex, endIndex);
    }

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

    @Override
    public abstract boolean canLoadContainer(Container container);


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

//    public boolean loadContainer(Container container) {
//        //Calculate total weight
//        double totalWeight = container.getWeight();
//        for (Container container1 : containers) {
//            totalWeight +=  container1.getWeight();
//        }
//        System.out.println("Total weight: "+ totalWeight);
//        //Condition
//
//        if (this.carryingCapacity < totalWeight) {
//            System.out.println("The total container weight is higher than vehicle capacity, so it can not load");
//            return false;
//        }
//        if(canLoadContainer(container) && !container.isLoaded() && container.getPort() == this.currentPort || container.getPort() == null) {
//            containers.add(container); //add container to vehicle container list
//            container.setLoaded(true); //set container is loaded so it can not be load to another vehicle
//            container.setPort(this.currentPort); //set port of container
//            System.out.println(container.getID() + ", Type: " + container.getContainerType() + " has been added");
//            return true;
//        } else if(container.getPort() != this.currentPort || container.getPort() != null) {
//            System.out.println("This container is on the " + container.getPort().getName() + " so it can not load on this vehicle");
//            return false;
//        }
//
//        System.out.println("The container Type: " + container.getContainerType() + " is currently onload, please unload first");
//        return false;
//    }
public boolean loadContainer(Container container) {
    // Calculate total weight
    double totalWeight = container.getWeight();
    for (Container container1 : containers) {
        totalWeight += container1.getWeight();
    }
    System.out.println("Total weight: " + totalWeight);

    // Condition
    if (this.carryingCapacity < totalWeight) {
        System.out.println("The total container weight is higher than vehicle capacity, so it cannot be loaded");
        return false;
    }

    if (canLoadContainer(container) && !container.isLoaded() && (container.getPort() == this.currentPort || container.getPort() == null)) {
        containers.add(container); // Add container to vehicle container list
        container.setLoaded(true); // Set container is loaded so it cannot be loaded to another vehicle
        container.setPort(this.currentPort); // Set port of container
        System.out.println(container.getID() + ", Type: " + container.getContainerType() + " has been added");

        // Update the vehicle.txt file
        try {
            updateVehicleFile(this);
        } catch (IOException e) {
            System.out.println("Error updating vehicle file: " + e.getMessage());
        }

        // Update the vehicle_containerload.txt file
        try {
            updateContainerLoadFile(this);
        } catch (IOException e) {
            System.out.println("Error updating vehicle_containerload file: " + e.getMessage());
        }

        return true;
    } else if (container.getPort() != null && !container.getPort().equals(this.currentPort)) {
        System.out.println("This container is on port " + container.getPort().getName() + " so it cannot be loaded on this vehicle");
        return false;
    }

    System.out.println("The container Type: " + container.getContainerType() + " is currently loaded, please unload first");
    return false;
}


    public void updateVehicleFile(Vehicle vehicle) throws IOException {
        String filePath = "src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt";
        List<String> lines = new ArrayList<>(Files.readAllLines(Paths.get(filePath)));

        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.contains("ID='" + vehicle.getID() + "'")) {
                lines.set(i, vehicle.toString()); // Update the line with the new vehicle data
                break;
            }
        }

        Files.write(Paths.get(filePath), lines);
    }




    @Override
    public boolean unloadContainer(Container container) {
        if (containers.contains(container)) {
            // Unload the container from this vehicle
            currentPort.addContainer(container);
            containers.remove(container);
            container.setLoaded(false); //container setload is false so it can load to another vehicle
            return true;
        } else {
            // The container is not loaded onto this vehicle
            System.out.println("Container is not be loaded in this vehicle.");
            return false;
        }

    }


    @Override
    public boolean canMoveTo(Port destination) {
        if (currentPort == null || destination == null) {
            // The vehicle is currently sailing, so it cannot move to another port
            System.out.println("The current port or destination are null, so this vehicle can not move to");
            return false;
        }
        if (!currentPort.hasLandingAbility() || !destination.hasLandingAbility()) {
            // One or both of the ports do not have landing ability, so a truck cannot move between them
            System.out.println("The " + destination.getName() + " is not have landing ability yet for truck! ");
            return false;
        }
        if (!currentPort.hasLandingAbility() || !destination.hasLandingAbility()) {
            return false;
        }
        double distance = currentPort.calculateDistanceTo(destination);
        double fuelRequired = 0;
        for (Container container : this.containers) {

            fuelRequired += getFuelConsumptionPerKm(container) * distance;
        }
//        System.out.println("Distance: " +distance);
//        System.out.println("Fuel required "+fuelRequired);
//        System.out.println("current fuel: " + currentFuel);
        return fuelRequired <= currentFuel;
    }

    @Override
    public void moveTo(Port destination) {
        double fuelRequired = 0;

        double distance = currentPort.calculateDistanceTo(destination);

        for (Container container : this.containers) {
            fuelRequired += getFuelConsumptionPerKm(container) * distance;

        }
        System.out.println("Distance: " + distance);
        //Check if container is empty so use the consumption of vehicle if container is loaded it use container consumption
        if (this.containers.isEmpty()) {
            fuelRequired = this.fuelConsumtion * distance;
        } else {
            for (Container container : this.containers) {
                fuelRequired += getFuelConsumptionPerKm(container) * distance;
            }
        }
        System.out.println("Fuel Required: " +fuelRequired);
        if (canMoveTo(destination) && fuelRequired <= this.currentFuel) {
            // Start a new trip
            Trip trip = new Trip(this, this.currentPort, destination);
            trip.start();
            this.currentPort.addTrip(trip);
            this.currentFuel -= fuelRequired;
            // Move the vehicle to the destination port
            this.currentPort = destination;
//            System.out.println("fuel require "+fuelRequired);
            // Complete the trip
            for (Container container : containers) {
                container.setPort(currentPort);
            }
            System.out.println("Vehicle move to " + destination.getName() + " successfully");

            destination.addVehicle(this);
            destination.addTrip(trip);

            trip.complete();

        } else {
            System.out.println("Current fuel : " + currentFuel + " is not enough to move to destination , require are: " + fuelRequired);
        }
    }

    @Override
    public void refuel(double amount) {
        this.currentFuel += Math.min(amount, this.fuelCapacity - this.currentFuel);
    }
    public void showLoadedContainers() {
        if(containers.isEmpty()) {
            System.out.println(this.name + " is currently not carrying any containers.");
            return;
        }

        System.out.println(this.name + " is carrying the following containers:");
        for(Container container : containers) {
            System.out.println("Container ID: " + container.getID() + ", Type: " + container.getContainerType());
        }
    }

//    @Override
//    public String toString() {
//        return "Vehicle{" +
//                "ID='" + ID + '\'' +
//                ", name='" + name + '\'' +
//                ", currentFuel=" + currentFuel +
//                ", carryingCapacity=" + carryingCapacity +
//                ", fuelCapacity=" + fuelCapacity +
//                '}';
//    }
@Override
public String toString() {
    return "Vehicle{ID='" + ID + "', name='" + name + "', currentFuel=" + currentFuel
            + ", carryingCapacity=" + carryingCapacity + ", fuelCapacity=" + fuelCapacity
            + ", currentPort=" + (currentPort != null ? currentPort.getID() : "null") + "}";
}
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

    public abstract double getFuelConsumptionPerKm(Container container);
}
