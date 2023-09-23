# Team7-Programming1-ContainerPortManagement

# Project file

GitHub Reppo: https://github.com/vntuananhbui/Team7-Programming1-ContainerPortManagement
<br />
UML DRIAGRAM: [https://drive.google.com/file/d/1XQal3Jv4b2wrsqM0Hp8xhQUr6fKyB86r/view?usp=sharing]
<br />
REPORT: [https://docs.google.com/document/d/1FaGOPAJO7O8iopQ83AJkvImcN2CtLalF50WXY0H32Qw/edit](https://docs.google.com/document/d/15kY-IDkmPn-3F1-Dq_BWMv9Sr43gnpeNLHbsD0oiAPU/edit?usp=sharing)
</br>
Video demonstration link: https://www.youtube.com/watch?v=GjAXfoVEZlY
<br />
<hr>
- Project start date: 12/08/2023
<br />
- Project end date: 24/09/2023

## Contribution

| Student Name      | Student ID | Contribution Score |
| :---------------- | :--------- | :----------------: |
| Bui Tuan Anh      | S-3970375  |         28         |
| Lao Vinh Khang    | S-3891925  |         28         |
| Ton Nu Ngoc Khanh | S-3932105  |         28         |
| Nguyen Tien Dung  | S-3999561  |         16         |



## Project Structure


## About:
This is an assignment project for COSC2081 Programming 1 offered at RMIT University Vietnam during Semester 2023B.

- Campus: Saigon South (SGS), Vietnam

- Lecturer: Mr. Minh Thanh Vu

### Project detail: 
</br>
Container Port Management System
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


### Technologies used:

InteliJ


### Supporting tools used

- Diagram: Draw.io

## Build


```
.
└── Team7-Programming1-src/
    └── src/
        └── main/
            └── java/
                └── components/
                    └── team7ContainerPortManagement/
                        ├── Controller/
                        │   ├── Operation/
                        │   │   ├── calculateOperation.java
                        │   │   ├── FileUtility.java
                        │   │   ├── RouteOptimizaiton.java
                        │   │   ├── StatisticOperation,java
                        │   │   ├── SatisticOperationAdmin.java
                        │   │   ├── tripDateSelector.java
                        │   │   └── VehicleInPort.java
                        │   ├── UserController/
                        │   │   ├── AdminController
                        │   │   ├── PortManagerController
                        │   │   └── userController
                        │   ├── VehicleController/
                        │   │   ├── User.java
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
                                    │   ├── capacity_cash.txt
                                    │   ├── port.txt
                                    │   ├── port_container.txt
                                    │   ├── port_portmanager.txt
                                    │   └── port_vehicles.txt
                                    ├── TripData/
                                    │   └── trip.txt
                                    ├── UserData/
                                    │   ├── admin.txt
                                    │   └── port_manager.txt
                                    ├── vehicleData/
                                    │   ├── vehicle.txt
                                    │   └── vehicle_containerLoad.txt
                                    └── utils/
                                        ├── BasicTruckFileUtils/
                                        │   ├── BasicTruckReadFile.java
                                        │   └── BasicTruckWriteFile.java
                                        ├── ReeferTruckFileUtils/
                                        │   ├── ReeferTruckReadFile.java
                                        │   └── ReeferTruckWriteFile.java
                                        ├── TankerTruckFileUtils/
                                        │   ├── TankerTruckReadFile.java
                                        │   └── TankerTruckWriteFile.java
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
                                        │   ├── AdminMenu.java
                                        │   ├── mainMenu.java
                                        │   └── userMainMenu.java
                                        └── Main.java

```
1. `Controller/`
The Controller is handle all method of system

2. `Operation/`
The Operation is contains all calculate statistic data of system

3. `UserController/`
The UserController is handle user functionality

3. `VehicleController/`
The VehicleController is handle vehicle functionality

4. `models/`
This models contains all class object of system (Object, Enum, Interface)

5. `entities/`
This entities stores all object of system (Vehicle, Port, Container)

6. `resource/`
The resource stores all the txt file of system (Database)

7. `utils/`
The utils handles all the read and write file to database

8. `view/`
The view handles all Menu of system


## Test accounts
1. Admin:
```
username: adm,in
password: Rmit2023@
```
2. StartPort:
```
username: pmstart
password: Rmit2023@
```
3. Ho Chi Minh Port:
```
username: pmhcm
password: Rmit2023@
```
4. Da Nang Port:
```
username: pmdn
password: Rmit2023@
```
5. Hai Phong Port:
```
username: pmhp
password: Rmit2023@
```
4. Kien Giang Port:
```
username: pmkg
password: Rmit2023@

...
```
### If the system break by some unpredictable situation: 
```bash
# In the main menu please choose number 3 (Reset and Add default data)







