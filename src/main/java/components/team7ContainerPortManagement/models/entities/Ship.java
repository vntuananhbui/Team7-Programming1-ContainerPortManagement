package src.main.java.components.team7ContainerPortManagement.models.entities;


public class Ship extends Vehicle{
    public Ship(String numberID, String name, double currentFuel, double carryingCapacity, double fuelCapacity,double fuelConsumtion, Port currentPort) {
        super(numberID, name, currentFuel, carryingCapacity, fuelCapacity,fuelConsumtion, currentPort);
    }

//    @Override
//    public boolean canLoadContainer(Container container) {
//        return true;
//    }

    @Override
    public boolean loadContainer(Container container) {
        return super.loadContainer(container);
    }

    @Override
    public boolean canMoveTo(Port destination) {
        if (!currentPort.hasLandingAbility() && !destination.hasLandingAbility()) {
            // One or both of the ports do not have landing ability, so a truck cannot move between them
            return false;
        }
        return true;
    }



    @Override
    public double getFuelConsumptionPerKm(Container container) {

        switch (container.getContainerType()) {
            case DRY_STORAGE:
                return 3.5;
            case OPEN_TOP:
                return 2.8;
            case OPEN_SIDE:
                return 2.7;
            case REFRIGERATED:
                return 4.5;
            case LIQUID:
                return 4.8;
            default:
                throw new IllegalArgumentException("Invalid container type");
        }
    }

}
