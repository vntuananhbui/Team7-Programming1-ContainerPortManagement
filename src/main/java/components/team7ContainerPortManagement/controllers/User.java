package src.main.java.components.team7ContainerPortManagement.controllers;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

public class User {
    private String U_ID;
    private String username;
    private String Password;
    private String userType;

    public User(String U_ID, String username, String Password, String userType) {
        this.U_ID = U_ID;
        this.username = username;
        this.Password = hashing(Password);
        this.userType = userType;
    }
    public User() {};

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


        public static List<User> readUserCredentialFromFile(String filename) throws IOException {
        List<User> credentials = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String U_ID = parts[0].trim();
                    String username = parts[1].trim();
                    String password = parts[2].trim();
                    String userType = parts[3].trim();
                    credentials.add(new User(U_ID,username,password,userType));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading credentials file: " + e.getMessage());
        }
        return credentials;
    }

    public static void registerUser(List<User> userList, String username, String password, String userType) {
                Random randNum = new Random();
                int U_ID = randNum.nextInt(9999);
                String nextUid = "u-" + U_ID;
                userList.add(new User(nextUid, username, password,userType));


        try (BufferedWriter writer = new BufferedWriter(new FileWriter("./src/main/java/components/team7ContainerPortManagement/resources/data/user.txt", true))) {
            User newUser = userList.get(userList.size() - 1);
            writer.write(newUser.getU_ID() + "," + newUser.getUsername() + "," + newUser.getPassword() + "," + newUser.getUserType());
            writer.newLine();
            System.out.println("Registration successful!");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public static boolean isValidPassword(String password) {
        if (password.length() < 8 || password.length() > 20) {
            return false;
        }

        if (!Pattern.compile("[0-9]").matcher(password).find()) {
            return false;
        }

        if (!Pattern.compile("[a-z]").matcher(password).find()) {
            return false;
        }

        if (!Pattern.compile("[A-Z]").matcher(password).find()) {
            return false;
        }
        return true;
    }
}

