package src.main.java.components.team7ContainerPortManagement.Controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class ShipJourney {
    private String portID;
    private LocalDateTime dateDeparture;
    private LocalDateTime dateArrival;
    private String portDeparture;
    private String portArrival;
    private String status;
    private double fuelConsumption;

    public ShipJourney(String portID, LocalDateTime dateDeparture, LocalDateTime dateArrival, String portDeparture, String portArrival, String status, double fuelConsumption) {
        this.portID = portID;
        this.dateDeparture = dateDeparture;
        this.dateArrival = dateArrival;
        this.portDeparture = portDeparture;
        this.portArrival = portArrival;
        this.status = status;
        this.fuelConsumption = fuelConsumption;
    }

    // Getters for all attributes (you can generate them in your IDE)


    public String getPortID() {
        return portID;
    }

    public LocalDateTime getDateDeparture() {
        return dateDeparture;
    }

    public LocalDateTime getDateArrival() {
        return dateArrival;
    }

    public String getPortDeparture() {
        return portDeparture;
    }

    public String getPortArrival() {
        return portArrival;
    }

    public String getStatus() {
        return status;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    @Override
    public String toString() {
        return "ShipJourney{" +
                "portID='" + portID + '\'' +
                ", dateDeparture=" + dateDeparture +
                ", dateArrival=" + dateArrival +
                ", portDeparture='" + portDeparture + '\'' +
                ", portArrival='" + portArrival + '\'' +
                ", status='" + status + '\'' +
                ", fuelConsumption=" + fuelConsumption +
                '}';
    }
}

public class ShipJourneyParser {
    public static List<ShipJourney> parseShipJourney() {
        String filePath = "src/main/java/components/team7ContainerPortManagement/resource/data/tripData/trip.txt";
        List<ShipJourney> shipJourneys = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                String portID = parts[0];
                LocalDateTime dateDeparture = LocalDateTime.parse(parts[1]);
                LocalDateTime dateArrival = LocalDateTime.parse(parts[2]);
                String portDeparture = parts[3];
                String portArrival = parts[4];
                String status = parts[5];
                double fuelConsumption = Double.parseDouble(parts[6]);

                ShipJourney journey = new ShipJourney(portID, dateDeparture, dateArrival, portDeparture, portArrival, status, fuelConsumption);
                shipJourneys.add(journey);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now, shipJourneys list contains objects representing ship journeys
        // You can access and work with the data as needed
//        for (ShipJourney journey : shipJourneys) {
//            System.out.println(journey);
//        }

        return shipJourneys;
    }
}
