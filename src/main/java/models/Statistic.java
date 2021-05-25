package models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

public class Statistic {

    //for each elevator
    private ArrayList<AtomicInteger> numOfTransportedHumans = new ArrayList<AtomicInteger>();
    //number of people picked up on the floor(for each floor)
    private ArrayList<AtomicInteger> numOfPickedUp = new ArrayList<AtomicInteger>();
    //the number of people brought to the floor (for each floor)
    private ArrayList<AtomicInteger> numOfBroughtTo = new ArrayList<AtomicInteger>();

    public Statistic(int numOfFloors, int numOfElevators) {
        for(int i = 0; i < numOfElevators; i++){
            numOfTransportedHumans.add(new AtomicInteger(0));
        }
        for(int i = 0; i < numOfFloors; i++){
            numOfPickedUp.add(new AtomicInteger(0));
            numOfBroughtTo.add(new AtomicInteger(0));
        }
    }

    public ArrayList<AtomicInteger> getNumOfTransportedHumans() {
        return numOfTransportedHumans;
    }

    public ArrayList<AtomicInteger> getNumOfPickedUp() {
        return numOfPickedUp;
    }

    public ArrayList<AtomicInteger> getNumOfBroughtTo() {
        return numOfBroughtTo;
    }

    public int getNumOfTransportedAll(){
        int integer = 0;
        for(AtomicInteger i:numOfTransportedHumans){
            integer += i.get();
        }
        return integer;
    }

    public void incrementTransported(int elevatorNumber){
        numOfTransportedHumans.get(elevatorNumber).incrementAndGet();
    }

    public void incrementPickedUp(int floorNumber){
        numOfPickedUp.get(floorNumber).incrementAndGet();
    }

    public void incrementBroughtTo(int floorNumber){
        numOfBroughtTo.get(floorNumber).incrementAndGet();
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
