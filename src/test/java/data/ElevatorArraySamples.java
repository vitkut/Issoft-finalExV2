package data;

import models.Elevator;

import java.util.ArrayList;

public class ElevatorArraySamples {

    public static ArrayList<Elevator> getElevators(){
        ArrayList<Elevator> elevators = new ArrayList<Elevator>();
        elevators.add(new Elevator(0, 1000, 1, 1));
        elevators.add(new Elevator(1, 1000, 1, 1));
        return elevators;
    }
}
