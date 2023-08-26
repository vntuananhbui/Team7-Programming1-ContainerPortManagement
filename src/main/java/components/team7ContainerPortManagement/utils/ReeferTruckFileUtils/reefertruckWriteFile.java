package src.main.java.components.team7ContainerPortManagement.utils.ReeferTruckFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class reefertruckWriteFile {
    public static void writeReeferTruckToFile(List<ReeferTrucks> reeferTrucks, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Vehicle reefertruck : reeferTrucks) {
                writer.write(reefertruck.toString());
                writer.newLine();
            }
        }
    }
}
