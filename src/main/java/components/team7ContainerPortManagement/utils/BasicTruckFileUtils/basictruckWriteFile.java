package src.main.java.components.team7ContainerPortManagement.utils.BasicTruckFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class basictruckWriteFile {
    public static void writeBasicTruckToFile(List<BasicTruck> basictrucks, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Vehicle basicTruck : basictrucks) {
                writer.write(basicTruck.toString());
                writer.newLine();
            }
        }
    }
}
