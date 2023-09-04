package src.main.java.components.team7ContainerPortManagement.models.interfaces;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

public interface VehicleOperations {
    boolean canLoadContainer(Container container);

    boolean loadContainer(Container container);

    boolean unloadContainer(Container container);

    boolean canMoveTo(Port destination);

    void moveTo(Port destination);

    void refuel(double amount);

    double getFuelConsumptionPerKm(Container container);
}
