package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.main.java.components.team7ContainerPortManagement.views.Containers.InputContainer.readContainersFromFile;
import static src.main.java.components.team7ContainerPortManagement.views.Containers.InputContainer.writeContainersToFile;

public class Container {
    private final String ID;
    private final double weight;
    private ContainerType containerType;  // Use the enum instead of String
    private boolean isLoaded;
    private Port port;  // Add this line
    // Constructors, getters, setters and other methods here...
    public Container(String ID, double weight, ContainerType containerType,Port port) {
        this.ID = ID;
        this.weight = weight;
        this.containerType = containerType;
        this.isLoaded = false; // Initialize isLoaded to false
        this.port = port;
    }

    public Container(String ID, double weight, ContainerType containerType, boolean isLoaded, Port port) {
        this.ID = ID;
        this.weight = weight;
        this.containerType = containerType;
        this.isLoaded = isLoaded;
        this.port = port;
    }

    public String getID() {
        return ID;
    }

    public double getWeight() {
        return weight;
    }
    public double getWeightFromFile(String containerID) throws IOException {
        String filePath = "src/main/java/components/team7ContainerPortManagement/models/utils/container.txt";

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ID='" + containerID + "'")) {
                    String weightPattern = "weight=([\\d.]+)";
                    Pattern pattern = Pattern.compile(weightPattern);
                    Matcher matcher = pattern.matcher(line);

                    if (matcher.find()) {
                        String weightStr = matcher.group(1);
                        return Double.parseDouble(weightStr);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Container file not found.");
            e.printStackTrace();
        }

        return 0.0; // Default value if weight is not found
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

    public void printPortName() {
        if (this.port != null) {
            System.out.println("Container belongs to port: " + this.port.getName());
        } else {
            System.out.println("Container does not belong to any port");
        }
    }
//    public double fuelConsumption(boolean isShip) {
//        double rate = 0;
//        switch (containerType) {
//            case DRY_STORAGE:
//                //If Ship => 1st value, else second value
//                rate = isShip ? 3.5 : 4.6;
//                break;
//            case OPEN_TOP:
//                rate = isShip ? 2.8 : 3.2;
//                break;
//            case OPEN_SIDE:
//                rate = isShip ? 2.7 : 3.2;
//                break;
//            case REFRIGERATED:
//                rate = isShip ? 4.5 : 5.4;
//                break;
//            case LIQUID:
//                rate = isShip ? 4.8 : 5.3;
//                break;
//        }
//        return rate * weight;
//    }
public void updateStatusContainer(boolean isLoaded) throws IOException {
    setLoaded(isLoaded);

    // Assuming you have a method to update the container data in the file
    updateContainerInFile();
}

    private void updateContainerInFile() throws IOException {
        // Assuming you have methods to read, update, and write the container data to the file
        List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/container.txt");

        for (int i = 0; i < containers.size(); i++) {
            if (containers.get(i).getID().equals(this.getID())) {
                containers.set(i, this);
                break;
            }
        }

        writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/models/utils/container.txt");
    }



}
