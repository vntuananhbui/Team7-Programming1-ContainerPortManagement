package src.main.java.components.team7ContainerPortManagement.models.entities;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static src.main.java.components.team7ContainerPortManagement.Controller.UserController.PortManagerController.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.portController.savePortsToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.PortFileUtils.portReadFile.readPortsFromFile;


public class CapacityPrice {
    private int capacity;
    private double price;

    public CapacityPrice(int capacity, double price) {
        this.capacity = capacity;
        this.price = price;
    }

    public int getCapacity() {
        return capacity;
    }

    public double getPrice() {
        return price;
    }

    public static List<CapacityPrice> loadCapacityPrices(String filePath) {
        List<CapacityPrice> capacityPrices = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Split each line into capacity and price using a comma as the delimiter
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    try {
                        int capacity = Integer.parseInt(parts[0].trim());
                        double price = Double.parseDouble(parts[1].trim());

                        // Create a new CapacityPrice object and add it to the list
                        CapacityPrice capacityPrice = new CapacityPrice(capacity, price);
                        capacityPrices.add(capacityPrice);
                    } catch (NumberFormatException e) {
                        // Handle parsing errors
                        System.err.println("Error parsing capacity and price: " + line);
                    }
                } else {
                    System.err.println("Invalid format: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return capacityPrices;
    }

    public static void displayCapacityOptions(List<CapacityPrice> capacityPrices) {
        System.out.println("Available Capacity Options:");
        for (int i = 0; i < capacityPrices.size(); i++) {
            CapacityPrice cp = capacityPrices.get(i);
            System.out.println(i + 1 + ". " + cp.getCapacity() + " tons : $" + cp.getPrice());
        }
    }

    public static void upgradePortCapacity(Port portToUpdate, String userName) throws IOException {
        String ANSI_RESET = "\u001B[0m";
        String ANSI_GREEN = "\u001B[32m";
        String ANSI_BLUE = "\u001B[34m";
        String ANSI_CYAN = "\u001B[36m";
        String ANSI_RED = "\u001B[31m";
        String yellow = "\u001B[33m";
        String reset = "\u001B[0m";
        // Load capacity and price mappings from the file
        List<CapacityPrice> capacityPrices = loadCapacityPrices("src/main/java/components/team7ContainerPortManagement/resource/data/portData/capacity_cash.txt");
        List<Port> ports = readPortsFromFile("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt");
        List<User> userList = loadUsers();
        // Display capacity options to the user
        displayCapacityOptions(capacityPrices);
        for (int i = 0; i < ports.size(); i++) {
            if (ports.get(i).getID().equals(portToUpdate.getID())) {
                ports.set(i, portToUpdate);
                break;
            }
        }
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of the capacity to upgrade (or 0 to cancel): ");
        int choice = scanner.nextInt();

        if (choice >= 1 && choice <= capacityPrices.size()) {
            CapacityPrice selectedCapacityPrice = capacityPrices.get(choice - 1);
            double price = selectedCapacityPrice.getPrice();

            // Check if the user has enough cash
            User currentUser = findUserByUsername(userName);
            assert currentUser != null;
            if (currentUser.getAmountCash() >= price) {
                // Deduct the price from the user's cash
                System.out.println("Debug price :" + price);
                // Update the port's capacity
                portToUpdate.setStoringCapacity(portToUpdate.getStoringCapacity() + selectedCapacityPrice.getCapacity());
                removeCashToUser(currentUser.getUsername(), price);

                // Save the updated port and user data
                savePortsToFile(ports);

                System.out.println("Port capacity upgraded successfully!");
                System.out.println(ANSI_CYAN + "╔════════════════════════════════════════════════════════╗");
                System.out.println("╟" + ANSI_CYAN + "                 UPDATE PORT SUCCESSFULLY" + "               ║");
                System.out.println("╟────────────────────────────────────────────────────────╢" + ANSI_RESET);
                System.out.println("   Port Capacity After Update: " + portToUpdate.getStoringCapacity());
                System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET);
                System.out.println(ANSI_CYAN + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
            } else {
                System.out.println("Insufficient funds to upgrade capacity.");
                System.out.println("Port capacity upgraded successfully!");
                System.out.println(ANSI_RED + "╔════════════════════════════════════════════════════════╗");
                System.out.println("╟" + ANSI_RED + "                 UPDATE PORT UNSUCCESSFULLY" + "             ║");
                System.out.println("╟────────────────────────────────────────────────────────╢" + ANSI_RESET);
                System.out.println("      Your Account Not Have Enough Cash To Upgrade ");
                System.out.println(yellow + "                       ★ ★ ★ ★ ★" + ANSI_RESET);
                System.out.println(ANSI_RED + "╚════════════════════════════════════════════════════════╝" + ANSI_RESET);
                System.out.println("Press any key to return...");
                scanner.next();
            }
        } else if (choice != 0) {
            System.out.println("Invalid choice.");
        }
    }

    public static void manageCapacityPrices() {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/capacity_cash.txt";
        List<CapacityPrice> entries = loadCapacityPrices(filePath);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Capacity Price Management Menu:");
            System.out.println("1. View Capacity and Price Entries");
            System.out.println("2. Add New Entry");
            System.out.println("3. Update Entry");
            System.out.println("4. Remove Entry");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

            switch (choice) {
                case 1:
                    viewCapacityPriceEntries(entries);
                    System.out.println("Press any key to return...");
                    scanner.next();
                    break;
                case 2:
                    addNewCapacityPriceEntry(entries, scanner);
                    break;
                case 3:
                    updateCapacityPriceEntry(entries, scanner);
                    break;
                case 4:
                    removeCapacityPriceEntry(entries, scanner);
                    break;
                case 5:
                    saveCapacityPrices(filePath, entries);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public static void viewCapacityPriceEntries(List<CapacityPrice> entries) {
        System.out.println("Current Capacity and Price Entries:");
        for (CapacityPrice entry : entries) {
            System.out.println("Capacity: " + entry.getCapacity() + " tons, Price: $" + entry.getPrice());
        }
    }

    public static void addNewCapacityPriceEntry(List<CapacityPrice> entries, Scanner scanner) {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/capacity_cash.txt";
        System.out.print("Enter Capacity (tons): ");
        int capacity = scanner.nextInt();
        System.out.print("Enter Price ($): ");
        double price = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline

        CapacityPrice newEntry = new CapacityPrice(capacity, price);
        entries.add(newEntry);
        saveCapacityPrices(filePath,entries);
        System.out.println("New entry added.");
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public static void updateCapacityPriceEntry(List<CapacityPrice> entries, Scanner scanner) {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/capacity_cash.txt";
        System.out.print("Enter Capacity to update (tons): ");
        int capacityToUpdate = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        CapacityPrice existingEntry = findCapacityPriceEntry(entries, capacityToUpdate);

        if (existingEntry != null) {
            System.out.print("Enter new Price ($): ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline

            existingEntry.setPrice(newPrice);
            saveCapacityPrices(filePath,entries);
            System.out.println("Entry updated.");
        } else {
            System.out.println("Entry not found.");
        }
    }

    public static void removeCapacityPriceEntry(List<CapacityPrice> entries, Scanner scanner) {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/capacity_cash.txt";
        System.out.print("Enter Capacity to remove (tons): ");
        int capacityToRemove = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        CapacityPrice existingEntry = findCapacityPriceEntry(entries, capacityToRemove);

        if (existingEntry != null) {
            entries.remove(existingEntry);
            System.out.println("Entry removed.");
            removeCapacityPriceFromFile(capacityToRemove,filePath);
        } else {
            System.out.println("Entry not found.");
        }
    }

    public static void removeCapacityPriceFromFile(int capacityToRemove, String filePath) {
        List<String> lines = new ArrayList<>();

        // Read the file and store lines in a list
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length > 0) {
                    int capacity = Integer.parseInt(parts[0].trim());
                    if (capacity != capacityToRemove) {
                        lines.add(line);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Write the updated lines back to the file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String line : lines) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static CapacityPrice findCapacityPriceEntry(List<CapacityPrice> entries, int capacity) {
        for (CapacityPrice entry : entries) {
            if (entry.getCapacity() == capacity) {
                return entry;
            }
        }
        return null;
    }

    public static void saveCapacityPrices(String filePath, List<CapacityPrice> entries) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (CapacityPrice entry : entries) {
                writer.write(entry.getCapacity() + ", " + entry.getPrice());
                writer.newLine();
            }
            System.out.println("Capacity and Price entries saved to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

