package src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class portReadFile {
    //===================================================================================================================
    //===================================================================================================================
    //CHOOSE PORT FUNCTION

    //get port by ordernumber
    public static Port getPortByOrderNumber(String orderNumber, String filePath) throws IOException {
        List<Port> ports = readPortsFromFile(filePath);

        for (Port port : ports) {
            if (port.getID().equals(orderNumber)) {
                return port;
            }
        }

        return null; // Port not found
    }
    //GET PORT BY ID
    public static Port getPortByID(String portID) throws IOException {
        // You might need to implement a method to retrieve a Port by its ID
        // This is just a placeholder implementation
        List<Port> availablePorts = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
        for (Port port : availablePorts) {
            if (port.getID().equals(portID)) {
                return port;
            }
        }
        return null; // Return null if port with the given ID is not found
    }
    //Read all port in port.txt
    public static List<Port> readPortsFromFile(String filePath) throws IOException {
        List<Port> ports = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] portData = line.split(", ");
                if (portData.length >= 6) {
                    String portID = portData[0].substring(4);
                    String portName = portData[1].substring(6);
                    double latitude = Double.parseDouble(portData[2].substring(9));
                    double longitude = Double.parseDouble(portData[3].substring(11));
                    int storingCapacity = Integer.parseInt(portData[4].substring(16));
                    boolean landingAbility = Boolean.parseBoolean(portData[5].substring(15, portData[5].length() - 1));
                    ports.add(new Port(portID, portName, latitude, longitude, storingCapacity, landingAbility));
                }
            }
        }
        return ports;
    }
//user login
    public static Map<String, Port> readPortFile() throws FileNotFoundException {
        Map<String, Port> portMap = new HashMap<>();
        Scanner scanner = new Scanner(new File("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt"));
        Pattern pattern = Pattern.compile("Port\\{ID='(.+)', name='(.+)', latitude=(.+), longitude=(.+), storingCapacity=(.+), landingAbility=(.+)\\}");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            Matcher matcher = pattern.matcher(line);

            if (matcher.find()) {
                String ID = matcher.group(1);
                String name = matcher.group(2);
                double latitude = Double.parseDouble(matcher.group(3));
                double longitude = Double.parseDouble(matcher.group(4));
                int storingCapacity = Integer.parseInt(matcher.group(5));
                boolean landingAbility = Boolean.parseBoolean(matcher.group(6));

                Port port = new Port(ID, name, latitude, longitude, storingCapacity, landingAbility);
                portMap.put(ID, port);
            }
        }
        return portMap;
    }
    //===================================================================================================================
    //===================================================================================================================
    public static List<Port> readAvailablePortsFromFile(String filePath, Port currentPort) throws IOException {
        List<Port> ports = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] portData = line.split(", ");
                if (portData.length >= 6) {
                    String portID = portData[0].substring(4);
                    String portName = portData[1].substring(6);
                    double latitude = Double.parseDouble(portData[2].substring(9));
                    double longitude = Double.parseDouble(portData[3].substring(11));
                    int storingCapacity = Integer.parseInt(portData[4].substring(16));
                    boolean landingAbility = Boolean.parseBoolean(portData[5].substring(15, portData[5].length() - 1));
                    Port port = new Port(portID, portName, latitude, longitude, storingCapacity, landingAbility);
                    if (!port.getID().equals(currentPort.getID())) {
                        ports.add(port);
                    }
                }
            }
        }
        return ports;
    }


    public static Map<String, List<String>> readVehiclePortMapFromFile(String fileName) throws IOException {
        Map<String, List<String>> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] parts = line.split(": ");
                int portIndex = line.indexOf("Port :");
                int vehiclesIndex = line.indexOf("Vehicles:");

                if (portIndex != -1 && vehiclesIndex != -1) {
                    String portID = line.substring(portIndex + 6, vehiclesIndex - 2).trim();
                    String vehiclesData = line.substring(vehiclesIndex + 9).trim();

//                    String[] values = parts[1].substring(0, parts[1].length() - 1).split(", ");
                    String[] vehicleIDs = parts[1].substring(0, parts[1].length() - 1).split(", ");
                    List<String> cleanedVehicleIDs = Arrays.asList(vehicleIDs);

                    map.put(portID, cleanedVehicleIDs);
                }
            }
        }

        return map;
    }

    public static Map<String, List<String>> readPortContainerMapFromFile(String fileName) throws IOException {

        Map<String, List<String>> map = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }
                String[] parts = line.split(": ");
                int portIndex = line.indexOf("Port :");
                int vehiclesIndex = line.indexOf("Container:");

                if (portIndex != -1 && vehiclesIndex != -1) {
                    String portID = line.substring(portIndex + 6, vehiclesIndex - 2).trim();
                    String vehiclesData = line.substring(vehiclesIndex + 9).trim();

//                    String[] values = parts[1].substring(0, parts[1].length() - 1).split(", ");
                    String[] vehicleIDs = parts[1].substring(0, parts[1].length() - 1).split(", ");
                    List<String> cleanedVehicleIDs = Arrays.asList(vehicleIDs);

                    map.put(portID, cleanedVehicleIDs);
                }
            }
        }

        return map;


    }
    public static Map<String, String> readPortUserMap(String filePath) throws IOException {
        Map<String, String> portUserMap = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String portID = parts[0].trim();
                    String username = parts[1].trim();
                    portUserMap.put(username, portID);
                }
            }
        }
//        System.out.println("Debug port user map: " + portUserMap);
        return portUserMap;
    }
    public static Port findPortByUsername(String username, String portPortManagerFilePath, String portFilePath) throws IOException {
        Map<String, String> portUserMap = readPortUserMap(portPortManagerFilePath);
        Map<String, String> cleanedPortUserMap = new HashMap<>();
        for (Map.Entry<String, String> entry : portUserMap.entrySet()) {
            String key = entry.getKey();
            if (key.endsWith("}")) {
                key = key.substring(0, key.length() - 1);
            }
            cleanedPortUserMap.put(key, entry.getValue());
        }


        String portID = cleanedPortUserMap.get(username);
        if (portID != null) {
            portID = portID.replaceAll("[{}]", "");
        }
//        System.out.println("debug map: " + portUserMap);
//        System.out.println("debug username: " + username);
//        System.out.println("debug get portID: " + portID);
//        for (String key : portUserMap.keySet()) {
//            System.out.println("Key in portUserMap: " + key);
//        }
        if (portID != null) {
            List<Port> ports = readPortsFromFile(portFilePath);
            for (Port port : ports) {
                if (port.getID().equals(portID)) {
//                    System.out.println("debug findport: " + port);
                    return port;
                }
            }
        }
//        System.out.println("debug: null"  );
        return null; // Username not found or associated port not found
    }


}
