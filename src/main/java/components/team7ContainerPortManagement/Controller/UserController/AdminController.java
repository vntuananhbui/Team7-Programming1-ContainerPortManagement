package src.main.java.components.team7ContainerPortManagement.Controller.UserController;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.User;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.*;
import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminController extends User {
    LinkedList<Port> listPost;
    LinkedList<Container> listContainer;

    LinkedList<Vehicle> listVehicle;

    public AdminController(String U_ID, String username, String Password, String userType) {
        super(U_ID, username, Password, userType);
    }

    public AdminController() {
        listPost = new LinkedList<>();
        listContainer = new LinkedList<>();
        listVehicle = new LinkedList<>();
    }

    ;

    public boolean checkAdminCredentials(String username, String password) {
        boolean isAuthentication = false;
        Pattern pattern = Pattern.compile("User\\{U_ID='(.+)', username='(.+)', Password='(.+)', userType='(.+)'\\}");

        try {
            Scanner fileScanner = new Scanner(new File("src/main/java/components/team7ContainerPortManagement/resource/data/userData/admin.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    String fileUsername = matcher.group(2);
                    String filePasswordHash = matcher.group(3);
                    String userType = matcher.group(4);

                    // Assuming you have a hashing function like User.hashing
                    String hashedPassword = User.hashing(password);

                    if (fileUsername.equals(username) && filePasswordHash.equals(hashedPassword) && "admin".equalsIgnoreCase(userType)) {
                        isAuthentication = true;
                        break;  // exit the loop if credentials are found
                    }
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return isAuthentication;
    }




    // Utility function to read port IDs from port_portmanager.txt
    public static Set<String> getPortIDsFromFile(String filename) throws IOException {
        Set<String> portIDs = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Assume the format is {p-port2:port manager 4}
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String portID = parts[0].trim().substring(1); // Remove the opening brace
                    portIDs.add(portID);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return portIDs;
    }


    //Belong to displayAllPort

    public static String extractDataFromLinePortMana(String line, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

//     function 16: display all ports are available

    public static void displayPortToPortMana() throws IOException {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt";
        String anotherFilePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_portmanager.txt";
        Set<String> uniquePortIDs = getPortIDsFromFile(anotherFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("All Ports:\n--------------------");
            int orderNumber = 1;
            while ((line = reader.readLine()) != null) {
                String portID = extractDataFromLinePortMana(line, "ID='(.*?)'");
                String portName = extractDataFromLinePortMana(line, "name='(.*?)'");
                if (portID != null && portName != null && !uniquePortIDs.contains(portID)) {
                    System.out.printf("%d. Port ID: '%s' , Port Name: '%s'\n", orderNumber++, portID, portName);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Port file not found.");
            e.printStackTrace();
        }
    }

//    // Utility function to get usernames from port_portmanager.txt


    public static Set<String> getPortManagerNamesFromFile(String filename) {
        Set<String> portManagerNames = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            // Read the file line by line
            while ((line = br.readLine()) != null) {
                // Assume the format is {p-port2:port manager 4}
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    portManagerNames.add(parts[1].substring( 0,parts[1].length() - 1));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return portManagerNames;
    }

    // DISPLAY ALL PORT MANAGER IS STILL PENDING, NOT HIDE WHEN ASSIGN PORT MANAGER



    public static void displayAllPortManager() throws IOException {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/userData/port_manager.txt";
        String anotherFilePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_portmanager.txt";
        Set<String> portManagerUserName = getPortManagerNamesFromFile(anotherFilePath);

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("All Port Manager:\n--------------------");
            int orderNumber = 1;
            while ((line = reader.readLine()) != null) {
//                String portID = extractDataFromLinePortMana(line, "ID='(.*?)'");
                String portUserName = extractDataFromLinePortMana(line, "username='(.*?)'");
                if (portUserName != null && !portManagerUserName.contains(portUserName)) {
                    System.out.printf("%d. %s\n", orderNumber++, portUserName);
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Port Manager file not found.");
            e.printStackTrace();
        }
    }


    public static Map<String, String> assignPortToPortManager() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String portFileName = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt";
        String managerFileName = "src/main/java/components/team7ContainerPortManagement/resource/data/userData/port_manager.txt";

        String portNumber;
        String portManagerUserName;

        // Check for valid port
        while (true) {
            System.out.println("Please enter the port you want to assign (ex: p-port6) or press 0 to exit: ");
            portNumber = scanner.nextLine();
            if ("0".equals(portNumber)) {
                return null; // Exit the method with a null map
            }
            if (checkIfExists(portFileName, "ID='(.*?)'", portNumber)) {
                break;
            } else {
                System.out.println("Invalid port. Please try again.");
            }
        }

        // Check for valid port manager
        while (true) {
            System.out.println("Please enter the port manager (ex: port manager 2) or press 0 to exit: ");
            portManagerUserName = scanner.nextLine();
            if ("0".equals(portManagerUserName)) {
                return null; // Exit the method with a null map
            }
            if (checkIfExists(managerFileName, "username='(.*?)'", portManagerUserName)) {
                break;
            } else {
                System.out.println("Invalid port manager. Please try again.");
            }
        }


            Map<String, String> chosen = new HashMap<>();
            chosen.put("chosenPortID", readValueInfo(portFileName, portNumber, "ID='(.*?)'"));
            chosen.put("chosenManager", readValueInfo(managerFileName, portManagerUserName, "username='(.*?)'"));

            return chosen;
        }

        // method to check whether the user type correct port and port manager information
    public static boolean checkIfExists(String fileName, String regexPattern, String valueToCheck) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            Pattern pattern = Pattern.compile(regexPattern);
            while ((line = br.readLine()) != null) {
                Matcher matcher = pattern.matcher(line);
                if (matcher.find()) {
                    String extractedValue = matcher.group(1);
                    if (extractedValue.equals(valueToCheck)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    public static void writePortAndPortManagerFile(Map<String, String> chosen) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        Scanner scanner = new Scanner(System.in);
        String outputFileName = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_portmanager.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true))) {
            writer.write("{" + chosen.get("chosenPortID") + ":" + chosen.get("chosenManager") + "}");
            writer.newLine();
            System.out.println(      ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
            System.out.println("╟" + ANSI_CYAN + "                 ASSIGN SUCCESSFULLY" + "                    ║");
            System.out.println("╟────────────────────────────────────────────────────────╢"+ANSI_RESET);
            System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET );
            System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
            System.out.print("Press any key to return...");
            scanner.next();  // Wait for the user to press Enter
//            System.out.println("Assign port to port manager successful!");
        }
    }
    //-----------------------------------------------------------------------------------
    //-----------------------------------------------------------------------------------

    public static String readValueInfo(String fileName, String name, String patternStr) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("ID='" + name + "'")) {
                    return extractDataFromLine(line, patternStr);
                } else if (line.contains("username='" + name + "'")) {
                    return extractDataFromLine(line, patternStr);
                }
            }
        }
        return null;
    }

    //Belong to displayAllPort
    private static String extractDataFromLine(String line, String patternStr) {
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(line);

        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }
//------------------------------------------------------------------------------------------------------------
// -----------------------------------------------------------------------------------------------------------




}


