package services;

import models.Elevator;
import models.Floor;
import models.Statistic;

import java.util.ArrayList;

public class ControllerCreator {

    public static Controller getController(int numOfFloors, int numOfElevators){
        ArrayList<Floor> floors = new ArrayList<Floor>();
        ArrayList<Elevator> elevators = new ArrayList<Elevator>();
        for(int i = 0; i < numOfFloors; i++){
            floors.add(new Floor(i));
        }
        for(int i = 0; i < numOfElevators; i++){
            elevators.add(new Elevator(i, 500, 1000, 1000));
        }
        Statistic statistic = new Statistic(numOfFloors, numOfElevators);
        return new Controller(floors, elevators, statistic);
    }
}
