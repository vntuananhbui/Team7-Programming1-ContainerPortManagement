package src.main.java.components.team7ContainerPortManagement;

import src.main.java.components.team7ContainerPortManagement.models.entities.Port;

import java.io.IOException;

import static src.main.java.components.team7ContainerPortManagement.view.mainMenu.*;
import static src.main.java.components.team7ContainerPortManagement.view.userMainMenu.mainLoopUser;

public class Main {

    public static void main(String[] args) throws IOException {

        displayBanner();
//        mainLoop();
        mainLoopUser();

    }

}//End bracket

