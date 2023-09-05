package src.main.java.components.team7ContainerPortManagement.view;

import src.main.java.components.team7ContainerPortManagement.Controller.portController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.VehicleInPort.listAllShipInPortAdmin;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateFuelInDay.calculateFuelInDayAdmin;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.containerTypeWeight.getTotalTypeContainerWeightAdmin;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.displayTripByDate.ListTripInDayAdmin;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.tripDateSelector.listTripsBetweenDatesAdmin;
import static src.main.java.components.team7ContainerPortManagement.Controller.portController.*;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortsFromFile;
import static src.main.java.components.team7ContainerPortManagement.view.mainMenu.*;

public class AdminMenu {
    public static void adminMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║               ADMIN MENU                 ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Port Controller                     ║");
            System.out.println("║  [2] Vehicle Controller                  ║");
            System.out.println("║  [3] Container Controller                ║");
            System.out.println("║  [4] Admin Statistic Operation           ║");
            System.out.println("║  [5] Port List                           ║");
            System.out.println("║  [6] Port Manager Functions              ║");
            System.out.println("║  [0] Back to Main Menu                   ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("  Choose an option: ");


            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminPortController();
                    break;
                case 2:
                    vehicleControllerMenu(selectPort());
                    break;
                case 3:
                    //container
                    containerMenu(selectPort());
                    break;
                case 4:
                    adminPortOperation();
                    break;
                case 5:
                    List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
                    System.out.println("Current Ports:");
                    portController.displayAllPorts();
                    System.out.println("Press any key to return...");
                    scanner.next();
                    return;
                case 6:
                    portManagerMenu();
                    break;
                case 0:
                    mainLoop();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static Port selectPort() throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<Port> availablePorts = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
        portController.displayAllPorts();
//    System.out.print("Choose a port by order number: ");
        int selectedPortOrderNumber;
        while (true) {
            try {
                System.out.print("Choose a port by order number: ");
                selectedPortOrderNumber = scanner.nextInt();
                if (selectedPortOrderNumber >= 1 && selectedPortOrderNumber <= availablePorts.size()) {
                    break; // Exit the loop if a valid port is selected
                } else {
                    System.out.println("Invalid choice. Please select a number between 1 and " + availablePorts.size() + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

// Get the selected port object
        Port selectedPort = availablePorts.get(selectedPortOrderNumber - 1);
        return selectedPort;
    }
    public static void adminPortController() throws IOException {
        // Load available ports from port.txt and display them here
        Scanner scanner = new Scanner(System.in);

        // Load available ports from port.txt and display them here


        while (true) {

            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║               ADMIN MENU                 ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Create Port                         ║");
            System.out.println("║  [2] Remove Port                         ║");
            System.out.println("║  [3] Update Port                         ║");
            System.out.println("║  [4] View Current Port                   ║");
            System.out.println("║  [5] Port operation                      ║");
            System.out.println("║  [0] Back to Main Menu                   ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("  Choose an option: ");


            int choice = scanner.nextInt();
            switch (choice) {
                case 1:

                    portController.inputPort();
                    break;
                case 2:
                    deletePort(selectPort());
                    //remove port
                    break;
                case 3:
                    //update Port
                    updatePort(selectPort());
                case 4:
                    //View port
                    portController.displayAllPorts();
                    System.out.println("Press any key to return...");
                    scanner.nextLine();
                case 5:
                    adminPortOperation();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");

            }
        }
    }
    public static void adminPortOperation() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("╔══════════════════════════════════════════╗");
        System.out.println("║         ADMIN MENU (Port operation)      ║");
        System.out.println("╟──────────────────────────────────────────╢");
        System.out.println("║  [1] Fuel Use in specific Day            ║"); //operation 1
        System.out.println("║  [2] Total Weight of Container Type      ║"); //operation 2
        System.out.println("║  [3] List all ship in port               ║");
        System.out.println("║  [4] List all trip in specific Day       ║");
        System.out.println("║  [5] List all trip from Day A to day B   ║");
        System.out.println("║  [0] Back to Main Menu                   ║");
        System.out.println("╚══════════════════════════════════════════╝");
        System.out.print("  Choose an option: ");

        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                calculateFuelInDayAdmin();
                break;
            case 2:
                //remove port
                getTotalTypeContainerWeightAdmin();
                break;
            case 3:
                listAllShipInPortAdmin();
                break;
            case 4:
                ListTripInDayAdmin();
                break;
            case 5:
                listTripsBetweenDatesAdmin();
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");

        }
    }

}
