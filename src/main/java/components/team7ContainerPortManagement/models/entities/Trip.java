package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.enums.TripStatus;

import java.time.LocalDateTime;

public class Trip {
    private Vehicle vehicle;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private TripStatus status;

    // Constructor, getters, and setters

    public Trip(Vehicle vehicle, Port departurePort, Port arrivalPort) {
        this.vehicle = vehicle;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = TripStatus.PLANNED;
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
        this.status = TripStatus.COMPLETED;
    }
}




