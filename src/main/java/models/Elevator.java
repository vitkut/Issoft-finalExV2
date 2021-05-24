package models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class Elevator implements Runnable{

    private static final Logger logger = LoggerFactory.getLogger(Elevator.class);
    private Integer id;
    private Integer location = 0;
    private boolean isDoorsOpen = false;
    private int liftingCapacity;
    private int speed;
    private int doorsSpeed;
    private LinkedList<Human> humans = new LinkedList<Human>();
    private LinkedList<ElevatorEvent> events = new LinkedList<ElevatorEvent>();

    public Elevator(Integer id, int liftingCapacity, int speed, int doorsSpeed) {
        this.id = id;
        this.liftingCapacity = liftingCapacity;
        this.speed = speed;
        this.doorsSpeed = doorsSpeed;
    }

    public void run() {
        try {
            while (true){
                ElevatorEvent elevatorEvent = events.poll();
                if(elevatorEvent != null){
                    if(elevatorEvent.getAction().equals(ElevatorAction.OpenDoors)){
                        isDoorsOpen = true;
                        logger.debug("Doors is opened");
                        Thread.sleep(doorsSpeed);
                    }
                    if(elevatorEvent.getAction().equals(ElevatorAction.CloseDoors)){
                        isDoorsOpen = false;
                        logger.debug("Doors is closed");
                        Thread.sleep(doorsSpeed);
                    }
                    if(elevatorEvent.getAction().equals(ElevatorAction.Move)){
                        if(location > elevatorEvent.getMoveToFloor()){
                            location--;
                            logger.debug("Moved down");
                            Thread.sleep(speed);
                        }
                        if(location < elevatorEvent.getMoveToFloor()){
                            location++;
                            logger.debug("Moved up");
                            Thread.sleep(speed);
                        }
                    }
                }
            }
        } catch (InterruptedException ex){
            logger.error(ex.getMessage());
        }
    }
}
