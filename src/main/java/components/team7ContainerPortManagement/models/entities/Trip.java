package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.enums.TripStatus;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Trip {
    private Vehicle vehicle;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private TripStatus status;
    private static List<Trip> tripHistory = new ArrayList<>();
    // Constructor, getters, and setters

    public Trip(Vehicle vehicle, Port departurePort, Port arrivalPort) {
        this.vehicle = vehicle;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = TripStatus.PLANNED;
        tripHistory.add(this);

    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public LocalDateTime getDepartureDate() {
        return departureDate;
    }

    public LocalDateTime getArrivalDate() {
        return arrivalDate;
    }

    public Port getDeparturePort() {
        return departurePort;
    }

    public Port getArrivalPort() {
        return arrivalPort;
    }

    public TripStatus getStatus() {
        return status;
    }

    public void start() {
        this.departureDate = LocalDateTime.now();
        this.status = TripStatus.IN_PROGRESS;
    }

    public void complete() {
        this.arrivalDate = LocalDateTime.now();
//        this.status = TripStatus.COMPLETED;
        if (status != TripStatus.IN_PROGRESS) {
            System.out.println("This trip is not start yet!");
        }
        Duration tripDuration;
        switch(departurePort.getID()) {
            case "p-1": tripDuration = Duration.ofDays(2); break;
            case "p-2": tripDuration = Duration.ofDays(4); break;
            case "p-3": tripDuration = Duration.ofDays(6); break;
            case "p-4": tripDuration = Duration.ofDays(8); break;
            case "p-5": tripDuration = Duration.ofDays(10); break;
            default: tripDuration = Duration.ofDays(1); break;
        }

        this.arrivalDate = arrivalDate.plus(tripDuration);
        this.status = TripStatus.COMPLETED;


    }

    public static void displayTrips() {
        System.out.println("Trip History:");
        for (Trip trip : tripHistory) {
            System.out.println("----------------------------");
            System.out.println("Vehicle ID: " + trip.getVehicle().getID());
            System.out.println("Vehicle Name: " + trip.getVehicle().getName());
            System.out.println("Starting Port: " + trip.getDeparturePort().getName());
            System.out.println("Destination Port: " + trip.getArrivalPort().getName());
            System.out.println("Start Time: " + trip.getDepartureDate());
            System.out.println("End Time: " + trip.getArrivalDate());
        }
        System.out.println("----------------------------");
    }

    @Override
    public String toString() {

        return "\n" +"Trip{" +
                "vehicle=" + vehicle.getName() + "\n" +
                ", departureDate=" + departureDate +"\n" +
                ", arrivalDate=" + arrivalDate +"\n" +
                ", departurePort=" + departurePort.getName() +"\n" +
                ", arrivalPort=" + arrivalPort.getName() +"\n" +
                ", status=" + status.name() +
                '}';
    }
}




