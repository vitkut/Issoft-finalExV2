package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Statistic {

    //for each elevator
    private ArrayList<Integer> numOfTransportedHumans = new ArrayList<Integer>();
    //number of people picked up on the floor(for each floor)
    private ArrayList<Integer> numOfPickedUp = new ArrayList<Integer>();
    //the number of people brought to the floor (for each floor)
    private ArrayList<Integer> numOfBroughtTo = new ArrayList<Integer>();

    public Statistic(int numOfFloors, int numOfElevators) {
        for(int i = 0; i < numOfElevators; i++){
            numOfTransportedHumans.add(0);
        }
        for(int i = 0; i < numOfFloors; i++){
            numOfPickedUp.add(0);
            numOfBroughtTo.add(0);
        }
    }

    public ArrayList<Integer> getNumOfTransportedHumans() {
        return numOfTransportedHumans;
    }

    public ArrayList<Integer> getNumOfPickedUp() {
        return numOfPickedUp;
    }

    public ArrayList<Integer> getNumOfBroughtTo() {
        return numOfBroughtTo;
    }

    public void incrementTransported(int elevatorNumber){
        numOfTransportedHumans.set(elevatorNumber, numOfTransportedHumans.get(elevatorNumber)+1);
    }

    public void incrementPickedUp(int floorNumber){
        numOfPickedUp.set(floorNumber, numOfPickedUp.get(floorNumber)+1);
    }

    public void incrementBroughtTo(int floorNumber){
        numOfBroughtTo.set(floorNumber, numOfBroughtTo.get(floorNumber)+1);
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "numOfTransportedHumans=" + numOfTransportedHumans +
                ", numOfPickedUp=" + numOfPickedUp +
                ", numOfBroughtTo=" + numOfBroughtTo +
                '}';
    }
}
