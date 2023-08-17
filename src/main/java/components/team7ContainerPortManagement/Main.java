package src.main.java.components.team7ContainerPortManagement;

import src.main.java.components.team7ContainerPortManagement.models.entities.*;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.BasicTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.ReeferTrucks;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.TankerTruck;
import src.main.java.components.team7ContainerPortManagement.models.entities.Truck.Truck;
import src.main.java.components.team7ContainerPortManagement.models.enums.ContainerType;

public class Main {

    public static void main(String[] args) {
        //Create Port
//        Port port1 = new Port("1","Port 1",18.25,15.28,2000,true);
//        Port port2 = new Port("2", "Port 2", 89.87,87.89,200,true);
//        //Calculate distance
////        System.out.println(port1.calculateDistanceTo(port2) );
//        //Create basic truck
//        Truck basicTruck1 = new BasicTruck("2", "Basic 1", 10, 1000, 100, port1);
////        System.out.println(basicTruck1.getID());
//        Truck tankerTruck1 = new TankerTruck("5", "Tanker truck 1", 500, 500, 1000, port1);
//        Truck referTruck1 = new ReeferTrucks("9", "Refer truck 1", 500, 500, 1000, port1);
//        //Create ship
//        Ship ship1 = new Ship("6","ship1",200,400,400,null);
//
//        //Create container
//        Container RefContainer = new Container("12",200, ContainerType.REFRIGERATED);
//        Container openTopContainer = new Container("99", 1800.0,ContainerType.OPEN_TOP);
//        Container liquidContainer = new Container("72", 900,ContainerType.LIQUID);
//        System.out.println(RefContainer.getContainerType());
//        System.out.println(openTopContainer.getContainerType());
        //Check Load container

//        System.out.println(tankerTruck1.canLoadContainer(RefContainer));
//        System.out.println(tankerTruck1.canLoadContainer(liquidContainer));
//        System.out.println(referTruck1.canLoadContainer(RefContainer));
//        System.out.println(referTruck1.canLoadContainer(liquidContainer));
//        System.out.println(ship1.canLoadContainer(liquidContainer));
//
//        //Load container
//
//        System.out.println(basicTruck1.loadContainer(openTopContainer));
//        System.out.println(basicTruck1.loadContainer(RefContainer));
//        System.out.println(tankerTruck1.loadContainer(openTopContainer));
//        System.out.println(tankerTruck1.loadContainer(liquidContainer));
//        referTruck1.loadContainer(RefContainer);
//        ship1.loadContainer(liquidContainer);
//        ship1.loadContainer(RefContainer);

        //check vehicle load ? container
//        tankerTruck1.showLoadedContainers();
//        ship1.showLoadedContainers();
//        ship1.unloadContainer(liquidContainer);
//        ship1.showLoadedContainers();

//        ship1.showLoadedContainers();
        // add container to port
//        port1.addContainer(liquidContainer);


//        System.out.println(port1.canAddContainer(liquidContainer));
//        port1.addContainer(liquidContainer);
//        port1.addContainer(RefContainer);
//        port1.removeContainer(RefContainer);
//        System.out.println(port1.calculateTotalContainerWeight());


        Port port1 = new Port("p-1", "Port 1", 52.5200, 13.4050, 1000, true);
        Port port2 = new Port("p-2", "Port 2", 48.8566, 2.3522, 800, true);

        // Create vehicles
        Truck basicTruck1 = new BasicTruck("1", "Basic 1", 50000, 500, 100000, port1);
        Truck tankerTruck1 = new TankerTruck("2", "Tanker 1", 50000, 500, 100000, port1);
        Truck reeferTruck1 = new ReeferTrucks("3", "Reefer 1", 50000, 500, 100000, port1);
        Ship ship1 = new Ship("1","Ship 1",30000,500000,2000000,port1);
        // create container
        Container RefContainer = new Container("12",200, ContainerType.REFRIGERATED);
        Container openTopContainer = new Container("99", 1800.0,ContainerType.OPEN_TOP);
        Container liquidContainer = new Container("72", 900,ContainerType.LIQUID);
        //Add container to vehicle
//        basicTruck1.loadContainer(openTopContainer);
//        tankerTruck1.loadContainer(liquidContainer);
        reeferTruck1.loadContainer(RefContainer);

        RefContainer.printPortName();
//        ship1.loadContainer(openTopContainer);
//        System.out.println(port1.getContainers());
//        // Print initial states
////        System.out.println("Initial port of BasicTruck1: " + basicTruck1.getCurrentPort().getName());
////        System.out.println("Initial port of TankerTruck1: " + tankerTruck1.getCurrentPort().getName());
////        System.out.println("Initial port of ReeferTruck1: " + reeferTruck1.getCurrentPort().getName());
////
////        // Move vehicles
//        basicTruck1.moveTo(port2);
//        tankerTruck1.moveTo(port2);
//        reeferTruck1.moveTo(port2);
////
////        // Print final states
//        System.out.println("Final port of BasicTruck1: " + basicTruck1.getCurrentPort().getName());
//        System.out.println("Final port of TankerTruck1: " + tankerTruck1.getCurrentPort().getName());
//        System.out.println("Final port of ReeferTruck1: " + reeferTruck1.getCurrentPort().getName());







    }
}
