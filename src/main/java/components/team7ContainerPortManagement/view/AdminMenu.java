package src.main.java.components.team7ContainerPortManagement.view;

import src.main.java.components.team7ContainerPortManagement.Controller.UserController.AdminController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.portController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.IOException;
import java.util.*;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.RouteOptimization.selectPortsAndOptimizeRoute;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.StatisticOperationAdmin.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.VehicleInPort.listAllShipInPortAdmin;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.loadContainerReeferTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.unloadContainerReeferTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.loadContainerShipMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.unloadContainerShipMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.loadContainerTankerTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.unloadContainerTankerTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.portController.deletePort;
import static src.main.java.components.team7ContainerPortManagement.Controller.portController.updatePort;
import static src.main.java.components.team7ContainerPortManagement.models.entities.CapacityPrice.manageCapacityPrices;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Container.viewContainerInPortAdmin;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle.moveToMenu;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readAvailablePortsFromFile;
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
            System.out.println("║  [7] Manage Update Port Operation        ║");
            System.out.println("║  [0] Back to Main Menu                   ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("  Choose an option: ");

            try {
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        adminPortController();
                        break;
                    case 2:
                        vehicleControllerMenuAdmin(selectPort());
                        break;
                    case 3:
                        // container
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
                        break;
                    case 6:
                        portManagerMenuAdmin();
                        break;
                    case 7:
                        manageCapacityPrices();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }

    public static void vehicleControllerMenuAdmin(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==============================================");
            System.out.println("               VEHICLE MENU                   ");
            System.out.println("==============================================");
            System.out.println("|                                             |");
            System.out.println("|   [1] Ship Controller                       |");
            System.out.println("|   [2] Truck Controller                      |");
            System.out.println("|   [3] Update Vehicle                        |");
            System.out.println("|   [4] Delete Vehicle                        |");
            System.out.println("|   [5] Refuel Vehicle                        |");
            System.out.println("|   [0] Back to Main Menu                     |");
            System.out.println("|                                             |");
            System.out.println("==============================================");
            System.out.print("   Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ShipMenuAdmin(selectedPort);
                    break;
                case 2:
                    TruckMenuManageAdmin(selectedPort);
                    break;
                case 3:
                    updateVehicle(selectedPort.getID());
                    break;
                case 4:
                    deleteVehicle(selectedPort.getID());
                    break;
                case 5:
                    refuelVehicle(selectedPort.getID());
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");


            }
        }
    }
    public static void portManagerMenuAdmin() throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Load available ports from port.txt and display them here
        List<Port> availablePorts = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
        portController.displayAllPorts();
        System.out.print("Choose a port by order number: ");
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


        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║           PORT MANAGER MENU              ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Container Controller                ║");
            System.out.println("║  [2] Vehicle Controller                  ║");
            System.out.println("║  [3] Port Operations                     ║");
            System.out.println("║  [4] Vehicle Movement                    ║");
            System.out.println("║  [5] Update Port                         ║");
//            System.out.println("║  [6] Change Port                         ║");
//            System.out.println("║  [7] Port Vehicles                       ║");
            System.out.println("║  [0] Back to Main Menu                   ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("  Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    containerMenu(selectedPort);
                    break;
                case 2:
                    // Call the function to create a ship
                    vehicleControllerMenuAdmin(selectedPort);
                    break;
                case 3:
                    statisticOperationsMenu(selectedPort);
                    break;
                case 4:
                    //moveTo
                    moveToMenu(selectedPort);
                    break;
                case 5:
                    updatePort(selectedPort);
                    break;
//                case 6:
//                    adminPortController();
//                    break;
                case 7:

                case 0:
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
            System.out.println("║  [6] Assign Portmanager                  ║");
            System.out.println("║  [0] Return main menu                    ║");
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

                    return;
                case 4:
                    //View port
                    portController.displayAllPorts();
                    System.out.println("Press any key to return...");
                    scanner.next();
                    return;
                case 5:
                    adminPortOperation();

                    return;
                case 6:
                    AdminController.displayPortToPortMana();
                    AdminController.displayAllPortManager();
                    Map<String, String> chosen = AdminController.assignPortToPortManager();
                    if (chosen == null) {
                        return;
                    } else {
                        AdminController.writePortAndPortManagerFile(chosen);
                        return;
                    }
//                    AdminController.writePortAndPortManagerFile(chosen);
//                    return;
                case 0:
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
                System.out.println("Press any key to return...");
                scanner.next();
                break;
            case 3:
                listAllShipInPortAdmin();
                System.out.println("Press any key to return...");
                scanner.next();
                break;
            case 4:
                ListTripInDayAdmin();
//                System.out.println("Press any key to return...");
//                scanner.next();
                break;
            case 5:
                listTripsBetweenDatesAdmin();
                System.out.println("Press any key to return...");
                scanner.next();
                break;
            default:
                System.out.println("Invalid choice. Please select a valid option.");

        }
    }
    public static void TruckMenuManageAdmin(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║                TRUCK MENU                ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Basic Truck Menu                    ║");
            System.out.println("║  [2] Tanker Truck Menu                   ║");
            System.out.println("║  [3] Reefer Truck Menu                   ║");
            System.out.println("║  [4] Return to Port Manager Menu         ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("   Choose an option: ");

            int choiceTruck = scanner.nextInt();

            switch (choiceTruck) {
                case 1:
                    BasicTruckMenuAdmin(selectedPort);
                    break;
                case 2:
                    TankerTruckMenuAdmin(selectedPort);
                    break;
                case 3:
                    ReeferTruckMenuAdmin(selectedPort);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static void ShipMenuAdmin(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║         PORT OPERATIONS MENU             ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Create Ship                         ║");
//            System.out.println("║  [2] Remove Ship                         ║");
            System.out.println("║  [2] Load Container on Ship              ║");
            System.out.println("║  [3] Unload Container on Ship            ║");
            System.out.println("║  [0] Return                              ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("   Choose an option: ");

            int choiceShip = scanner.nextInt();
            switch (choiceShip) {
                case 1:
                    shipController.createShip(selectedPort);
                    break;
                case 2:
                    loadContainerShipMenu(selectedPort);
                    break;
                case 3:
                    unloadContainerShipMenu(selectedPort);
                    break;
                case 0:
                    return;
            }

        }
    }

    public static void BasicTruckMenuAdmin(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║           BASIC TRUCK MENU               ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Create Basic Truck                  ║");
//            System.out.println("║  [2] Remove Basic Truck                  ║");
            System.out.println("║  [2] Load Container on Basic Truck       ║");
            System.out.println("║  [3] Unload Container on Basic Truck     ║");
            System.out.println("║  [0] Return                              ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("   Choose an option: ");

            int choiceShip = scanner.nextInt();
            switch (choiceShip) {
                case 1:
                    basictruckController.createBasicTruck(selectedPort);
                    break;
                case 2:
                    loadContainerBasicTruckMenu(selectedPort);
                    break;
                case 3:
                    unloadContainerBasicTruckMenu(selectedPort);
                    break;
                case 0:
                    return;
            }

        }
    }
    public static void TankerTruckMenuAdmin(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {

            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║           TANKER TRUCK MENU              ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Create Tanker Truck                 ║");
//            System.out.println("║  [2] Remove Tanker Truck                 ║");
            System.out.println("║  [2] Load Container on Tanker Truck      ║");
            System.out.println("║  [3] Unload Container on Tanker Truck    ║");
            System.out.println("║  [0] Return                              ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("  Choose an option: ");


            int choiceShip = scanner.nextInt();
            switch (choiceShip) {
                case 1:
                    tankertruckController.createTankerTruck(selectedPort);
                    break;
                case 2:
                    loadContainerTankerTruckMenu(selectedPort);
                    break;
                case 3:
                    unloadContainerTankerTruckMenu(selectedPort);
                    break;
                case 0:
                    return;
            }

        }
    }
    public static void ReeferTruckMenuAdmin(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║           REEFER TRUCK MENU              ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Create Reefer Truck                 ║");
//            System.out.println("║  [2] Remove Reefer Truck                 ║");
            System.out.println("║  [2] Load Container on Reefer Truck      ║");
            System.out.println("║  [3] Unload Container on Reefer Truck    ║");
            System.out.println("║  [0] Return                              ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("   Choose an option: ");

            int choiceShip = scanner.nextInt();
            switch (choiceShip) {
                case 1:
                    reefertruckController.createReeferTruck(selectedPort);
                    break;
                case 2:
                    loadContainerReeferTruckMenu(selectedPort);
                    break;
                case 3:
                    unloadContainerReeferTruckMenu(selectedPort);
                    break;
                case 0:
                    return;
            }

        }
    }
}
