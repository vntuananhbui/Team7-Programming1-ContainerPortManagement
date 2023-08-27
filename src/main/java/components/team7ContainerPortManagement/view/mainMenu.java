package src.main.java.components.team7ContainerPortManagement.view;

import src.main.java.components.team7ContainerPortManagement.Controller.Operation.FileUtility;
import src.main.java.components.team7ContainerPortManagement.Controller.Operation.moveTo;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.containerController;
import src.main.java.components.team7ContainerPortManagement.Controller.portController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.calculateFuelConsumption;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.calculateTotalWeightContainerPort;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.loadAndunloadContainer.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.containerController.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.portController.deletePort;
import static src.main.java.components.team7ContainerPortManagement.Controller.portController.updatePort;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortsFromFile;

public class mainMenu {
    public static void displayBanner() {
        System.out.println("  #####   ######  #     #   #####   ");
        System.out.println("#     #  #     #  ##   ##  #     #  ");
        System.out.println("#        #     #  # # # #  #        ");
        System.out.println("#        ######   #  #  #   #####   ");
        System.out.println("#        #        #     #        #  ");
        System.out.println("#     #  #        #     #  #     #  ");
        System.out.println(" #####   #        #     #   #####   ");
        System.out.println("====================================");
        System.out.println("  TEAM 7 CONTAINER PORT MANAGEMENT  ");
        System.out.println("           SYSTEM 2023              ");
        System.out.println("====================================");
        System.out.println();

    }

    public static void mainLoop() throws IOException {

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Container Port Management System!");

        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║          CONTAINER PORT SYSTEM           ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Admin                               ║");
            System.out.println("║  [2] Port Manager                        ║");
            System.out.println("║  [3] Reset File                          ║");
            System.out.println("║  [0] Exit                                ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("  Choose an option: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    portManagerMenu();
                    break;
                case 3:
                    resetFileMenu();
                    System.out.println("File reset successfully, Let's Start now!");
                    break;
                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static void adminMenu() throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║               ADMIN MENU                 ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Create Port                         ║");
            System.out.println("║  [2] Remove Port                         ║");
            System.out.println("║  [3] Port List                           ║");
            System.out.println("║  [4] Port Manager Functions              ║");
            System.out.println("║  [0] Back to Main Menu                   ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("  Choose an option: ");


            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    portController.inputPort();
                    break;
                case 2:
                    // Call the function to remove a port
//                    InputManager.removePort();
                    break;
                case 3:
                    List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
                    System.out.println("Current Ports:");
                    portController.displayAllPorts();
                    break;
                case 4:
                    portManagerMenu();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static void portManagerMenu() throws IOException {
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
            System.out.println("║  [4] Calculate Menu                      ║");
            System.out.println("║  [5] Update Port                         ║");
            System.out.println("║  [6] Delete Port                         ║");
            System.out.println("║  [7] Change Port                         ║");
            System.out.println("║  [8] Port Vehicles                       ║");
            System.out.println("║  [9] Load Container                      ║");
            System.out.println("║  [10] Test                               ║");
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
                    vehicleControllerMenu(selectedPort);
                    break;
                case 4:
                    PortOperationsMenu(selectedPort);
                    break;
                case 5:
                    calculateTotalWeightContainerPort(selectedPort);
                case 0:
                    portManagerMenu();
                    break;
                case 6:
                    updatePort(selectedPort);
                    break;

                case 7:
                    deletePort(selectedPort);
                    return;
                case 8:

                    System.out.println(selectedPort.getName());
                    System.out.println(selectedPort.getLatitude());
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
public static void vehicleControllerMenu(Port selectedPort) throws IOException {
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
                ShipMenu(selectedPort);
                break;
            case 2:
                TruckMenuManage(selectedPort);
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
    public static void containerMenu(Port selectPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==============================================");
            System.out.println("            CONTAINER MENU                   ");
            System.out.println("==============================================");
            System.out.println("|                                            |");
            System.out.println("|   [1] Create Container                      |");
            System.out.println("|   [2] View all Containers                   |");
            System.out.println("|   [3] Update Container                      |");
            System.out.println("|   [4] Delete Container                      |");
            System.out.println("|   [5] Return to Port Manager Menu           |");
            System.out.println("|                                            |");
            System.out.println("==============================================");
            System.out.print("   Choose an option: ");
            int choiceTruck = scanner.nextInt();

            switch (choiceTruck) {
                case 1:
                    createContainer(selectPort);
                    break;
                case 2:
                    break;
                case 3:
                    updateContainer(selectPort.getID());
                    break;
                case 4:
                    deleteContainer(selectPort.getID());
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }

    public static void TruckMenuManage(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║            CONTAINER MENU                ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Create Container                    ║");
            System.out.println("║  [2] View all Containers                 ║");
            System.out.println("║  [3] Update Container                    ║");
            System.out.println("║  [4] Delete Container                    ║");
            System.out.println("║  [5] Return to Port Manager Menu         ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("   Choose an option: ");

            int choiceTruck = scanner.nextInt();

            switch (choiceTruck) {
                case 1:
                    BasicTruckMenu(selectedPort);
                    break;
                case 2:
                    TankerTruckMenu(selectedPort);
                    break;
                case 3:
                    ReeferTruckMenu(selectedPort);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static void ShipMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║         PORT OPERATIONS MENU             ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Create Ship                         ║");
            System.out.println("║  [2] Remove Ship                         ║");
            System.out.println("║  [3] Load Container on Ship              ║");
            System.out.println("║  [4] Unload Container on Ship            ║");
            System.out.println("║  [5] Return                              ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("   Choose an option: ");

            int choiceShip = scanner.nextInt();
            switch (choiceShip) {
                case 1:
                    shipController.createShip(selectedPort);
                    break;
                case 3:
                    loadContainerShipMenu(selectedPort);
                    break;
                case 4:
                    unloadContainerShipMenu(selectedPort);
                    break;
                case 5:
                    return;
            }

        }
    }

    public static void BasicTruckMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║           BASIC TRUCK MENU               ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Create Basic Truck                  ║");
            System.out.println("║  [2] Remove Basic Truck                  ║");
            System.out.println("║  [3] Load Container on Basic Truck       ║");
            System.out.println("║  [4] Unload Container on Basic Truck     ║");
            System.out.println("║  [5] Return                              ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("   Choose an option: ");

            int choiceShip = scanner.nextInt();
            switch (choiceShip) {
                case 1:
                    basictruckController.createBasicTruck(selectedPort);
                    break;
                case 3:
                    loadContainerBasicTruckMenu(selectedPort);
                    break;
                case 4:
                    unloadContainerBasicTruckMenu(selectedPort);
                    break;
                case 5:
                    return;
            }

        }
    }
    public static void TankerTruckMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {

                System.out.println("╔══════════════════════════════════════════╗");
                System.out.println("║           TANKER TRUCK MENU              ║");
                System.out.println("╟──────────────────────────────────────────╢");
                System.out.println("║  [1] Create Tanker Truck                 ║");
                System.out.println("║  [2] Remove Tanker Truck                 ║");
                System.out.println("║  [3] Load Container on Tanker Truck      ║");
                System.out.println("║  [4] Unload Container on Tanker Truck    ║");
                System.out.println("║  [5] Return                              ║");
                System.out.println("╚══════════════════════════════════════════╝");
                System.out.print("  Choose an option: ");


            int choiceShip = scanner.nextInt();
            switch (choiceShip) {
                case 1:
                    tankertruckController.createTankerTruck(selectedPort);
                    break;
                case 3:
                    loadContainerTankerTruckMenu(selectedPort);
                    break;
                case 4:
                    unloadContainerTankerTruckMenu(selectedPort);
                    break;
                case 5:
                    return;
            }

        }
    }
    public static void ReeferTruckMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║           REEFER TRUCK MENU              ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Create Reefer Truck                 ║");
            System.out.println("║  [2] Remove Reefer Truck                 ║");
            System.out.println("║  [3] Load Container on Reefer Truck      ║");
            System.out.println("║  [4] Unload Container on Reefer Truck    ║");
            System.out.println("║  [5] Return                              ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("   Choose an option: ");

            int choiceShip = scanner.nextInt();
            switch (choiceShip) {
                case 1:
                    reefertruckController.createReeferTruck(selectedPort);
                    break;
                case 3:
                    loadContainerReeferTruckMenu(selectedPort);
                    break;
                case 4:
                    unloadContainerReeferTruckMenu(selectedPort);
                    break;
                case 5:
                    return;
            }

        }
    }

    public static void PortOperationsMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1. Move Vehicle to another Port");
//            System.out.println("2. Remove Reefer Truck");
//            System.out.println("3. Load Container on Reefer Truck");
//            System.out.println("4. Unload Container on Reefer Truck");
            System.out.println("5. Return");
            System.out.print("Choose an option: ");
            int choiceShip = scanner.nextInt();
            switch (choiceShip) {
                case 1:
                    moveTo.moveToMenu(selectedPort);
                    break;
                case 3:
                    loadContainerReeferTruckMenu(selectedPort);
                    break;
                case 4:
                    unloadContainerReeferTruckMenu(selectedPort);
                    break;
                case 5:
                    return;
            }

        }    }
    public static void resetFileMenu() {
        FileUtility utility = new FileUtility();
        utility.writeAllData();
    }
    public static void statisticOperationsMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("==============================================");
            System.out.println("          Statistics Operations Menu          ");
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
                    ShipMenu(selectedPort);
                    break;
                case 2:
                    TruckMenuManage(selectedPort);
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
}
