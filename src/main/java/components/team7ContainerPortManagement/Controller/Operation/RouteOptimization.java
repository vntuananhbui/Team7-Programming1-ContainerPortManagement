package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import src.main.java.components.team7ContainerPortManagement.Controller.portController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.calculateDistanceBetweenPorts;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readAvailablePortsFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortsFromFile;
import static src.main.java.components.team7ContainerPortManagement.view.AdminMenu.selectPort;

public class RouteOptimization {
    public static List<Port> selectPortRoute(Port selectPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Port> availablePorts = readAvailablePortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt",selectPort);

        System.out.println("Available Ports:");
        for (int i = 0; i < availablePorts.size(); i++) {
            System.out.println((i + 1) + ". " + availablePorts.get(i).getName());
        }

        List<Port> selectedPorts = new ArrayList<>();
        System.out.println("Choose destination ports by entering their order numbers (0 to finish):");

        while (true) {
            int selectedPortOrderNumber = scanner.nextInt();
            if (selectedPortOrderNumber == 0) {
                break;
            }

            if (selectedPortOrderNumber >= 1 && selectedPortOrderNumber <= availablePorts.size()) {
                selectedPorts.add(availablePorts.get(selectedPortOrderNumber - 1));
                System.out.println("Port selected: " + availablePorts.get(selectedPortOrderNumber - 1).getName());
            } else {
                System.out.println("Invalid choice. Please select a valid order number or enter 0 to finish.");
            }
        }

        return selectedPorts;
    }
    public static List<List<Port>> generatePermutations(Port currentPort, List<Port> destinations) {
        List<List<Port>> permutations = new ArrayList<>();
        generatePermutationsHelper(currentPort, destinations, new ArrayList<>(), permutations);
        return permutations;
    }

    private static void generatePermutationsHelper(Port currentPort, List<Port> remainingPorts, List<Port> currentPermutation, List<List<Port>> permutations) {
        if (remainingPorts.isEmpty()) {
            // If no remaining ports, create a single permutation with only the current port.
            List<Port> permutation = new ArrayList<>(currentPermutation);
            permutation.add(currentPort);
            permutation.add(currentPort);
            permutations.add(permutation);
        } else {
            for (int i = 0; i < remainingPorts.size(); i++) {
                Port nextPort = remainingPorts.get(i);

                // Calculate the distance between currentPort and nextPort.
                double distance = calculateDistanceBetweenPorts(currentPort.getLatitude(), currentPort.getLongitude(), nextPort.getLatitude(), nextPort.getLongitude());

                // Create a copy of the remainingPorts list without the nextPort.
                List<Port> updatedRemainingPorts = new ArrayList<>(remainingPorts);
                updatedRemainingPorts.remove(i);

                // Create a copy of the current permutation and add the current port.
                List<Port> updatedPermutation = new ArrayList<>(currentPermutation);
                updatedPermutation.add(currentPort);

                // Recursively generate permutations for the remaining ports.
                generatePermutationsHelper(nextPort, updatedRemainingPorts, updatedPermutation, permutations);
            }
        }
    }

    public static double calculateDistancePort(Port currentPort) throws IOException {
        Port selectedPort = selectPort();
        System.out.println("Distance from " + currentPort.getID() + " to " + selectedPort.getID() + " is: " + currentPort.calculateDistanceTo(selectedPort));
        return currentPort.calculateDistanceTo(selectedPort);
    }
    public static List<Port> selectPortsAndOptimizeRoute(Port selectPort) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        Port currentPort = selectPort; // Get the starting port
        List<Port> selectedPorts = selectPortRoute(selectPort); // Get the list of destination ports
        List<Port> optimizedRoute = optimizeRoute(currentPort, selectedPorts);

        // Print the optimized route
//        System.out.println("Optimized Route:");
        System.out.println();
        System.out.println(ANSI_CYAN + "╔══════════════════════════════════════════════════════════════╗");
        System.out.println("╟" + ANSI_CYAN + "                    ROUTE OPTIMIZATION" + "                        ║");
        System.out.println("╟──────────────────────────────────────────────────────────────╢"+ANSI_RESET);

        double totalDistance = 0;
        boolean isFirstPort = true;

        for (int i = 0; i < optimizedRoute.size(); i++) {
            Port destinationPort = optimizedRoute.get(i);
            double distanceToPort = currentPort.calculateDistanceTo(destinationPort);

            if (distanceToPort > 0) {
                totalDistance += distanceToPort;
                System.out.print("  "+currentPort.getName() + " ===> " + destinationPort.getName() + " (" + distanceToPort + " km)\n");

                currentPort = destinationPort;
            }
        }
        System.out.println();
        System.out.println("  Total Distance: " + totalDistance + " km");
        System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
        System.out.println(ANSI_CYAN + "╚══════════════════════════════════════════════════════════════╝" + ANSI_RESET);

        return optimizedRoute;
    }


    public static List<Port> optimizeRoute(Port currentPort, List<Port> destinations) {
        List<Port> optimizedRoute = new ArrayList<>();
        List<List<Port>> permutations = generatePermutations(currentPort, destinations);
        double minDistance = Double.MAX_VALUE;

        for (List<Port> permutation : permutations) {
            double distance = calculateTotalDistance(permutation);
            if (distance < minDistance) {
                minDistance = distance;
                optimizedRoute = new ArrayList<>(permutation);
            }
        }

        return optimizedRoute;
    }

    public static double calculateTotalDistance(List<Port> route) {
        double totalDistance = 0.0;
        for (int i = 0; i < route.size() - 1; i++) {
            Port port1 = route.get(i);
            Port port2 = route.get(i + 1);
            totalDistance += port1.calculateDistanceTo(port2);
        }
        return totalDistance;

    }

}
