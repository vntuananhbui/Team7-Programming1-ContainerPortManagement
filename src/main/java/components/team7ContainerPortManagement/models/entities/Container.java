package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeContainersToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortsFromFile;

public class Container {
    private final String ID;
    private double weight;
    private ContainerType containerType;  // Use the enum instead of String
    private boolean isLoaded;
    private static Port port;
    private String portID;

    //Use for calculate
    public Container(String ID, double weight, ContainerType containerType, boolean isLoaded, String portID) {
        this.ID = ID;
        this.weight = weight;
        this.containerType = containerType;
        this.isLoaded = isLoaded;
        this.portID = portID; // Store the port ID as a string
    }


    public void setWeight(double weight) {
        this.weight = weight;
    }

    //Use to check container onload or not
    public Container(String ID, double weight, ContainerType containerType, boolean isLoaded, Port port) {
        this.ID = ID;
        this.weight = weight;
        this.containerType = containerType;
        this.isLoaded = isLoaded(); // Initialize isLoaded to false
        this.port = port;
    }
    public Container(String ID, double weight, ContainerType containerType,Port port) {
        this.ID = ID;
        this.weight = weight;
        this.containerType = containerType;
        this.isLoaded = false; // Initialize isLoaded to false
        this.port = port;
    }

    public String getID() {
        return ID;
    }

    public double getWeight() {
        return weight;
    }
    public ContainerType getContainerType() {
        return containerType;
    }
    public boolean isLoaded() {
        return this.isLoaded;
    }


    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }
    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Container{" +
                "ID='" + ID + '\'' +
                ", weight=" + weight +
                ", containerType=" + containerType +
                ", isLoaded=" + isLoaded +
                ", port=" + port +
                '}';
    }


    public static List<String> readoutContainerInPort(String portID) {
        List<String> containerIDs = new ArrayList<>();
        String portContainerFilePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt";
        boolean inContainerSection = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(portContainerFilePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim(); // Trim leading/trailing whitespace
                System.out.println("Debug line: " + line);
                if (line.startsWith("{Port :")) {
                    int portIndex = line.indexOf("Port :");
                    int containerIndex = line.indexOf("Container:");

                    if (portIndex != -1) {

                        String currentPort = line.substring(portIndex + "Port :".length(), containerIndex).trim();
                        if (currentPort.endsWith(",")) {
                            currentPort = currentPort.substring(0, currentPort.length() - 1);
                        }
                        System.out.println("Debug current port: " + currentPort);
                        System.out.println("Debug portID: " + portID);
                        inContainerSection = currentPort.equals(portID);
                        System.out.println("Debug inContainerSection: " + inContainerSection);

                    }
                }
                if (inContainerSection && line.startsWith("c-")) {
                    // Check if the line starts with "c-", indicating a container ID
                    containerIDs.add(line);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return containerIDs;
    }




    //    public static List<String> readoutContainerInPort(String portID) {
//        List<String> containerIDs = new ArrayList<>();
//        String portContainerFilePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt";
//        try (BufferedReader reader = new BufferedReader(new FileReader(portContainerFilePath))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                // Example format: {Port :p-HCM, Container: c-8dSF, c-eltl}
//                String[] parts = line.split("[,:}]");
//                System.out.println("debug parts: " + Arrays.toString(parts));
//                boolean inContainerSection = false;
//
//                for (String part : parts) {
//                    part = part.trim();
//                    if (part.equals("Port") && inContainerSection) {
//                        // End of the container section
//                        break;
//                    }
//                    if (part.equals("Container")) {
//                        inContainerSection = true;
//                    } else if (inContainerSection && !part.isEmpty()) {
//                        // Add the non-empty parts as container IDs
//                        containerIDs.add(part);
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return containerIDs;
//    }
    public static void viewContainerInPort(String portID) throws IOException {
        List<String> containerIDs = readoutContainerInPort(portID);

        for (int i = 0; i < containerIDs.size(); i++) {
            String containerID = containerIDs.get(i);
            Container container = getContainerByID(containerID);
            System.out.println("[" + (i+ 1) + "]" +" ContainerID: " +container.getID() + " | Type: " + container.getContainerType() + " | Weight: " + container.getWeight());
        }
    }




//Special method
public void updateStatusContainer(boolean isLoaded) throws IOException {
    setLoaded(isLoaded);

    // Assuming you have a method to update the container data in the file
    updateContainerInFile();
}

    private void updateContainerInFile() throws IOException {
        // Assuming you have methods to read, update, and write the container data to the file
        List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");

        for (int i = 0; i < containers.size(); i++) {
            if (containers.get(i).getID().equals(this.getID())) {
                containers.set(i, this);
                break;
            }
        }

        writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");
    }
    private static final Pattern PORT_ID_PATTERN = Pattern.compile("port=Port\\{ID='(.*?)'");
    private static final Pattern WEIGHT_PATTERN = Pattern.compile("weight=(.*?),");
    public static double getTotalContainerWeightByPort(String portID) throws IOException {
        double totalWeight = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher portMatcher = PORT_ID_PATTERN.matcher(line);
                Matcher weightMatcher = WEIGHT_PATTERN.matcher(line);

                if (portMatcher.find() && weightMatcher.find()) {
                    String extractedPortId = portMatcher.group(1);
                    double weight = Double.parseDouble(weightMatcher.group(1));

                    if (extractedPortId.equals(portID)) {
                        totalWeight += weight;
                    }
                }
            }
        }

        System.out.println("Total Weight of Containers at Port " + portID + ": " + totalWeight);
        return totalWeight;
    }
    static Pattern CONTAINER_ID_PATTERN = Pattern.compile("Container\\{ID='(.*?)'");
    public static double getTotalWeightOfContainersInVehicle(List<String> containerIDs) throws IOException {
        double totalWeight = 0.0;

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher containerIDMatcher = CONTAINER_ID_PATTERN.matcher(line);
                Matcher weightMatcher = WEIGHT_PATTERN.matcher(line);

                if (containerIDMatcher.find() && weightMatcher.find()) {
                    String containerID = containerIDMatcher.group(1);
                    double weight = Double.parseDouble(weightMatcher.group(1));
                    if (containerIDs== null) {
                        totalWeight+= 0;
                        break;
                    }
                    if (containerIDs.contains(containerID)) {
                        totalWeight += weight;
                    }
                }
            }
        }

        return totalWeight;
    }





}
