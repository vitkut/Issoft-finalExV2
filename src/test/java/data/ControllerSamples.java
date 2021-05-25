package data;

import models.Statistic;
import services.Controller;

public class ControllerSamples {

    public static Controller getController(){
        return new Controller(FloorArraySamples.getFloors(), ElevatorArraySamples.getElevators(),
                new Statistic(FloorArraySamples.getFloors().size(), ElevatorArraySamples.getElevators().size()));
    }
}
