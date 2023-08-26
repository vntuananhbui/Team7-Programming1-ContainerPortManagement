package src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.getShipByLine;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;

public class shipReadFile {

    public static List<Ship> readShipFromFile(String fileName) throws IOException {
        List<Ship> ships = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Ship ship = getShipByLine(line);
                ships.add(ship);
            }
        }
        return ships;
    }
    public static double getShipTotalContainerWeight(Ship ship, Port port) throws IOException {
        Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt");
        List<String> loadedContainerIDs = vehicleContainerMap.getOrDefault(ship.getID(), Collections.emptyList());

        if (loadedContainerIDs.isEmpty()) {
            return 0.0;
        }

        double totalWeight = 0.0;

        for (String containerID : loadedContainerIDs) {
            String containerLine = getContainerLineByContainerID(containerID, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
            Container container = getContainerByLine(containerLine);
            totalWeight += container.getWeight();
        }

        return totalWeight;
    }
}
