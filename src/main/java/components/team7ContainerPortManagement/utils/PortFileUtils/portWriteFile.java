package src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class portWriteFile {
    public static void writeVehicleToPort(Port port, Vehicle vehicle) throws IOException {
        File file = new File("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_vehicles.txt");
        List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("{Port :" + port.getID())) {
                line = line.substring(0, line.lastIndexOf('}')) + ", " + vehicle.getID() + "}";
                lines.set(i, line);
                found = true;
                break;
            }
        }

        if (!found) {
            lines.add("{Port :" + port.getID() + ", Vehicles: " + vehicle.getID() + "}");
        }

        Files.write(file.toPath(), lines);
    }
}
