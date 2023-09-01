package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.enums.TripStatus;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Trip {
    private Vehicle vehicle;
    private LocalDateTime departureDate;
    private LocalDateTime arrivalDate;
    private Port departurePort;
    private Port arrivalPort;
    private TripStatus status;
    private double fuelConsumption;
    private static List<Trip> tripHistory = new ArrayList<>();
    // Constructor, getters, and setters

    public Trip(Vehicle vehicle, Port departurePort, Port arrivalPort) {
        this.vehicle = vehicle;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.status = TripStatus.PLANNED;
        tripHistory.add(this);

    }

    public Trip(Vehicle vehicle, Port departurePort, Port arrivalPort, double fuelConsumption) {
        this.vehicle = vehicle;
        this.departurePort = departurePort;
        this.arrivalPort = arrivalPort;
        this.fuelConsumption = fuelConsumption;
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
        // Read the last arrivalDate from the trip.txt file
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/java/components/team7ContainerPortManagement/resource/data/TripData/trip.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts[0].equals(vehicle.getID())) {
                    if (!"null".equals(parts[2])) {
                        this.departureDate = LocalDateTime.parse(parts[2]);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (this.departureDate == null) {
            this.departureDate = LocalDateTime.now();
        }

        this.status = TripStatus.IN_PROGRESS;
    }

    public LocalDateTime tripStart() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the departure date in dd.mm.yyyy format:");

        String userInputDate = scanner.nextLine();

        // Appending the time to the date provided by the user
        String completeDate = userInputDate + " 00:00";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        try {
            this.departureDate = LocalDateTime.parse(completeDate, formatter);
            this.status = TripStatus.IN_PROGRESS;
        } catch (Exception e) {
            System.out.println("Invalid date format! Please use the dd.mm.yyyy format.");
        }
        return departureDate;
    }
    public void complete() {
        this.arrivalDate = LocalDateTime.now();
//        this.status = TripStatus.COMPLETED;
        if (status != TripStatus.IN_PROGRESS) {
            System.out.println("This trip is not start yet!");
        }
        Duration tripDuration;
        switch(departurePort.getID()) {
            case "p-1": tripDuration = Duration.ofHours(3); break;
            case "p-2": tripDuration = Duration.ofHours(6); break;
            case "p-3": tripDuration = Duration.ofHours(8); break;
            case "p-4": tripDuration = Duration.ofHours(10); break;
            case "p-5": tripDuration = Duration.ofHours(12); break;
            default: tripDuration = Duration.ofHours(1); break;
        }

        this.arrivalDate = arrivalDate.plus(tripDuration);
        this.status = TripStatus.COMPLETED;


    }
    public void tripComplete() {
        int tripDuration;
        switch (arrivalPort.getID()) {
            case "p-StartPort":
                tripDuration = 3;
                break;
            case "p-HCM":
                tripDuration = 5;
                break;
            case "p-DN":
                tripDuration = 7;
                break;
            case "p-HP":
                tripDuration = 9;
                break;
            case "p-KG":
                tripDuration = 10;
                break;
            default:
                tripDuration = 1;
        }
        this.arrivalDate = this.departureDate.plusHours(tripDuration);
        this.status = TripStatus.COMPLETED;

        saveTripToFile();
    }
    public void saveTripToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src/main/java/components/team7ContainerPortManagement/resource/data/TripData/trip.txt", true))) {
            writer.write(String.format("%s, %s, %s, %s, %s, %s, %f%n",
                    vehicle.getID(),
                    departureDate,
                    arrivalDate,
                    departurePort.getID(),
                    arrivalPort.getID(),
                    status,
                    fuelConsumption
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

//    @Override
//    public String toString() {
//
//        return "\n" +"Trip{" +
//                "vehicle=" + vehicle.getName() + "\n" +
//                ", departureDate=" + departureDate +"\n" +
//                ", arrivalDate=" + arrivalDate +"\n" +
//                ", departurePort=" + departurePort.getName() +"\n" +
//                ", arrivalPort=" + arrivalPort.getName() +"\n" +
//                ", status=" + status.name() +
//                '}';
//    }
}




