package src.main.java.components.team7ContainerPortManagement.controllers;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Scanner;

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
        try {
            Scanner fileScanner = new Scanner(new File("./src/main/java/components/team7ContainerPortManagement/resources/data/user.txt"));

            while (fileScanner.hasNext()) {
                String line = fileScanner.nextLine();
                String[] values = line.split(","); // Split each value in each line by comma
                if (values[1].equals(username) && values[2].equals(User.hashing(password))&& values[3].equalsIgnoreCase("port manager"))
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
}
