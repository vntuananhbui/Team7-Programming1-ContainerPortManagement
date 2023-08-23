package src.main.java.components.team7ContainerPortManagement.Controller.VehicleController;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portWriteFile.writeVehicleToPort;

public class reefertruckController {
    public static void createReeferTruck(Port selectedPort) throws IOException {
        FileWriter shipWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt", true);
        Scanner scanner = new Scanner(System.in);
        // Collect input values
        System.out.println("Enter Reefer Truck ID:");
        String shipID = "rtr-" + scanner.next();
        scanner.nextLine();
        System.out.println("Enter Reefer Truck name:");
        String shipName = scanner.nextLine();

        System.out.println("Enter Reefer Truck carrying capacity:");
        double carryingCapacity = scanner.nextDouble();
        scanner.nextLine();

        System.out.println("Enter Tanker Truck fuel capacity:");
        double fuelCapacity = scanner.nextDouble();
        scanner.nextLine();
        double currentFuel = fuelCapacity;
        if (selectedPort != null) {
            ReeferTrucks newReeferTruck = new ReeferTrucks(shipID, shipName, currentFuel, carryingCapacity, fuelCapacity, 3.5,selectedPort);
            selectedPort.addVehicle(newReeferTruck);
            newReeferTruck.setCurrentPort(selectedPort);
            shipWriter.write(newReeferTruck.toString() + ", Port=" + selectedPort.toString() + "\n");
            writeVehicleToPort(selectedPort, newReeferTruck);
            System.out.println("Reefer Truck created and added to selected port.");
        } else {
            System.out.println("Invalid port choice.");
        }

        shipWriter.close();
    }
}
