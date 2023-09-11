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
                "Port{ID='p-HCM', name='Ho Chi Minh Port', latitude=222.200000, longitude=92.500000, storingCapacity=20000, landingAbility=true}\n" + "Port{ID='p-DN', name='Da Nang Port', latitude=980.800000, longitude=600.800000, storingCapacity=154000, landingAbility=false}\n" + "Port{ID='p-HP', name='Hai Phong Port', latitude=1080.800000, longitude=760.800000, storingCapacity=114000, landingAbility=true}\n" + "Port{ID='p-KG', name='Kien Giang Port', latitude=808.800000, longitude=660.600000, storingCapacity=111400, landingAbility=true}\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(portFile, false))) {
            writer.write(content);
        }
    }


    // Writes to vehicle.txt
    private void writeToVehicleFile() throws IOException {
        String content = "Vehicle{ID='sh-BxW9', name='ShipStart', currentFuel=150000.0, carryingCapacity=10000.0, fuelCapacity=150000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='btr-p7k3', name='BTruckStart', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='ttr-QnFO', name='TTruckStart', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='rtr-tK1Z', name='BTruckStart', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='sh-6SGz', name='BigShipStart', currentFuel=170000.0, carryingCapacity=15000.0, fuelCapacity=170000.0, currentPort=p-StartPort}\n" +
                "Vehicle{ID='sh-bcgw', name='ShipHCM', currentFuel=150000.0, carryingCapacity=10000.0, fuelCapacity=150000.0, currentPort=p-HCM}\n" +
                "Vehicle{ID='sh-UyAv', name='BigShipHCM', currentFuel=170000.0, carryingCapacity=15000.0, fuelCapacity=170000.0, currentPort=p-HCM}\n" +
                "Vehicle{ID='btr-oyeM', name='BTruckHCM', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-HCM}\n" +
                "Vehicle{ID='ttr-Ipjr', name='TTruckHCM', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-HCM}\n" +
                "Vehicle{ID='rtr-rIOb', name='RTruckHCM', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-HCM}\n" +
                "Vehicle{ID='sh-yFNi', name='ShipDN', currentFuel=150000.0, carryingCapacity=10000.0, fuelCapacity=150000.0, currentPort=p-DN}\n" +
                "Vehicle{ID='sh-9Aad', name='BigShipDN', currentFuel=170000.0, carryingCapacity=15000.0, fuelCapacity=170000.0, currentPort=p-DN}\n" +
                "Vehicle{ID='btr-DTCQ', name='BTruckDN', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-DN}\n" +
                "Vehicle{ID='ttr-ySNy', name='TTruckDN', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-DN}\n" +
                "Vehicle{ID='rtr-stmf', name='RTruckDN', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-DN}\n" +
                "Vehicle{ID='sh-2pr3', name='ShipHP', currentFuel=100000.0, carryingCapacity=10000.0, fuelCapacity=100000.0, currentPort=p-HP}\n" +
                "Vehicle{ID='sh-DEpg', name='BigShipHP', currentFuel=170000.0, carryingCapacity=15000.0, fuelCapacity=170000.0, currentPort=p-HP}\n" +
                "Vehicle{ID='btr-3yiG', name='BTruckHP', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-HP}\n" +
                "Vehicle{ID='ttr-hT1s', name='TTruckHP', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-HP}\n" +
                "Vehicle{ID='rtr-yIna', name='RTruckHP', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-HP}\n" +
                "Vehicle{ID='sh-1rZN', name='ShipKG', currentFuel=150000.0, carryingCapacity=10000.0, fuelCapacity=150000.0, currentPort=p-KG}\n" +
                "Vehicle{ID='btr-kSFX', name='BTruckKG', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-KG}\n" +
                "Vehicle{ID='ttr-1NQq', name='TTruckKG', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-KG}\n" +
                "Vehicle{ID='rtr-gdys', name='RTruckKG', currentFuel=100000.0, carryingCapacity=7000.0, fuelCapacity=100000.0, currentPort=p-KG}\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(vehicleFile, false))) {
            writer.write(content);
        }
    }
    private void writeToportvehicleFile() throws IOException {
        String content = "{Port :p-StartPort, Vehicles: sh-BxW9, btr-p7k3, ttr-QnFO, rtr-tK1Z, sh-6SGz}\n" +
                "{Port :p-HCM, Vehicles: sh-bcgw, sh-UyAv, btr-oyeM, ttr-Ipjr, rtr-rIOb}\n" +
                "{Port :p-DN, Vehicles: sh-yFNi, sh-9Aad, btr-DTCQ, ttr-ySNy, rtr-stmf}\n" +
                "{Port :p-HP, Vehicles: sh-2pr3, sh-DEpg, btr-3yiG, ttr-hT1s, rtr-yIna}\n" +
                "{Port :p-KG, Vehicles: sh-1rZN, btr-kSFX, ttr-1NQq, rtr-gdys}\n";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(port_vehicleFile, false))) {
            writer.write(content);
        }
    }
    private void writeTocontainerFile() throws IOException {
        String content = "Container{ID='c-bxDh', weight=3000.0, containerType=DRY_STORAGE, isLoaded=false, port=Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=12.900000, storingCapacity=500000, landingAbility=true}}\n" +
                "Container{ID='c-R8dQ', weight=2500.0, containerType=OPEN_TOP, isLoaded=false, port=Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=12.900000, storingCapacity=500000, landingAbility=true}}\n" +
                "Container{ID='c-1u5g', weight=4100.0, containerType=OPEN_SIDE, isLoaded=false, port=Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=12.900000, storingCapacity=500000, landingAbility=true}}\n" +
                "Container{ID='c-mET1', weight=1800.0, containerType=REFRIGERATED, isLoaded=false, port=Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=12.900000, storingCapacity=500000, landingAbility=true}}\n" +
                "Container{ID='c-VV3b', weight=3200.0, containerType=LIQUID, isLoaded=false, port=Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=12.900000, storingCapacity=500000, landingAbility=true}}\n" +
                "Container{ID='c-YqMK', weight=3250.0, containerType=DRY_STORAGE, isLoaded=false, port=Port{ID='p-HCM', name='Ho Chi Minh Port', latitude=222.200000, longitude=2.500000, storingCapacity=20000, landingAbility=true}}\n" +
                "Container{ID='c-qmhB', weight=2870.0, containerType=OPEN_TOP, isLoaded=false, port=Port{ID='p-HCM', name='Ho Chi Minh Port', latitude=222.200000, longitude=2.500000, storingCapacity=20000, landingAbility=true}}\n" +
                "Container{ID='c-wlet', weight=4010.0, containerType=OPEN_SIDE, isLoaded=false, port=Port{ID='p-HCM', name='Ho Chi Minh Port', latitude=222.200000, longitude=2.500000, storingCapacity=20000, landingAbility=true}}\n" +
                "Container{ID='c-Qocy', weight=1950.0, containerType=REFRIGERATED, isLoaded=false, port=Port{ID='p-HCM', name='Ho Chi Minh Port', latitude=222.200000, longitude=2.500000, storingCapacity=20000, landingAbility=true}}\n" +
                "Container{ID='c-hSuu', weight=3015.0, containerType=LIQUID, isLoaded=false, port=Port{ID='p-HCM', name='Ho Chi Minh Port', latitude=222.200000, longitude=2.500000, storingCapacity=20000, landingAbility=true}}\n" +
                "Container{ID='c-up4N', weight=4500.0, containerType=DRY_STORAGE, isLoaded=false, port=Port{ID='p-DN', name='Da Nang Port', latitude=980.800000, longitude=0.800000, storingCapacity=154000, landingAbility=false}}\n" +
                "Container{ID='c-Et47', weight=2100.0, containerType=OPEN_TOP, isLoaded=false, port=Port{ID='p-DN', name='Da Nang Port', latitude=980.800000, longitude=0.800000, storingCapacity=154000, landingAbility=false}}\n" +
                "Container{ID='c-oyWy', weight=2950.0, containerType=OPEN_SIDE, isLoaded=false, port=Port{ID='p-DN', name='Da Nang Port', latitude=980.800000, longitude=0.800000, storingCapacity=154000, landingAbility=false}}\n" +
                "Container{ID='c-PRx0', weight=1000.0, containerType=REFRIGERATED, isLoaded=false, port=Port{ID='p-DN', name='Da Nang Port', latitude=980.800000, longitude=0.800000, storingCapacity=154000, landingAbility=false}}\n" +
                "Container{ID='c-7rJx', weight=500.0, containerType=LIQUID, isLoaded=false, port=Port{ID='p-DN', name='Da Nang Port', latitude=980.800000, longitude=0.800000, storingCapacity=154000, landingAbility=false}}\n" +
                "Container{ID='c-5Y0r', weight=4000.0, containerType=DRY_STORAGE, isLoaded=false, port=Port{ID='p-HP', name='Hai Phong Port', latitude=1080.800000, longitude=60.800000, storingCapacity=114000, landingAbility=true}}\n" +
                "Container{ID='c-p2rN', weight=3000.0, containerType=OPEN_TOP, isLoaded=false, port=Port{ID='p-HP', name='Hai Phong Port', latitude=1080.800000, longitude=60.800000, storingCapacity=114000, landingAbility=true}}\n" +
                "Container{ID='c-ZEJo', weight=2000.0, containerType=OPEN_SIDE, isLoaded=false, port=Port{ID='p-HP', name='Hai Phong Port', latitude=1080.800000, longitude=60.800000, storingCapacity=114000, landingAbility=true}}\n" +
                "Container{ID='c-8U7r', weight=1500.0, containerType=REFRIGERATED, isLoaded=false, port=Port{ID='p-HP', name='Hai Phong Port', latitude=1080.800000, longitude=60.800000, storingCapacity=114000, landingAbility=true}}\n" +
                "Container{ID='c-dg4P', weight=4000.0, containerType=LIQUID, isLoaded=false, port=Port{ID='p-HP', name='Hai Phong Port', latitude=1080.800000, longitude=60.800000, storingCapacity=114000, landingAbility=true}}\n" +
                "Container{ID='c-iExm', weight=4500.0, containerType=DRY_STORAGE, isLoaded=false, port=Port{ID='p-KG', name='Kien Giang Port', latitude=808.800000, longitude=60.600000, storingCapacity=111400, landingAbility=true}}\n" +
                "Container{ID='c-Mkbw', weight=3200.0, containerType=OPEN_TOP, isLoaded=false, port=Port{ID='p-KG', name='Kien Giang Port', latitude=808.800000, longitude=60.600000, storingCapacity=111400, landingAbility=true}}\n" +
                "Container{ID='c-YdQZ', weight=2100.0, containerType=OPEN_SIDE, isLoaded=false, port=Port{ID='p-KG', name='Kien Giang Port', latitude=808.800000, longitude=60.600000, storingCapacity=111400, landingAbility=true}}\n" +
                "Container{ID='c-kcBV', weight=1200.0, containerType=REFRIGERATED, isLoaded=false, port=Port{ID='p-KG', name='Kien Giang Port', latitude=808.800000, longitude=60.600000, storingCapacity=111400, landingAbility=true}}\n" +
                "Container{ID='c-95Qd', weight=3290.0, containerType=LIQUID, isLoaded=false, port=Port{ID='p-KG', name='Kien Giang Port', latitude=808.800000, longitude=60.600000, storingCapacity=111400, landingAbility=true}}\n" +
                "Container{ID='c-qOCv', weight=1000.0, containerType=DRY_STORAGE, isLoaded=false, port=Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=12.900000, storingCapacity=500000, landingAbility=true}}\n" +
                "Container{ID='c-n6mV', weight=2000.0, containerType=OPEN_TOP, isLoaded=false, port=Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=12.900000, storingCapacity=500000, landingAbility=true}}\n" +
                "Container{ID='c-WHuo', weight=3000.0, containerType=OPEN_SIDE, isLoaded=false, port=Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=12.900000, storingCapacity=500000, landingAbility=true}}\n" +
                "Container{ID='c-mNBq', weight=4000.0, containerType=REFRIGERATED, isLoaded=false, port=Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=12.900000, storingCapacity=500000, landingAbility=true}}\n" +
                "Container{ID='c-gUPR', weight=5000.0, containerType=LIQUID, isLoaded=false, port=Port{ID='p-StartPort', name='Start Port', latitude=291.500000, longitude=12.900000, storingCapacity=500000, landingAbility=true}}\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(containerFile, false))) {
            writer.write(content);
        }
    }

    private void writeToportcontainerFile() throws IOException {
        String content = "{Port :p-StartPort, Container: c-bxDh, c-R8dQ, c-1u5g, c-mET1, c-VV3b, c-qOCv, c-n6mV, c-WHuo, c-mNBq, c-gUPR}\n" +
                "{Port :p-HCM, Container: c-YqMK, c-qmhB, c-wlet, c-Qocy, c-hSuu}\n" +
                "{Port :p-DN, Container: c-up4N, c-Et47, c-oyWy, c-PRx0, c-7rJx}\n" +
                "{Port :p-HP, Container: c-5Y0r, c-p2rN, c-ZEJo, c-8U7r, c-dg4P}\n" +
                "{Port :p-KG, Container: c-iExm, c-Mkbw, c-YdQZ, c-kcBV, c-95Qd}\n";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(port_containerFile, false))) {
            writer.write(content);
        }
    }







    // Define a constant port information









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
            writeToportvehicleFile();
            writeTocontainerFile();
            writeToportcontainerFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
