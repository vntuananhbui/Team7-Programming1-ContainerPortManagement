package src.main.java.components.team7ContainerPortManagement.view;

import src.main.java.components.team7ContainerPortManagement.Controller.UserController.AdminController;
import src.main.java.components.team7ContainerPortManagement.Controller.UserController.PortManagerController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.containerController;
import src.main.java.components.team7ContainerPortManagement.Controller.portController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.vehicleOperation.loadContainerShipMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.vehicleOperation.unloadContainerShipMenu;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortsFromFile;

public class mainMenu {
//    public static void mainLoop() throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Welcome to the Container Port Management System!");
//
//        while (true) {
//            System.out.println("\nMain Menu:");
//            System.out.println("1. Admin");
//            System.out.println("2. Port Manager");
//            System.out.println("0. Exit");
//            System.out.print("Choose an option: ");
//            int choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1:
//                    adminMenu();
//                    break;
//                case 2:
//                    portManagerMenu();
//                    break;
//                case 0:
//                    System.out.println("Exiting the program. Goodbye!");
//                    scanner.close();
//                    return;
//                default:
//                    System.out.println("Invalid choice. Please select a valid option.");
//            }
//        }
//    }
//    public static void adminMenu() throws IOException {
//        Scanner scanner = new Scanner(System.in);
//
//        while (true) {
//            System.out.println("\nAdmin Menu:");
//            System.out.println("1. Create Port");
//            System.out.println("2. Remove Port");
//            System.out.println("3. Port List");
//            System.out.println("4. Port Manager Functions");
//            System.out.println("0. Back to Main Menu");
//            System.out.print("Choose an option: ");
//            int choice = scanner.nextInt();
//
//            switch (choice) {
//                case 1:
//                    portController.inputPort();
//                    break;
//                case 2:
//                    // Call the function to remove a port
////                    InputManager.removePort();
//                    break;
//                case 3:
//                    List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
//                    System.out.println("Current Ports:");
//                    portController.displayAllPorts();
//                    break;
//                case 4:
//                    portManagerMenu();
//                    break;
//                case 0:
//                    return;
//                default:
//                    System.out.println("Invalid choice. Please select a valid option.");
//            }
//        }
//    }
    public static void portManagerMenu(String username) throws IOException {
        Scanner scanner = new Scanner(System.in);

//         Load available ports from port.txt and display them here
//        List<Port> availablePorts = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
//        portController.displayAllPorts();
//        System.out.print("Choose a port by order number: ");
//        int selectedPortOrderNumber = scanner.nextInt();

        Port selectedPort = PortManagerController.getAssociatedPort(username);


        // Get the selected port object
//        Port selectedPort = availablePorts.get(selectedPortOrderNumber - 1);
//        Port selectedPort = port;


        while (true) {
            System.out.println("\nPort Manager Menu:");
            System.out.println("1. Create Container");
            System.out.println("2. Ship Controller");
            System.out.println("3. Truck Controller");
            System.out.println("4. Calculate Total Container Weight");
            System.out.println("5. Change port");
            System.out.println("6. Port Vehicles");
            System.out.println("7. Load container");
            System.out.println("8. Test");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Call the function to create a container
                    containerController.createContainer(selectedPort);
                    break;
                case 2:
                    // Call the function to create a ship
                    ShipMenu(selectedPort);
                    break;
                case 3:

                    TruckMenuManage(selectedPort);
                case 4:
                    // Call the method to calculate total container weight for the selected port
                    // selectedPort.calculateTotalContainerWeight();
                    break;
                case 5:
                    portManagerMenu(username);
                    break;
                case 6:
//                    System.out.println(selectedPort.getID());
                    break;

                case 7:
                    return;
                case 8:

                    System.out.println(selectedPort.getName());
                    System.out.println(selectedPort.getLatitude());
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
    public static void TruckMenuManage(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nCreate Truck Menu:");
            System.out.println("1. Create Basic Truck");
            System.out.println("2. Create Tanker Truck");
            System.out.println("3. Create Reefer Truck");
            System.out.println("4. Return to Port Manager Menu");
            System.out.print("Choose an option: ");
            int choiceTruck = scanner.nextInt();

            switch (choiceTruck) {
                case 1:
                    // Call the function to create a basic truck
                    basictruckController.createBasicTruck(selectedPort);
                    break;
                case 2:
                    tankertruckController.createTankerTruck(selectedPort);
                    break;
                case 3:
                    reefertruckController.createReeferTruck(selectedPort);
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
            System.out.println("1. Create Ship");
            System.out.println("2. Remove Ship");
            System.out.println("3. Load Container on Ship");
            System.out.println("4. Unload Container on Ship");
            System.out.println("5. Return");
            System.out.print("Choose an option: ");
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
            System.out.println("1. Create Basic Truck");
            System.out.println("2. Remove Basic Truck");
            System.out.println("3. Load Container on Basic Truck");
            System.out.println("4. Unload Container on Basic Truck");
            System.out.println("5. Return");
            System.out.print("Choose an option: ");
            int choiceShip = scanner.nextInt();
            switch (choiceShip) {
                case 1:
                    basictruckController.createBasicTruck(selectedPort);
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
}
