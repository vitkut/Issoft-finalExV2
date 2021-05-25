package models;

public class ElevatorEvent {

    private DirectionEnum direction;
    private Integer toFloor;


    public ElevatorEvent(DirectionEnum direction, Integer toFloor) {
        this.direction = direction;
        this.toFloor = toFloor;
    }

    public DirectionEnum getDirection() {
        return direction;
    }

    public void setDirection(DirectionEnum direction) {
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
