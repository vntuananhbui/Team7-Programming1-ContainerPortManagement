package src.main.java.components.team7ContainerPortManagement.views;

import src.main.java.components.team7ContainerPortManagement.controllers.AdminController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.util.Scanner;

public class AdminView {
    public static void main(String[] args) {
        AdminController admin = new AdminController();
        Port port = new Port("1", "Port 1", 52.5200, 13.4050, 1000, true);
        Container Container = new Container("12",200, ContainerType.REFRIGERATED);
        Vehicle vehicle = new TankerTruck("5", "Tanker truck 1", 500, 500, 1000,3.5, port);



        admin.addPort(port);
        admin.addContainer(Container);
        admin.addVehicle(vehicle);


        // Creating Scanner Object to read input
        Scanner input = new Scanner(System.in);

        // Creating option integer variable
        int option = 0;

        // Do - While loop
        do {
            menu();
            option = input.nextInt();
            input.nextLine();

            // Switch case
            switch (option) {

                // Case 1
                case 1:

                    // Display message
                    System.out.println("What is the Port id Number ? ");

                    String idNumber = input.nextLine();

                    // Display message
                    System.out.println("What is the Port name ? ");

                    String portName = input.nextLine();

                    // Display message
                    System.out.println(
                            "What is the Port latitude ? ");

                    double latitude = Double.parseDouble(input.nextLine());

                    System.out.println(
                            "What is the Port longitude ? ");

                    double longitude = Double.parseDouble(input.nextLine());
                    System.out.println(
                            "What is the Port storing capacity ? ");

                    int storingCapacity = Integer.parseInt(input.nextLine());
                    System.out.println(
                            "Is port has landing ability(True/False) ? ");

                    Boolean landingAbility = Boolean.parseBoolean(input.nextLine());

                    // Create port object and pass constructor
                    // parameters.
                    port = new Port(idNumber, portName, latitude, longitude, storingCapacity, landingAbility);
                    // Call add() record
                    admin.addPort(port);
                    System.out.println(port);

                    // Break statement used to terminate program
                    // from here only once it entered this case
                    break;
                case 2:
                    // Display message
                    System.out.print(
                            "What is the Port id number ? ");
                    String pId = input.nextLine();

                    // Invoke remove/delete record
                    admin.deletePort(pId);
                    break;
                // Case 3
                case 3:

                    // Display message
                    System.out.print(
                            "What is the Port id number? ");

                    String pIdNo = input.nextLine();
                    admin.updatePort(pIdNo, input);

                    break;
                // Case 4
                case 4:

                    // Display message
                    System.out.print(
                            "What is the Port id ? ");
                    String portId = input.nextLine();

                    if (!admin.findPortID(portId)) {
                        System.out.println(
                                "Port id does not exist\n");
                    }

                    break;
                // Case 5
                case 5:
                    admin.displayPort();
                    break;

                case 6:
                    // Display message
                    System.out.println("What is the Container id Number ?");
                    String containerID = input.nextLine();

                    // Display message
                    System.out.println("What is the weight ?");
                    double weight = input.nextDouble();
                    input.nextLine();  // Consume newline left by nextDouble()

                    // Display available ContainerType options
                    int i = 1;
                    for (ContainerType type : ContainerType.values()) {
                        System.out.println(i + ". " + type.getLabel());
                        i++;
                    }

                    // FIX THIS FUNCTION 6 (ADD CONTAINER)
                    // Ask user for their choice

                    System.out.println("Enter the number corresponding to the desired Container type: ");
                    int choice = Integer.parseInt(input.nextLine());
//                    input.nextLine();  // Consume newline left by nextInt()

                    // Validate user's choice
                    if (choice > 0 && choice <= ContainerType.values().length) {
                        ContainerType selectedType = ContainerType.values()[choice - 1];

                        // Create Container object with selected type
                        Container = new Container(containerID, weight, selectedType);

                        // Assuming you have an Admin class and an addPort method
                        // port = new Port();
                         admin.addContainer(Container);
                        System.out.println("Add container successfully");
                    } else {
                        System.out.println("Invalid choice. Container not created.");
                    }
                    break;
                case 7:
                    // Display message
                    System.out.print(
                            "What is the Container id number ? ");
                    String cId = input.nextLine();
                    // Invoke remove/delete record
                    admin.deleteContainer(cId);
                    break;
                case 8:

                    // Display message
                    System.out.print(
                            "What is the Container id number? ");
                    String cIdNo = input.nextLine();
                    admin.updateContainer(cIdNo, input);

                    break;
                case 9:

                    // Display message
                    System.out.print(
                            "What is the Container id ? ");
                    String containerId = input.nextLine();

                    if (!admin.findContainerID(containerId)) {
                        System.out.println(
                                "Port id does not exist\n");
                    }
                    break;
                case 10:
                    admin.displayContainer();
                    break;
                case 11:
                    System.out.println("What type of vehicle do you want to create?");
                    System.out.println("1. Ship");
                    System.out.println("2. Truck");
                    System.out.println("Please choose numbers (1 or 2): ");
                    Scanner scanner = new Scanner(System.in);


                    int choiceNum = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    if (choiceNum == 1) {
                        String id, name, currentPortName;
                    double currentFuel, carryingCapacity, fuelCapacity, fuelConsumption = 3.5;
                    Port currentPort; // Assuming you have a Port class with a constructor that takes a name
//
                    System.out.print("Enter vehicle ID: ");
                    id = scanner.nextLine();

                    System.out.print("Enter vehicle name: ");
                    name = scanner.nextLine();
//
                    System.out.print("Enter current fuel: ");
                    currentFuel = scanner.nextDouble();
//
                    System.out.print("Enter carrying capacity: ");
                    carryingCapacity = scanner.nextDouble();
//
                    System.out.print("Enter fuel capacity: ");
                    fuelCapacity = scanner.nextDouble();
//
                    scanner.nextLine(); // Consume newline
                         vehicle = new Ship(id, name, currentFuel, carryingCapacity, fuelCapacity, fuelConsumption, port);

//
                    } else {
                        System.out.println("What type of truck do you want to create?");
                            System.out.println("1. Basic Truck");
                            System.out.println("2. Reefer Truck");
                            System.out.println("3. Tanker Truck");
                        System.out.print("Please choose numbers (1,2 or 3): ");
                            int truckChoice = scanner.nextInt();
                            scanner.nextLine();

                        System.out.print("Enter vehicle ID: ");
                        String id1 = scanner.nextLine();

                        System.out.print("Enter vehicle name: ");
                        String name1 = scanner.nextLine();
//
                        System.out.print("Enter current fuel: ");
                        double currentFuel1 = scanner.nextDouble();
//
                        System.out.print("Enter carrying capacity: ");
                        double carryingCapacity1 = scanner.nextDouble();
//
                        System.out.print("Enter fuel capacity: ");
                        double fuelCapacity1 = scanner.nextDouble();
//
                        scanner.nextLine(); // Consume newline
                        double fuelConsumption1 = 3.5;


                        switch (truckChoice) {
                            case 1:
                                    vehicle = new BasicTruck(id1, name1, currentFuel1, carryingCapacity1, fuelCapacity1, fuelConsumption1, port);
                                    break;
                                case 2:
                                    vehicle = new ReeferTrucks(id1, name1, currentFuel1, carryingCapacity1, fuelCapacity1, fuelConsumption1, port);
                                    break;
                                case 3:
                                    vehicle = new TankerTruck(id1, name1, currentFuel1, carryingCapacity1, fuelCapacity1, fuelConsumption1, port);
                                    break;
                                default:
                                    System.out.println("Invalid truck type choice.");
                                    break;

                        }

                    }


                    if (vehicle != null) {
                        admin.addVehicle(vehicle);
                        System.out.println( "New vehicle has been added to the vehicle list.");
                    } else {
                        System.out.println("No vehicle was added due to an error or invalid choice.");
                    }

                    break;
                case 12:
                    // Display message
                    System.out.print(
                            "What is the Vehicle id number ? ");
                    String vId = input.nextLine();
                    // Invoke remove/delete record
                    admin.deleteVehicle(vId);
                    break;
                case 13:
                    // Display message
                    System.out.print(
                            "What is the Vehicle id number? ");
                    String vIdNo = input.nextLine();
                    admin.updateVehicle(vIdNo, input);

                    break;
                case 14:
                    // Display message
                    System.out.print(
                            "What is the Vehicle id ? ");
                    String vehicleId = input.nextLine();

                    if (!admin.findVehicleID(vehicleId)) {
                        System.out.println(
                                "Vehicle id does not exist\n");
                    }
                    break;

                case 15:
                    admin.displayVehicle();
                    break;

                case 20:
                    System.out.println("\nThank you for using the program. Goodbye!\n");
                    System.exit(0);

                default:
                    System.out.println("Invalid input");
                    break;
            }
        }
        while (option != 20);
    }

            // Method 2
            // Menu - Static menu for displaying options
            public static void menu() {



                // Formatting the table-like structure for the menu
                System.out.printf("%-25s | %-25s | %-25s%n", "PORT MENU", "CONTAINER MENU", "VEHICLE MENU");
                System.out.printf("%-25s | %-25s | %-25s%n","--------------------","---------------------","--------------------");
                System.out.printf("%-25s | %-25s | %-25s%n", "1: Add Port", "6: Add Container", "11: Add Vehicle");
                System.out.printf("%-25s | %-25s | %-25s%n", "2: Delete Port", "7: Delete Container", "12: Delete Vehicle");
                System.out.printf("%-25s | %-25s | %-25s%n", "3: Update Port", "8: Update Container", "13: Update Vehicle");
                System.out.printf("%-25s | %-25s | %-25s%n", "4: Search Port", "9: Search Container", "14: Search Vehicle");
                System.out.printf("%-25s | %-25s | %-25s%n", "5: Display Port", "10: Display Container", "15: Display Vehicle");
                System.out.printf("%-75s%n", "20: Exit program");
                System.out.println("-------------------------------------------------------------");
                System.out.print("Enter your selection : ");

            }
        }
