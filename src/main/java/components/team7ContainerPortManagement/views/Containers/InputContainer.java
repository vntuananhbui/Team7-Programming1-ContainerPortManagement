package src.main.java.components.team7ContainerPortManagement.views.Containers;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.views.InputManager.readPortsFromFile;

public class InputContainer {
    //    private final String ID;
//    private final double weight;
//    private ContainerType containerType;  // Use the enum instead of String
//    private boolean isLoaded;
//    private Port port;  // Add this line
    public static void createContainer(Port currentPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileWriter containerWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/models/utils/container.txt", true);

        // Collect input values
        System.out.println("Enter container ID:");
        String containerID = scanner.next();
        scanner.nextLine();

        System.out.println("Enter container weight:");
        double weight = scanner.nextDouble();

        // Display available container types for user to choose
        System.out.println("Available container types:");
        ContainerType[] containerTypes = ContainerType.values();
        for (int i = 0; i < containerTypes.length; i++) {
            System.out.println((i + 1) + ": " + containerTypes[i].getLabel());
        }
        System.out.println("Choose a container type by number:");
        int typeNumber = scanner.nextInt();

        // Convert the chosen number to index and get the selected container type
        ContainerType selectedType = containerTypes[typeNumber - 1];

        // Create an instance of Container using the input values and current port
        Container newContainer = new Container(containerID, weight, selectedType, currentPort);
        currentPort.addContainer(newContainer);
        // Write the input values to the file
        containerWriter.write(newContainer.toString() + "\n");
        writeContainerToPort(currentPort, newContainer);
        containerWriter.close();
    }

//    static List<Container> parseContainerList(String containerListStr) {
//        // Example format: Containers: [container1, container2, container3]
//        List<Container> containers = new ArrayList<>();
//        if (!containerListStr.equals("Containers: []")) {
//            String[] containerIDs = containerListStr.substring(containerListStr.indexOf("[") + 1, containerListStr.indexOf("]")).split(", ");
//            for (String containerID : containerIDs) {
//                Container container = readContainerFromFile(containerID);
//                if (container != null) {
//                    containers.add(container);
//                }
//            }
//        }
//        return containers;
//    }

//    private static Container readContainerFromFile(String containerID) {
//        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/models/utils/container.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (line.contains("Container{ID='" + containerID)) {
//                    // Parse container data from the line
//                    String[] parts = line.split(", ");
//                    double weight = Double.parseDouble(parts[1].substring(parts[1].indexOf("=") + 1));
//                    ContainerType containerType = ContainerType.valueOf(parts[2].substring(parts[2].indexOf("=") + 1));
//
//                    // Create and return a Container object
//                    return new Container(containerID, weight, containerType, null); // You can set the port to null for now
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading container file: " + e.getMessage());
//        }
//
//        return null; // Container not found or error occurred
//    }
    public static void writeContainerToPort(Port port, Container container) throws IOException {
        File file = new File("src/main/java/components/team7ContainerPortManagement/models/utils/port_containers.txt");
        List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("{Port :" + port.getID())) {
                line = line.substring(0, line.lastIndexOf('}')) + ", " + container.getID() + "}";
                lines.set(i, line);
                found = true;
                break;
            }
        }

        if (!found) {
            lines.add("{Port :" + port.getID() + ", Container: " + container.getID() + "}");
        }

        Files.write(file.toPath(), lines);
    }

    public static String getContainerLineByContainerNumber(String containerNumber, String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ID='" + containerNumber + "'")) {
                    return line;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Container file not found.");
            e.printStackTrace();
        }
        return null; // Container line not found
    }
    public static String getContainerLineByContainerID(String containerNumber, String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(containerNumber)) {
                    return line;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Container file not found.");
            e.printStackTrace();
        }
        return null; // Container line not found
    }
    public static void updateContainerLoadFile(Vehicle vehicle) throws IOException {
        String filePath = "src/main/java/components/team7ContainerPortManagement/models/utils/vehicle_containerload.txt";

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Vehicle: " + vehicle.getID())) {
                    // Append the new container IDs and isLoaded values to the line
                    line += ", Container: " + getContainerInfoAsString(vehicle.getContainers());
                }
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Container load file not found.");
            e.printStackTrace();
        }

        // Write updated lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        }
    }

    private static String getContainerInfoAsString(List<Container> containers) {
        StringBuilder containerInfo = new StringBuilder();
        for (Container container : containers) {
            if (containerInfo.length() > 0) {
                containerInfo.append(", ");
            }
            containerInfo.append(container.getID()).append(" (").append(container.isLoaded()).append(")");
        }
        return containerInfo.toString();
    }
    public static Container getContainerByID(String containerID) throws IOException {
        // Path to the container file
        String filePath = "src/main/java/components/team7ContainerPortManagement/models/utils/container.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Container{ID='" + containerID + "'")) {
                    // Parse the container data and return the container object
                    Container container = parseContainerLine(line);
                    return container;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Container file not found.");
            e.printStackTrace();
        }

        return null; // Container not found
    }
    public static Container parseContainerLine(String line) throws IOException {
        String[] parts = line.split(", ");

        String containerID = parts[0].substring(parts[0].indexOf("'") + 1, parts[0].lastIndexOf("'"));
        double weight = Double.parseDouble(parts[1].substring(parts[1].indexOf("=") + 1));
        ContainerType containerType = ContainerType.valueOf(parts[2].substring(parts[2].indexOf("=") + 1));
        boolean isLoaded = Boolean.parseBoolean(parts[3].substring(parts[3].indexOf("=") + 1));

        Port port = null;
        if (!parts[4].equals("port=null")) {
            String portID = parts[4].substring(parts[4].indexOf("'") + 1, parts[4].lastIndexOf("'"));
            // You might need to implement a method to get a Port object by its ID
            // For example: Port port = getPortByID(portID);
            // Here, I'll assume you have a method getPortByID that retrieves a Port by its ID

            port = getPortByID(portID);
        }

        return new Container(containerID, weight, containerType, port);
    }
    public static Port getPortByID(String portID) throws IOException {
        // You might need to implement a method to retrieve a Port by its ID
        // This is just a placeholder implementation
        List<Port> availablePorts = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
        for (Port port : availablePorts) {
            if (port.getID().equals(portID)) {
                return port;
            }
        }
        return null; // Return null if port with the given ID is not found
    }


}
