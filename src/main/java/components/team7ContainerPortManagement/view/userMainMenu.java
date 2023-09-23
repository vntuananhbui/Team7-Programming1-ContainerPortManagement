package src.main.java.components.team7ContainerPortManagement.view;

import src.main.java.components.team7ContainerPortManagement.Controller.UserController.AdminController;
import src.main.java.components.team7ContainerPortManagement.Controller.UserController.PortManagerController;
import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.User;
import src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.UserController.userController.checkPortManagerBelongPort;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.readContainersFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.findPortByUsername;
import static src.main.java.components.team7ContainerPortManagement.view.AdminMenu.adminMenu;
import static src.main.java.components.team7ContainerPortManagement.view.mainMenu.portManagerMenu;
import static src.main.java.components.team7ContainerPortManagement.view.mainMenu.resetFileMenu;

public class userMainMenu {
    String red = "\u001B[31m";
    String green = "\u001B[32m";
    static String yellow = "\u001B[33m";
    String blue = "\u001B[34m";
    String purple = "\u001B[35m";
    String cyan = "\u001B[36m";
    String white = "\u001B[37m";
    static String reset = "\u001B[0m";
    public static void mainLoopUser() throws IOException {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println(yellow + "╔══════════════════════════════════════════╗");
            System.out.println("║                LOGIN MENU                ║");
            System.out.println("╟──────────────────────────────────────────╢");
            System.out.println("║  [1] REGISTER                            ║");
            System.out.println("║  [2] LOGIN                               ║");
            System.out.println("║  [3] RESET AND ADD DEFAULT DATA          ║");
            System.out.println("║  [4] STOP SYSTEM                         ║");
            System.out.println("║                                          ║");
            System.out.println("╚══════════════════════════════════════════╝" + reset);
            System.out.print("   Choose an option: ");

            try {
                int option = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (option) {
                    case 1:
                        register();
                        break;
                    case 2:
                        login();
                        break;
                    case 3:
                        resetFileMenu();
                        break;
                    case 4:
                        System.out.println("Exiting the program. Goodbye!");
                        System.exit(0);
                        break;
//                        scanner.close();
//                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            } catch (java.util.InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Consume the invalid input
            }
        }
    }


    public static void register() {
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        AdminController admin = new AdminController();
        PortManagerController portManager1 = new PortManagerController();
        List<User> userAdminList = new ArrayList<>();
        List<User> userPortManagerList = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);


        // read the admin credential in file
        try {
            userAdminList = User.readAdminCredentialFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/userData/admin.txt");
        } catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }
        // read the port manager credential in file

        try {
            userAdminList = User.readPortManagerCredentialFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/userData/port_manager.txt");
        } catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }

        String newUsername = " ";
        while (true) {
            System.out.print("Enter a new username: ");
            newUsername = scanner.nextLine();

            if (!User.checkUserName(newUsername)) {
//                System.out.println("Username has been already exist. Please re-enter again!");
                System.out.println();
                System.out.println(ANSI_RED + "╔══════════════════════════════════════════════╗");
                System.out.println("║          USERNAME EXIST, TRY AGAIN           ║");
                System.out.println("╚══════════════════════════════════════════════╝"+ reset);
                System.out.println();

            } else {
                break;
            }
        }
        String newPassword = ""; // Declare outside the loop


        while (true) {
            System.out.print(
                    "🔐Enter a new password that meets these criteria 🔐:\n" +
                    "🔒 At least one digit [0-9]\n" +
                    "🔒 At least one lowercase character [a-z]\n" +
                    "🔒 At least one uppercase character [A-Z]\n" +
                    "🔒 At least one special character [!@#$%^&*]\n" +
                    "🔒 Length of at least 8 characters and a maximum of 20 characters\n" +
                    "Your password is your fortress! Make it strong: ");
            newPassword = scanner.nextLine();

            if (User.isValidPassword(newPassword)) {
                System.out.println("Password is valid!");
                break;  // break the loop if password is valid
            } else {
                System.out.println();
                System.out.println(ANSI_RED + "╔══════════════════════════════════════════════╗");
                System.out.println("║ INVALID TYPE, PlEASE FOLLOW THE REQUIREMENTS.║");
                System.out.println("╚══════════════════════════════════════════════╝"+ reset);
                System.out.println();
            }

        }

        System.out.println("\nEnter type of user (admin or port manager): ");
        String userType = scanner.nextLine();
        if (userType.equalsIgnoreCase("admin")) {
            if (!User.checkUserTypeAdmin(userType)) {
//                System.out.println("Admin already exists! There is only one admin in system.");
                System.out.println();
                System.out.println(ANSI_RED + "╔══════════════════════════════════════════════╗");
                System.out.println("║ ADMIN EXISTS, ONLY ONE ADMIN IN SYSTEM.      ║");
                System.out.println("╚══════════════════════════════════════════════╝"+ reset);
                System.out.println();
                System.out.println("Press any key to return Login menu...");
                scanner.next();
            } else {
                User.registerAdmin(userAdminList, newUsername, newPassword, userType);
            }
        } else {
            User.registerPortManager(userPortManagerList, newUsername, newPassword, userType);
        }

    }

    public static void login() throws IOException {
        String ANSI_RED = "\u001B[31m";
        String ANSI_cyan = "\u001B[36m";
        String reset = "\u001B[0m";
        String portPortManagerFilePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_portmanager.txt";
        String portFilePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt";
        AdminController admin = new AdminController();
        PortManagerController portManager1 = new PortManagerController();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your username: ");
        String username = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();


        if (admin.checkAdminCredentials(username, password)) {
            System.out.println(ANSI_cyan+"||  Login successful  ||" + reset);
            adminMenu();
        } else if (portManager1.checkPortManagerCredentials(username, password)) {
            if (checkPortManagerBelongPort(username)) {
                System.out.println(ANSI_cyan+"||  Login successful  ||" + reset);
//                printPortManagerBelongPort(username);

                Port selectedPort = findPortByUsername(username,portPortManagerFilePath,portFilePath);
//                portManagerMenu(selectedPort);
//                System.out.println("user name: "+username);
//                System.out.println(selectedPort);
                portManagerMenu(selectedPort);
            } else {
                System.out.println(username + " is not associated with any port.");
                System.out.println();
                System.out.println(ANSI_RED+"╔══════════════════════════════════════════════════════════════╗");
                System.out.println("║ ACCOUNT NOT ASSOCIATED WITH ANY PORT, PLEASE CONTACT ADMIN   ║");
                System.out.println("╚══════════════════════════════════════════════════════════════╝"+ reset);
                System.out.println();
                System.out.println("Press any key to return Login menu...");
                scanner.next();
            }
        } else {
//            System.out.println("Login failed");
            System.out.println();
            System.out.println(ANSI_RED + "╔══════════════════════════════════════════════╗");
            System.out.println("║ LOGIN FAIL: WRONG USERNAME OR PASSWORD!      ║");
            System.out.println("╚══════════════════════════════════════════════╝"+ reset);
            System.out.println();
            System.out.println("Press any key to return Login menu...");
            scanner.next();
        }
    }
}

