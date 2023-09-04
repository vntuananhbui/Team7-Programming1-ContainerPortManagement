package src.main.java.components.team7ContainerPortManagement;

import src.main.java.components.team7ContainerPortManagement.Controller.ShipJourneyParser;

import java.io.IOException;
import java.time.LocalDate;

import static src.main.java.components.team7ContainerPortManagement.Controller.TripController.*;
import static src.main.java.components.team7ContainerPortManagement.view.mainMenu.mainLoop;

public class Main {

    public static void main(String[] args) throws IOException {
//        mainLoop();

        // Testing for TripController.calculateFuelUsageForDay() method
        LocalDate targetDate = LocalDate.of(2023, 9, 2);
        double fuelUsage = calculateFuelUsageForDay(targetDate);
        System.out.println("Fuel usage for " + targetDate + ": " + fuelUsage);

        //Testing for TripController.displayTripsForDay() method
//        LocalDate targetDate = LocalDate.of(2023, 9, 2);
//        displayTripsForDay(targetDate);

        //Testing for TripController.displayTripsBetweenDates
//        LocalDate startDate = LocalDate.of(2023, 9, 2);
//        LocalDate endDate = LocalDate.of(2023, 9, 3);
//        displayTripsBetweenDates(startDate, endDate);

        //Testing for ShipJourneyParser
//        ShipJourneyParser.parseShipJourney();
    }

}//End bracket
