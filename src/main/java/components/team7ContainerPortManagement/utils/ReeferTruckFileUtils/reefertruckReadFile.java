package src.main.java.components.team7ContainerPortManagement.utils.ReeferTruckFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.getReeferTruckByLine;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;

public class reefertruckReadFile {
    public static double getReeferTruckTotalContainerWeight(ReeferTrucks reeferTrucks, Port port) throws IOException {
        Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        List<String> loadedContainerIDs = vehicleContainerMap.getOrDefault(reeferTrucks.getID(), Collections.emptyList());

        double totalWeight = 0.0;

        for (String containerID : loadedContainerIDs) {
            String containerLine = getContainerLineByContainerID(containerID, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            Container container = getContainerByLine(containerLine);
            totalWeight += container.getWeight(); // Ensure container.getWeight() returns a double
        }

        return totalWeight;
    }

    public static List<ReeferTrucks> readReeferTruckFromFile(String fileName) throws IOException {
        List<ReeferTrucks> reeferTrucks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                ReeferTrucks reeferTruck = getReeferTruckByLine(line);
                reeferTrucks.add(reeferTruck);
            }
        }
        return reeferTrucks;
    }
}
