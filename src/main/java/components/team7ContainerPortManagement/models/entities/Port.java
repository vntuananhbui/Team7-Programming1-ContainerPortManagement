package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.interfaces.PortOperations;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
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

        this.ID = ID;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;

    }

    public String getID() {
        int startIndex = ID.indexOf("'") + 1;
        int endIndex = ID.lastIndexOf("'");
        return ID.substring(startIndex, endIndex);
//        return ID;
    }

    public String writeID() {
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

//    public List<Trip> getTraffic() {
//        return trips;
//    }

//    public void setContainers(List<Container> containers) {
//        this.containers = containers;
//    }
//
//    public void setVehicles(List<Vehicle> vehicles) {
//        this.vehicles = vehicles;
//    }

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
    public static double calculateDistanceBetweenPorts(double latitude1, double longitude1, double latitude2, double longitude2) {
        final int R = 6371; // Earth's radius in km

        double latDistance = Math.toRadians(Math.abs(latitude2 - latitude1));
        double lonDistance = Math.toRadians(Math.abs(longitude2 - longitude1));

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2))
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
            System.out.println(container.getPort().name + " has added container " + container.getID() + " successfully");
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
//        String vehicleInfo = vehicles.stream().map(Vehicle::getID).collect(Collectors.joining(","));
        return String.format("Port{ID='%s', name='%s', latitude=%f, longitude=%f, storingCapacity=%d, landingAbility=%b}",
                getID(), getName(), getLatitude(), getLongitude(), getStoringCapacity(), isLandingAbility());
    }
    @Override
    public String toStringAdd() {
//        String vehicleInfo = vehicles.stream().map(Vehicle::getID).collect(Collectors.joining(","));
        return String.format("Port{ID='%s', name='%s', latitude=%f, longitude=%f, storingCapacity=%d, landingAbility=%b}",
                writeID(), getName(), getLatitude(), getLongitude(), getStoringCapacity(), isLandingAbility());
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
    //Special method
    public static List<String> getVehiclesByPortID(String portID) throws IOException {
        List<String> vehicleIDs = new ArrayList<>();

        // Path to the port_vehicles file
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_vehicles.txt";

        // Try-with-resources to automatically close BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.contains("Port :" + portID)) {
                    // Extract the vehicles portion
                    String vehicleSegment = line.split("Vehicles: ")[1];
                    vehicleSegment = vehicleSegment.replace("}", "").trim();

                    // Split by comma and collect all vehicle IDs
                    String[] vehicles = vehicleSegment.split(", ");
                    Collections.addAll(vehicleIDs, vehicles);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Port vehicles file not found.");
            e.printStackTrace();
        }

        return vehicleIDs;
    }
    public List<String> getShipsInPort() throws IOException {
        List<String> shipIDs = new ArrayList<>();

        List<String> vehicleIDs = getVehiclesByPortID(this.getID());

        for (String vehicleID : vehicleIDs) {
            if (vehicleID.startsWith("sh-")) {
                shipIDs.add(vehicleID);
            }
        }

        return shipIDs;
    }
    public List<String> getBasicTrucksInPort() throws IOException {
        List<String> basictruckIDs = new ArrayList<>();

        List<String> vehicleIDs = getVehiclesByPortID(this.getID());

        for (String vehicleID : vehicleIDs) {
            if (vehicleID.startsWith("btr-")) {
                basictruckIDs.add(vehicleID);
            }
        }

        return basictruckIDs;
    }
    public List<String> getTankerTrucksInPort() throws IOException {
        List<String> tankertruckIDs = new ArrayList<>();

        List<String> vehicleIDs = getVehiclesByPortID(this.getID());

        for (String vehicleID : vehicleIDs) {
            if (vehicleID.startsWith("ttr-")) {
                tankertruckIDs.add(vehicleID);
            }
        }

        return tankertruckIDs;
    }
    public List<String> getReeferTrucksInPort() throws IOException {
        List<String> reeferIDs = new ArrayList<>();

        List<String> vehicleIDs = getVehiclesByPortID(this.getID());

        for (String vehicleID : vehicleIDs) {
            if (vehicleID.startsWith("rtr-")) {
                reeferIDs.add(vehicleID);
            }
        }

        return reeferIDs;
    }


    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }

//    public static List<String> getContainerIDInPort(Port port) throws IOException {
//        List<String> containerIDs = new ArrayList<>();
//
//        // Path to the port_containers file
//        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt";
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (line.contains("Port :" + port.getID())) {
//                    String containerSegment = line.split("Container: ")[1];
//                    containerSegment = containerSegment.replace("}", "").trim();
//                    String[] containers = containerSegment.split(", ");
//                    Collections.addAll(containerIDs, containers);
//                    break;
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Port containers file not found.");
//            e.printStackTrace();
//        }
//
//        return containerIDs;
//    }

// ...

    public static List<String> getContainerIDInPort(Port port) throws IOException {
        List<String> containerIDs = new ArrayList<>();

        // Path to the port_containers file
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Port :" + port.getID())) {
                    String containerSegment = line.split("Container: ")[1];
                    containerSegment = containerSegment.replace("}", "").trim();
                    String[] containers = containerSegment.split(", ");

                    for (String container : containers) {
                        container = container.trim();
                        if (!container.isEmpty()) {  // Add only valid non-empty container IDs
                            containerIDs.add(container);
                        }
                    }

                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Port containers file not found.");
            e.printStackTrace();
        }

        return containerIDs;
    }





    public void setName(String name) {
        this.name = name;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setStoringCapacity(int storingCapacity) {
        this.storingCapacity = storingCapacity;
    }



}



