package src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    public static void writeVehiclePortMapInFile(Map<String, List<String>> vehiclePortMap, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, List<String>> entry : vehiclePortMap.entrySet()) {
                String portID = entry.getKey();
                List<String> vehicleIDs = entry.getValue();

                // Filter out any null or empty vehicle IDs
                vehicleIDs = vehicleIDs.stream()
                        .filter(vehicleID -> vehicleID != null && !vehicleID.isEmpty())
                        .collect(Collectors.toList());

                // Construct the formatted string for each entry
                StringBuilder entryString = new StringBuilder();
                entryString.append("{Port :").append(portID).append(", Vehicles: ");
                for (int i = 0; i < vehicleIDs.size(); i++) {
                    entryString.append(vehicleIDs.get(i));
                    if (i < vehicleIDs.size() - 1) {
                        entryString.append(", ");
                    }
                }
                entryString.append("}");

                // Write the formatted entry to the file
                writer.write(entryString.toString());
                writer.newLine();
            }
        }
    }

    public static Map<String, List<String>> processDataFromVehicleFile(String filePath, Map<String, List<String>> existingMap) throws IOException {
        Map<String, List<String>> vehiclePortMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Vehicle")) {
                    String[] parts = line.split(", ");
                    String vehicleID = parts[0].substring(parts[0].indexOf('\'') + 1, parts[0].lastIndexOf('\''));
                    String portID = parts[5].substring(parts[5].indexOf('=') + 1);

                    if (existingMap.containsKey(portID)) {
                        vehiclePortMap.computeIfAbsent(portID, k -> new ArrayList<>()).addAll(existingMap.get(portID));
                        vehiclePortMap.get(portID).add(vehicleID);
                    } else {
                        List<String> vehicles = new ArrayList<>();
                        vehicles.add(vehicleID);
                        vehiclePortMap.put(portID, vehicles);
                    }
                }
            }
        }

        return vehiclePortMap;
    }

    public static void writeVehiclePortMapInFile1(Map<String, List<String>> vehiclePortMap, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Map.Entry<String, List<String>> entry : vehiclePortMap.entrySet()) {
                String portID = entry.getKey();
                List<String> vehicleIDs = entry.getValue();

                // Construct the formatted string for each entry
                StringBuilder entryString = new StringBuilder();
                entryString.append("{Port :").append(portID).append(", Vehicles: ");
                for (int i = 0; i < vehicleIDs.size(); i++) {
                    entryString.append(vehicleIDs.get(i));
                    if (i < vehicleIDs.size() - 1) {
                        entryString.append(", ");
                    }
                }
                entryString.append("}");

                // Write the formatted entry to the file
                writer.write(entryString.toString());
                writer.newLine();
            }
        }
    }



}
