package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.IOException;
import java.util.List;

import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.readContainersFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeContainersToFile;

public class Container {
    private final String ID;
    private final double weight;
    private ContainerType containerType;  // Use the enum instead of String
    private boolean isLoaded;
    private Port port;

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
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



}
