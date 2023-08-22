package src.main.java.components.team7ContainerPortManagement.controllers;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

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
        try {
            Scanner fileScanner = new Scanner(new File("./src/main/java/components/team7ContainerPortManagement/resources/data/user.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(","); // Split each value in each line by comma
                if (values[1].equals(username) && values[2].equals(User.hashing(password)) && values[3].equalsIgnoreCase("admin"))
                // If the username already had in customer file continue to check the password
                {
//                    if (values[2].equals(password))
//                    // If the password match, it will return true
                    isAuthentication = true;
                }
            }
        } catch (FileNotFoundException fe) {
            fe.printStackTrace();
        }
        return isAuthentication;
    }

    public double calculateDistanceBetweenPorts(Port port1, Port port2) {
        return port1.calculateDistanceTo(port2);
    }

    //CRUD PORT
    // Method 1
    // Add Port
    // @param record
    public void addPort(Port port) {

        // Checking if a Port already exists or not,
        // if not add it to Port list, Otherwise
        // error display message
        if (!findPortID(port.getID())) {
            listPost.add(port);
        } else {

            // Print statement
            System.out.println(
                    "Port already exists in the Port list");
        }
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
            if (l.getID().equals(idNumber)) {
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
            if (port.getID().equals(portIdNumber)) {
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
            if (port.getID().equals(idNumber)) {
                return port;
            }
        }
        return null;
    }

    // Method 5
    // Update Port
    // @param id
    // @param input
    public void updatePort(String id, Scanner input) {

        if (findPortID(id)) {
            Port port = findPort(id);

            String idNumber = port.getID();
            String portName = port.getName();
            double latitude = port.getLatitude();
            double longitude = port.getLongitude();
            int storingCapacity = port.getStoringCapacity();
            boolean landingAbility = port.isLandingAbility();


            // Display message only
            System.out.print("Press Y if want to update Port Id: ");
            String choose = input.nextLine();
            if (choose.equalsIgnoreCase("Y")) {
                System.out.print("What is the New Port ID ? ");
                idNumber = input.nextLine();
            }
            System.out.print("Press Y if want to update Port Name: ");
            String choose1 = input.nextLine();
            if (choose1.equalsIgnoreCase("Y")) {
                // Display message only
                System.out.print("What is the New Port Name ?");
                portName = input.nextLine();

            }
            System.out.print("Press Y if want to update Latitude: ");
            String choose2 = input.nextLine();
            if (choose2.equalsIgnoreCase("Y")) {
                // Display message only
                System.out.print("What is the New Latitude ? ");
                latitude = Double.parseDouble(input.nextLine());
            }

            System.out.print("Press Y if want to update Longitude: ");
            String choose3 = input.nextLine();
            if (choose3.equalsIgnoreCase("Y")) {
                // Display message only
                System.out.print("What is the New Longitude ? ");
                longitude = Double.parseDouble(input.nextLine());
            }

            System.out.print("Press Y if want to update Storing Capacity: ");
            String choose4 = input.nextLine();
            if (choose4.equalsIgnoreCase("Y")) {
                // Display message only
                System.out.print("What is the New Storing Capacity ? ");
                storingCapacity = Integer.parseInt(input.nextLine());
            }

            System.out.print("Press Y if want to update Landing Ability: ");
            String choose5 = input.nextLine();
            if (choose5.equalsIgnoreCase("Y")) {
                // Display message only
                System.out.print("Is port having Landing Ability ? ");
                landingAbility = Boolean.parseBoolean(input.nextLine());
            }


            port.setID(idNumber);
            port.setName(portName);
            port.setLatitude(latitude);
            port.setLongitude(longitude);
            port.setStoringCapacity(storingCapacity);
            port.setLandingAbility(landingAbility);

            System.out.println(
                    "Port Updated Successfully");
        } else {

            // Print statement
            System.out.println(
                    "Port Not Found in the Port list");
        }
    }

    public void displayPort() {

        // If record list is empty then
        // print the message below
        if (listPost.isEmpty()) {

            // Print statement
            System.out.println("The list has no port\n");
        }
        // Iterating Record list
        // using for each loop
        for (Port port : listPost) {

            // Printing the list
            System.out.println(port);
        }
    }

    //CRUD CONTAINER
    public void addContainer(Container container) {

        // Checking if a record already exists or not,
        // if not add it to Record list, Otherwise
        // error display message
        if (!findContainerID(container.getID())) {
            listContainer.add(container);
        } else {

            // Print statement
            System.out.println(
                    "Container already exists in the Container list");
        }
    }

    public boolean findContainerID(String idNumber) {
        // Iterating record list
        // using for each loop
        for (Container container : listContainer) {

            // Checking record by id Number
            if (container.getID().equals(idNumber)) {

                System.out.println(container);
                return true;
            }
        }
        return false;
    }

    public void deleteContainer(String containerIdNumber) {
        Container containerDel = null;

        // Iterating record list
        for (Container container : listContainer) {

            // Finding record to be deleted by id Number
            if (container.getID().equals(containerIdNumber)) {
                containerDel = container;
            }
        }

        // If recordDel is null, then show error message,
        // otherwise remove the record from Record list
        if (containerDel == null) {

            // Displaying no record found
            System.out.println("Invalid container Id");
        } else {

            listContainer.remove(containerDel);

            // Display message for successful deletion of
            // record
            System.out.println(
                    "Successfully removed container from the list");
        }
    }

    public Container findContainer(String idNumber) {

        // Iterate Record list
        // using for each loop
        for (Container container : listContainer) {

            // Checking record by id Number.
            if (container.getID().equals(idNumber)) {
                return container;
            }
        }

        return null;
    }

    public void updateContainer(String id, Scanner input) {

        if (findContainerID(id)) {
            Container container = findContainer(id);
//
//            String idNumber = container.getID();
//            double weight = container.getWeight();
            ContainerType containerType = container.getContainerType();


            System.out.print("Press Y if you want to update Container Type: ");
            String choose = input.nextLine();
            if (choose.equalsIgnoreCase("Y")) {
                // Display available ContainerType options
                int i = 1;
                for (ContainerType type : ContainerType.values()) {
                    System.out.println(i + ". " + type.getLabel());
                    i++;
                }
                System.out.print("Enter the desired number for new Container Type: ");
                int choice = input.nextInt();
                input.nextLine(); // Consume newline

                // Check if user's choice is valid
                if (choice > 0 && choice <= ContainerType.values().length) {
                    containerType = ContainerType.values()[choice - 1];
                    container.setContainerType(containerType); // Assuming there's a setter for containerType
                    System.out.println("Container Type Updated Successfully");
                } else {
                    System.out.println("Invalid choice. Container Type not updated.");
                }
                System.out.println("Port Updated Successfully");
            }
        } else {
            // Print statement
            System.out.println(
                    "Port Not Found in the Port list");
        }

        }

    public void displayContainer()
    {

        // If record list is empty then
        // print the message below
        if (listContainer.isEmpty()) {

            // Print statement
            System.out.println("The list has no container\n");
        }
        // Iterating Record list
        // using for each loop
        for (Container container : listContainer) {
            // Printing the list
            System.out.println(container.toString());
        }
    }

    public  void addVehicle(Vehicle vehicle) {
//        String compositeKey = vehicle.getClass().getSimpleName() + "-" + vehicle.getID();
        if (!findVehicleID(vehicle.getID())) {
            listVehicle.add(vehicle);
        } else {
            System.out.println("Vehicle already exists in the list.");
        }
    }

    public boolean findVehicleID(String vehicleID) {
        for (Vehicle vehicle : listVehicle) {
//            String existingCompositeKey = vehicle.getClass().getSimpleName() + "-" + vehicle.getID();
            if (vehicle.getID().equals(vehicleID)) {
                System.out.println(vehicle);
                return true;
            }
        }
        return false;
    }

    public Vehicle findVehicle(String idNumber) {

        // Iterate Record list
        // using for each loop
        for (Vehicle vehicle : listVehicle) {

            // Checking record by id Number.
            if (vehicle.getID().equals(idNumber)) {
                return vehicle;
            }
        }

        return null;
    }

    public void updateVehicle(String id, Scanner input) {

        if (findVehicleID(id)) {
            Vehicle vehicle = findVehicle(id);
//
//            String idNumber = container.getID();
//            double weight = container.getWeight();

            String vName = vehicle.getName();
            double currentFuel = vehicle.getCurrentFuel();
            double carryCapacity = vehicle.getCarryingCapacity();
            double fuelCapacity = vehicle.getCarryingCapacity();



            System.out.print("Press Y if you want to update vehicle name: ");
            String choose = input.nextLine();
            if (choose.equalsIgnoreCase("Y")) {
                System.out.print("What is the new vehicle name ? ");
                vName = input.nextLine();
            }
            System.out.println("Press Y if you want to update current fuel: ");
            String choose1 = input.nextLine();
            if(choose1.equalsIgnoreCase("Y")) {
                System.out.print("What is the new current fuel ? ");
                currentFuel = Double.parseDouble(input.nextLine());
            }
            System.out.println("Press Y if you want to update carrying capacity: ");
            String choose2 = input.nextLine();
            if(choose2.equalsIgnoreCase("Y")) {
                System.out.print("What is the new carrying capacity ? ");
                carryCapacity = Double.parseDouble(input.nextLine());
            }
            System.out.println("Press Y if you want to update fuel capacity: ");
            String choose3 = input.nextLine();
            if(choose3.equalsIgnoreCase("Y")) {
                System.out.print("What is the new fuel capacity ? ");
                fuelCapacity = Double.parseDouble(input.nextLine());
            }

            vehicle.setName(vName);
            vehicle.setCurrentFuel(currentFuel);
            vehicle.setCarryingCapacity(carryCapacity);
            vehicle.setFuelCapacity(fuelCapacity);

            System.out.println("Vehicle information updated successfully");

        } else {
            // Print statement
            System.out.println(
                    "Vehicle Not Found in the Vehicle list");
        }

    }

    public void deleteVehicle(String vehicleIdNumber) {
        Vehicle vehicleDel = null;

        // Iterating record list
        for (Vehicle vehicle : listVehicle) {

            // Finding record to be deleted by id Number
            if (vehicle.getID().equals(vehicleIdNumber)) {
                vehicleDel = vehicle;
            }
        }

        // If recordDel is null, then show error message,
        // otherwise remove the record from Record list
        if (vehicleDel == null) {

            // Displaying no record found
            System.out.println("Invalid vehicle Id");
        } else {

            listVehicle.remove(vehicleDel);

            // Display message for successful deletion of
            // record
            System.out.println(
                    "Successfully removed vehicle from the list");
        }
    }

    public void displayVehicle()
    {

        // If record list is empty then
        // print the message below
        if (listVehicle.isEmpty()) {

            // Print statement
            System.out.println("The list has no vehicle\n");
        }
        // Iterating Record list
        // using for each loop
        for (Vehicle vehicle : listVehicle) {
            // Printing the list
            System.out.println(vehicle.toString());
        }
    }

}


