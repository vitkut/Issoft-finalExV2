package models;

public class ControllerEvent {

    private Integer floor;
    private DirectionEnum direction;

    public ControllerEvent(Integer floor, DirectionEnum direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
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
