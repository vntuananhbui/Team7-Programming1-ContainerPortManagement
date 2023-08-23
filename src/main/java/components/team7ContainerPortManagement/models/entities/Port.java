package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.interfaces.PortOperations;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static src.main.java.components.team7ContainerPortManagement.views.Containers.InputContainer.*;

//import static src.main.java.components.team7ContainerPortManagement.views.InputManager.getVehiclesByPortID;
//import static src.main.java.components.team7ContainerPortManagement.views.InputControl.getVehiclesByPortID;
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
        this.containers = new ArrayList<>();
        this.vehicles = new ArrayList<>();
        this.trips = new ArrayList<>();

    }
    public static List<String> getVehiclesByPortID(String portID) throws IOException {
        List<String> vehicleIDs = new ArrayList<>();

        // Path to the port_vehicles file
        String filePath = "src/main/java/components/team7ContainerPortManagement/models/utils/port_vehicles.txt";

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

    public String getID() {
        int startIndex = ID.indexOf("'") + 1;
        int endIndex = ID.lastIndexOf("'");
        return ID.substring(startIndex, endIndex);
//        return ID;
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
//        String vehicleInfo = vehicles.stream().map(Vehicle::getID).collect(Collectors.joining(","));
        return String.format("Port{ID='%s', name='%s', latitude=%f, longitude=%f, storingCapacity=%d, landingAbility=%b}",
                getID(), getName(), getLatitude(), getLongitude(), getStoringCapacity(), isLandingAbility());
    }

//        return "Port{" +
//                "ID='" + ID + '\'' +
//                ", name='" + name + '\'' +
//                ", latitude=" + latitude +
//                ", longitude=" + longitude +
//                ", storingCapacity=" + storingCapacity +
//                ", landingAbility=" + landingAbility +
//                '}';
//    }
//@Override
//public String toString() {
//    // Format the list of containers and vehicles
//    String containerList = containers.stream().map(Container::getID).collect(Collectors.joining(", "));
//    String vehicleList = vehicles.stream().map(Vehicle::getID).collect(Collectors.joining(", "));
//
//    // Create the formatted output string
//    return String.format("Port{ID='%s', name='%s', latitude=%.1f, longitude=%.1f, storingCapacity=%d, landingAbility=%s, Containers: [%s], Vehicles: [%s]}",
//            ID, name, latitude, longitude, storingCapacity, landingAbility, containerList, vehicleList);
//}

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
//        System.out.println("All container belong to " + this.name);
//        if (containers.isEmpty()) {
//            System.out.println(this.name + " is currrent not have container.");
//        }
//        for (Container container: containers) {
//            System.out.println("Container ID: " +container.getID() + "Type: " + container.getContainerType());
//        }


    }

    public void showAllVehicleInPort() {
//        System.out.println("All vehicle belong to " + this.name);
//        if (vehicles.isEmpty()) {
//            System.out.println(this.name + " is not have any vehicle");
//
//        }
//        for (Vehicle vehicle : vehicles) {
//                System.out.println("Vehicle ID: " + vehicle.getID());
//
//        }
        System.out.println("All vehicles belong to port: " + this.name);
        List<String> vehicleIDs;
        try {
            vehicleIDs = getVehiclesByPortID(this.ID);
            System.out.println("Vehicle ID");
            System.out.println(vehicleIDs);
            System.out.println("this id");
            System.out.println(this.ID);
            if (vehicleIDs.isEmpty()) {
                System.out.println("Port " + this.ID + " does not have any vehicles.");
            } else {
                for (String vehicleID : vehicleIDs) {
                    System.out.println("Vehicle ID: " + vehicleID);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading vehicles for port " + this.name);
        }
    }
    public void addTrip(Trip trip) {
        this.trips.add(trip);
    }

    public List<Trip> getTrips() {
        return this.trips;
    }
//    public static Port fromString(String line) {
//        String[] parts = line.split(", ");
//        String ID = parts[0].substring(parts[0].indexOf('=') + 1);
//        String name = parts[1].substring(parts[1].indexOf('=') + 1);
//        double latitude = Double.parseDouble(parts[2].substring(parts[2].indexOf('=') + 1));
//        double longitude = Double.parseDouble(parts[3].substring(parts[3].indexOf('=') + 1));
//        int storingCapacity = Integer.parseInt(parts[4].substring(parts[4].indexOf('=') + 1));
//        boolean landingAbility = Boolean.parseBoolean(parts[5].substring(parts[5].indexOf('=') + 1));
//
//        return new Port(ID, name, latitude, longitude, storingCapacity, landingAbility);
//    }
private static String extractPortID(String portLine) {
    int startIndex = portLine.indexOf("ID='") + 4;
    int endIndex = portLine.indexOf("'", startIndex);
    return portLine.substring(startIndex, endIndex);
}

    private static Port parsePortLine(String portLine) {
        // Example format: Port{ID='p-1', name='Starting Port', latitude=442.0, longitude=556.0, storingCapacity=20000, landingAbility=true, Containers: [], Vehicles: [], Trips: []}
        String[] parts = portLine.split(", ");

        String id = parts[0].substring(parts[0].indexOf("'") + 1, parts[0].indexOf("'", parts[0].indexOf("'") + 1));
        String name = parts[1].substring(parts[1].indexOf("'") + 1, parts[1].indexOf("'", parts[1].indexOf("'") + 1));
        double latitude = Double.parseDouble(parts[2].substring(parts[2].indexOf("=") + 1));
        double longitude = Double.parseDouble(parts[3].substring(parts[3].indexOf("=") + 1));
        int storingCapacity = Integer.parseInt(parts[4].substring(parts[4].indexOf("=") + 1));
        boolean landingAbility = Boolean.parseBoolean(parts[5].substring(parts[5].indexOf("=") + 1, parts[5].indexOf("}")));



        return new Port(id, name, latitude, longitude, storingCapacity, landingAbility);
    }
    public void displayShipsInPort() throws IOException {
        List<String> vehicleIDs = getVehiclesByPortID(this.getID()); // Use this.getID()

        System.out.println("Ships in port " + this.getID() + ":"); // Use this.getName()
        for (String vehicleID : vehicleIDs) {
            if (vehicleID.startsWith("sh-")) {
                System.out.println("Ship ID: " + vehicleID);
            }
        }
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
//    public Ship getShipByID(String shipID) throws IOException {
//        List<String> vehicleIDs = getVehiclesByPortID(this.getID());
//
//        for (String vehicleID : vehicleIDs) {
//            if (vehicleID.equals(shipID)) {
//                String[] parts = vehicleID.split("-");
//                if (parts.length == 2 && parts[0].equals("sh")) {
//                    String shipNumber = parts[1];
//                    String shipLine = getShipLineByShipNumber(shipNumber); // Implement this method to retrieve ship information
//                    if (shipLine != null) {
//                        return parseShipLine(shipLine); // Implement this method to parse ship information
//                    }
//                }
//                break;
//            }
//        }
//
//        return null; // Ship with the specified ID not found
//    }

//    public List<Container> getContainerInPort() {
//
//        return new ArrayList<>(containers);
//    }
public static List<String> getContainerIDInPort(Port port) throws IOException {
    List<String> containerIDs = new ArrayList<>();

    // Path to the port_containers file
    String filePath = "src/main/java/components/team7ContainerPortManagement/models/utils/port_containers.txt";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.contains("Port :" + port.getID())) {
                String containerSegment = line.split("Container: ")[1];
                containerSegment = containerSegment.replace("}", "").trim();
                String[] containers = containerSegment.split(", ");
                Collections.addAll(containerIDs, containers);
                break;
            }
        }
    } catch (FileNotFoundException e) {
        System.out.println("Port containers file not found.");
        e.printStackTrace();
    }

    return containerIDs;
}
    public static List<String> getContainersInPort(Port port) throws IOException {
        List<String> containerInfo = new ArrayList<>();

        // Path to the port_containers file
        String filePath = "src/main/java/components/team7ContainerPortManagement/models/utils/port_containers.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Port :" + port.getID())) {
                    String containerSegment = line.split("Container: ")[1];
                    containerSegment = containerSegment.replace("}", "").trim();
                    String[] containers = containerSegment.split(", ");

                    for (String containerID : containers) {
//                        String containerLine = getContainerLineByContainerID(containerID, filePath);
//                        System.out.println("containerLine: "+containerLine);
//                        Container container = parseContainerLine(containerLine);
//                        String containerInfoString = String.format("Container ID='%s', Type='%s', isLoaded=%s",
//                                container.getID(), container.getContainerType(), container.isLoaded());
                        boolean status = isContainerLoaded(containerID);


                        String containerInfoString = "ID: " + containerID + " | Status Onload : " + status;
                        containerInfo.add(containerInfoString);
//                        System.out.println("container ID" +containerID);
                    }
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Port containers file not found.");
            e.printStackTrace();
        }

        return containerInfo;
    }
    public static boolean isContainerLoaded(String containerID) throws IOException {
        String filePath = "src/main/java/components/team7ContainerPortManagement/models/utils/container.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ID='" + containerID + "'")) {
                    // Extract the isLoaded attribute
                    boolean isLoaded = Boolean.parseBoolean(getAttributeValue(line, "isLoaded"));
                    return isLoaded;
                }
            }
        }
        return false; // Return false if the container is not found
    }




    // Helper method to extract attribute value from the line
    private static String getAttributeValue(String line, String attributeName) {
        int startIndex = line.indexOf(attributeName + '=') + attributeName.length() + 1;
        int endIndex = line.indexOf(',', startIndex);
        if (endIndex == -1) {
            endIndex = line.indexOf('}', startIndex);
        }
        return line.substring(startIndex, endIndex).trim();
    }



    public static List<String> getContainersInPortLoadAvailable(Port port) throws IOException {
        List<String> containerIDs = new ArrayList<>();

        // Path to the port_containers file
        String filePath = "src/main/java/components/team7ContainerPortManagement/models/utils/port_containers.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Port :" + port.getID())) {
                    String containerSegment = line.split("Container: ")[1];
                    containerSegment = containerSegment.replace("}", "").trim();
                    String[] containers = containerSegment.split(", ");

                    for (String containerID : containers) {
                        Container container = getContainerByID(containerID);
                        if (container != null && !container.isLoaded() && (container.getPort() == port || container.getPort() == null)) {
                            containerIDs.add(containerID);
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



}
