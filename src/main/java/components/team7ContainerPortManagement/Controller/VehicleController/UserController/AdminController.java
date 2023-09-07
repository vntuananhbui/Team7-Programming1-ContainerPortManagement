package src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.UserController;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static src.main.java.components.team7ContainerPortManagement.Controller.UserController.AdminController.extractDataFromLinePortMana;

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



    //CRUD PORT
    // Method 1
    // Add Port
    // @param record
    public void addPort(Port port) throws IOException {
        FileWriter portWriter1 = new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt", true);


        // Checking if a Port already exists or not,
        // if not add it to Port list, Otherwise
        // error display message

            portWriter1.write(port.toStringAdd() + "\n");
            portWriter1.close();
        }


    // Method 2
    // Searching Port
    // @param idNumber
    //  @return
    public boolean findPortID(String idNumber) {

        // Iterating record list
        // using for each loop
        for (Port l : listPost) {

            // Checking record by id Number
            if (l.writeID().equals(idNumber)) {
                System.out.println(l);
                return true;
            }
        }
        return false;
    }

    // Method 3
    // Delete Port
    // @param recIdNumber
    public void deletePort(String portIdNumber) {
        Port portDel = null;

        // Iterating record list
        for (Port port : listPost) {

            // Finding port to be deleted by id Number
            if (port.writeID().equals(portIdNumber)) {
                portDel = port;
            }
        }

        // If portDel is null, then show error message,
        // otherwise remove the port from Port list
        if (portDel == null) {

            // Displaying no port found
            System.out.println("Invalid port Id");
        } else {

            listPost.remove(portDel);

            // Display message for successful deletion of
            // port
            System.out.println(
                    "Successfully removed port from the list");
        }
    }

    // Method 4
    // Finding Port
    // @param idNumber
    // @return
    public Port findPort(String idNumber) {
        // Iterate Port list
        // using for each loop
        for (Port port : listPost) {

            // Checking port by id Number.
            if (port.writeID().equals(idNumber)) {
                return port;
            }
        }
        return null;
    }

    // Method 5
    // Update Port
    // @param id
    // @param input
//    public void updatePort(String id, Scanner input) {
//
//        if (findPortID(id)) {
//            Port port = findPort(id);
//
//            String idNumber = port.getID();
//            String portName = port.getName();
//            double latitude = port.getLatitude();
//            double longitude = port.getLongitude();
//            int storingCapacity = port.getStoringCapacity();
//            boolean landingAbility = port.isLandingAbility();
//
//
//            // Display message only
//            System.out.print("Press Y if want to update Port Id: ");
//            String choose = input.nextLine();
//            if (choose.equalsIgnoreCase("Y")) {
//                System.out.print("What is the New Port ID ? ");
//                idNumber = input.nextLine();
//            }
//            System.out.print("Press Y if want to update Port Name: ");
//            String choose1 = input.nextLine();
//            if (choose1.equalsIgnoreCase("Y")) {
//                // Display message only
//                System.out.print("What is the New Port Name ?");
//                portName = input.nextLine();
//
//            }
//            System.out.print("Press Y if want to update Latitude: ");
//            String choose2 = input.nextLine();
//            if (choose2.equalsIgnoreCase("Y")) {
//                // Display message only
//                System.out.print("What is the New Latitude ? ");
//                latitude = Double.parseDouble(input.nextLine());
//            }
//
//            System.out.print("Press Y if want to update Longitude: ");
//            String choose3 = input.nextLine();
//            if (choose3.equalsIgnoreCase("Y")) {
//                // Display message only
//                System.out.print("What is the New Longitude ? ");
//                longitude = Double.parseDouble(input.nextLine());
//            }
//
//            System.out.print("Press Y if want to update Storing Capacity: ");
//            String choose4 = input.nextLine();
//            if (choose4.equalsIgnoreCase("Y")) {
//                // Display message only
//                System.out.print("What is the New Storing Capacity ? ");
//                storingCapacity = Integer.parseInt(input.nextLine());
//            }
//
//            System.out.print("Press Y if want to update Landing Ability: ");
//            String choose5 = input.nextLine();
//            if (choose5.equalsIgnoreCase("Y")) {
//                // Display message only
//                System.out.print("Is port having Landing Ability ? ");
//                landingAbility = Boolean.parseBoolean(input.nextLine());
//            }
//
//
//            port.setID(idNumber);
//            port.setName(portName);
//            port.setLatitude(latitude);
//            port.setLongitude(longitude);
//            port.setStoringCapacity(storingCapacity);
//            port.setLandingAbility(landingAbility);
//
//            System.out.println(
//                    "Port Updated Successfully");
//        } else {
//
//            // Print statement
//            System.out.println(
//                    "Port Not Found in the Port list");
//        }
//    }


////    DISPLAY ALL PORT IN FILE
//    // display port store in file
//    public static void displayAllPorts() throws IOException {
//        // Path to the ports file
//        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt";
//
//        // Try-with-resources to automatically close BufferedReader
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            System.out.println("Current Ports:\n--------------------");
//            int orderNumber = 1;
//
//            while ((line = reader.readLine()) != null) {
//                // Extract port ID and name from the line using regex
//                String portID = extractDataFromLine(line, "ID='(.*?)'");
//                String portName = extractDataFromLine(line, "name='(.*?)'");
//
//                if (portID != null && portName != null) {
//                    System.out.printf("%d. Port ID: '%s' , Port Name: '%s'\n", orderNumber++, portID, portName);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Port file not found.");
//            e.printStackTrace();
//        }
//    }
//
//
//    // Utility function to read port IDs from port_portmanager.txt
//    public static Set<String> getPortIDsFromFile(String filename) throws IOException {
//        Set<String> portIDs = new HashSet<>();
//        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                // Assume the format is {p-port2:port manager 4}
//                String[] parts = line.split(":");
//                if (parts.length == 2) {
//                    String portID = parts[0].trim().substring(1); // Remove the opening brace
//                    portIDs.add(portID);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        return portIDs;
//    }
//
//
//    //Belong to displayAllPort
//
//    private static String extractDataFromLinePortMana(String line, String patternStr) {
//        Pattern pattern = Pattern.compile(patternStr);
//        Matcher matcher = pattern.matcher(line);
//
//        if (matcher.find()) {
//            return matcher.group(1);
//        }
//
//        return null;
//    }
//
//    // function 16: display all ports are available
//
//    public static void displayPortToPortMana() throws IOException {
//        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt";
//        String anotherFilePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_portmanager.txt";
//        Set<String> uniquePortIDs = getPortIDsFromFile(anotherFilePath);
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
//            String line;
//            System.out.println("All Ports:\n--------------------");
//            int orderNumber = 1;
//            while ((line = reader.readLine()) != null) {
//                String portID = extractDataFromLinePortMana(line, "ID='(.*?)'");
//                String portName = extractDataFromLinePortMana(line, "name='(.*?)'");
//                if (portID != null && portName != null && !uniquePortIDs.contains(portID)) {
//                    System.out.printf("%d. Port ID: '%s' , Port Name: '%s'\n", orderNumber++, portID, portName);
//                }
//            }
//        } catch (FileNotFoundException e) {
//            System.out.println("Port file not found.");
//            e.printStackTrace();
//        }
//    }
//
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
            System.out.println("Please enter the port you want to assign (ex: p-port6): ");
            portNumber = scanner.nextLine();
            if (checkIfExists(portFileName, "ID='(.*?)'", portNumber)) {
                break;
            } else {
                System.out.println("Invalid port. Please try again.");
            }

        }

        // Check for valid port manager
        while (true) {
            System.out.println("Please enter the port manager (ex: port manager 2): ");
            portManagerUserName = scanner.nextLine();
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
        String outputFileName = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_portmanager.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName, true))) {
            writer.write("{" + chosen.get("chosenPortID") + ":" + chosen.get("chosenManager") + "}");
            writer.newLine();
            System.out.println("Assign port to port manager successful!");
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


//    public static void displayAllPortManager() {
//        String fileName = "src/main/java/components/team7ContainerPortManagement/resource/data/userData/admin.txt";
//        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
//            String line;
//            System.out.print("---------------------------------\n");
//            System.out.println("All port managers: ");
//            int sequenceNumber = 1;  // Initialize sequence number
//
//            // Read the file line by line
//            while ((line = br.readLine()) != null) {
//                // Skip lines that are too short to contain the necessary information
//                if (line.trim().isEmpty()) continue;
//
//                // Split each line based on comma
//                String[] parts = line.split(",");
//
//                // Check if there are enough parts to prevent ArrayIndexOutOfBoundsException
//                if (parts.length < 4) continue;
//
//                // Check the user type; it's assumed to be in the 4th element
//                String userType = parts[3].trim();
//
//                if ("port manager".equals(userType)) {
//                    // Assuming the username is in the 2nd element
//                    String username = parts[1].trim();
//
//                    System.out.println(sequenceNumber + ". " + username);
//
//                    // Increment sequence number for next "port manager"
//                    sequenceNumber++;
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    public void displayPort() {
//
//        // If record list is empty then
//        // print the message below
//        if (listPost.isEmpty()) {
//
//            // Print statement
//            System.out.println("The list has no port\n");
//        }
//        // Iterating Record list
//        // using for each loop
//        for (Port port : listPost) {
//
//            // Printing the list
//            System.out.println(port.toStringAdd());
//        }
//    }

    //CRUD CONTAINER
//    public void addContainer(Container container) {
//
//        // Checking if a record already exists or not,
//        // if not add it to Record list, Otherwise
//        // error display message
//        if (!findContainerID(container.getID())) {
//            listContainer.add(container);
//        } else {
//
//            // Print statement
//            System.out.println(
//                    "Container already exists in the Container list");
//        }
//    }
//
//    public boolean findContainerID(String idNumber) {
//        // Iterating record list
//        // using for each loop
//        for (Container container : listContainer) {
//
//            // Checking record by id Number
//            if (container.getID().equals(idNumber)) {
//
//                System.out.println(container);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public void deleteContainer(String containerIdNumber) {
//        Container containerDel = null;
//
//        // Iterating record list
//        for (Container container : listContainer) {
//
//            // Finding record to be deleted by id Number
//            if (container.getID().equals(containerIdNumber)) {
//                containerDel = container;
//            }
//        }
//
//        // If recordDel is null, then show error message,
//        // otherwise remove the record from Record list
//        if (containerDel == null) {
//
//            // Displaying no record found
//            System.out.println("Invalid container Id");
//        } else {
//
//            listContainer.remove(containerDel);
//
//            // Display message for successful deletion of
//            // record
//            System.out.println(
//                    "Successfully removed container from the list");
//        }
//    }
//
//    public Container findContainer(String idNumber) {
//
//        // Iterate Record list
//        // using for each loop
//        for (Container container : listContainer) {
//
//            // Checking record by id Number.
//            if (container.getID().equals(idNumber)) {
//                return container;
//            }
//        }
//
//        return null;
//    }
//
//    public void updateContainer(String id, Scanner input) {
//
//        if (findContainerID(id)) {
//            Container container = findContainer(id);
////
////            String idNumber = container.getID();
////            double weight = container.getWeight();
//            ContainerType containerType = container.getContainerType();
//
//
//            System.out.print("Press Y if you want to update Container Type: ");
//            String choose = input.nextLine();
//            if (choose.equalsIgnoreCase("Y")) {
//                // Display available ContainerType options
//                int i = 1;
//                for (ContainerType type : ContainerType.values()) {
//                    System.out.println(i + ". " + type.getLabel());
//                    i++;
//                }
//                System.out.print("Enter the desired number for new Container Type: ");
//                int choice = input.nextInt();
//                input.nextLine(); // Consume newline
//
//                // Check if user's choice is valid
//                if (choice > 0 && choice <= ContainerType.values().length) {
//                    containerType = ContainerType.values()[choice - 1];
//                    container.setContainerType(containerType); // Assuming there's a setter for containerType
//                    System.out.println("Container Type Updated Successfully");
//                } else {
//                    System.out.println("Invalid choice. Container Type not updated.");
//                }
//                System.out.println("Port Updated Successfully");
//            }
//        } else {
//            // Print statement
//            System.out.println(
//                    "Port Not Found in the Port list");
//        }
//
//        }
//
//    public void displayContainer()
//    {
//
//        // If record list is empty then
//        // print the message below
//        if (listContainer.isEmpty()) {
//
//            // Print statement
//            System.out.println("The list has no container\n");
//        }
//        // Iterating Record list
//        // using for each loop
//        for (Container container : listContainer) {
//            // Printing the list
//            System.out.println(container.toString());
//        }
//    }
//
//    public  void addVehicle(Vehicle vehicle) {
////        String compositeKey = vehicle.getClass().getSimpleName() + "-" + vehicle.getID();
//        if (!findVehicleID(vehicle.getID())) {
//            listVehicle.add(vehicle);
//        } else {
//            System.out.println("Vehicle already exists in the list.");
//        }
//    }
//
//    public boolean findVehicleID(String vehicleID) {
//        for (Vehicle vehicle : listVehicle) {
////            String existingCompositeKey = vehicle.getClass().getSimpleName() + "-" + vehicle.getID();
//            if (vehicle.getID().equals(vehicleID)) {
//                System.out.println(vehicle);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    public Vehicle findVehicle(String idNumber) {
//
//        // Iterate Record list
//        // using for each loop
//        for (Vehicle vehicle : listVehicle) {
//
//            // Checking record by id Number.
//            if (vehicle.getID().equals(idNumber)) {
//                return vehicle;
//            }
//        }
//
//        return null;
//    }
//
//    public void updateVehicle(String id, Scanner input) {
//
//        if (findVehicleID(id)) {
//            Vehicle vehicle = findVehicle(id);
////
////            String idNumber = container.getID();
////            double weight = container.getWeight();
//
//            String vName = vehicle.getName();
//            double currentFuel = vehicle.getCurrentFuel();
//            double carryCapacity = vehicle.getCarryingCapacity();
//            double fuelCapacity = vehicle.getCarryingCapacity();
//
//
//
//            System.out.print("Press Y if you want to update vehicle name: ");
//            String choose = input.nextLine();
//            if (choose.equalsIgnoreCase("Y")) {
//                System.out.print("What is the new vehicle name ? ");
//                vName = input.nextLine();
//            }
//            System.out.println("Press Y if you want to update current fuel: ");
//            String choose1 = input.nextLine();
//            if(choose1.equalsIgnoreCase("Y")) {
//                System.out.print("What is the new current fuel ? ");
//                currentFuel = Double.parseDouble(input.nextLine());
//            }
//            System.out.println("Press Y if you want to update carrying capacity: ");
//            String choose2 = input.nextLine();
//            if(choose2.equalsIgnoreCase("Y")) {
//                System.out.print("What is the new carrying capacity ? ");
//                carryCapacity = Double.parseDouble(input.nextLine());
//            }
//            System.out.println("Press Y if you want to update fuel capacity: ");
//            String choose3 = input.nextLine();
//            if(choose3.equalsIgnoreCase("Y")) {
//                System.out.print("What is the new fuel capacity ? ");
//                fuelCapacity = Double.parseDouble(input.nextLine());
//            }
//
//            vehicle.setName(vName);
//            vehicle.setCurrentFuel(currentFuel);
//            vehicle.setCarryingCapacity(carryCapacity);
//            vehicle.setFuelCapacity(fuelCapacity);
//
//            System.out.println("Vehicle information updated successfully");
//
//        } else {
//            // Print statement
//            System.out.println(
//                    "Vehicle Not Found in the Vehicle list");
//        }
//
//    }
//
//    public void deleteVehicle(String vehicleIdNumber) {
//        Vehicle vehicleDel = null;
//
//        // Iterating record list
//        for (Vehicle vehicle : listVehicle) {
//
//            // Finding record to be deleted by id Number
//            if (vehicle.getID().equals(vehicleIdNumber)) {
//                vehicleDel = vehicle;
//            }
//        }
//
//        // If recordDel is null, then show error message,
//        // otherwise remove the record from Record list
//        if (vehicleDel == null) {
//
//            // Displaying no record found
//            System.out.println("Invalid vehicle Id");
//        } else {
//
//            listVehicle.remove(vehicleDel);
//
//            // Display message for successful deletion of
//            // record
//            System.out.println(
//                    "Successfully removed vehicle from the list");
//        }
//    }
//
//    public void displayVehicle()
//    {
//
//        // If record list is empty then
//        // print the message below
//        if (listVehicle.isEmpty()) {
//
//            // Print statement
//            System.out.println("The list has no vehicle\n");
//        }
//        // Iterating Record list
//        // using for each loop
//        for (Vehicle vehicle : listVehicle) {
//            // Printing the list
//            System.out.println(vehicle.toString());
//        }
//    }

}


