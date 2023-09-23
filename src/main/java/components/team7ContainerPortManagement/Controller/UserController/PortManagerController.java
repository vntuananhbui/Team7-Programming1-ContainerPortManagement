package src.main.java.components.team7ContainerPortManagement.Controller.UserController;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.User;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortFile;

public class PortManagerController extends User {
    LinkedList<Container> listContainer;
    public PortManagerController(String U_ID, String username, String Password, String userType) {
        super(U_ID, username, Password, userType);
    }
    public PortManagerController() {
        listContainer = new LinkedList<>();
    }


    public boolean checkPortManagerCredentials(String username, String password) {
        boolean isAuthentication = false;
        Pattern pattern = Pattern.compile("User\\{U_ID='(.+)', username='(.+)', Password='(.+)', userType='(.+)', amountCash=(.+)\\}");

        try {
            Scanner fileScanner = new Scanner(new File("src/main/java/components/team7ContainerPortManagement/resource/data/userData/port_manager.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                Matcher matcher = pattern.matcher(line);

                if (matcher.find()) {
                    String fileUsername = matcher.group(2);
                    String filePasswordHash = matcher.group(3);
                    String userType = matcher.group(4);

                    String hashedPassword = User.hashing(password);

                    if (fileUsername.equals(username) && filePasswordHash.equals(hashedPassword) && "port manager".equalsIgnoreCase(userType)) {
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


    // return the port object belongs to port manager
    public static Port getAssociatedPort(String username) throws IOException {
        String associatedPortId = null;
        Map<String, Port> portMap = readPortFile() ;

        try (Scanner portManagerScanner = new Scanner(new File("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_portmanager.txt"))) {
            while (portManagerScanner.hasNextLine()) {
                String portManagerLine = portManagerScanner.nextLine();
                String[] parts = portManagerLine.substring(1, portManagerLine.length() - 1).split(":");
                if (username.equals(parts[1])) {
                    associatedPortId = parts[0];
                    break;
                }
            }
        }

        if (associatedPortId != null) {
            return portMap.get(associatedPortId);
        }

        return null;
    }

    private static final Map<String, String> portMap = new HashMap<>();

    static {
        loadPortMap("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_portmanager.txt");
    }

    private static void loadPortMap(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("{") && line.endsWith("}")) {
                    line = line.substring(1, line.length() - 1);
                    String[] parts = line.split(":");
                    if (parts.length == 2) {
                        String portID = parts[0].trim();
                        String userName = parts[1].trim();
                        portMap.put(portID, userName);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String findUsernameByPortID(String portID) {
        return portMap.get(portID);
    }



    public static User findUserByUsername(String username) {
        List<User> userList = loadUsers();
        for (User user : userList) {
//            System.out.println("Debug user list: " + userList);
//            System.out.println("Debug user: " + user);
//            System.out.println("Debug password: " + user.getPassword());
            if (user.getUsername().equals(username)) {
//                System.out.println("CORRREEEEEE");
                return user;
            }
        }
        return null; // User not found
    }

    public static List<User> loadUsers() {

        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/userData/port_manager.txt";
        List<User> userList = new ArrayList<>();
        Pattern userPattern = Pattern.compile("User\\{U_ID='([^']+)', username='([^']+)', Password='([^']+)', userType='([^']+)'(, amountCash=([-+]?[0-9]*\\.?[0-9]+))?}");

        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));
            for (String line : lines) {
                Matcher matcher = userPattern.matcher(line);
                if (matcher.find()) {
                    String U_ID = matcher.group(1);
                    String username = matcher.group(2);
//                    String Password = matcher.group(3).trim();
                    String Password = matcher.group(3).trim(); // Password is already hashed
                    String userType = matcher.group(4);
                    double amountCash = 0.0;
                    String amountCashStr = matcher.group(6);
                    if (amountCashStr != null) {
                        try {
                            amountCash = Double.parseDouble(amountCashStr);
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }
                    }
                    User user = new User(U_ID, username, Password, userType, amountCash);

                    user.setAmountCash(amountCash);
                    userList.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return userList;
    }
    public static void saveUsers(List<User> userList) {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/userData/port_manager.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (User user : userList) {
                // Write each user as a line in the file
//                System.out.println("save check: " + user.getAmountCash());
                writer.write(user.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void addCashToUser(String username, double amountToAdd) {
        List<User> userList = loadUsers();
        User user = findUserByUsername(username);
        if (user != null) {
            double currentAmount = user.getAmountCash();
//            System.out.println("Before cash: " + currentAmount);
            user.setAmountCash(currentAmount + amountToAdd);
//            System.out.println("After cash: " + user.getAmountCash());
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUsername().equals(username)) {
                    userList.set(i, user);

                    break;
                }
            }
            saveUsers(userList);
        } else {
            System.out.println("User not found: " + username);
        }
    }
    public static void removeCashToUser(String username, double amountToMinus) {
        List<User> userList = loadUsers();
        User user = findUserByUsername(username);
        if (user != null) {
            double currentAmount = user.getAmountCash();
//            System.out.println("Before cash: " + currentAmount);
            user.setAmountCash(currentAmount - amountToMinus);
//            System.out.println("After cash: " + user.getAmountCash());
            for (int i = 0; i < userList.size(); i++) {
                if (userList.get(i).getUsername().equals(username)) {
                    userList.set(i, user);
                    break;
                }
            }
            saveUsers(userList);
        } else {
            System.out.println("User not found: " + username);
        }
    }

}
