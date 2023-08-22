package src.main.java.components.team7ContainerPortManagement.views;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import src.main.java.components.team7ContainerPortManagement.models.entities.*;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.*;

//import static src.main.java.components.team7ContainerPortManagement.views.Containers.InputContainer.parseContainerList;

public class InputManager {

    public static Scanner scanner = new Scanner(System.in);
    private static Map<String, List<String>> portVehicles = new HashMap<>();

    public static void main(String[] args) throws IOException {

    }
//Create port function
    public static void inputPort() throws IOException {
        FileWriter portWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt", true);

        // Collect input values
        System.out.println("Enter port ID:");
        String portID = "p-" + scanner.next();
        scanner.nextLine();
        System.out.println("Enter port name:");
        String portName = scanner.nextLine();
        System.out.println("Enter latitude:");
        double latitude = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter longitude:");
        double longitude = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter storing capacity:");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter landing ability (true or false):");
        boolean landingAbility = scanner.nextBoolean();

        // Create an instance of Port using the input values
        Port newPort = new Port(portID, portName, latitude, longitude, capacity, landingAbility);

        // Write the input values to the file
        portWriter.write(newPort.toString() + "\n");

        portWriter.close();
    }
    public static void addVehicleToPortFile(String portID, String vehicleID) throws IOException {
        Path filePath = Paths.get("src/main/java/components/team7ContainerPortManagement/models/utils/port_vehicles.txt");
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        boolean portFound = false;

        // Loop through the lines to find the port and append the vehicle
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).startsWith("Port ID: " + portID + "'")) {
                if (lines.get(i).contains("Vehicles:")) {
                    lines.set(i, lines.get(i) + ", " + vehicleID);
                } else {
                    lines.set(i, lines.get(i) + ", Vehicles: " + vehicleID);
                }
                portFound = true;
                break;
            }
        }

        // If the port entry was not found, create a new entry for the port and add the vehicle
        if (!portFound) {
            String newPortLine = "Port ID: " + portID + ", Vehicles: " + vehicleID + "}";
            lines.add(newPortLine);
        }

        // Write the changes back to the file
        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

//    public static List<String> getVehiclesByPortID(String portID) throws IOException {
//        List<String> vehicles = new ArrayList<>();
//        Pattern pattern = Pattern.compile("ID='(.*?)'"); // This regex will extract the port ID
//        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/models/utils/port_vehicles.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                Matcher matcher = pattern.matcher(line);
//                if (matcher.find()) {
//                    String extractedPortID = matcher.group(1);
//                    if (extractedPortID.equals(portID)) {
//                        // New pattern to extract vehicle ID
//                        Pattern vehiclePattern = Pattern.compile("Vehicles: (\\S+)");
//                        Matcher vehicleMatcher = vehiclePattern.matcher(line);
////                        System.out.println(vehicleMatcher);
//                        if (vehicleMatcher.find()) {
//                            String vehicleID = vehicleMatcher.group(1);
//                            vehicles.add(vehicleID);
//                        }
//                    }
//                }
//            }
//        }
//        return vehicles;
//    }









    //    public static void removePort() throws IOException {
//        List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
//
//        System.out.println("Available Ports:");
//        displayAvailablePorts(ports);
//
//        System.out.print("Enter the order number of the port to remove: ");
//        int orderNumber = scanner.nextInt();
//
//        if (orderNumber >= 1 && orderNumber <= ports.size()) {
//            Port portToRemove = ports.get(orderNumber - 1);
//
//            // Remove the selected port from the list
//            ports.remove(portToRemove);
//
//            // Write the updated list of ports back to the file
//            writePortsToFile(ports, "src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
//
//            System.out.println("Port removed successfully.");
//        } else {
//            System.out.println("Invalid order number. Port removal failed.");
//        }
//    }
//    public static void displayAvailablePorts(List<Port> ports) {
//        int orderNumber = 1;
//        for (Port port : ports) {
//            System.out.println(orderNumber + ": " + port.getID() + ", " + port.getName());
//            orderNumber++;
//        }
//    }
public static void displayAvailablePorts(List<Port> availablePorts) {
    if (availablePorts == null || availablePorts.isEmpty()) {
        System.out.println("No available ports.");
        return;
    }

    System.out.println("\nAvailable Ports:");
    int orderNumber = 1;
    for (Port port : availablePorts) {
        System.out.println(orderNumber + ". ID: " + port.getID() + " | Name: " + port.getName());
        orderNumber++;
    }
}
    public static List<Port> readPortsFromFile(String filePath) throws IOException {
        List<Port> ports = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] portData = line.split(", ");
                if (portData.length >= 6) {
                    String portID = portData[0].substring(4);
                    String portName = portData[1].substring(6);
                    double latitude = Double.parseDouble(portData[2].substring(9));
                    double longitude = Double.parseDouble(portData[3].substring(11));
                    int storingCapacity = Integer.parseInt(portData[4].substring(16));
                    boolean landingAbility = Boolean.parseBoolean(portData[5].substring(15, portData[5].length() - 1));
                    ports.add(new Port(portID, portName, latitude, longitude, storingCapacity, landingAbility));
                }
            }
        }

        return ports;
    }

    public static void writePortsToFile(List<Port> ports, String filePath) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (Port port : ports) {
            writer.write(port.toString() + "\n");
        }
        writer.close();
    }

//CreateShip function

    public static void createShip(Port selectedPort) throws IOException {
        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt", true);

        // Collect input values
        System.out.println("Enter ship ID:");
        String shipID = "sh-" + scanner.next();
        scanner.nextLine();
        System.out.println("Enter ship name:");
        String shipName = scanner.nextLine();

        System.out.println("Enter ship carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter ship fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        scanner.nextLine();

        // Display available ports for user to choose
//        List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
//        displayAvailablePorts(ports);
//        System.out.println("Enter current port ID:");
//        int orderNumber = scanner.nextInt();
//        scanner.nextLine();

        // Create an instance of Port based on the selected port ID
//        Port selectedPort = getPortByOrderNumber(orderNumber, "src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
        double currentFuel = fuelCapacity;
        // Create an instance of Vehicle using the input values and selected port
        // Create an instance of Ship using the input values
        if (selectedPort != null) {
            Ship newShip = new Ship(shipID, shipName, currentFuel, carryingCapacity, fuelCapacity, 3.5,selectedPort);
            selectedPort.addVehicle(newShip);
            newShip.setCurrentPort(selectedPort);

            // After adding the ship to the selected port

//            if (!portVehicles.containsKey(selectedPort.getID())) {
//                portVehicles.put(selectedPort.getID(), new ArrayList<>());
//            }
//            portVehicles.get(selectedPort.getID()).add(newShip.getID());

            // Update the port_vehicles.txt file
//            updatePortVehiclesFile(portVehicles);
//            shipWriter.write(newShip.toString() + "\n");
            shipWriter.write(newShip.toString() + ", Port=" + selectedPort.toString() + "\n");
            // Update the port.txt file with the modified port information
//            updatePortFile(selectedPort);
            addVehicleToPortFile(selectedPort.getID(), newShip.getID());
            System.out.println("Ship created and added to selected port.");
        } else {
            System.out.println("Invalid port choice.");
        }

        // Write the input values to the file


        shipWriter.close();
    }

//    public static void updatePortFile(Port port) throws IOException {
//        List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
//        for (int i = 0; i < ports.size(); i++) {
//            if (ports.get(i).getID().equals(port.getID())) {
//                ports.set(i, port);
//                break;
//            }
//        }
//
//        FileWriter portWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt", false);
//        for (Port updatedPort : ports) {
//            // Include the containers and vehicles associated with the port
//            List<Container> containers = updatedPort.getContainers();
//            List<Vehicle> vehicles = updatedPort.getVehicles();
//            List<Trip> trips = updatedPort.getTrips();
//
//            // Create a string representation for containers and vehicles
//            String containerInfo = containers.stream().map(Container::getID).collect(Collectors.joining(", "));
//            String vehicleInfo = vehicles.stream().map(Vehicle::getID).collect(Collectors.joining(", "));
//
//            // Write the updated port information along with containers and vehicles to the file
//            portWriter.write(updatedPort.getID() + ", " + updatedPort.getName() + ", " + updatedPort.getLatitude() + ", " + updatedPort.getLongitude() + ", " + updatedPort.getStoringCapacity() + ", " + updatedPort.isLandingAbility() + "\n");
//        }
//        portWriter.close();
//    }

//public static void updatePortFile(Port selectedPort) throws IOException {
//    File file = new File("src/main/java/components/team7ContainerPortManagement/models/utils/port_vehicles.txt");
//    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
//
//    String vehiclesString = selectedPort.getVehicles().stream()
//            .map(Vehicle::getID)
//            .collect(Collectors.joining(", "));
//
//    String line = String.format("Port ID: %s , Port name: %s, Vehicles: %s%n",
//            selectedPort.getID(), selectedPort.getName(), vehiclesString);
//
//    writer.write(line);
//    writer.close();
//}






//    public static void updatePortVehiclesFile(Map<String, List<String>> portVehicles) throws IOException {
//        FileWriter writer = new FileWriter("src/main/java/components/team7ContainerPortManagement/models/utils/port_vehicles.txt");
//
//        for (Map.Entry<String, List<String>> entry : portVehicles.entrySet()) {
//            String portID = entry.getKey();
//            List<String> vehicleIDs = entry.getValue();
//            String vehiclesString = String.join(",", vehicleIDs);
//            writer.write(portID + ":" + vehiclesString + "\n");
//        }
//
//        writer.close();
//    }

    //Display available port

//    public static List<String> getCurrentVehiclePorts(List<String> vehicleIDs) throws IOException {
//        List<String> currentPorts = new ArrayList<>();
//
//        for (String vehicleID : vehicleIDs) {
//            String portID = getPortIDByVehicleID(vehicleID);
//            if (portID != null) {
//                currentPorts.add(portID);
//            }
//        }
//
//        return currentPorts;
//    }
//    public static String getPortIDByVehicleID(String vehicleID) throws IOException {
//        List<String> portVehicles = readPortVehiclesFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/port_vehicles.txt");
//        for (String association : portVehicles) {
//            String[] parts = association.split(",");
//            if (parts[1].equals(vehicleID)) {
//                return parts[0];
//            }
//        }
//        return null;
//    }
//    public static List<String> readPortVehiclesFromFile(String filePath) throws IOException {
//        List<String> portVehicles = new ArrayList<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                portVehicles.add(line); // Assuming each line contains the vehicle IDs for a single port
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Port vehicles file not found.");
//            e.printStackTrace();
//        }
//
//        return portVehicles;
//    }
public static Map<String, List<String>> readPortVehiclesFromFile(String filePath) throws IOException {
    Map<String, List<String>> portVehicles = new HashMap<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String portID = line.split("ID='")[1].split("'")[0]; // Extracting the port ID
            String vehicleID = line.split("Vehicles: ")[1].replace("}", "").trim(); // Extracting the vehicle ID

            if (!portVehicles.containsKey(portID)) {
                portVehicles.put(portID, new ArrayList<>());
            }
            portVehicles.get(portID).add(vehicleID);
        }
    } catch (FileNotFoundException e) {
        System.out.println("Port vehicles file not found.");
        e.printStackTrace();
    }

    return portVehicles;
}

//    public static void updatePortVehiclesFile(Map<String, List<String>> portVehicles, String filePath) throws IOException {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
//            for (Map.Entry<String, List<String>> entry : portVehicles.entrySet()) {
//                String portID = entry.getKey();
//                List<String> vehicleIDs = entry.getValue();
//                String vehiclesStr = String.join(",", vehicleIDs);
//                writer.write(portID + ": " + vehiclesStr);
//                writer.newLine();
//            }
//        } catch (IOException e) {
//            System.out.println("Error updating port vehicles file.");
//            e.printStackTrace();
//        }
//    }
// get port by Order number

//    public static Port getPortByOrderNumber(int orderNumber, String filePath) throws IOException {
//        List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
//
//        if (orderNumber >= 1 && orderNumber <= ports.size()) {
//            return ports.get(orderNumber - 1);
//        } else {
//            return null; // Invalid order number
//        }
//        }

        //Read port from file

//    public static List<Port> readPortsFromFile(String portFilePath) {
//        List<Port> ports = new ArrayList<>();
//
//        try (Scanner scanner = new Scanner(new File(portFilePath))) {
//            while (scanner.hasNextLine()) {
//                String portLine = scanner.nextLine();
//                // Parse the portLine and create a Port object
//                Port port = parsePortLine(portLine);
//                ports.add(port);
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Port file not found.");
//            e.printStackTrace();
//        }
//
//        return ports;
    }
//    private static Port parsePortLine(String portLine) {
//        // Example format: Port{ID='p-1', name='Starting Port', latitude=442.0, longitude=556.0, storingCapacity=20000, landingAbility=true, Containers: [], Vehicles: [], Trips: []}
//        String[] parts = portLine.split(", ");
//
//        String id = parts[0].substring(parts[0].indexOf("'") + 1, parts[0].indexOf("'", parts[0].indexOf("'") + 1));
//        String name = parts[1].substring(parts[1].indexOf("'") + 1, parts[1].indexOf("'", parts[1].indexOf("'") + 1));
//        double latitude = Double.parseDouble(parts[2].substring(parts[2].indexOf("=") + 1));
//        double longitude = Double.parseDouble(parts[3].substring(parts[3].indexOf("=") + 1));
//        int storingCapacity = Integer.parseInt(parts[4].substring(parts[4].indexOf("=") + 1));
//        boolean landingAbility = Boolean.parseBoolean(parts[5].substring(parts[5].indexOf("=") + 1, parts[5].indexOf("}")));
//
//        List<Container> containers = parseContainerList(parts[6]);
//        List<Vehicle> vehicles = parseVehicleList(parts[7]);
//        List<Trip> trips = parseTripList(parts[8]);
//
//        return new Port(id, name, latitude, longitude, storingCapacity, landingAbility, containers, vehicles, trips);
//    }
//
//
//    //ship
//    private static List<Vehicle> parseVehicleList(String vehicleListStr, Port port) {
//        // Example format: Vehicles: [vehicle1, vehicle2, vehicle3]
//        List<Vehicle> vehicles = new ArrayList<>();
//        if (!vehicleListStr.equals("Vehicles: []")) {
//            String[] vehicleIDs = vehicleListStr.substring(vehicleListStr.indexOf("[") + 1, vehicleListStr.indexOf("]")).split(", ");
//            for (String vehicleID : vehicleIDs) {
//                Vehicle vehicle = readVehicleFromFile(vehicleID);
//                if (vehicle != null) {
//                    vehicles.add(vehicle);
//                }
//            }
//        }
//        return vehicles;
//    }
//    private static List<Vehicle> readVehiclesFromFile(String vehicleFilePath) {
//        List<Vehicle> vehicles = new ArrayList<>();
//
//        try (Scanner scanner = new Scanner(new File(vehicleFilePath))) {
//            while (scanner.hasNextLine()) {
//                String vehicleLine = scanner.nextLine();
//                Vehicle vehicle = parseVehicleLine(vehicleLine);
//                if (vehicle != null) {
//                    vehicles.add(vehicle);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Vehicle file not found.");
//            e.printStackTrace();
//        }
//
//        return vehicles;
//    }
//    private static Vehicle parseVehicleLine(String vehicleLine) {
//        // Parse vehicleLine and create a Vehicle object
//        // Example format: Ship{ID='sh-1', name='Cargo Ship', currentFuel=1000.0, carryingCapacity=2000.0, fuelCapacity=5000.0, fuelConsumption=3.5, loadStatus=false}
//        String[] parts = vehicleLine.split(", ");
//
//        String id = parts[0].substring(parts[0].indexOf("'") + 1, parts[0].indexOf("'", parts[0].indexOf("'") + 1));
//        String name = parts[1].substring(parts[1].indexOf("'") + 1, parts[1].indexOf("'", parts[1].indexOf("'") + 1));
//        double currentFuel = Double.parseDouble(parts[2].substring(parts[2].indexOf("=") + 1));
//        double carryingCapacity = Double.parseDouble(parts[3].substring(parts[3].indexOf("=") + 1));
//        double fuelCapacity = Double.parseDouble(parts[4].substring(parts[4].indexOf("=") + 1));
//        double fuelConsumption = Double.parseDouble(parts[5].substring(parts[5].indexOf("=") + 1));
//        boolean loadStatus = Boolean.parseBoolean(parts[6].substring(parts[6].indexOf("=") + 1, parts[6].indexOf("}")));
//
//        List<Container> containers = parseContainerList(parts[7]);
//
//        if (id.startsWith("sh-")) {
//            return new Ship(id, name, currentFuel, carryingCapacity, fuelCapacity, fuelConsumption, loadStatus);
//        } else if (id.startsWith("ttr-")) {
//            return new TankerTruck(id, name, currentFuel, carryingCapacity, fuelCapacity, currentPort, fuelConsumption, containers, loadStatus);
//        } else if (id.startsWith("ltr-")) {
//            return new BasicTruck(id, name, currentFuel, carryingCapacity, fuelCapacity, currentPort, fuelConsumption, containers, loadStatus);
//        } else if (id.startsWith("rtr-")) {
//            return new ReeferTrucks(id, name, currentFuel, carryingCapacity, fuelCapacity, currentPort, fuelConsumption, containers, loadStatus);
//        } else {
//            // Handle unknown vehicle type
//            return null;
//        }


        // Other methods...


//    }
