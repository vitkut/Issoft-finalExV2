package services;

import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;

public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private LinkedList<ControllerEvent> events = new LinkedList<ControllerEvent>();
    private Statistic statistic;
    private ArrayList<Floor> floors;
    private ArrayList<Elevator> elevators;

    public Controller(ArrayList<Floor> floors, ArrayList<Elevator> elevators, Statistic statistic) {
        this.floors = floors;
        this.elevators = elevators;
        this.statistic = statistic;
    }

    public void controlFloors(){
        for(Floor f:floors){
            if(!f.getToUp().isEmpty()){
                if(!containsInEvents(f.getNumber(), DirectionEnum.Up)){
                    events.add(new ControllerEvent(f.getNumber(), DirectionEnum.Up));
                }
            }
            if(!f.getToDown().isEmpty()){
                if(!containsInEvents(f.getNumber(), DirectionEnum.Down)){
                    events.add(new ControllerEvent(f.getNumber(), DirectionEnum.Down));
                }
            }
        }
    }

    private boolean containsInEvents(Integer floor, DirectionEnum direction){
        return events.stream().anyMatch(e -> (e.getFloor().equals(floor) && e.getDirection().equals(direction)));
    }

    public void clearEventListDuplicates(){
        boolean needToRemove = false;
        ArrayList<ControllerEvent> removeEventsList = new ArrayList<ControllerEvent>();
        for(ControllerEvent controllerEvent:events){
            for(Elevator e:elevators){
                for(ElevatorEvent event:e.getEvents()){
                    if(event.getDirection().equals(controllerEvent.getDirection())
                            && event.getToFloor().equals(controllerEvent.getFloor())){
                        //if same move
                        needToRemove = true;
                        break;
                    }
                }
                if(needToRemove){
                    break;
                }
            }
            if(needToRemove){
                removeEventsList.add(controllerEvent);
                needToRemove = false;
            }
        }
        removeEventsList.forEach(r -> events.remove(r));
    }

    public void checkElevatorsEvents(){
        for(Elevator elevator:elevators){
            ArrayList<ControllerEvent> removeEventsList = new ArrayList<ControllerEvent>();
            for(ControllerEvent controllerEvent:events){
                if(elevator.getEvents().isEmpty()){
                    elevator.getEvents().add(controllerEvent.toElevatorEvent());
                    removeEventsList.add(controllerEvent);
                } else {
                    Integer location = elevator.getLocation();
                    Integer toFloor = controllerEvent.getFloor();
                    DirectionEnum action = null;
                    for(ElevatorEvent elevatorEvent:elevator.getEvents()){
                        action = elevatorEvent.getDirection();
                        break;
                    }
                    if(action == null){
                        elevator.getEvents().add(controllerEvent.toElevatorEvent());
                        removeEventsList.add(controllerEvent);
                    } else {
                        if(action.equals(controllerEvent.getDirection())){
                            if(action.equals(DirectionEnum.Up) && location < toFloor){
                                insertEventToElevatorsEvents(controllerEvent.toElevatorEvent(), elevator);
                                removeEventsList.add(controllerEvent);
                            }
                            if(action.equals(DirectionEnum.Down) && location > toFloor){
                                insertEventToElevatorsEvents(controllerEvent.toElevatorEvent(), elevator);
                                removeEventsList.add(controllerEvent);
                            }
                        }
                    }

                }
            }
            removeEventsList.forEach(r -> events.remove(r));
        }

    }

    private void insertEventToElevatorsEvents(ElevatorEvent event, Elevator elevator){
        int index = 0;
        if(event.getDirection().equals(DirectionEnum.Up)){
            for(int i = 0; i < elevator.getEvents().size()+1; i++){
                if(i == elevator.getEvents().size() || event.getToFloor() > elevator.getEvents().get(i).getToFloor()){
                    index = i;
                }

            }
            elevator.getEvents().add(index, event);
        }
        if(event.getDirection().equals(DirectionEnum.Down)){
            for(int i = 0; i < elevator.getEvents().size()+1; i++){
                if(i == elevator.getEvents().size() || event.getToFloor() < elevator.getEvents().get(i).getToFloor()){
                    index = i;
                }
            }
            elevator.getEvents().add(index, event);
        }

    }

    public void humanControl(){
        for(Elevator elevator:elevators){
            if(elevator.isDoorsOpen()){
                Floor floor = getFloorByNumber(elevator.getLocation());
                if(floor != null){
                    ArrayList<Human> removeHumans = new ArrayList<>();

                    elevator.getHumans().stream().filter(h -> h.getRequiredFloor().equals(floor.getNumber())).forEach(h -> removeHumans.add(h));
                    removeHumans.forEach(h -> {
                        elevator.getHumans().remove(h);
                        statistic.incrementTransported(elevator.getIdEl());
                        statistic.incrementBroughtTo(floor.getNumber());
                    });

                    removeHumans.clear();

                    if(elevator.getEvents().getFirst().getDirection().equals(DirectionEnum.Up)){
                        floor.getToUp().stream()
                                .filter(h -> elevator.getFreeCapacity()-h.getWeight() >= 0)
                                .forEach(h -> {
                                    elevator.getHumans().add(h);
                                    addHumanRequestToEvents(elevator, h.getRequiredFloor(), DirectionEnum.Up);
                                    removeHumans.add(h);
                                    statistic.incrementPickedUp(floor.getNumber());
                                });
                    }
                    if(elevator.getEvents().getFirst().getDirection().equals(DirectionEnum.Down)){
                        floor.getToDown().stream()
                                .filter(h -> elevator.getFreeCapacity()-h.getWeight() >= 0)
                                .forEach(h -> {
                                    elevator.getHumans().add(h);
                                    addHumanRequestToEvents(elevator, h.getRequiredFloor(), DirectionEnum.Down);
                                    removeHumans.add(h);
                                    statistic.incrementPickedUp(floor.getNumber());
                                });
                    }
                    removeHumans.forEach(h -> {
                        floor.getToUp().remove(h);
                        floor.getToDown().remove(h);
                    });
                }
            }
        }

    }

    private void addHumanRequestToEvents(Elevator elevator, Integer toFloor, DirectionEnum direction){
        if(!containsInElevatorEvents(elevator, toFloor, direction)){
            ElevatorEvent elevatorEvent = new ElevatorEvent(direction, toFloor);
            insertEventToElevatorsEvents(elevatorEvent, elevator);
        }

    }

    private Floor getFloorByNumber(Integer number){
        for(Floor f:floors){
            if(f.getNumber().equals(number)){
                return f;
            }
        }
        return null;
    }

    private boolean containsInElevatorEvents(Elevator elevator, Integer toFloor, DirectionEnum direction){
        return elevator.getEvents().stream().anyMatch(e -> (e.getToFloor().equals(toFloor) && e.getDirection().equals(direction)));
    }

    public LinkedList<ControllerEvent> getEvents() {
        return events;
    }

    public ArrayList<Elevator> getElevators() {
        return elevators;
    }

    public ArrayList<Floor> getFloors() {
        return floors;
    }

    public Statistic getStatistic() {
        return statistic;
    }
}
