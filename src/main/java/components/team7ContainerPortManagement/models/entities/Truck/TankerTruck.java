package src.main.java.components.team7ContainerPortManagement.models.entities.Truck;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;


public class TankerTruck extends Truck{

    public TankerTruck(String numberID, String name, double currentFuel, double carryingCapacity, double fuelCapacity,double fuelConsumtion, Port currentPort) {
        super(numberID, name, currentFuel, carryingCapacity, fuelCapacity,fuelConsumtion, currentPort);
    }

    @Override
    public boolean canLoadContainer(Container container) {
        ContainerType containerType = container.getContainerType();
        return containerType == ContainerType.LIQUID;
    }

    @Override
    public boolean loadContainer(Container container) {
        return super.loadContainer(container);
    }
}
