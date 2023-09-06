package src.main.java.components.team7ContainerPortManagement.models.interfaces;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

public interface VehicleOperations {
    boolean loadContainer(Container container);
    boolean unloadContainer(Container container);
    boolean canMoveTo(Port destination);

}
