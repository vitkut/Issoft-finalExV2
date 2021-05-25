package models;

public class ElevatorEvent {

    private DirectionAction direction;
    private Integer toFloor;


    public ElevatorEvent(DirectionAction direction, Integer toFloor) {
        this.direction = direction;
        this.toFloor = toFloor;
    }

    public DirectionAction getDirection() {
        return direction;
    }

    public void setDirection(DirectionAction direction) {
        this.direction = direction;
    }

    public Integer getToFloor() {
        return toFloor;
    }

    public void setToFloor(Integer toFloor) {
        this.toFloor = toFloor;
    }

    @Override
    public String toString() {
        return "ElevatorEvent{" +
                "dir=" + direction +
                ", toF=" + toFloor +
                '}';
    }
}
