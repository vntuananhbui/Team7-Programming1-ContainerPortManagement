package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class containerTypeWeight {
    private static final Pattern PORT_ID_PATTERN = Pattern.compile("port=Port\\{ID='(.*?)'");
    private static final Pattern WEIGHT_PATTERN = Pattern.compile("weight=(.*?),");
    private static final Pattern CONTAINER_TYPE_PATTERN = Pattern.compile("containerType=(.*?),");

    public static double getTotalTypeContainerWeight(String portID) throws IOException {
        Map<String, Double> totalWeights = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher portMatcher = PORT_ID_PATTERN.matcher(line);
                Matcher weightMatcher = WEIGHT_PATTERN.matcher(line);
                Matcher containerTypeMatcher = CONTAINER_TYPE_PATTERN.matcher(line);

                if (portMatcher.find() && weightMatcher.find() && containerTypeMatcher.find()) {
                    String extractedPortId = portMatcher.group(1);
                    double weight = Double.parseDouble(weightMatcher.group(1));
                    String containerType = containerTypeMatcher.group(1);

                    if (extractedPortId.equals(portID)) {
                        totalWeights.put(containerType, totalWeights.getOrDefault(containerType, 0.0) + weight);
                    }
                }
            }
        }

        // Display container types and their order numbers for the user to choose
        System.out.println("Available Container Types for Port " + portID + ":");
        List<String> containerTypes = new ArrayList<>(totalWeights.keySet());
        for (int i = 0; i < containerTypes.size(); i++) {
            System.out.println((i + 1) + ". " + containerTypes.get(i));
        }

        // Prompt the user to choose a container type by order number
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the order number of the Container Type: ");
        int selectedOrder = Integer.parseInt(scanner.nextLine());

        if (selectedOrder > 0 && selectedOrder <= containerTypes.size()) {
            String selectedContainerType = containerTypes.get(selectedOrder - 1);
            double totalWeight = totalWeights.getOrDefault(selectedContainerType, 0.0);
            System.out.println("Total Weight of " + selectedContainerType + " Containers at Port " + portID + ": " + totalWeight);
            return totalWeight;
        } else {
            System.out.println("Invalid order number.");
            return 0.0;
        }
    }

    public static double getTotalTypeContainerWeightAdmin() throws IOException {
        Map<String, Double> totalWeights = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                Matcher weightMatcher = WEIGHT_PATTERN.matcher(line);
                Matcher containerTypeMatcher = CONTAINER_TYPE_PATTERN.matcher(line);

                if (weightMatcher.find() && containerTypeMatcher.find()) {
                    double weight = Double.parseDouble(weightMatcher.group(1));
                    String containerType = containerTypeMatcher.group(1);

                    totalWeights.put(containerType, totalWeights.getOrDefault(containerType, 0.0) + weight);
                }
            }
        }

        // Display container types and their order numbers for the user to choose
        System.out.println("Available Container Types:");
        List<String> containerTypes = new ArrayList<>(totalWeights.keySet());
        for (int i = 0; i < containerTypes.size(); i++) {
            System.out.println((i + 1) + ". " + containerTypes.get(i));
        }

        // Prompt the user to choose a container type by order number
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the order number of the Container Type: ");
        int selectedOrder = Integer.parseInt(scanner.nextLine());

        if (selectedOrder > 0 && selectedOrder <= containerTypes.size()) {
            String selectedContainerType = containerTypes.get(selectedOrder - 1);
            double totalWeight = totalWeights.getOrDefault(selectedContainerType, 0.0);
            System.out.println("Total Weight of " + selectedContainerType + " Containers: " + totalWeight);
            return totalWeight;
        } else {
            System.out.println("Invalid order number.");
            return 0.0;
        }
    }


}
