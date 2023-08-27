package src.main.java.components.team7ContainerPortManagement.Controller;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;
import src.main.java.components.team7ContainerPortManagement.models.interfaces.PortOperations;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortsFromFile;

public class portController implements PortOperations {
    //===================================================================================================================
    //===================================================================================================================
    public static void inputPort() throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileWriter portWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt", true);

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
        portWriter.write(newPort.toStringAdd() + "\n");

        portWriter.close();
    }
    //===================================================================================================================
    //===================================================================================================================


    // Updating a port's information
    public static void updatePort(Port portToUpdate) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");

        // Replace the old port with the updated port in the list
        for (int i = 0; i < ports.size(); i++) {
            if (ports.get(i).getID().equals(portToUpdate.getID())) {
                ports.set(i, portToUpdate);
                break;
            }
        }
        System.out.println("================");
        System.out.println("Update Port " + portToUpdate.getID());
        System.out.println("================");
        System.out.println("Enter new port name (leave empty to skip):");
        String portName = scanner.nextLine();
        System.out.println("Enter new latitude (type -1 to skip):");
        double latitude = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter new longitude (type -1 to skip):");
        double longitude = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("Enter new storing capacity (type -1 to skip):");
        int capacity = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter new landing ability (true, false or leave empty to skip):");
        String landingAbilityInput = scanner.nextLine();

        if (!portName.isEmpty()) portToUpdate.setName(portName);
        if (latitude != -1) portToUpdate.setLatitude(latitude);
        if (longitude != -1) portToUpdate.setLongitude(longitude);
        if (capacity != -1) portToUpdate.setStoringCapacity(capacity);
        if (!landingAbilityInput.isEmpty()) {
            try {
                portToUpdate.setLandingAbility(Boolean.parseBoolean(landingAbilityInput));
            } catch (Exception e) {
                System.out.println("Invalid input for landing ability. Skipped.");
            }
        }

        // Save the updated ports back to the file
        savePortsToFile(ports);
    }

    // Deleting a port
    public static void deletePort(Port portToDelete) throws IOException {
        List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
        for (int i = 0; i < ports.size(); i++) {
            if (ports.get(i).getID().equals(portToDelete.getID())) {
                ports.set(i, portToDelete);
                break;
            }
        }

        ports.remove(portToDelete);
        // Save the updated ports back to the file
        savePortsToFile(ports);
        System.out.println("Port deleted successfully!");
    }

    public static void savePortsToFile(List<Port> ports) throws IOException {
        FileWriter portWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt", false);
        for (Port port : ports) {
            portWriter.write(port.toString() + "\n");
        }
        portWriter.close();
    }

    //DISPLAY ALL PORT IN FILE
    public static void displayAllPorts() throws IOException {
        // Path to the ports file
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt";

        // Try-with-resources to automatically close BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Current Ports:\n------------");
            int orderNumber = 1;

            while ((line = reader.readLine()) != null) {
                // Extract port ID and name from the line using regex
                String portID = extractDataFromLine(line, "ID='(.*?)'");
                String portName = extractDataFromLine(line, "name='(.*?)'");

                if (portID != null && portName != null) {
                    System.out.printf("%d. Port ID: '%s' , Port Name: '%s'\n", orderNumber++, portID, portName);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Port file not found.");
            e.printStackTrace();
        }
    }
    public static void displayDestinationPort(Port currentPort) throws IOException {
        // Path to the ports file
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt";

        // Try-with-resources to automatically close BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("Destination Ports:\n------------------");
            int orderNumber = 1;

            while ((line = reader.readLine()) != null) {
                // Extract port ID and name from the line using regex
                String portID = extractDataFromLine(line, "ID='(.*?)'");
                String portName = extractDataFromLine(line, "name='(.*?)'");

                if (portID != null && portName != null && !portID.equals(currentPort.getID())) {
                    System.out.printf("%d. Port ID: '%s' , Port Name: '%s'\n", orderNumber++, portID, portName);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Port file not found.");
            e.printStackTrace();
        }
    }

    //Belong to displayAllPort
    private static String extractDataFromLine(String line, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    @Override
    public double calculateDistanceTo(Port anotherPort) {
        return 0;
    }

    @Override
    public boolean addVehicle(Vehicle vehicle) {
        return false;
    }

    @Override
    public boolean removeVehicle(Vehicle vehicle) {
        return false;
    }

    @Override
    public String toStringAdd() {
        return null;
    }

    @Override
    public boolean hasLandingAbility() {
        return false;
    }

    @Override
    public double calculateTotalContainerWeight() {
        return 0;
    }

    @Override
    public boolean addContainer(Container container) {
        return false;
    }

    @Override
    public boolean canAddContainer(Container container) {
        return false;
    }

    @Override
    public boolean removeContainer(Container container) {
        return false;
    }
    //===================================================================================================================
    //===================================================================================================================

}
