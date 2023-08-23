package src.main.java.components.team7ContainerPortManagement.Controller.VehicleController;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portWriteFile.writeVehicleToPort;

public class tankertruckController {
    public static void createTankerTruck(Port selectedPort) throws IOException {
        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt", true);
        Scanner scanner = new Scanner(System.in);
        // Collect input values
        System.out.println("Enter Tanker Truck ID:");
        String shipID = "ttr-" + scanner.next();
        scanner.nextLine();
        System.out.println("Enter Tanker Truck name:");
        String shipName = scanner.nextLine();

        System.out.println("Enter Tanker Truck carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Tanker Truck fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        scanner.nextLine();
        double currentFuel = fuelCapacity;
        if (selectedPort != null) {
            BasicTruck newTankerTruck = new BasicTruck(shipID, shipName, currentFuel, carryingCapacity, fuelCapacity, 3.5,selectedPort);
            selectedPort.addVehicle(newTankerTruck);
            newTankerTruck.setCurrentPort(selectedPort);
            shipWriter.write(newTankerTruck.toString() + ", Port=" + selectedPort.toString() + "\n");
            writeVehicleToPort(selectedPort, newTankerTruck);
            System.out.println("Tanker Truck created and added to selected port.");
        } else {
            System.out.println("Invalid port choice.");
        }

        shipWriter.close();
    }
}
