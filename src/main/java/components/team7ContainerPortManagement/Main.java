package src.main.java.components.team7ContainerPortManagement;

import src.main.java.components.team7ContainerPortManagement.Controller.UserController.AdminController;
import src.main.java.components.team7ContainerPortManagement.Controller.UserController.PortManagerController;
import src.main.java.components.team7ContainerPortManagement.Controller.UserController.User;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.view.AdminView;
import src.main.java.components.team7ContainerPortManagement.view.PortManagerView;
import src.main.java.components.team7ContainerPortManagement.view.mainMenu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
//        mainLoop();
        AdminController admin = new AdminController();
        PortManagerController portManager1 = new PortManagerController();
        List<User> userAdminList = new ArrayList<>();
        List<User> userPortManagerList = new ArrayList<>();

        try {
            userAdminList = User.readAdminCredentialFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/userData/admin.txt");
        } catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }

        try {
            userAdminList = User.readPortManagerCredentialFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/userData/port_manager.txt");
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
                        System.out.println("INVALID PASSWORD! PlEASE FOLLOW THE REQUIREMENTS.");
                        System.out.println();
                    }
                }

                System.out.println("\nEnter type of user (admin or port manager): ");
                String userType = scanner.nextLine();
                if(userType.equalsIgnoreCase("admin")) {
                    User.registerAdmin(userAdminList, newUsername, newPassword,userType);
                } else {
                    User.registerPortManager(userPortManagerList, newUsername, newPassword,userType);
                }

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
//                    Port port = PortManagerController.getAssociatedPort(username);
                    mainMenu.portManagerMenu(username);
//                    PortManagerView.main(new String[]{});
                } else {
                    System.out.println("Login failed");
                }

            } else {
                System.out.println("Stop system");
                break;
            }
        }
    }
}//End bracket
