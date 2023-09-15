package src.main.java.components.team7ContainerPortManagement.Controller;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;
import src.main.java.components.team7ContainerPortManagement.models.interfaces.PortOperations;

import java.io.*;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortsFromFile;

public class portController {
    //===================================================================================================================
    //===================================================================================================================

    public static boolean isPortIDAlreadyExists(String portID) throws IOException {
        File file = new File("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.contains("ID='" + portID + "'")) {
                scanner.close();
                return true; // ID already exists
            }
        }
        scanner.close();
        return false; // ID does not exist
    }
    //===================================================================================================================
    //===================================================================================================================


    // Updating a port's information


    // Deleting a port


    public static void savePortsToFile(List<Port> ports) throws IOException {
        FileWriter portWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt", false);
        for (Port port : ports) {
            portWriter.write(port.toString() + "\n");
        }
        portWriter.close();
    }

    //DISPLAY ALL PORT IN FILE
    public static void displayAllPorts() throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String ANSI_yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        // Path to the ports file
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt";

        // Try-with-resources to automatically close BufferedReader
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println(ANSI_yellow+"╔══════════════════════════════════════════╗");
            System.out.println("║             CURRENT PORTS                ║");
            System.out.println("╟──────────────────────────────────────────╢" + reset);

            int orderNumber = 1;

            while ((line = reader.readLine()) != null) {
                // Extract port ID and name from the line using regex
                String portID = extractDataFromLine(line, "ID='(.*?)'");
                String portName = extractDataFromLine(line, "name='(.*?)'");

                if (portID != null && portName != null) {
                    System.out.println("║ [" + orderNumber + "] " + padString(portName, 36) + " ║");
                    orderNumber++;
                }
            }
            System.out.println("╚══════════════════════════════════════════╝");
        } catch (FileNotFoundException e) {
            System.out.println("Port file not found.");
            e.printStackTrace();
        }
    }

    // Utility method to pad the string to the desired length
    private static String padString(String input, int length) {
        return String.format("%-" + length + "s", input);
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

    //===================================================================================================================
    //===================================================================================================================
    public static void inputPort() throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
        FileWriter portWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt", true);
        String portID;
        boolean idExists;
        System.out.println(ANSI_CYAN +"╔══════════════════════════════════════════╗");
        System.out.println(ANSI_CYAN +"╟" +  "                CREATE PORT " + "              ║" + reset);
        System.out.println();
//        do {
//
//            // Collect input values
//            System.out.println("Enter port ID:");
//            portID = "p-" + scanner.next();
//            scanner.nextLine();
//
//            // Check if the port ID already exists in the file
//            idExists = isPortIDAlreadyExists(portID);
//
//            if (idExists) {
//                System.out.println(ANSI_RED + "Error: " + reset + "Port ID already exists. Retry!");
//            }
//        } while (idExists);

        do {
            portID = "p-" + generateRandomID();
            // Check if the port ID already exists in the file
            idExists = isPortIDAlreadyExists(portID);
        } while (idExists);

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
        System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
        System.out.println("╟" + ANSI_CYAN + "                 CREATE PORT SUCCESSFULLY" + "               ║");
        System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
        System.out.println( "            Port ID: " + portID);
        System.out.println( "            Port Name: " + portName);
        System.out.println( "            Port Latitude: " + latitude);
        System.out.println( "            Port Longtitude: " + longitude);
        System.out.println( "            Port Capacity: " + capacity);
        System.out.println( "            Port Landing Ability: " + landingAbility);
        System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
        System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);

        portWriter.close();
        System.out.print("Press any key to return...\n");
        scanner.next();  // Wait for the user to press Enter
    }
    // Method to generate a random alphanumeric port ID
    public static String generateRandomID() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(4); // You can adjust the length here
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
    public static void updatePort(Port portToUpdate) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
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
        int capacity = portToUpdate.getStoringCapacity();
//        System.out.println("Enter new storing capacity (type -1 to skip):");
//        int capacity = scanner.nextInt();
//        scanner.nextLine();
        System.out.println("Enter new landing ability (true, false or leave empty to skip):");
        String landingAbilityInput = scanner.nextLine();

        if (!portName.isEmpty()) portToUpdate.setName(portName);
        if (latitude != -1) portToUpdate.setLatitude(latitude);
        if (longitude != -1) portToUpdate.setLongitude(longitude);
        portToUpdate.setStoringCapacity(capacity);
        if (!landingAbilityInput.isEmpty()) {
            try {
                portToUpdate.setLandingAbility(Boolean.parseBoolean(landingAbilityInput));
            } catch (Exception e) {
                System.out.println("Invalid input for landing ability. Skipped.");
            }
        }
        savePortsToFile(ports);
        System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
        System.out.println("╟" + ANSI_CYAN + "                 UPDATE PORT SUCCESSFULLY" + "               ║");
        System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
        System.out.println( "            Port Name: " + portToUpdate.getName());
        System.out.println( "            Port Latitude: " + portToUpdate.getLatitude());
        System.out.println( "            Port Longtitude: " + portToUpdate.getLongitude());
        System.out.println( "            Port Capacity: " + portToUpdate.getStoringCapacity());
        System.out.println( "            Port Landing Ability: " + portToUpdate.isLandingAbility());
        System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
        System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);

        System.out.print("Press any key to return...");
        scanner.next();  // Wait for the user to press Enter

        return;
        // Save the updated ports back to the file

    }
    public static void deletePort(Port portToDelete) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
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
        System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
        System.out.println("╟" + ANSI_CYAN + "                 DELETE PORT SUCCESSFULLY" + "               ║");
        System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
        System.out.println( "               Port " + portToDelete.getID() + " has been deleted!");
        System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
        System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
        System.out.print("Press any key to return...");
        scanner.next();  // Wait for the user to press Enter
        return;
    }

}
