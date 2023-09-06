package src.main.java.components.team7ContainerPortManagement.Controller.Operation;

import src.main.java.components.team7ContainerPortManagement.models.entities.Container;
import src.main.java.components.team7ContainerPortManagement.models.entities.Port;
import src.main.java.components.team7ContainerPortManagement.models.entities.Ship;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;
import src.main.java.components.team7ContainerPortManagement.models.interfaces.VehicleOperations;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Math.round;
import static src.main.java.components.team7ContainerPortManagement.Controller.Operation.calculateOperation.calculateTotalWeightContainerPort;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.basictruckController.*;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.getReeferTruckByLine;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.reefertruckController.getReeferTruckLineByreefertruckID;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.getShipByLine;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.shipController.getShipLineByShipID;
import static src.main.java.components.team7ContainerPortManagement.Controller.VehicleController.tankertruckController.*;
import static src.main.java.components.team7ContainerPortManagement.models.entities.Port.getContainerIDInPort;
import static src.main.java.components.team7ContainerPortManagement.utils.BasicTruckFileUtils.basictruckReadFile.getBasicTruckTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.BasicTruckFileUtils.basictruckReadFile.readBasicTruckFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.BasicTruckFileUtils.basictruckWriteFile.writeBasicTruckToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerReadFile.*;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeContainersToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ContainerFileUtils.containerWriteFile.writeVehicleContainerMapToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ReeferTruckFileUtils.reefertruckReadFile.getReeferTruckTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.ReeferTruckFileUtils.reefertruckReadFile.readReeferTruckFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ReeferTruckFileUtils.reefertruckWriteFile.writeReeferTruckToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipReadFile.getShipTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipReadFile.readShipFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.ShipFileUtils.shipWriteFile.writeShipToFile;
import static src.main.java.components.team7ContainerPortManagement.utils.TankerTruckFileUtils.tankertruckReadFile.getTankerTruckTotalContainerWeight;
import static src.main.java.components.team7ContainerPortManagement.utils.TankerTruckFileUtils.tankertruckReadFile.readTankerTruckFromFile;
import static src.main.java.components.team7ContainerPortManagement.utils.TankerTruckFileUtils.tankertruckWriteFile.writeTankerTruckToFile;

public class loadAndunloadContainer implements VehicleOperations {
    //vehicle Load container



    //========================


    //===================================================================================================================

    //======================================================================================








    //vehicle unload Container









    @Override
    public boolean loadContainer(Container container) {
        return false;
    }

    @Override
    public boolean unloadContainer(Container container) {
        return false;
    }

    @Override
    public boolean canMoveTo(Port destination) {
        return false;
    }



}
