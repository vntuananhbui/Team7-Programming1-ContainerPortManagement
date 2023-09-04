package src.main.java.components.team7ContainerPortManagement.Controller;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TripController {
    private Vehicle vehicle;
    private Port departurePort;
    private Port arrivalPort;
    private double fuelConsumption;

    public TripController(Vehicle vehicle, Port departurePort, Port arrivalPort, double fuelConsumption) {
        this.vehicle = vehicle;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.fuelConsumption = fuelConsumption;
    }

    //    Calculate how much fuel has been used in a day
    public static double calculateFuelUsageForDay(LocalDate targetDate) {
        double totalFuelUsage = 0.0;
        List<ShipJourney> shipJourneys = ShipJourneyParser.parseShipJourney();

        for (ShipJourney journey : shipJourneys) {
            LocalDate departureDate = journey.getDateDeparture().toLocalDate();
            LocalDate arrivalDate = journey.getDateArrival().toLocalDate();

            // Check if the journey started or ended on the target date
            if (departureDate.equals(targetDate) || arrivalDate.equals(targetDate)) {
                totalFuelUsage += journey.getFuelConsumption();
//            } else if (departureDate.isBefore(targetDate) && arrivalDate.isAfter(targetDate)) {
//                // Check if the journey spans the target date
//                totalFuelUsage += journey.getFuelConsumption();
            }
        }

        return totalFuelUsage;
    }

    //    List all the trips in a given day
    public static void displayTripsForDay(LocalDate targetDate) {
        List<ShipJourney> shipJourneys = ShipJourneyParser.parseShipJourney();

        System.out.println("Trips for " + targetDate + ":");

        for (ShipJourney journey : shipJourneys) {
            LocalDate departureDate = journey.getDateDeparture().toLocalDate();
            LocalDate arrivalDate = journey.getDateArrival().toLocalDate();

            // Check if the journey started or ended on the target date
            if (departureDate.equals(targetDate) || arrivalDate.equals(targetDate)) {
                System.out.println(journey);
            } else if (departureDate.isBefore(targetDate) && arrivalDate.isAfter(targetDate)) {
                // Check if the journey spans the target date
                System.out.println(journey);
            }
        }
    }

    //    List all the trips from day A to day B
    public static void displayTripsBetweenDates(LocalDate startDate, LocalDate endDate) {
        List<ShipJourney> shipJourneys = ShipJourneyParser.parseShipJourney();
        System.out.println("Trips between " + startDate + " and " + endDate + ":");

        for (ShipJourney journey : shipJourneys) {
            LocalDate departureDate = journey.getDateDeparture().toLocalDate();
            LocalDate arrivalDate = journey.getDateArrival().toLocalDate();

            // Check if the journey falls within the specified date range
            if ((departureDate.isEqual(startDate) || departureDate.isAfter(startDate)) &&
                    (arrivalDate.isEqual(endDate) || arrivalDate.isBefore(endDate))) {
                System.out.println(journey);
            }
        }
    }
}
