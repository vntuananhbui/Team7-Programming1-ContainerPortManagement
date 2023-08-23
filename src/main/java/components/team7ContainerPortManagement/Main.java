package src.main.java.components.team7ContainerPortManagement;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.views.Containers.InputContainer;
import src.main.java.components.team7ContainerPortManagement.views.InputManager;
import src.main.java.components.team7ContainerPortManagement.views.Ports.portControl;
import src.main.java.components.team7ContainerPortManagement.views.Vehicle.ReeferTruckControl;
import src.main.java.components.team7ContainerPortManagement.views.Vehicle.TankerTruckControl;
import src.main.java.components.team7ContainerPortManagement.views.Vehicle.basicTruckControl;
import src.main.java.components.team7ContainerPortManagement.views.Vehicle.shipControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getContainerIDInPort;
import static src.main.java.components.team7ContainerPortManagement.views.Containers.InputContainer.*;
import static src.main.java.components.team7ContainerPortManagement.views.InputManager.readPortsFromFile;
import static src.main.java.components.team7ContainerPortManagement.views.Vehicle.shipControl.*;


public class Main {

    public static void main(String[] args) throws IOException {
////        Port startPort = new Port("p-start", "Start Port", 52.5200, 13.4050, 10000, true);
////        Port port2 = new Port("2", "Port 2", 48.8566, 32.3522, 800000, true);
//        Port port4 = new Port("4", "Port 4", 89.8566, 22.3522, 800, true);
//        Port port5 = new Port("5", "Port 5", 148.8566, 72.3522, 800000000, false);
//        port4.calculateDistanceTo(port5);
////
////
////        // Create vehicles
////        Truck basicTruck1 = new BasicTruck("1", "Basic 1", 50000, 5000, 100000,3.5, startPort);
////        Truck tankerTruck1 = new TankerTruck("2", "Tanker 1", 50000, 5000, 100000,3.5, startPort);
////        Truck reeferTruck1 = new ReeferTrucks("3", "Reefer 1", 50000, 5000, 100000,3.5, startPort);
////        Truck reeferTruck2 = new ReeferTrucks("4", "Reefer 2", 5000000, 5000, 100000000,3.5, startPort);
////        Ship ship1 = new Ship("1","Ship 1",500000,2000,2000000,3.5,startPort);
////
////        // create container
////        Container RefContainer = new Container("Ref-1",2000, ContainerType.REFRIGERATED);
////        Container openTopContainer = new Container("OP-1", 1800.0,ContainerType.OPEN_TOP);
////        Container liquidContainer = new Container("LQ-1", 1500,ContainerType.LIQUID);
////
////        //Add container to vehicle
////        ship1.loadContainer(liquidContainer);
////        reeferTruck1.loadContainer(RefContainer);
////        basicTruck1.loadContainer(openTopContainer);
////        ship1.moveTo(port2);
////        reeferTruck1.moveTo(port2);
////        basicTruck1.moveTo(port2);
////        ship1.unloadContainer(liquidContainer);
////        reeferTruck1.unloadContainer(RefContainer);
////        basicTruck1.unloadContainer(openTopContainer);
////        System.out.println(port2.getContainers());
////        System.out.println(port2.getTrips());
////
////        System.out.println(port2.calculateTotalContainerWeight());
//
        mainLoop();
    }

    public static void mainLoop() throws IOException {
        List<Port> availablePorts = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
//        Map<String, List<String>> portVehicles = readPortVehiclesFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Container Port Management System!");

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Admin");
            System.out.println("2. Port Manager");
            System.out.println("0. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminMenu();
                    break;
                case 2:
                    portManagerMenu();
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
            System.out.println("\nAdmin Menu:");
            System.out.println("1. Create Port");
            System.out.println("2. Remove Port");
            System.out.println("3. Port List");
            System.out.println("4. Port Manager Functions");
            System.out.println("0. Back to Main Menu");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    InputManager.inputPort();
                    break;
                case 2:
                    // Call the function to remove a port
//                    InputManager.removePort();
                    break;
                case 3:
                    List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
                    System.out.println("Current Ports:");
                    portControl.displayAllPorts();
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
        List<Port> availablePorts = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/port.txt");
        portControl.displayAllPorts();
        System.out.print("Choose a port by order number: ");
        int selectedPortOrderNumber = scanner.nextInt();
// Get the selected port object
        Port selectedPort = availablePorts.get(selectedPortOrderNumber - 1);


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
                    InputContainer.createContainer(selectedPort);
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
                    portManagerMenu();
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
                    basicTruckControl.createBasicTruck(selectedPort);
                    break;
                case 2:
                    TankerTruckControl.createTankerTruck(selectedPort);
                    break;
                case 3:
                    ReeferTruckControl.createReeferTruck(selectedPort);
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
                    shipControl.createShip(selectedPort);
                    break;
                case 3:
                    loadContainerMenu(selectedPort);
                    break;
                case 4:
                    unloadContainerMenu(selectedPort);
                    break;
                case 5:
                    return;
            }

        }
    }

    public static void loadContainerMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableShipIDs = selectedPort.getShipsInPort();

        System.out.println("Available ships in port " + selectedPort.getName() + ":");
        for (int i = 0; i < availableShipIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableShipIDs.get(i));
        }

        System.out.print("Choose a ship by order number: ");
        int selectedShipOrderNumber = scanner.nextInt();
        String selectedShipNumber = availableShipIDs.get(selectedShipOrderNumber - 1);
        String shipLine = getShipLineByShipNumber(selectedShipNumber, "src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt");
//        System.out.println("Ship line: " + shipLine);

//        System.out.println(getShipByLine(shipLine));
        Ship selectedShip = getShipByLine(shipLine);
//        System.out.println("Ship :" + selectedShip);
        //container select
        List<String> availableContainerIDs = getContainerIDInPort(selectedPort);
        System.out.println("Available container in port: " + selectedPort.getID() + ":");
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
//            System.out.println(container.isLoaded());
//            if (container.isLoaded() == false) {
//                System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + "| Is Loaded: " + container.isLoaded());
//                System.out.println("Choose a container by order number: ");
//            } else {
//                System.out.println("No container available!");
//                return;
//            }
            String status;
            if (!container.isLoaded()) {
                status = "Available";
            } else {
                status = "Unavailable!";
            }
            System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + "| Is Loaded: " + status);
        }
        int selectedContainerOrderNumber = scanner.nextInt();
        String selectedContainerNumber = availableContainerIDs.get(selectedContainerOrderNumber - 1);
//        System.out.println("Container Number: " + selectedContainerNumber);
        System.out.println();
        String containerLine = getContainerLineByContainerNumber(selectedContainerNumber, "src/main/java/components/team7ContainerPortManagement/models/utils/container.txt");
//        System.out.println("Container Line" + containerLine);

//        System.out.println("Get container: " + getContainerByLine(containerLine));

        Container selectedContainer = getContainerByLine(containerLine);

        //Load function
        // Try to load the container onto the vehicle

        if (selectedShip.loadContainer(selectedContainer)) {
            System.out.println("Successfully loaded container " + selectedContainer.getID() + " onto vehicle " + selectedShip.getID());

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(true);
            selectedContainer.setPort(selectedShip.getCurrentPort());
            selectedContainer.updateStatusContainer(true);

            // Update the vehicleContainerMap
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/vehicle_containerload.txt");
            vehicleContainerMap.computeIfAbsent(selectedShip.getID(), k -> new ArrayList<>()).add(selectedContainer.getID());
            List<Ship> Ship = readShipFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/container.txt");
            // Write the updated data back to the files
            writeShipToFile(Ship, "src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/models/utils/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/models/utils/vehicle_containerload.txt");
        } else {
            System.out.println("Failed to load container.");
        }


        // Define other methods (e.g., readPortsFromFile, displayAvailablePorts) here
    }
    public static void unloadContainerMenu(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);
        List<String> availableShipIDs = selectedPort.getShipsInPort();

        System.out.println("Available ships in port " + selectedPort.getName() + ":");
        for (int i = 0; i < availableShipIDs.size(); i++) {
            System.out.println((i + 1) + ": " + availableShipIDs.get(i));
        }

        System.out.print("Choose a ship by order number: ");
        int selectedShipOrderNumber = scanner.nextInt();
        String selectedShipNumber = availableShipIDs.get(selectedShipOrderNumber - 1);
        String shipLine = getShipLineByShipNumber(selectedShipNumber, "src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt");
        System.out.println("Ship line: " + shipLine);

//        System.out.println(getShipByLine(shipLine));
        Ship selectedShip = getShipByLine(shipLine);
        System.out.println("Ship :" + selectedShip);

        //container select

        List<String> availableContainerIDs = getContainerIDInPort(selectedPort);
        System.out.println("Available container in port: " + selectedPort.getID() + ":");
        for (int i = 0; i < availableContainerIDs.size(); i++) {
            String containerID = availableContainerIDs.get(i);
            Container container = getContainerByID(containerID);
//            System.out.println(container.isLoaded());
            String status;
            if (container.isLoaded()) {
                status = "Available";
            } else {
                status = "Unavailable!";
            }
            System.out.println((i + 1) + ": " + availableContainerIDs.get(i) + "| Is Loaded: " + status);
        }
        System.out.println("Choose a container by order number: ");
        int selectedContainerOrderNumber = scanner.nextInt();
        String selectedContainerNumber = availableContainerIDs.get(selectedContainerOrderNumber - 1);
        System.out.println();
        String containerLine = getContainerLineByContainerNumber(selectedContainerNumber, "src/main/java/components/team7ContainerPortManagement/models/utils/container.txt");

        Container selectedContainer = getContainerByLine(containerLine);

        //Unload function
        if (selectedShip.unloadContainer(selectedContainer)) {
            System.out.println("Successfully unloaded container " + selectedContainer.getID() + " onto vehicle " + selectedShip.getID());

            // Update the container's isLoaded status and port
            selectedContainer.setLoaded(false);
            selectedContainer.updateStatusContainer(false);

            // Update the vehicleContainerMap
            Map<String, List<String>> vehicleContainerMap = readVehicleContainerMapFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/vehicle_containerload.txt");
            vehicleContainerMap.get(selectedShip.getID()).remove(selectedContainer.getID());
            List<Ship> Ship = readShipFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt");
            List<Container> containers = readContainersFromFile("src/main/java/components/team7ContainerPortManagement/models/utils/container.txt");
            // Write the updated data back to the files
            writeShipToFile(Ship, "src/main/java/components/team7ContainerPortManagement/models/utils/vehicle.txt");
            writeContainersToFile(containers, "src/main/java/components/team7ContainerPortManagement/models/utils/container.txt");
            writeVehicleContainerMapToFile(vehicleContainerMap, "src/main/java/components/team7ContainerPortManagement/models/utils/vehicle_containerload.txt");
        } else {
            System.out.println("Failed to unload container.");
        }
    }

}


//=============

//import java.util.Scanner;
//
//public class Main {
//    private static Scanner scanner = new Scanner(System.in);
//    private static boolean isLoggedIn = false;
//    private static String currentUser = "";
//
//    public static void main(String[] args) throws IOException {
//
//        System.out.println("\nMain Menu:");
//        System.out.println("1. Register");
//        System.out.println("2. Login");
//        // ... Other options...
//
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1:
//                System.out.print("Enter username: ");
//                String username = scanner.nextLine();
//                System.out.print("Enter password: ");
//                String password = scanner.nextLine();
//                AccountManager.register(username, password);
//                System.out.println("Registered successfully!");
//                break;
//            case 2:
//                System.out.print("Enter username: ");
//                username = scanner.nextLine();
//                System.out.print("Enter password: ");
//                password = scanner.nextLine();
//                Account account = AccountManager.login(username, password);
//                if (account != null) {
//                    System.out.println("Logged in successfully!");
//                    // Move to the appropriate menu based on the username
//                    if (username.equals("admin")) {
//                        adminMenu();
//                    } else {
//                        portManagerMenu(account);  // pass the account object to handle actions specific to this manager
//                    }
//                } else {
//                    System.out.println("Invalid credentials!");
//                }
//                break;
//            // ... Handle other cases...
//        }
//    }
//
//    public static void adminMenu() throws IOException {
//        System.out.println("\nAdmin Menu:");
//        System.out.println("1. Create Port");
//        System.out.println("2. Remove Port");
//        System.out.println("3. Port Manager Functions");
//        System.out.println("4. Logout");
//        System.out.print("Choose an option: ");
//
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1:
//                // Call function to create a port.
//                InputManager.inputPort();
//                break;
//            case 2:
//                // Call function to remove a port.
//                break;
//            case 3:
//                System.out.println("Enter Port Manager's username: ");
//                String username = scanner.nextLine();
//                Account account = getAccountByUsername(username);  // This function retrieves the account by username.
//                if (account != null && account.getType().equals("PortManager")) {
//                    portManagerMenu(account);
//                } else {
//                    System.out.println("Invalid Port Manager username.");
//                }
//            case 4:
//                logout();
//                break;
//            default:
//                System.out.println("Invalid choice. Please select a valid option.");
//        }
//    }
//
//    public static void portManagerMenu(Account account) throws IOException {
//        Scanner scanner = new Scanner(System.in);
//        Port assignedPort = null;  // Assuming you have a method to get the port based on its ID
//
//        if (!account.getAssignedPort().isEmpty()) {
//            System.out.println("Your account do not have port yet!");  // You would need a method to retrieve a port by its ID
//        }
//        System.out.println("\nPort Manager Menu:");
//        System.out.println("1. Create Object");
//        System.out.println("2. Actions");
//        System.out.println("3. Logout");
//        System.out.print("Choose an option: ");
//
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1:
//                createObjectMenu();
//                break;
//            case 2:
//                actionMenu();
//                break;
//            case 3:
//                logout();
//                break;
//            default:
//                System.out.println("Invalid choice. Please select a valid option.");
//        }
//    }
//
//    public static void createObjectMenu() {
//        System.out.println("\nCreate Object Menu:");
//        System.out.println("1. Create Container");
//        System.out.println("2. Create Ship");
//        // Add other vehicle types here...
//        System.out.println("0. Back to Port Manager Menu");
//        System.out.print("Choose an object to create: ");
//
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1:
//                // Call function to create a container.
//
//                break;
//            case 2:
//                // Call function to create a ship.
////                InputManager.createShip();
//                break;
//            // Handle other vehicle types here...
//            case 0:
//                return;
//            default:
//                System.out.println("Invalid choice. Please select a valid option.");
//        }
//    }
//
//    public static void actionMenu() {
//        System.out.println("\nActions Menu:");
//        System.out.println("1. Calculate Total Container Weight");
//        System.out.println("0. Back to Port Manager Menu");
//        System.out.print("Choose an action: ");
//
//        int choice = scanner.nextInt();
//        scanner.nextLine();
//
//        switch (choice) {
//            case 1:
//                // Call function to calculate total container weight.
//                break;
//            case 0:
//                return;
//            default:
//                System.out.println("Invalid choice. Please select a valid option.");
//        }
//    }
//
//    public static void logout() {
//        isLoggedIn = false;
//        currentUser = "";
//    }
//    public static Account getAccountByUsername(String username) {
//        File accountFile = new File("account.txt");
//        try (Scanner scanner = new Scanner(accountFile)) {
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String[] parts = line.split(",");
//                if (parts[0].trim().equals(username)) {
//                    String password = parts[1].trim();
//                    String type = parts[2].trim();
//                    return new Account(username, password, type);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public static void setPortManagerToPort() throws IOException {
//        Scanner scanner = new Scanner(System.in);
//
//        // 1. Display all ports.
//        List<Port> ports =InputManager.readPortsFromFile("port.txt"); // Assuming you have a function readPortsFromFile
//        for (int i = 0; i < ports.size(); i++) {
//            System.out.println((i+1) + ". " + ports.get(i).getName());
//        }
//
//        // 2. User chooses a port.
//        System.out.print("Select a port by its number: ");
//        int portChoice = scanner.nextInt();
//        Port selectedPort = ports.get(portChoice - 1);
//
//        // 3. Display all port managers without an assigned port.
//        List<Account> availablePortManagers = getAllAvailablePortManagers(); // This function retrieves port managers without ports.
//        for (int i = 0; i < availablePortManagers.size(); i++) {
//            System.out.println((i+1) + ". " + availablePortManagers.get(i).getUsername());
//        }
//
//        // 4. User chooses a port manager.
//        System.out.print("Select a port manager by their number: ");
//        int managerChoice = scanner.nextInt();
//        Account selectedManager = availablePortManagers.get(managerChoice - 1);
//
//        // 5. Assign the port to the chosen port manager.
//        selectedManager.setAssignedPort(selectedPort.getID());
//        saveAccountChanges(selectedManager); // This function saves the updated account information.
//    }
//
//    private static List<Account> getAllAvailablePortManagers() {
//        List<Account> availablePortManagers = new ArrayList<>();
//        File accountFile = new File("account.txt");
//
//        try (Scanner scanner = new Scanner(accountFile)) {
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String[] parts = line.split(",");
//                String username = parts[0].trim();
//                String password = parts[1].trim();
//                String type = parts[2].trim();
//                String assignedPort = parts.length > 3 ? parts[3].trim() : null;
//
//                if ("PortManager".equalsIgnoreCase(type) && assignedPort == null) {
//                    availablePortManagers.add(new Account(username, password, type));
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return availablePortManagers;
//    }
//
//
//    private static void saveAccountChanges(Account account) {
//        File accountFile = new File("account.txt");
//        List<String> updatedLines = new ArrayList<>();
//
//        try (Scanner scanner = new Scanner(accountFile)) {
//            while (scanner.hasNextLine()) {
//                String line = scanner.nextLine();
//                String[] parts = line.split(",");
//                String username = parts[0].trim();
//
//                if (username.equals(account.getUsername())) {
//                    // Update the line for the changed account
//                    line = account.getUsername() + "," + account.getPassword() + "," + account.getType() + "," + account.getAssignedPort();
//                }
//                updatedLines.add(line);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        // Write the updated lines back to the file
//        try (PrintWriter writer = new PrintWriter(accountFile)) {
//            for (String line : updatedLines) {
//                writer.println(line);
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//
//}



