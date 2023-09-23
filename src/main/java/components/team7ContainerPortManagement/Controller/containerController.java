package src.main.java.components.team7ContainerPortManagement.Controller;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import static src.main.java.components.team7ContainerPortManagement.Controller.portController.generateRandomID;

import static src.main.java.components.team7ContainerPortManagement.models.entities.Container.getTotalContainerWeightByPort;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.getContainerByID;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.readContainersFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortContainerMapFromFile;

public class containerController {
    public static void createContainer(Port currentPort) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
        FileWriter containerWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt", true);

        // Collect input values
//        System.out.println("Enter container ID:");
        String containerID;
//        scanner.nextLine();
        containerID = "c-" + generateRandomID();
        // Check if the port ID already exists in the file
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
        double currentWeightOfContainerInPort = getTotalContainerWeightByPort(currentPort.getID());
        if (currentWeightOfContainerInPort + weight > currentPort.getStoringCapacity()) {
            System.out.println("This port storing capacity is overweight, try to modify the container weight!");
            System.out.println("Weight when add new container: " + currentWeightOfContainerInPort + weight);
            System.out.println("Port capacity: " + currentPort.getStoringCapacity());
            return;
        }
        System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
        System.out.println("╟" + ANSI_CYAN + "                 CREATE CONTAINER SUCCESSFULLY" + "          ║");
        System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
        System.out.println( "            Container ID: " + containerID);
        System.out.println( "            Container Weight: " + weight);
        System.out.println( "            Container Type: " + selectedType);
        System.out.println( "            Container Port: " + currentPort.getID());
        System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
        System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);

        currentPort.addContainer(newContainer);
        // Write the input values to the file
        containerWriter.write(newContainer + "\n");
        writeContainerToPort(currentPort, newContainer); //write to port_containers.txt
        containerWriter.close();
        System.out.print("Press any key to return...\n");
        scanner.next();  // Wait for the user to press Enter

    }
    //Update
    public static void updateContainer(String portID) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
        List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");

        // Filter containers belonging to the same port
        List<Container> filteredContainers = containers.stream()
                .filter(container -> container.getPort().getID().equals(portID))
                .collect(Collectors.toList());

        // Show the list of containers to the user
        System.out.println("Select a container to update from port " + portID + ":");
        if (filteredContainers.isEmpty()) {
            System.out.println("No containers found for this port.");
            return;
        }
        for (int i = 0; i < filteredContainers.size(); i++) {
            System.out.println((i + 1) + ". " + filteredContainers.get(i).getID());
        }

        // Get the user's choice
        System.out.println("Enter the order number of the container you want to update:");
        int selectedIndex = scanner.nextInt() - 1;  // Convert order number to index
        if (selectedIndex >= 0 && selectedIndex < filteredContainers.size()) {
            Container selectedContainer = filteredContainers.get(selectedIndex);

            // Update the container's weight
            System.out.println("Enter new weight for " + selectedContainer.getID() + ":");
            double newWeight = scanner.nextDouble();
            selectedContainer.setWeight(newWeight);

            // Save the updated list of containers back to the file
            saveContainersToFile(containers);
            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 UPDATE CONTAINER SUCCESSFULLY" + "          ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            Container Weight: " + newWeight);
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.print("Press any key to return...\n");
            scanner.next();  // Wait for the user to press Enter
            return;
        } else {
            System.out.println("Invalid selection!");
        }
    }
    public static void deleteContainer(String portID) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
        List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt");

        // Filter containers belonging to the same port
        List<Container> filteredContainers = containers.stream()
                .filter(container -> container.getPort().getID().equals(portID))
                .collect(Collectors.toList());

        // Show the list of containers to the user
        System.out.println("Select a container to delete from port " + portID + ":");
        if (filteredContainers.isEmpty()) {
            System.out.println("No containers found for this port.");
            return;
        }
        for (int i = 0; i < filteredContainers.size(); i++) {
            System.out.println((i + 1) + ". " + filteredContainers.get(i).getID());
        }

        // Get the user's choice
        System.out.println("Enter the order number of the container you want to delete:");
        int selectedIndex = scanner.nextInt() - 1;  // Convert order number to index

        if (selectedIndex >= 0 && selectedIndex < filteredContainers.size()) {
            Container selectedContainer = filteredContainers.get(selectedIndex);

            // Remove the container from the main list
            containers.remove(selectedContainer);
            // Save the updated list of containers back to the file
            saveContainersToFile(containers);
            //Delete from port_container
            Map<String, List<String>> containerPortMap = readPortContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt");
            String containerIDToRemove = selectedContainer.getID();

            System.out.println(containerPortMap);
            if (containerPortMap.containsKey(portID)) {
                List<String> currentPortContainersMap = new ArrayList<>(containerPortMap.get(portID));
                System.out.println(currentPortContainersMap);

                currentPortContainersMap.remove(containerIDToRemove);
                containerPortMap.put(portID, currentPortContainersMap);
                System.out.println(containerPortMap);
            }
            writePortContainerMapToFile(containerPortMap,"src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt");



            System.out.println(      ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 DELETE CONTAINER SUCCESSFULLY" + "          ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.print("Press any key to return...");
            scanner.next();  // Wait for the user to press Enter
        } else {
            System.out.println("Invalid selection!");
        }
    }


    public static void saveContainersToFile(List<Container> containers) throws IOException {
        FileWriter containerWriter = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt", false);
        for (Container container : containers) {
            containerWriter.write(container.toString() + "\n");
        }
        containerWriter.close();
    }
//======================================================
    //==================================================
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
            if (ObjectContainerList == null) {
                System.out.println("There is no container");
                break;
            }
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
