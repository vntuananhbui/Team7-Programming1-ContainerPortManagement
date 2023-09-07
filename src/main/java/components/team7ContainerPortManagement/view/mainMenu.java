package src.main.java.components.team7ContainerPortManagement.view;

import src.main.java.components.team7ContainerPortManagement.Controller.Operation.FileUtility;
import src.main.java.components.team7ContainerPortManagement.Controller.UserController.PortManagerController;
import src.main.java.components.team7ContainerPortManagement.Controller.portController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.StatisticOperation.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.VehicleInPort.listAllShipInPort;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.calculateTotalWeightContainerPort;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.loadContainerReeferTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.unloadContainerReeferTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.loadContainerShipMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.unloadContainerShipMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.loadContainerTankerTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.unloadContainerTankerTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.containerController.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.portController.updatePort;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle.moveToMenu;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortsFromFile;
import static src.main.java.components.team7ContainerPortManagement.view.AdminMenu.adminMenu;
import static src.main.java.components.team7ContainerPortManagement.view.userMainMenu.mainLoopUser;

public class mainMenu {
    public static void displayBanner() {


                String red = "\u001B[31m";
                String green = "\u001B[32m";
                String yellow = "\u001B[33m";
                String blue = "\u001B[34m";
                String purple = "\u001B[35m";
                String cyan = "\u001B[36m";
                String white = "\u001B[37m";
                String reset = "\u001B[0m";
                System.out.println(red + "====================================");
                System.out.println(cyan + "  " + cyan + "#####   " + purple + "######  " + yellow + "#     #   " + blue + "#####   " + reset);
                System.out.println(cyan + "#     #  " + purple + "#     #  " + yellow + "##   ##  " + blue + "#     #  " + reset);
                System.out.println(cyan + "#        " + purple + "#     #  " + yellow + "# # # #  " + blue + "#        " + reset);
                System.out.println(cyan + "#        " + purple + "######   " + yellow + "#  #  #   " + blue + "#####   " + reset);
                System.out.println(cyan + "#        " + purple + "#        " + yellow + "#     #  " + blue + "      #  " + reset);
                System.out.println(cyan + "#     #  " + purple + "#        " + yellow + "#     #  " + blue + "#     #  " + reset);
                System.out.println(cyan + " #####   " + purple + "#        " + yellow + "#     #   " + blue + "#####   " + reset);
                System.out.println(red + "====================================");
                System.out.println(red + "  TEAM 7 CONTAINER PORT MANAGEMENT  ");
                System.out.println("           SYSTEM 2023              ");
                System.out.println(red + "====================================");
                System.out.println(reset);
            }





    public static void mainLoop() throws IOException {
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";
        String purple = "\u001B[35m";
        String cyan = "\u001B[36m";
        String white = "\u001B[37m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Container Port Management System!");

        while (true) {


                    System.out.println("╔══════════════════════════════════════════╗");
                    System.out.println("║      " + blue + "   CONTAINER" + reset + " " + blue + "PORT" + reset + " " + blue + "SYSTEM" + reset + "            ║");
                    System.out.println("╟──────────────────────────────────────────╢");
                    System.out.println("║  [1] " + blue + "Admin" + reset + "                               ║");
                    System.out.println("║  [2] " + blue + "Port Manager" + reset + "                        ║");
                    System.out.println("║  [3] " + blue + "Reset File" + reset + "                          ║");
                    System.out.println("║  [0] " + blue + "Exit" + reset + "                                ║");
                    System.out.println("╚══════════════════════════════════════════╝");


            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
//                    portManagerMenu();
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




    public static void portManagerMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String red = "\u001B[31m";
        String green = "\u001B[32m";
        String yellow = "\u001B[33m";
        String blue = "\u001B[34m";
        String purple = "\u001B[35m";
        String cyan = "\u001B[36m";
        String white = "\u001B[37m";
        String reset = "\u001B[0m";

//        // Load available ports from port.txt and display them here
//        List<Port> availablePorts = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
//        portController.displayAllPorts();
//        System.out.print("Choose a port by order number: ");
//        int selectedPortOrderNumber;
//        while (true) {
//            try {
//                System.out.print("Choose a port by order number: ");
//                selectedPortOrderNumber = scanner.nextInt();
//                if (selectedPortOrderNumber >= 1 && selectedPortOrderNumber <= availablePorts.size()) {
//                    break; // Exit the loop if a valid port is selected
//                } else {
//                    System.out.println("Invalid choice. Please select a number between 1 and " + availablePorts.size() + ".");
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("Please enter a valid number.");
//                scanner.nextLine(); // Clear the invalid input
//            }
//        }
//
//// Get the selected port object
//        Port selectedPort = availablePorts.get(selectedPortOrderNumber - 1);


        while (true) {

            System.out.println(yellow+"╔══════════════════════════════════════════╗");
            System.out.println("║           PORT MANAGER MENU              ║");
            System.out.println("╟──────────────────────────────────────────╢" + reset);
            System.out.println("║  [1] Container Controller                ║");
            System.out.println("║  [2] Vehicle Controller                  ║");
            System.out.println("║  [3] Port Operations                     ║");
            System.out.println("║  [4] Vehicle Movement                    ║");
            System.out.println("║  [5] Update Port                         ║");
            System.out.println("║  [7] Change Port                         ║");
            System.out.println("║  [8] Port Vehicles                       ║");
            System.out.println("║  [0] LOGOUT                              ║");
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
                case 0:
                    mainLoopUser();
                    break;
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
            System.out.println("|   [1] Load Container to Vehicle             |");
            System.out.println("|   [2] UnLoad Container to Vehicle           |");
            System.out.println("|   [3] Refuel Vehicle                        |");
            System.out.println("|   [0] Back to Main Menu                     |");
            System.out.println("|                                             |");
            System.out.println("==============================================");
            System.out.print("   Choose an option: ");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                loadContainerMenu(selectedPort);
                break;
            case 2:
                unloadContainerMenu(selectedPort);
                break;
            case 3:
                refuelVehicle(selectedPort.getID());
            case 0:
                return;
            default:
                System.out.println("Invalid choice. Please select a valid option.");


        }
    }
}
    public static void loadContainerMenu(Port selectPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║            CONTAINER MENU                ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] Load on Ship                        ║");
            System.out.println("║  [2] Load on Basic Truck                 ║");
            System.out.println("║  [3] Load on Tanker Truck                ║");
            System.out.println("║  [4] Load on Refregerator Truck          ║");
            System.out.println("║  [5] Return to Port Manager Menu         ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("   Choose an option: ");
            int choiceTruck = scanner.nextInt();

            switch (choiceTruck) {
                case 1:
                    loadContainerShipMenu(selectPort);
                    break;
                case 2:
                    loadContainerBasicTruckMenu(selectPort);
                    break;
                case 3:
                    loadContainerTankerTruckMenu(selectPort);
                    break;
                case 4:
                    loadContainerReeferTruckMenu(selectPort);
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static void unloadContainerMenu(Port selectPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║            CONTAINER MENU                ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] UnLoad on Ship                        ║");
            System.out.println("║  [2] UnLoad on Basic Truck                 ║");
            System.out.println("║  [3] UnLoad on Tanker Truck                ║");
            System.out.println("║  [4] UnLoad on Refregerator Truck          ║");
            System.out.println("║  [5] Return to Port Manager Menu         ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝");
            System.out.print("   Choose an option: ");
            int choiceTruck = scanner.nextInt();

            switch (choiceTruck) {
                case 1:
                    unloadContainerShipMenu(selectPort);
                    break;
                case 2:
                    unloadContainerBasicTruckMenu(selectPort);
                    break;
                case 3:
                    unloadContainerTankerTruckMenu(selectPort);
                    break;
                case 4:
                    unloadContainerReeferTruckMenu(selectPort);
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static void containerMenu(Port selectPort) throws IOException {
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
                    moveToMenu(selectedPort);
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
            System.out.println("|  [1] Calculate Distance between Port       |");
            System.out.println("║  [2] Fuel Use in specific Day               ║"); //operation 1
            System.out.println("║  [3] Total Weight of Container Type         ║"); //operation 2
            System.out.println("║  [4] List all ship in port                  ║");
            System.out.println("║  [5] List all trip in specific Day          ║");
            System.out.println("║  [6] List all trip from Day A to day B      ║");
            System.out.println("║  [7] Calculate total weight of Container    ║");
            System.out.println("|  [0] Back to Main Menu                     |");
            System.out.println("|                                             |");
            System.out.println("==============================================");
            System.out.print("   Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    //distance
                    break;
                case 2:
                    //fuel in day
                    calculateFuelInDay(selectedPort.getID());
                    break;
                case 3:
                    //total weight of type
                    getTotalTypeContainerWeight(selectedPort.getID());
                    break;
                case 4:
                    listAllShipInPort(selectedPort.getID());
                    break;
                case 5:
                    listTripsInDay(selectedPort.getID());

                    break;
                case 6:
                    listTripsBetweenDates(selectedPort.getID());
                    break;
                case 7:
                    calculateTotalWeightContainerPort(selectedPort);
                    System.out.println("Press any key to return...");
                    scanner.next();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");


            }
        }
    }
}
