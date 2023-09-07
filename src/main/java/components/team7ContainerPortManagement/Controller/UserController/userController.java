package src.main.java.components.team7ContainerPortManagement.Controller.UserController;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class userController {
    public static boolean checkPortManagerBelongPort(String portManagerUsername) throws IOException {
        String portManagerAssignmentFileName = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_portmanager.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(portManagerAssignmentFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Remove the '{' character at the beginning and '}' character at the end of the line if they exist
                line = line.replace("{", "").replace("}", "").trim();

                // Split the line into port and port manager parts
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String port = parts[0].trim();
                    String manager = parts[1].trim();
                    if (manager.equals(portManagerUsername)) {
                        // Port manager is associated with this port
                        return true;
                    }
                }
            }
        }

        // Port manager is not associated with any port
        return false;
    }

    // print the port manager belongs to port

    public static void printPortManagerBelongPort(String portManagerUsername) throws IOException {
        String portManagerAssignmentFileName = "src/main/java/components/team7ContainerPortManagement/resource/data/portData/port_portmanager.txt";

        try (BufferedReader br = new BufferedReader(new FileReader(portManagerAssignmentFileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Remove the '{' character at the beginning and '}' character at the end of the line if they exist
                line = line.replace("{", "").replace("}", "").trim();

                // Split the line into port and port manager parts
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String port = parts[0].trim();
                    String manager = parts[1].trim();
                    if (manager.equals(portManagerUsername)) {
                        // Port manager is associated with this port
                        System.out.println(manager + " " + "manages" + " " + port);
                    }
                }
            }
        }

    }
}
