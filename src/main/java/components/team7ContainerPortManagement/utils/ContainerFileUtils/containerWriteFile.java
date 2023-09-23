package src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.getContainerInfoAsString;

public class containerWriteFile {
    public static void writeContainerToPort(Port port, Container container) throws IOException {
        File file = new File("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt");
        List<String> lines = new ArrayList<>(Files.readAllLines(file.toPath()));

        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (line.startsWith("{Port :" + port.getID())) {
                line = line.substring(0, line.lastIndexOf('}')) + ", " + container.getID() + "}";
                lines.set(i, line);
                found = true;
                break;
            }
        }

        if (!found) {
            lines.add("{Port :" + port.getID() + ", Container: " + container.getID() + "}");
        }

        Files.write(file.toPath(), lines);
    }

    public static void updateContainerLoadFile(Vehicle vehicle) throws IOException {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt";

        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Vehicle: " + vehicle.getID())) {
                    // Append the new container IDs and isLoaded values to the line
                    line += ", Container: " + getContainerInfoAsString(vehicle.getContainers());
                }
                lines.add(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Container load file not found.");
            e.printStackTrace();
        }

        // Write updated lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line + "\n");
            }
        }
    }

    public static void writeContainersToFile(List<Container> containers, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Container container : containers) {
                writer.write(container.toString());
                writer.newLine();
            }
        }
    }

    public static void writeVehicleContainerMapToFile(Map<String, List<String>> map, String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Map.Entry<String, List<String>> entry : map.entrySet()) {
                writer.write("{" + entry.getKey() + ": " + String.join(", ", entry.getValue()) + "}");
                writer.newLine();
            }
        }
    }


public static void writePortContainerMapToFile(Map<String, List<String>> map, String fileName) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            writer.write("{Port :" + entry.getKey() + ", Container: " + String.join(", ", entry.getValue()) + "}");
            writer.newLine();
        }
    }
}

    public static void updateContainerPort(String containerID, String newPortID, Port newPort) throws IOException {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt";

        // Read the contents of the file into memory
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        // Find the line corresponding to the container and update the port
        for (int i = 0; i < lines.size(); i++) {
            if (lines.get(i).contains("Container{ID='" + containerID + "'")) {
                int portStartIndex = lines.get(i).indexOf("port=Port{ID='") + "port=Port{ID='".length();
                int portEndIndex = lines.get(i).indexOf("'", portStartIndex);
                String currentPortID = lines.get(i).substring(portStartIndex, portEndIndex);

                lines.set(i, lines.get(i).replace("port=Port{ID='" + currentPortID, newPort.toString()));
                break;
            }
        }

        // Write the updated contents back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        }
    }
    public static void writePortContainersToFile(String portID, List<String> containerList) throws IOException {
        String fileName = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (String containerID : containerList) {
                writer.write("{Port :" + portID + ", Container: " + containerID + "}");
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            throw e;
        }
    }
}
