package models;

public class ElevatorEvent {

    private ElevatorAction action;
    private Integer moveToFloor;

    public ElevatorEvent(ElevatorAction action) {
        this.action = action;
    }

    public ElevatorEvent(ElevatorAction action, Integer moveToFloor) {
        this.action = action;
        this.moveToFloor = moveToFloor;
    }

    public ElevatorAction getAction() {
        return action;
    }

    public void setAction(ElevatorAction action) {
        this.action = action;
    }

    public Integer getMoveToFloor() {
        return moveToFloor;
    }

    public void setMoveToFloor(Integer moveToFloor) {
        this.moveToFloor = moveToFloor;
    }

    @Override
    public String toString() {
        return "ElevatorEvent{" +
                "a=" + action +
                ", mToF=" + moveToFloor +
                '}';
    }
}
