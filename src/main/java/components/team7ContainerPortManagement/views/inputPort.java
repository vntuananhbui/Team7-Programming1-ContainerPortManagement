package src.main.java.components.team7ContainerPortManagement.views;

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

public class inputPort {
    public static void main(String[] args) {
        inputPort(); // Call the inputPort method
    }

    public static void inputPort() {
        Scanner scanner = new Scanner(System.in);
        try {
            FileWriter portWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt", true);

            // Collect input values
            System.out.println("Enter port ID:");
            String portID = scanner.next();
            System.out.println("Enter port name:");
            String portName = scanner.next();

            System.out.println("Enter latitude:");
            double latitude = scanner.nextDouble();

            System.out.println("Enter longitude:");
            double longitude = scanner.nextDouble();

            System.out.println("Enter storing capacity:");
            int capacity = scanner.nextInt();

            System.out.println("Enter landing ability (true or false):");
            boolean landingAbility = scanner.nextBoolean();

            // Create an instance of Port using the input values
            Port newPort = new Port(portID, portName, latitude, longitude, capacity, landingAbility);

            // Write the input values to the file
            portWriter.write(newPort.toString() + "\n");

            portWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
