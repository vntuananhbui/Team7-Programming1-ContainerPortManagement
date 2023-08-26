package src.main.java.components.team7ContainerPortManagement.utils.TankerTruckFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class tankertruckWriteFile {
    public static void writeTankerTruckToFile(List<TankerTruck> tankertrucks, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Vehicle tankertruck : tankertrucks) {
                writer.write(tankertruck.toString());
                writer.newLine();
            }
        }
    }
}
