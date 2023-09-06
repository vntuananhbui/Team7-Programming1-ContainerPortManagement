# Team7-Programming1-ContainerPortManagement

# Project file
UML DRIAGRAM: https://drive.google.com/file/d/1XQal3Jv4b2wrsqM0Hp8xhQUr6fKyB86r/view?usp=sharing
<br />
<hr>
- Project start date: 12/08/2023
<br />
- Project end date: 

## Contribution

| Student Name      | Student ID | Contribution Score |
| :---------------- | :--------- | :----------------: |
| Bui Tuan Anh      | S-3970375  |         20         |


## Project Structure






## About:
This is an assignment project for COSC2081 Programming 1 offered at RMIT University Vietnam during Semester 2023B.

- Campus: Saigon South (SGS), Vietnam

- Lecturer: Mr. Minh Thanh Vu

### Project detail: 
</br>
Container Port Management
</br>
As the global economy develops steadily, the amount of traffic between container ports is adding
pressure to the ports to increase capacity. Besides physical expansion, the alternative solution to
increasing capacity is via increased port performance. Therefore, a need for a digital management
system is required to replace the traditional paper-based system.
For this project, we will implement a simulation of a port management system. The main objective
is to document the traffic of the vehicles between ports. The system can also manage
vehicles/ports/containers and keep track of all the history of the vehicles in the ports. All these
tasks can be executed only by the persons in charge.

#### Port Management System

In this project, with Lazada's permission, you are working on a simplified and slightly modified version of the above system.

### Technologies used:

InteliJ

### Supporting tools used

- 


## Build


```
.
└── Team7-Programming1-ContainerPortManagement/
    └── src/
        └── main/
            └── java/
                └── components/
                    └── team7ContainerPortManagement/
                        ├── Controller/
                        │   ├── Operation/
                        │   │   └── vehicleOperation.java
                        │   ├── VehicleController/
                        │   │   ├── basictruckContainer.java
                        │   │   ├── reefertruckContainer.java
                        │   │   ├── shipController.java
                        │   │   └── tankertruckController.java
                        │   ├── containerController.java
                        │   └── portController.java
                        └── models/
                            ├── entities/
                            │   ├── Trucks/
                            │   │   ├── BaicTruck.java
                            │   │   ├── ReeferTrucks.java
                            │   │   ├── TankerTruck.java
                            │   │   └── Truck.java
                            │   ├── Container.java
                            │   ├── Port.java
                            │   ├── Ship.java
                            │   ├── Trip.java
                            │   └── Vehicle.java
                            ├── enums/
                            │   ├── ContainerType.java
                            │   └── TripStatus.java
                            ├── interfaces/
                            │   ├── PortOperation.java
                            │   └── VehicleOperations.java
                            └── resource/
                                └── data/
                                    ├── containerData/
                                    │   └── container.txt
                                    ├── portData/
                                    │   ├── port.txt
                                    │   ├── port_container.txt
                                    │   └── port_vehicles.txt
                                    ├── vehicleData/
                                    │   ├── vehicle.txt
                                    │   └── vehicle_containerLoad.txt
                                    └── utils/
                                        ├── ContainerFileUtils/
                                        │   ├── containerReadFile.java
                                        │   └── containerWriteFile.java
                                        ├── PortFileUtils/
                                        │   ├── portReadFile.java
                                        │   └── portWriteFile.java
                                        ├── ShipFileUtils/
                                        │   ├── shipReadFile.java
                                        │   └── shipWriteFile.java
                                        ├── view/
                                        │   └── mainmenu.java
                                        └── Main.java

```
## License

This software is licensed under the MIT License ©

