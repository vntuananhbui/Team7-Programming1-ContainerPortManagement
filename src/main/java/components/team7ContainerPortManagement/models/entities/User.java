package src.main.java.components.team7ContainerPortManagement.models.entities;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class User {
    private String U_ID;
    private String username;
    private String Password;
    private String userType;
    private double amountCash;  // New attribute



//    public User(String U_ID, String username, String Password, String userType) {
//        this.U_ID = U_ID;
//        this.username = username;
//        this.Password = hashing(Password);
//        this.userType = userType;
//
//    }

    public double getAmountCash() {
        return amountCash;
    }

    public void setAmountCash(double amountCash) {
        this.amountCash = amountCash;
    }

    public User(String U_ID, String username, String Password, String userType) {
        this.U_ID = U_ID;
        this.username = username;
        this.Password = hashing(Password);
        this.userType = userType;
        if (userType.equalsIgnoreCase("port manager")) {
            this.amountCash = 0.0;  // Initialize to 0 for port managers
        }
    }
    public User(String U_ID, String username, String Password, String userType, double amountCash) {
        this.U_ID = U_ID;
        this.username = username;
        this.Password = Password;
        this.userType = userType;

        this.amountCash = 0.0;  // Initialize to 0 for port managers
    }
    public void addCash(double amount) {
        if (this.userType.equalsIgnoreCase("port manager")) {
            this.amountCash += amount;
        } else {
            System.out.println("Operation not allowed. This user is not a port manager.");
        }
    }

    public User() {
    }

    ;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return Password;
    }

    public String getU_ID() {
        return U_ID;
    }

    public String getUserType() {
        return userType;
    }

    public static String hashing(String password) {
        try {
            // MessageDigest instance for MD5.
            MessageDigest m = MessageDigest.getInstance("MD5");

            // Add plain-text password bytes to digest using MD5 update() method.
            m.update(password.getBytes());

            // Convert the hash value into bytes
            byte[] bytes = m.digest();

            // The bytes array has bytes in decimal form. Converting it into hexadecimal format.
            StringBuilder s = new StringBuilder();
            for (byte aByte : bytes) {
                s.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            // Complete hashed password in hexadecimal format
            return s.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    // create adminCredentials list and read only admin object

    public static List<User> readAdminCredentialFromFile(String filename) throws IOException {
        List<User> adminCredentials = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String U_ID = parts[0].trim();
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    String userType = parts[3].trim();
                    adminCredentials.add(new User(U_ID, username, password, userType));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }
        return adminCredentials;
    }

    // create portManagerCredentials list and read only port manager object

    public static List<User> readPortManagerCredentialFromFile(String filename) throws IOException {
        List<User> portManagerCredentials = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String U_ID = parts[0].trim();
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    String userType = parts[3].trim();
                    portManagerCredentials.add(new User(U_ID, username, password, userType));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }
        return portManagerCredentials;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        if (this.userType.equalsIgnoreCase("port manager")) {
            return "User{" +
                    "U_ID='" + U_ID + '\'' +
                    ", username='" + username + '\'' +
                    ", Password='" + Password + '\'' +
                    ", userType='" + userType + '\'' +
                    ", amountCash=" + amountCash +
                    '}';
        } else {
            return "User{" +
                    "U_ID='" + U_ID + '\'' +
                    ", username='" + username + '\'' +
                    ", Password='" + Password + '\'' +
                    ", userType='" + userType + '\'' +
                    '}';
        }
    }

    // add admin object into adminList and write object into admin.txt

    public static void registerAdmin(List<User> userAdminList, String username, String password, String userType) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        Random randNum = new Random();
        int U_ID = randNum.nextInt(9999);
        String nextUid = "u-" + U_ID;
        userAdminList.add(new User(nextUid, username, password, userType));


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/userData/admin.txt", true))) {
            // get the user object of the last index ( add new user object to list)
            User newUser = userAdminList.get(userAdminList.size() - 1);
            writer.write(newUser.toString());
            writer.newLine();
//            System.out.println("Registration successful!");
            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "              CREATE ACCOUNT SUCCESSFULLY" + "               ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            USER ID: " + nextUid);
            System.out.println( "            USER Name: " + username);
            System.out.println( "            USER Type: " + userType);;
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println("Type any key to return...");
            Scanner scanner = new Scanner(System.in);
            scanner.next();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to check if the admin userType already exists
    public static boolean checkUserTypeAdmin(String userType) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/userData/admin.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("userType='" + userType + "'")) {
                    return false; // userType already exists
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return true; // userType is unique
    }
    // add port manager object into adminList and write object into port_manager.txt

    public static void registerPortManager(List<User> userPortManagerList, String username, String password, String userType) {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        Random randNum = new Random();
        int U_ID = randNum.nextInt(9999);
        String nextUid = "u-" + U_ID;
        userPortManagerList.add(new User(nextUid, username, password, userType));


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/userData/port_manager.txt", true))) {
            // get the user object of the last index ( add new user object to list)
            User newUser = userPortManagerList.get(userPortManagerList.size() - 1);
            writer.write(newUser.toString());
            writer.newLine();
            System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "              CREATE ACCOUNT SUCCESSFULLY" + "               ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println( "            USER ID: " + nextUid);
            System.out.println( "            USER Name: " + username);
            System.out.println( "            USER Type: " + userType);;
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.println("Type any key to return...");
            Scanner scanner = new Scanner(System.in);
            scanner.next();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    // Method to check if the username already exists
    public static boolean checkUserName(String username) {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/userData/port_manager.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("username='" + username + "'")) {
                    return false; // Username already exists
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return true; // Username is unique
    }


    public static boolean isValidPassword(String password) {
        // Check if password is between 8 and 20 characters long
        if (password.length() < 8 || password.length() > 20) {
            return false;
        }

        // Check if password contains at least one digit
        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }

        // Check if password contains at least one lowercase letter
        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }

        // Check if password contains at least one uppercase letter
        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }

        // Check if password contains at least one special character
        if (!Pattern.compile("[!@#$%^&*()-+=<>?]").matcher(password).find()) {
            return false;
        }
        return true;
    }

    public static void saveUsersToFile(List<User> userList, String filename) {
        try (FileWriter fw = new FileWriter(filename, false);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            for (User user : userList) {
                out.println(user.toString());
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
    }


