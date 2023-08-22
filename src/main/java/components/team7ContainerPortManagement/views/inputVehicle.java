package src.main.java.components.team7ContainerPortManagement.views;

import java.util.Scanner;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

public class inputVehicle {
    public static void main(String[] args) {
        inputVehicle(); // Call the inputVehicle method
    }

    public static void inputVehicle() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Enter Vehicle ID:");
            String vehicleID = scanner.next();

            System.out.println("Enter Vehicle name:");
            String vehicleName = scanner.next();

            System.out.println("Enter current fuel:");
            double currentFuel = scanner.nextDouble();

            System.out.println("Enter carrying capacity:");
            double carryingCapacity = scanner.nextDouble();

            System.out.println("Enter fuel capacity:");
            double fuelCapacity = scanner.nextDouble();

            System.out.println("Enter fuel consumption:");
            double fuelConsumption = scanner.nextDouble();

            // Assume you have a 'Port' instance available
            Port port = new Port("exampleID", "Example Port", 0.0, 0.0, 0, true);

            // Create an instance of Vehicle using the input values and the port instance
            Vehicle newVehicle = new Vehicle(vehicleID, vehicleName, currentFuel, carryingCapacity, fuelCapacity, fuelConsumption, port) {
                @Override
                public boolean canLoadContainer(Container container) {
                    return false;
                }

                @Override
                public double getFuelConsumptionPerKm(Container container) {
                    return 0;
                }
            };

            // Additional logic to add the newly created vehicle to your data structures or operations
        } catch (Exception e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
