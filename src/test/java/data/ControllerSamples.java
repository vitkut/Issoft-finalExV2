package data;

import services.Controller;

public class ControllerSamples {

    public static Controller getController(){
        Controller controller = new Controller(FloorArraySamples.getFloors(), ElevatorArraySamples.getElevators());
        return controller;
    }
}
