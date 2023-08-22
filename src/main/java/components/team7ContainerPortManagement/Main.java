package src.main.java.components.team7ContainerPortManagement;

import src.main.java.components.team7ContainerPortManagement.controllers.AdminController;
import src.main.java.components.team7ContainerPortManagement.controllers.PortManagerController;
import src.main.java.components.team7ContainerPortManagement.controllers.User;
import src.main.java.components.team7ContainerPortManagement.models.entities.*;
import src.main.java.components.team7ContainerPortManagement.views.AdminView;
import src.main.java.components.team7ContainerPortManagement.views.PortManagerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //Create Port
//        Port port1 = new Port("1","Port 1",18.25,15.28,2000,true);
//        Port port2 = new Port("2", "Port 2", 89.87,87.89,200,true);
//        //Calculate distance
////        System.out.println(port1.calculateDistanceTo(port2) );
//        //Create basic truck
//        Truck basicTruck1 = new BasicTruck("2", "Basic 1", 10, 1000, 100, port1);
////        System.out.println(basicTruck1.getID());
//        Truck tankerTruck1 = new TankerTruck("5", "Tanker truck 1", 500, 500, 1000, port1);
//        Truck referTruck1 = new ReeferTrucks("9", "Refer truck 1", 500, 500, 1000, port1);
//        //Create ship
//        Ship ship1 = new Ship("6","ship1",200,400,400,null);
//
//        //Create container
//        Container RefContainer = new Container("12",200, ContainerType.REFRIGERATED);
//        Container openTopContainer = new Container("99", 1800.0,ContainerType.OPEN_TOP);
//        Container liquidContainer = new Container("72", 900,ContainerType.LIQUID);
//        System.out.println(RefContainer.getContainerType());
//        System.out.println(openTopContainer.getContainerType());
        //Check Load container

//        System.out.println(tankerTruck1.canLoadContainer(RefContainer));
//        System.out.println(tankerTruck1.canLoadContainer(liquidContainer));
//        System.out.println(referTruck1.canLoadContainer(RefContainer));
//        System.out.println(referTruck1.canLoadContainer(liquidContainer));
//        System.out.println(ship1.canLoadContainer(liquidContainer));
//
//        //Load container
//
//        System.out.println(basicTruck1.loadContainer(openTopContainer));
//        System.out.println(basicTruck1.loadContainer(RefContainer));
//        System.out.println(tankerTruck1.loadContainer(openTopContainer));
//        System.out.println(tankerTruck1.loadContainer(liquidContainer));
//        referTruck1.loadContainer(RefContainer);
//        ship1.loadContainer(liquidContainer);
//        ship1.loadContainer(RefContainer);

        //check vehicle load ? container
//        tankerTruck1.showLoadedContainers();
//        ship1.showLoadedContainers();
//        ship1.unloadContainer(liquidContainer);
//        ship1.showLoadedContainers();

//        ship1.showLoadedContainers();
        // add container to port
//        port1.addContainer(liquidContainer);


//        System.out.println(port1.canAddContainer(liquidContainer));
//        port1.addContainer(liquidContainer);
//        port1.addContainer(RefContainer);
//        port1.removeContainer(RefContainer);
//        System.out.println(port1.calculateTotalContainerWeight());

//
//        Port startPort = new Port("p-start", "Start Port", 52.5200, 13.4050, 1000, true);
//        Port port2 = new Port("2", "Port 2", 48.8566, 32.3522, 800000, true);
//        Port port4= new Port("4", "Port 4", 89.8566, 22.3522, 800, true);
//        Port port5 = new Port("5", "Port 5", 148.8566, 72.3522, 800000000, false);
//
//
//        // Create vehicles
//        Truck basicTruck1 = new BasicTruck("1", "Basic 1", 50000, 500, 100000,3.5, startPort);
//        Truck tankerTruck1 = new TankerTruck("2", "Tanker 1", 50000, 500, 100000,3.5, startPort);
//        Truck reeferTruck1 = new ReeferTrucks("3", "Reefer 1", 50000, 500, 100000,3.5, startPort);
//        Truck reeferTruck2 = new ReeferTrucks("4", "Reefer 2", 5000000, 500, 100000000,3.5, startPort);
//        Ship ship1 = new Ship("1","Ship 1",500000,2000,2000000,3.5,startPort);
//
//        // create container
//        Container RefContainer = new Container("12",200, ContainerType.REFRIGERATED);
//        Container openTopContainer = new Container("99", 1800.0,ContainerType.OPEN_TOP);
//        Container liquidContainer = new Container("72", 900,ContainerType.LIQUID);
//        //Add container to vehicle
////        ship1.loadContainer(RefContainer);
//        ship1.loadContainer(RefContainer);
//        ship1.moveTo(port4);
//        ship1.unloadContainer(RefContainer);
//        System.out.println(port4.getTrips());
//        ship1.moveTo(port2);
//        System.out.println(port2.getTrips());


        AdminController admin = new AdminController();
        PortManagerController portManager1 = new PortManagerController();
        List<User> userList = new ArrayList<>();
        try {
            userList = User.readUserCredentialFromFile("src/main/java/resources/data/user.txt");
        } catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);

        while(true) {
            System.out.println("Welcome to the Authentication System!");
            System.out.println("Select an option:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Stop system");

            int option = scanner.nextInt();

            scanner.nextLine(); // Consume newline

            if (option == 1) {
                System.out.print("Enter a new username: ");
                String newUsername = scanner.nextLine();

                String newPassword = ""; // Declare outside the loop


                while (true) {
                    System.out.print("Enter a new password \n" +
                            "Password must contain at least one digit [0-9].\n" +
                            "Password must contain at least one lowercase character [a-z].\n" +
                            "Password must contain at least one uppercase character [A-Z].\n" +
                            "Password must contain at least one special character [!@#$%^&*].\n" +
                            "Password must contain a length of at least 8 characters and a maximum of 20 characters): ");

                    newPassword = scanner.nextLine();

                    if (User.isValidPassword(newPassword)) {
                        System.out.println("Password is valid!");
                        break;  // break the loop if password is valid
                    } else {
                        System.out.println("INVALID PASSWORD! PlEASE fOLLOW THE REQUIREMENTS.");
                        System.out.println();
                    }
                }

                System.out.println("\nEnter type of user (admin or port manager): ");
                String userType = scanner.nextLine();
                User.registerUser(userList, newUsername, newPassword,userType);

            } else if (option == 2) {
                System.out.print("Enter your username: ");
                String username = scanner.nextLine();
                System.out.print("Enter your password: ");
                String password = scanner.nextLine();


                if (admin.checkAdminCredentials(username,password)) {
                    System.out.println("Login successful admin!");
                    AdminView.main(new String[]{});
                } else if (portManager1.checkPortManagerCredentials(username,password)) {
                    System.out.println("Login successful port manager");
                    PortManagerView.main(new String[]{});
                } else {
                    System.out.println("Login failed");
                }

            } else {
                System.out.println("Stop system");
                break;
            }
        }




// Container weight > vehicle weight to load done
// Container phai cung port moi duoc add len container cung port done
        //Vehicle moveTo port (truck (both of port are true is required), ship (only 1 port is true is required)) done
        //moveTo require curent fuel > requireFuel

        //set container fuel consumtion, neu vehicle k co container se su dung, neu co container se su dung cua container!!!!!



//        ship1.moveTo(port4);
//        reeferTruck1.moveTo(port4);
//        basicTruck1.moveTo(port4);
//        port4.showAllVehicleInPort();
//        ship1.unloadContainer(liquidContainer);
//        port4.showAllContainer();


//        System.out.println(RefContainer.getPort());
//        port2.showAllVehicleInPort();
//        port1.showAllVehicleInPort();

//        System.out.println(RefContainer.getPort());
//        reeferTruck1.moveTo(port2);
//        System.out.println(RefContainer.getPort());
//        ship1.loadContainer(openTopContainer);
//        System.out.println(port1.getContainers());
//        // Print initial states
////        System.out.println("Initial port of BasicTruck1: " + basicTruck1.getCurrentPort().getName());
////        System.out.println("Initial port of TankerTruck1: " + tankerTruck1.getCurrentPort().getName());
////        System.out.println("Initial port of ReeferTruck1: " + reeferTruck1.getCurrentPort().getName());
////
////        // Move vehicles
//        basicTruck1.moveTo(port2);
//        tankerTruck1.moveTo(port2);
//        reeferTruck1.moveTo(port2);
////
////        // Print final states
//        System.out.println("Final port of BasicTruck1: " + basicTruck1.getCurrentPort().getName());
//        System.out.println("Final port of TankerTruck1: " + tankerTruck1.getCurrentPort().getName());
//        System.out.println("Final port of ReeferTruck1: " + reeferTruck1.getCurrentPort().getName());







    }
}
