package models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class Elevator extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(Elevator.class);
    private Integer idEl;
    private Integer location = 0;
    private boolean isDoorsOpen = false;
    private int liftingCapacity;
    private int speed;
    private int doorsSpeed;
    private LinkedList<Human> humans = new LinkedList<Human>();
    private LinkedList<ElevatorEvent> events = new LinkedList<ElevatorEvent>();

    public Elevator(Integer id, int liftingCapacity, int speed, int doorsSpeed) {
        this.idEl = id;
        this.liftingCapacity = liftingCapacity;
        this.speed = speed;
        this.doorsSpeed = doorsSpeed;
    }

    @Override
    public void run() {
        try {
            while (true){
                if(!events.isEmpty()){
                    ElevatorEvent elevatorEvent = events.getFirst();
                    if(elevatorEvent != null){
                        while (!location.equals(elevatorEvent.getToFloor())){
                            if(!elevatorEvent.equals(events.getFirst())){
                                break;
                            }
                            if(location > elevatorEvent.getToFloor()){
                                location--;
                                logger.debug("Elevator "+idEl+" moved down");
                                Thread.sleep(speed);
                            }
                            if(location < elevatorEvent.getToFloor()){
                                location++;
                                logger.debug("Elevator "+idEl+" moved up");
                                Thread.sleep(speed);
                            }
                        }
                        if(location.equals(elevatorEvent.getToFloor())){
                            isDoorsOpen = true;
                            logger.debug("Elevator "+idEl+" open doors");
                            Thread.sleep(doorsSpeed);
                            isDoorsOpen = false;
                            logger.debug("Elevator "+idEl+" close doors");
                            Thread.sleep(doorsSpeed);
                            events.removeFirst();
                        }
                    }
                }
                sleep(20);

            }
        } catch (InterruptedException ex){
            logger.error(ex.getMessage());
        }
    }

    public Integer getIdEl() {
        return idEl;
    }

    public void setIdEl(Integer idEl) {
        this.idEl = idEl;
    }

    public Integer getLocation() {
        return location;
    }

    public void setLocation(Integer location) {
        this.location = location;
    }

    public boolean isDoorsOpen() {
        return isDoorsOpen;
    }

    public void setDoorsOpen(boolean doorsOpen) {
        isDoorsOpen = doorsOpen;
    }

    public int getLiftingCapacity() {
        return liftingCapacity;
    }

    public void setLiftingCapacity(int liftingCapacity) {
        this.liftingCapacity = liftingCapacity;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDoorsSpeed() {
        return doorsSpeed;
    }

    public void setDoorsSpeed(int doorsSpeed) {
        this.doorsSpeed = doorsSpeed;
    }

    public LinkedList<Human> getHumans() {
        return humans;
    }

    public void setHumans(LinkedList<Human> humans) {
        this.humans = humans;
    }

    public LinkedList<ElevatorEvent> getEvents() {
        return events;
    }

    public void setEvents(LinkedList<ElevatorEvent> events) {
        this.events = events;
    }

    public Integer getFreeCapacity(){
        Integer free = liftingCapacity;
        for(Human h:humans){
            free-=h.getWeight();
        }
        return free;
    }

    @Override
    public String toString() {
        return "Elevator{" +
                "idEl=" + idEl +
                ", loc=" + location +
                ", isDoorsOpen=" + isDoorsOpen +
                ", humans=" + humans +
                ", events=" + events +
                '}';
    }
}
