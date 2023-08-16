# Team7-Programming1-ContainerPortManagement

# Project file
Google drive: https://drive.google.com/drive/folders/1REA1BfpRaBPQKx333JmbhGbpEuKiPIhW?usp=sharing
UML DRIAGRAM: https://drive.google.com/file/d/1XQal3Jv4b2wrsqM0Hp8xhQUr6fKyB86r/view?usp=sharing
<br />
<hr>
- Project start date: 12/08/2023
<br />
- Project end date: 

## Contribution

| Student Name      | Student ID | Contribution Score |
| :---------------- | :--------- | :----------------: |
| Bui Tuan Anh      | S-3963207  |         20         |


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

PortManagementSystem/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── com/
│   │   │   │   ├── yourpackage/
│   │   │   │   │   ├── controllers/  (Handlers for the application logic)
│   │   │   │   │   │   ├── AdminController.java
│   │   │   │   │   │   ├── PortManagerController.java
│   │   │   │   │   │   ├── ... (other controllers)
│   │   │   │   │   │
│   │   │   │   │   ├── models/  (All the data-related classes)
│   │   │   │   │   │   ├── enums/
│   │   │   │   │   │   │   ├── ContainerType.java
│   │   │   │   │   │   │   ├── TruckType.java
│   │   │   │   │   │   │   ├── ... (other enums)
│   │   │   │   │   │   │
│   │   │   │   │   │   ├── interfaces/
│   │   │   │   │   │   │   ├── VehicleOperations.java
│   │   │   │   │   │   │   ├── PortOperations.java
│   │   │   │   │   │   │   ├── ... (other interfaces)
│   │   │   │   │   │   │
│   │   │   │   │   │   ├── entities/
│   │   │   │   │   │   │   ├── Vehicle.java
│   │   │   │   │   │   │   ├── Truck.java
│   │   │   │   │   │   │   ├── Ship.java
│   │   │   │   │   │   │   ├── Port.java
│   │   │   │   │   │   │   ├── Container.java
│   │   │   │   │   │   │   ├── Trip.java
│   │   │   │   │   │   │   ├── ... (other entity classes)
│   │   │   │   │   │   │
│   │   │   │   │   │   ├── utils/  (Utility classes, for things like file handling)
│   │   │   │   │   │       ├── FileReaderUtil.java
│   │   │   │   │   │       ├── FileWriterUtil.java
│   │   │   │   │   │       ├── ... (other utility classes)
│   │   │   │   │   │
│   │   │   │   │   ├── views/  (For your user interface, if applicable)
│   │   │   │   │       ├── AdminView.java
│   │   │   │   │       ├── PortManagerView.java
│   │   │   │   │       ├── ... (other views)
│   │   │   │   │
│   │   │   │   ├── Main.java  (Main class to run your application)
│   │   │   │
│   │   │   ├── resources/  (Non-Java files, like data files)
│   │   │       ├── data/
│   │   │           ├── vehicles.txt
│   │   │           ├── ports.txt
│   │   │           ├── containers.txt
│   │   │           ├── trips.txt
│   │   │           ├── ... (other data files)
│   │   │
│   │   └── test/  (For your unit tests, if you're writing any)
│   │       ├── java/
│   │       │   ├── com/
│   │       │   │   ├── yourpackage/
│   │       │   │       ├── models/
│   │       │   │       ├── controllers/
│   │       │   │       ├── ... (corresponding package structure for tests)
│   │       │
│   │       └── resources/  (Test-specific resources)
│
└── README.md  (Project description, instructions, etc.)

## License

This software is licensed under the MIT License ©

