package src.main.java.components.team7ContainerPortManagement.models.entities.Truck;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.Truck;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;


public class BasicTruck extends Truck {

    public BasicTruck(String numberID, String name, double currentFuel, double carryingCapacity, double fuelCapacity,double fuelConsumtion, Port currentPort) {
        super(numberID, name, currentFuel, carryingCapacity, fuelCapacity,fuelConsumtion, currentPort);
    }


    @Override
    public boolean canLoadContainer(Container container) {
        ContainerType containerType = container.getContainerType();
        // Basic truck can only carry dry storage, open top, and open side containers
        if(containerType == ContainerType.OPEN_TOP) {
            System.out.println("The vehicle can load the Open Top container");
            return true;
        }
        if(containerType == ContainerType.DRY_STORAGE) {
            System.out.println("The vehicle can load the DRY_STORAGE container");
            return true;
        }
        if (containerType == ContainerType.OPEN_SIDE) {
            System.out.println("The vehicle can load the OPEN_SIDE container");
            return true;
        }
        System.out.println("The vehicle can not load this container");
        return false;
    }

    public boolean loadContainer(Container container) {
//        if (canLoadContainer(container)) {
//            System.out.println(container.getID() + ", Type: " + container.getContainerType() + " has been added");
//            return true;
//        }
//        System.out.println("The container Type: " + container.getContainerType() + " can not be added");
//
//        return false;
        return super.loadContainer(container);  // Use the implementation from the Vehicle class

    }

}
