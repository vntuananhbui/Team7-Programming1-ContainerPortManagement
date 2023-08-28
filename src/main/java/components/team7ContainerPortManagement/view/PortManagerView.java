package src.main.java.components.team7ContainerPortManagement.view;

import src.main.java.components.team7ContainerPortManagement.Controller.UserController.PortManagerController;
import src.main.java.components.team7ContainerPortManagement.Controller.containerController;
import src.main.java.components.team7ContainerPortManagement.Controller.portController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortsFromFile;

public class PortManagerView {
    public static void main(String[] args) {
        PortManagerController portManager = new PortManagerController();
        Port port = new Port("4", "Port 4", 60.5500, 20.4050, 1000, true);
        Container Container = new Container("23", 800, ContainerType.REFRIGERATED, port);
//        Vehicle vehicle = new TankerTruck("9", "Tanker truck 9", 888, 888, 1200,3.5, port);


//        portManager.addContainer(Container);


        // Creating Scanner Object to read input
        Scanner input = new Scanner(System.in);

        // Creating option integer variable
        int option = 0;

        // Do - While loop
        do {
//            menu();
            option = input.nextInt();
            input.nextLine();
        }
        while (true);
    }
}

            // Switch case
//            switch (option) {
//                case 1:
//                    // Display message
//                    System.out.println("What is the Container id Number ?");
//                    String containerID = input.nextLine();
//
//                    // Display message
//                    System.out.println("What is the weight ?");
//                    double weight = input.nextDouble();
//                    input.nextLine();  // Consume newline left by nextDouble()
//
//                    // Display available ContainerType options
//                    int i = 1;
//                    for (ContainerType type : ContainerType.values()) {
//                        System.out.println(i + ". " + type.getLabel());
//                        i++;
//                    }
//
//                    // FIX THIS FUNCTION 6 (ADD CONTAINER)
//                    // Ask user for their choice
//
//                    System.out.println("Enter the number corresponding to the desired Container type: ");
//                    int choice = Integer.parseInt(input.nextLine());
////                    input.nextLine();  // Consume newline left by nextInt()
//
//                    // Validate user's choice
//                    if (choice > 0 && choice <= ContainerType.values().length) {
//                        ContainerType selectedType = ContainerType.values()[choice - 1];
//
//                        // Create Container object with selected type
//                        Container = new Container(containerID, weight, selectedType,port);
//
//                        // Assuming you have an Admin class and an addPort method
//                        // port = new Port();
//                        portManager.addContainer(Container);
//                        System.out.println("Add container successfully");
//                    } else {
//                        System.out.println("Invalid choice. Container not created.");
//                    }
//                    break;
//                case 2:
//                    // Display message
//                    System.out.print(
//                            "What is the Container id number ? ");
//                    String cId = input.nextLine();
//                    // Invoke remove/delete record
//                    portManager.deleteContainer(cId);
//                    break;
//                case 3:
//
//                    // Display message
//                    System.out.print(
//                            "What is the Container id number? ");
//                    String cIdNo = input.nextLine();
//                    portManager.updateContainer(cIdNo, input);
//
//                    break;
//                case 4:
//
//                    // Display message
//                    System.out.print(
//                            "What is the Container id ? ");
//                    String containerId = input.nextLine();
//
//                    if (!portManager.findContainerID(containerId)) {
//                        System.out.println(
//                                "Port id does not exist\n");
//                    }
//                    break;
//                case 5:
//                    portManager.displayContainer();
//                    break;
//                case 6:
//                    System.out.println("\nThank you for using the program. Goodbye!\n");
//                    System.exit(0);
//                default:
//                    System.out.println("Invalid input");
//                    break;
//            }
//        }
//        while (option != 7);
//    }
//            public static void menu () {
//
//
//                // Formatting the table-like structure for the menu
//                System.out.printf("%-25s%n",  "CONTAINER MENU");
//                System.out.printf("%-25s%n", "--------------------");
//                System.out.printf("%-25s%n",  "1: Add Container");
//                System.out.printf("%-25s%n",  "2: Delete Container");
//                System.out.printf("%-25s%n",  "3: Update Container");
//                System.out.printf("%-25s%n",  "4: Search Container");
//                System.out.printf("%-25s%n",  "5: Display Container");
//                System.out.printf("%-25s%n",  "6: Exit program");
//                System.out.println("---------------------");
//                System.out.print("Enter your selection : ");
//
//            }

