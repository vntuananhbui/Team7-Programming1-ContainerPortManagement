package src.main.java.components.team7ContainerPortManagement.views.Vehicle;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.views.Vehicle.shipControl.writeVehicleToPort;

public class basicTruckControl {
    public static void createBasicTruck(Port selectedPort) throws IOException {
        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt", true);
        Scanner scanner = new Scanner(System.in);
        // Collect input values
        System.out.println("Enter Basic Truck ID:");
        String shipID = "btr-" + scanner.next();
        scanner.nextLine();
        System.out.println("Enter Basic Truck name:");
        String shipName = scanner.nextLine();

        System.out.println("Enter Basic Truck carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Baisc Truck fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        scanner.nextLine();
        double currentFuel = fuelCapacity;
        if (selectedPort != null) {
            BasicTruck newBasicTruck = new BasicTruck(shipID, shipName, currentFuel, carryingCapacity, fuelCapacity, 3.5,selectedPort);
            selectedPort.addVehicle(newBasicTruck);
            newBasicTruck.setCurrentPort(selectedPort);
            shipWriter.write(newBasicTruck.toString() + ", Port=" + selectedPort.toString() + "\n");
            writeVehicleToPort(selectedPort, newBasicTruck);
            System.out.println("Ship created and added to selected port.");
        } else {
            System.out.println("Invalid port choice.");
        }

        shipWriter.close();
    }
}
