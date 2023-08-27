package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtility {
    // Writes to port.txt
    String portFile = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port.txt";
    String vehicleFile = "src/main/java/components/team7ContainerPortManagement/resource/data/vehicleData/vehicle.txt";
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
            writeToPortFile();
            writeToVehicleFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
