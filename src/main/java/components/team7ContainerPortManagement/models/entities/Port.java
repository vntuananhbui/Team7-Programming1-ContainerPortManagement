package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.interfaces.PortOperations;

import java.util.ArrayList;
import java.util.List;

public class Port implements PortOperations {
    private String ID;
    private String name;
    private double latitude;
    private double longitude;
    private int storingCapacity;
    private boolean landingAbility;
    private List<Container> containers = new ArrayList<>();
    private List<Vehicle> vehicles = new ArrayList<>();
    private List<Trip> trips = new ArrayList<>();


    public Port(String ID, String name, double latitude, double longitude, int storingCapacity, boolean landingAbility) {

        this.ID = "p-" + ID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;

    }

    public String getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public int getStoringCapacity() {
        return storingCapacity;
    }

    public boolean isLandingAbility() {
        return landingAbility;
    }

    public List<Container> getContainers() {
        return containers;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public List<Trip> getTraffic() {
        return trips;
    }

    public void setContainers(List<Container> containers) {
        this.containers = containers;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    //Override the interfaces
    @Override
    public double calculateDistanceTo(Port anotherPort) {
        final int R = 6371; //Earth's radius in km

        double latDistance = Math.toRadians(Math.abs(anotherPort.latitude - this.latitude));
        double lonDistance = Math.toRadians(Math.abs(anotherPort.longitude - this.longitude));

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(this.latitude)) * Math.cos(Math.toRadians(anotherPort.latitude))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return Math.round(distance);
    }
    @Override
    public boolean canAddContainer(Container container) {
        return calculateTotalContainerWeight() + container.getWeight() <= storingCapacity;
    }
    @Override
    public boolean addContainer(Container container) {
        //Check if the port can add more container
        if (canAddContainer(container)) {
            System.out.println("The " + container.getID() + " container is unloaded on " + container.getPort().name);
            System.out.println(container.getPort().name + " has added container " + container.getID() +" successfully");
            container.setPort(this);
            containers.add(container); //If true, add container to port containers list
            return true; //add container.
        }
        System.out.println(this.name + " is full of load!");
        return false;
    }

    @Override
    public boolean removeContainer(Container container) {
        System.out.println("Container remove successfully");
        return containers.remove(container);

    }

    @Override
    public boolean addVehicle(Vehicle vehicle) {
        vehicles.add(vehicle);
        return true;
    }
    @Override
    public boolean removeVehicle(Vehicle vehicle) {
        vehicles.remove(vehicle);
        return true;
    }
    @Override
    public String toString() {
        return "Port{" +
                "ID='" + ID + '\'' +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", storingCapacity=" + storingCapacity +
                ", landingAbility=" + landingAbility +
                '}';
    }

    public Port(String ID, String name, double latitude, double longitude, int storingCapacity, boolean landingAbility, List<Container> containers, List<Vehicle> vehicles, List<Trip> traffic) {
        this.ID = ID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
        this.containers = containers;
        this.vehicles = vehicles;
        this.trips = traffic;
    }

    @Override
    public boolean hasLandingAbility() {
        return landingAbility;
    }
    @Override
    public double calculateTotalContainerWeight() {
        double totalWeight = 0.0;
        for (Container container : containers) {
            totalWeight += container.getWeight();
        }
        return totalWeight;
    }

    //Show all container
    public void showAllContainer() {
        System.out.println("All container belong to " + this.name);
        if (containers.isEmpty()) {
            System.out.println(this.name + " is currrent not have container.");
        }
        for (Container container: containers) {
            System.out.println("Container ID: " +container.getID() + "Type: " + container.getContainerType());
        }
    }

    public void showAllVehicleInPort() {
        System.out.println("All vehicle belong to " + this.name);
        if (vehicles.isEmpty()) {
            System.out.println(this.name + " is not have any vehicle");

        }
        for (Vehicle vehicle : vehicles) {
                System.out.println("Vehicle ID: " + vehicle.getID());

        }
    }
    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }

    public List<Trip> getTrips() {
        return this.trips;
    }

}
