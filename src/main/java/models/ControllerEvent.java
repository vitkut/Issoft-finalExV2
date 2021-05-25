package models;

public class ControllerEvent {

    private Integer floor;
    private DirectionAction direction;

    public ControllerEvent(Integer floor, DirectionAction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public DirectionAction getDirection() {
        return direction;
    }

    public void setDirection(DirectionAction direction) {
        this.direction = direction;
    }

    public ElevatorEvent toElevatorEvent(){
        return new ElevatorEvent(direction, floor);
    }

    @Override
    public String toString() {
        return "ControllerEvent{" +
                "f=" + floor +
                ", dir=" + direction +
                '}';
    }
}
