package src.main.java.components.team7ContainerPortManagement.models.entities.Truck;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Vehicle;


public abstract class Truck extends Vehicle {

    public Truck(String numberID, String name, double currentFuel, double carryingCapacity, double fuelCapacity,double fuelConsumtion, Port currentPort) {
        super(numberID, name, currentFuel, carryingCapacity, fuelCapacity,fuelConsumtion, currentPort);
    }


    public double getFuelConsumptionPerKm(Container container) {
        switch (container.getContainerType()) {
            case DRY_STORAGE:
                return 4.6;
            case OPEN_TOP:
            case OPEN_SIDE:
                return 3.2;
            case REFRIGERATED:
                return 5.4;
            case LIQUID:
                return 5.3;
            default:
                throw new IllegalArgumentException("Invalid container type");
        }
    }

    public abstract boolean canLoadContainer(Container container);
}
