package src.main.java.components.team7ContainerPortManagement.Controller;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class portController {
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

}
