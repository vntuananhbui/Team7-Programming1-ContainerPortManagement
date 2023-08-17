package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.interfaces.VehicleOperations;

import java.util.ArrayList;
import java.util.List;

public abstract class Vehicle implements VehicleOperations {
    protected String ID;
    protected String name;
    protected double currentFuel;
    protected double carryingCapacity;
    protected double fuelCapacity;
    protected Port currentPort;
    protected List<Container> containers;



    public Vehicle(String ID, String name, double currentFuel, double carryingCapacity, double fuelCapacity, Port currentPort) {
        this.ID = ID;
        this.name = name;
        this.currentFuel = currentFuel;
        this.carryingCapacity = carryingCapacity;
        this.fuelCapacity = fuelCapacity;
        this.currentPort = currentPort;
        this.containers = new ArrayList<>();

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


    public boolean loadContainer(Container container) {
        if(canLoadContainer(container) && !container.isLoaded()) {
            containers.add(container);
            System.out.println(container.getID() + ", Type: " + container.getContainerType() + " has been added");
            return true;
        }
        System.out.println("The container Type: " + container.getContainerType() + " can not be added");
        return false;
    }



    @Override
    public boolean unloadContainer(Container container) {
        if (containers.contains(container)) {
            // Unload the container from this vehicle
            containers.remove(container);
            container.setLoaded(false);
            return true;
        } else {
            // The container is not loaded onto this vehicle
            System.out.println("Container is not loaded onto this vehicle.");
            return false;
        }

    }

    @Override
    public boolean canMoveTo(Port destination) {
        if (currentPort == null || destination == null) {
            // The vehicle is currently sailing, so it cannot move to another port
            return false;
        }
        if (!currentPort.hasLandingAbility() || !destination.hasLandingAbility()) {
            // One or both of the ports do not have landing ability, so a truck cannot move between them
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
        System.out.println(distance);
        System.out.println("Fuel required "+fuelRequired);
        System.out.println("current fuel: " + currentFuel);
        return fuelRequired <= currentFuel;
    }

    @Override
    public void moveTo(Port destination) {
        double fuelRequired = 0;
        double distance = currentPort.calculateDistanceTo(destination);

        if (canMoveTo(destination)) {
            // Start a new trip
            Trip trip = new Trip(this, this.currentPort, destination);
            trip.start();
            for (Container container : this.containers) {

                fuelRequired += getFuelConsumptionPerKm(container) * distance;

            }
            // Move the vehicle to the destination port
            this.currentPort = destination;
//            System.out.println("fuel require "+fuelRequired);
            // Complete the trip
            trip.complete();
        } else {
            System.out.println("Error: " + currentFuel);
        }
    }

    @Override
    public void refuel(double amount) {
        this.currentFuel += amount;
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




    public abstract double getFuelConsumptionPerKm(Container container);
}
