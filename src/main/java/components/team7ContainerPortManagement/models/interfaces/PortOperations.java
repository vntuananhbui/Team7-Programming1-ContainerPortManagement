package src.main.java.components.team7ContainerPortManagement.models.interfaces;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;

import java.io.IOException;

public interface PortOperations {
    // Calculate distance to another port
    double calculateDistanceTo(Port anotherPort);

    boolean addVehicle(Vehicle vehicle);

    boolean removeVehicle(Vehicle vehicle);

    String toStringAdd();

    // Check if port is available for landing for a specific vehicle (e.g., truck)
    boolean hasLandingAbility();

    // Calculate total container weight
    double calculateTotalContainerWeight();
    // Add a container to the port
    boolean addContainer(Container container);
    boolean canAddContainer(Container container);
    // Remove a container from the port
    boolean removeContainer(Container container);



}
