package src.main.java.components.team7ContainerPortManagement.view;

import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController;
import src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.IOException;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.loadContainerBasicTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.unloadContainerBasicTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.loadContainerReeferTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.unloadContainerReeferTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.loadContainerShipMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.unloadContainerShipMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.loadContainerTankerTruckMenu;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.unloadContainerTankerTruckMenu;

public class PortManagerMenu {
    public static void TruckMenuManage(Port selectedPort) throws IOException {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("╔══════════════════════════════════════════╗");
            System.out.println("║            CONTAINER MENU                ║");
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
}
