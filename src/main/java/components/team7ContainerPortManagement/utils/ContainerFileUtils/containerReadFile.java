package src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.getPortByID;

public class containerReadFile {
    public static Container getContainerByID(String containerID) throws IOException {
        // Path to the container file
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Container{ID='" + containerID + "'")) {
                    // Parse the container data and return the container object
                    Container container = parseContainerLine(line);
                    return container;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Container file not found.");
            e.printStackTrace();
        }

        return null; // Container not found
    }
    public static String getContainerLineByContainerID(String containerID, String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ID='" + containerID + "'")) {
                    return line;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Container file not found.");
            e.printStackTrace();
        }
        return null; // Container line not found
    }
    protected static String getContainerInfoAsString(List<Container> containers) {
        StringBuilder containerInfo = new StringBuilder();
        for (Container container : containers) {
            if (containerInfo.length() > 0) {
                containerInfo.append(", ");
            }
            containerInfo.append(container.getID()).append(" (").append(container.isLoaded()).append(")");
        }
        return containerInfo.toString();
    }
    public static Container parseContainerLine(String line) throws IOException {
        String[] parts = line.split(", ");

        String containerID = parts[0].substring(parts[0].indexOf("'") + 1, parts[0].lastIndexOf("'"));
        double weight = Double.parseDouble(parts[1].substring(parts[1].indexOf("=") + 1));
        ContainerType containerType = ContainerType.valueOf(parts[2].substring(parts[2].indexOf("=") + 1));
        boolean isLoaded = Boolean.parseBoolean(parts[3].substring(parts[3].indexOf("=") + 1));

        Port port = null;
        if (!parts[4].equals("port=null")) {
            String portID = parts[4].substring(parts[4].indexOf("'") + 1, parts[4].lastIndexOf("'"));
            port = getPortByID(portID);
        }

        return new Container(containerID, weight, containerType, isLoaded,port);
    }

    public static Container getContainerByLine(String line) throws IOException {
        // Parse the line to extract the fields
        // Assuming the line format is: Container{ID='1212', weight=1212.0, containerType=DRY_STORAGE, isLoaded=false, port=Port{ID='p-TTu7', name='12w'', latitude=1212.000000, longitude=212.000000, storingCapacity=1212, landingAbility=true}}
        String[] parts = line.split(", ");
        String id = parts[0].split("'")[1];
        double weight = Double.parseDouble(parts[1].split("=")[1]);
        String containerType = parts[2].split("=")[1];
        boolean isLoaded = Boolean.parseBoolean(parts[3].split("=")[1]);
        // Debugging: print the line and the parts array
//        System.out.println("Debugging: line = " + line);
//        System.out.println("Debugging: parts = " + Arrays.toString(parts));

        String portID = parts[4].split("'")[1];

        // Create a new Container object
        Container container = new Container(id, weight, ContainerType.valueOf(containerType),getPortByID(portID));
        container.setLoaded(isLoaded);

        return container;
    }
    public static List<Container> readContainersFromFile(String fileName) throws IOException {
        List<Container> containers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Container container = getContainerByLine(line);
                containers.add(container);
            }
        }
        return containers;
    }
    public static Map<String, List<String>> readVehicleContainerMapFromFile(String fileName) throws IOException {

        Map<String, List<String>> map = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] parts = line.split(": ");
                String key = parts[0].substring(1);
                String[] values = parts[1].substring(0, parts[1].length() - 1).split(", ");
                map.put(key, new ArrayList<>(Arrays.asList(values)));
            }
        }
        return map;
    }




    public static String getStatusContainerbyID(String containerID) throws IOException {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("Container{ID='" + containerID + "'")) {
                    String isLoadedValue = extractAttributeValue(line, "isLoaded");
                    return "isLoaded=" + isLoadedValue;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading container file: " + e.getMessage());
            throw e;
        }

        return "Container not found";
    }


    public static String extractAttributeValue(String line, String attributeName) {
        String pattern = attributeName + "=";
        int startIndex = line.indexOf(pattern) + pattern.length();
        int endIndex = line.indexOf(",", startIndex);
        if (endIndex == -1) {
            endIndex = line.indexOf("}", startIndex);
        }
        return line.substring(startIndex, endIndex).trim();
    }

//    public static double getTotalContainerWeightInVehicle(String vehicleID) throws IOException {
//        String vehicleContainerLoadFilePath = "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle_containerLoad.txt";
//        String containerFilePath = "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt";
//
//        Map<String, Double> containerWeights = new HashMap<>();
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(containerFilePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String containerID = extractAttributeValue(line, "ID");
//                double containerWeight = Double.parseDouble(extractAttributeValue(line, "weight"));
//                containerWeights.put(containerID, containerWeight);
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading container file: " + e.getMessage());
//            throw e;
//        }
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(vehicleContainerLoadFilePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                if (line.contains("{" + vehicleID + ":")) {
//                    String[] containerIDs = line.split(":")[1].trim().replaceAll("[{}]", "").split(", ");
//                    if (containerIDs.length == 0) {
//                        return 0.0; // No containers loaded in the vehicle
//                    }
//                    double totalWeight = 0.0;
//                    for (String containerID : containerIDs) {
//                        totalWeight += containerWeights.get(containerID);
//                    }
//                    return totalWeight;
//                }
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading vehicle container load file: " + e.getMessage());
//            throw e;
//        }
//
//        return 0.0; // Vehicle ID not found
//    }


}
