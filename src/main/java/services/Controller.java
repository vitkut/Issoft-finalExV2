package services;

import models.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;

public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);
    private LinkedList<ControllerEvent> events = new LinkedList<ControllerEvent>();
    private ArrayList<Floor> floors;
    private ArrayList<Elevator> elevators;

    public Controller(ArrayList<Floor> floors, ArrayList<Elevator> elevators) {
        this.floors = floors;
        this.elevators = elevators;
    }

    public void controlFloors(){
        for(Floor f:floors){
            if(!f.getToUp().isEmpty()){
                if(!containsInEvents(f.getNumber(), DirectionAction.Up)){
                    events.add(new ControllerEvent(f.getNumber(), DirectionAction.Up));
                }
            }
            if(!f.getToDown().isEmpty()){
                if(!containsInEvents(f.getNumber(), DirectionAction.Down)){
                    events.add(new ControllerEvent(f.getNumber(), DirectionAction.Down));
                }
            }
        }
        //logger.debug("controlFloors");
    }

    private boolean containsInEvents(Integer floor, DirectionAction direction){
        for(ControllerEvent e:events){
            if(e.getFloor().equals(floor) && e.getDirection().equals(direction)){
                return true;
            }
        }
        return false;
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
        for(ControllerEvent c:removeEventsList){
            events.remove(c);
            //logger.debug("Remove controller event "+c);
        }
    }

    public void checkElevatorsEvents(){
        for(Elevator elevator:elevators){
            ArrayList<ControllerEvent> removeEvents = new ArrayList<ControllerEvent>();
            for(ControllerEvent controllerEvent:events){
                if(elevator.getEvents().isEmpty()){
                    elevator.getEvents().add(controllerEvent.toElevatorEvent());
                    removeEvents.add(controllerEvent);
                } else {
                    Integer location = elevator.getLocation();
                    Integer toFloor = controllerEvent.getFloor();
                    DirectionAction action = null;
                    for(ElevatorEvent elevatorEvent:elevator.getEvents()){
                        action = elevatorEvent.getDirection();
                        break;
                    }
                    if(action == null){
                        elevator.getEvents().add(controllerEvent.toElevatorEvent());
                        removeEvents.add(controllerEvent);
                    } else {
                        if(action.equals(controllerEvent.getDirection())){
                            if(action.equals(DirectionAction.Up) && location < toFloor){
                                insertEventToElevatorsEvents(controllerEvent.toElevatorEvent(), elevator);
                                removeEvents.add(controllerEvent);
                            }
                            if(action.equals(DirectionAction.Down) && location > toFloor){
                                insertEventToElevatorsEvents(controllerEvent.toElevatorEvent(), elevator);
                                removeEvents.add(controllerEvent);
                            }
                        }
                    }

                }
            }
            for(ControllerEvent c:removeEvents){
                events.remove(c);
            }
        }
        //logger.debug("Check elevators events");
    }

    private void insertEventToElevatorsEvents(ElevatorEvent event, Elevator elevator){
        int index = 0;
        if(event.getDirection().equals(DirectionAction.Up)){
            for(int i = 0; i < elevator.getEvents().size()+1; i++){
                if(i == elevator.getEvents().size() || event.getToFloor() > elevator.getEvents().get(i).getToFloor()){
                    index = i;
                }

            }
            elevator.getEvents().add(index, event);
        }
        if(event.getDirection().equals(DirectionAction.Down)){
            for(int i = 0; i < elevator.getEvents().size()+1; i++){
                if(i == elevator.getEvents().size() || event.getToFloor() < elevator.getEvents().get(i).getToFloor()){
                    index = i;
                }
            }
            elevator.getEvents().add(index, event);
        }
        //logger.debug("Insert event to elevators events "+event);
    }

    public void humanControl(){
        for(Elevator elevator:elevators){
            if(elevator.isDoorsOpen()){
                Floor floor = getFloorByNumber(elevator.getLocation());
                if(floor != null){
                    ArrayList<Human> removeHumans = new ArrayList<Human>();
                    for(Human h:elevator.getHumans()){
                        if(h.getRequiredFloor().equals(floor.getNumber())){
                            removeHumans.add(h);
                        }
                    }
                    for(Human h:removeHumans){
                        elevator.getHumans().remove(h);
                    }
                    removeHumans.clear();

                    if(elevator.getEvents().getFirst().getDirection().equals(DirectionAction.Up)){
                        for(Human h:floor.getToUp()){
                            if(elevator.getFreeCapacity()-h.getWeight() >= 0){
                                elevator.getHumans().add(h);
                                addHumanRequestToEvents(elevator, h.getRequiredFloor(), DirectionAction.Up);
                                removeHumans.add(h);
                            }
                        }
                    }
                    if(elevator.getEvents().getFirst().getDirection().equals(DirectionAction.Down)){
                        for(Human h:floor.getToDown()){
                            if(elevator.getFreeCapacity()-h.getWeight() >= 0){
                                elevator.getHumans().add(h);
                                addHumanRequestToEvents(elevator, h.getRequiredFloor(), DirectionAction.Down);
                                removeHumans.add(h);
                            }
                        }
                    }
                    for(Human h:removeHumans){
                        floor.getToUp().remove(h);
                        floor.getToDown().remove(h);
                    }
                }
            }
        }
        //logger.debug("Human control");
    }

    private void addHumanRequestToEvents(Elevator elevator, Integer toFloor, DirectionAction direction){
        if(!containsInElevatorEvents(elevator, toFloor, direction)){
            ElevatorEvent elevatorEvent = new ElevatorEvent(direction, toFloor);
            insertEventToElevatorsEvents(elevatorEvent, elevator);
        }
        //logger.debug("Add human request");
    }

    private Floor getFloorByNumber(Integer number){
        for(Floor f:floors){
            if(f.getNumber().equals(number)){
                return f;
            }
        }
        return null;
    }

    private boolean containsInElevatorEvents(Elevator elevator, Integer toFloor, DirectionAction direction){
        for(ElevatorEvent e:elevator.getEvents()){
            if(e.getToFloor().equals(toFloor) && e.getDirection().equals(direction)){
                return true;
            }
        }
        return false;
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
}
