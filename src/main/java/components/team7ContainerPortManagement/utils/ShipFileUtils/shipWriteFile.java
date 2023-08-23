package src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class shipWriteFile {


    public static void writeShipToFile(List<Ship> ships, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Vehicle ship : ships) {
                writer.write(ship.toString());
                writer.newLine();
            }
        }
    }




}
