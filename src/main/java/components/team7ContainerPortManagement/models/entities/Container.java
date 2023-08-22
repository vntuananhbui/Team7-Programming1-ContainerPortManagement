package src.main.java.components.team7ContainerPortManagement.models.entities;

import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

public class Container {
    private final String ID;
    private final double weight;

    public void setContainerType(ContainerType containerType) {
        this.containerType = containerType;
    }

    private ContainerType containerType;  // Use the enum instead of String
    private boolean isLoaded;
    private Port port;  // Add this line
    // Constructors, getters, setters and other methods here...
    public Container(String ID, double weight, ContainerType containerType) {
        this.ID = "c-" + ID;
        this.weight = weight;
        this.containerType = containerType;
        this.isLoaded = false; // Initialize isLoaded to false

    }
    public String getID() {
        return ID;
    }

    public double getWeight() {
        return weight;
    }
    public ContainerType getContainerType() {
        return containerType;
    }
    public boolean isLoaded() {
        return this.isLoaded;
    }


    public void setLoaded(boolean isLoaded) {
        this.isLoaded = isLoaded;
    }
    public Port getPort() {
        return port;
    }

    public void setPort(Port port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return "Container{" +
                "ID='" + ID + '\'' +
                ", weight=" + weight +
                ", containerType=" + containerType +
                ", isLoaded=" + isLoaded +
                ", port=" + port +
                '}';
    }

    public void printPortName() {
        if (this.port != null) {
            System.out.println("Container belongs to port: " + this.port.getName());
        } else {
            System.out.println("Container does not belong to any port");
        }
    }
//    public double fuelConsumption(boolean isShip) {
//        double rate = 0;
//        switch (containerType) {
//            case DRY_STORAGE:
//                //If Ship => 1st value, else second value
//                rate = isShip ? 3.5 : 4.6;
//                break;
//            case OPEN_TOP:
//                rate = isShip ? 2.8 : 3.2;
//                break;
//            case OPEN_SIDE:
//                rate = isShip ? 2.7 : 3.2;
//                break;
//            case REFRIGERATED:
//                rate = isShip ? 4.5 : 5.4;
//                break;
//            case LIQUID:
//                rate = isShip ? 4.8 : 5.3;
//                break;
//        }
//        return rate * weight;
//    }



}
