package src.main.java.components.team7ContainerPortManagement.Controller;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.getContainerByID;
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

    public static double getFinalShipConsumption(List<String> containerIDs) throws IOException {
        double finalConsumption = 0;
        for (String container : containerIDs) {
            Container ObjectContainerList = getContainerByID(container);
//            System.out.println("Object container: " + ObjectContainerList);
            assert ObjectContainerList != null;
            ContainerType containerConsumption = ObjectContainerList.getContainerType();
            System.out.println(containerConsumption); //DRY_STORAGE
            double currentConsumption = 0; // value for the current container

            switch (containerConsumption) {
                case DRY_STORAGE:
                    currentConsumption =  3.5;
                    break;
                case OPEN_TOP:
                    currentConsumption = 2.8;
                case OPEN_SIDE:
                    currentConsumption= 2.7;
                    break;
                case REFRIGERATED:
                    currentConsumption= 4.5;
                    break;
                case LIQUID:
                    currentConsumption= 4.8;
                    break;
                default:
                    currentConsumption = 3.5;
                    break;
            }
            if (currentConsumption > finalConsumption) {
                finalConsumption = currentConsumption;
            }
        }

        return finalConsumption;
    }
    public static double getFinalTruckConsumption(List<String> containerIDs) throws IOException {
        double finalConsumption = 0;
        for (String container : containerIDs) {
            Container ObjectContainerList = getContainerByID(container);
//            System.out.println("Object container: " + ObjectContainerList);
            assert ObjectContainerList != null;
            ContainerType containerConsumption = ObjectContainerList.getContainerType();
            System.out.println(containerConsumption); //DRY_STORAGE
            double currentConsumption = 0; // value for the current container

            switch (containerConsumption) {
                case DRY_STORAGE:
                    currentConsumption =  4.6;
                    break;
                case OPEN_TOP:
                case OPEN_SIDE:
                    currentConsumption= 3.2;
                    break;
                case REFRIGERATED:
                    currentConsumption= 5.4;
                    break;
                case LIQUID:
                    currentConsumption= 5.3;
                    break;
                default:
                    currentConsumption = 3.5;
                    break;
            }
            if (currentConsumption > finalConsumption) {
                finalConsumption = currentConsumption;
            }
        }

        return finalConsumption;
    }

}
