package src.main.java.components.team7ContainerPortManagement.Controller;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeContainerToPort;

public class containerController {
    public static void createContainer(Port currentPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileWriter containerWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt", true);

        // Collect input values
        System.out.println("Enter container ID:");
        String containerID = "c-" + scanner.next();
        scanner.nextLine();

        System.out.println("Enter container weight:");
        double weight = scanner.nextDouble();

        // Display available container types for user to choose
        System.out.println("Available container types:");
        ContainerType[] containerTypes = ContainerType.values();
        for (int i = 0; i < containerTypes.length; i++) {
            System.out.println((i + 1) + ": " + containerTypes[i].getLabel());
        }
        System.out.println("Choose a container type by number:");
        int typeNumber = scanner.nextInt();

        // Convert the chosen number to index and get the selected container type
        ContainerType selectedType = containerTypes[typeNumber - 1];

        // Create an instance of Container using the input values and current port
        Container newContainer = new Container(containerID, weight, selectedType,currentPort);
        currentPort.addContainer(newContainer);
        // Write the input values to the file
        containerWriter.write(newContainer + "\n");
        writeContainerToPort(currentPort, newContainer); //write to port_containers.txt
        containerWriter.close();
    }
}
