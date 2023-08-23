package src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    //===================================================================================================================
    //===================================================================================================================

}
