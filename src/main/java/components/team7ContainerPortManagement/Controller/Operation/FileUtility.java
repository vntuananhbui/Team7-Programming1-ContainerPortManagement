package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class FileUtility {
    // Writes to port.txt
    String portFile = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt";
    String vehicleFile = "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt";
    String containerFile = "src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt";
    String port_vehicleFile = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_vehicles.txt";
    String port_containerFile = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt";

    private void writeToPortFile() throws IOException {
        String content = "Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=112.900000, storingCapacity=500000, landingAbility=true}\n" +
                // Add other ports here if needed...
                "Port{ID='p-HCM', name='Ho Chi Minh Port', latitude=222.200000, longitude=92.500000, storingCapacity=20000, landingAbility=true}\n" + "Port{ID='p-DN', name='Da Nang Port', latitude=980.800000, longitude=600.800000, storingCapacity=154000, landingAbility=false}\n" + "Port{ID='p-HP', name='Hai Phong Port', latitude=1080.800000, longitude=760.800000, storingCapacity=114000, landingAbility=true}\n" + "Port{ID='p-KG', name='Kien Giang Port', latitude=808.800000, longitude=660.600000, storingCapacity=111400, landingAbility=true}";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(portFile, false))) {
            writer.write(content);
        }
    }


    // Writes to vehicle.txt
    private void writeToVehicleFile() throws IOException {
        String content = "Vehicle{ID='sh-RMIT1', name='RMIT Ship 1', currentFuel=400000.0, carryingCapacity=80000.0, fuelCapacity=400000.0, currentPort=p-StartPort}\n" +
                // Add other vehicles here...
                "Vehicle{ID='rtr-RMIT2', name='Reefer Truck RMIT 2', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" + "Vehicle{ID='btr-RMIT3', name='Basic Truck RMIT 3', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" + "Vehicle{ID='ttr-RMIT4', name='Tanker Truck RMIT 4', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +"Vehicle{ID='sh-RMIT5', name='Ship RMIT 5', currentFuel=400000.0, carryingCapacity=80000.0, fuelCapacity=400000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='sh-RMIT6', name='Ship RMIT 6', currentFuel=400000.0, carryingCapacity=80000.0, fuelCapacity=400000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='sh-RMIT7', name='Ship RMIT 7', currentFuel=400000.0, carryingCapacity=80000.0, fuelCapacity=400000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='sh-RMIT8', name='Ship RMIT 8', currentFuel=400000.0, carryingCapacity=80000.0, fuelCapacity=400000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='sh-RMIT9', name='Ship RMIT 9', currentFuel=400000.0, carryingCapacity=80000.0, fuelCapacity=400000.0, currentPort=p-StartPort}\n" +

                "Vehicle{ID='ttr-RMIT5', name='Tanker Truck RMIT 5', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='ttr-RMIT6', name='Tanker Truck RMIT 6', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='ttr-RMIT7', name='Tanker Truck RMIT 7', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='ttr-RMIT8', name='Tanker Truck RMIT 8', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='ttr-RMIT9', name='Tanker Truck RMIT 9', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +

                "Vehicle{ID='rtr-RMIT5', name='Reefer Truck RMIT 5', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='rtr-RMIT6', name='Reefer Truck RMIT 6', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='rtr-RMIT7', name='Reefer Truck RMIT 7', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='rtr-RMIT8', name='Reefer Truck RMIT 8', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='rtr-RMIT9', name='Reefer Truck RMIT 9', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +

                "Vehicle{ID='btr-RMIT5', name='Basic Truck RMIT 5', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='btr-RMIT6', name='Basic Truck RMIT 6', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='btr-RMIT7', name='Basic Truck RMIT 7', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='btr-RMIT8', name='Basic Truck RMIT 8', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='btr-RMIT9', name='Basic Truck RMIT 9', currentFuel=200000.0, carryingCapacity=20000.0, fuelCapacity=200000.0, currentPort=p-StartPort}\n"
                ;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vehicleFile, false))) {
            writer.write(content);
        }
    }

    private static final String[] FRUITS = {
            "Apple", "Banana", "Orange", "Mango", "Strawberry", "Pineapple",
            "Grapes", "Cherry", "Peach", "Watermelon", "Lemon", "Kiwi"
    };

    private static final String[] FOODS = {
            "Carrot", "Broccoli", "Potato", "Tomato", "Cucumber", "Eggplant",
            "Lettuce", "Onion", "Pepper", "Spinach", "Cabbage", "Corn"
    };

    private static final String[] CONTAINER_TYPES = {
            "DRY_STORAGE", "OPEN_TOP", "OPEN_SIDE", "REFRIGERATED", "LIQUID"
    };

    private static final Random random = new Random();
    private static int containerCounter = 0; // Maintain a counter for container IDs

    // Define a constant port information
    private static final String PORT_INFO = "port=Port{ID='p-StartPort', name='Start Port'', latitude=291.500000, longitude=112.900000, storingCapacity=500000, landingAbility=true}";


    private static void writeToContainerFile() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/containerData/container.txt", false))) {
            for (String containerType : CONTAINER_TYPES) {
                for (int i = 0; i < 6; i++) {
                    String containerId = "c-" + containerCounter;
                    double weight = generateRandomWeightLessThan2000(); // Generate weight less than 2000
                    boolean isLoaded = false; // Set isLoaded to false

                    String content = String.format("Container{ID='%s', weight=%.1f, containerType=%s, isLoaded=%s, %s}",
                            containerId, weight, containerType, isLoaded, PORT_INFO);

                    writer.write(content);
                    writer.newLine();

                    containerCounter++; // Increment the container counter
                }
            }
        }
    }

    private static double generateRandomWeightLessThan2000() {
        return random.nextDouble() * 2000; // Weight less than 2000
    }

    public static void addLineToPortVehiclesFile() {
        String line = "{Port :p-StartPort, Vehicles: sh-RMIT1, rtr-RMIT2, btr-RMIT3, ttr-RMIT4, sh-RMIT5, sh-RMIT6, sh-RMIT7, sh-RMIT8, sh-RMIT9, ttr-RMIT5, ttr-RMIT6, ttr-RMIT7, ttr-RMIT8, ttr-RMIT9, rtr-RMIT5, rtr-RMIT6, rtr-RMIT7, rtr-RMIT8, rtr-RMIT9, btr-RMIT5, btr-RMIT6, btr-RMIT7, btr-RMIT8, btr-RMIT9}";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_vehicles.txt", false))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addPortContainerFile() {
        String line  = "{Port :p-StartPort, Container: c-0, c-1, c-2, c-3, c-4, c-5, c-6, c-7, c-8, c-9, c-10, c-11, c-12, c-13, c-14, c-15, c-16, c-17, c-18, c-19, c-20, c-21, c-22, c-23, c-24, c-25, c-26, c-27, c-28, c-29}\n\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_containers.txt", false))) {
            writer.write(line);
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // Clears content of a file
    private void clearFileContent(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
            writer.write(""); // Write empty content
        }
    }
    public void writeAllData() {
        try {
            clearFileContent(portFile);
            clearFileContent(vehicleFile);
            clearFileContent(containerFile);
            clearFileContent(port_vehicleFile);
            clearFileContent(port_containerFile);
            writeToPortFile();
            writeToVehicleFile();
            writeToContainerFile();
            addLineToPortVehiclesFile();
            addPortContainerFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
