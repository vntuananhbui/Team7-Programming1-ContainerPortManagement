package src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.getShipByLine;

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
}
