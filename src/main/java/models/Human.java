package models;

public class Human {

    private Integer weight;
    private Integer requiredFloor;

    public Human(Integer weight, Integer requiredFloor) {
        this.weight = weight;
        this.requiredFloor = requiredFloor;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getRequiredFloor() {
        return requiredFloor;
    }

    public void setRequiredFloor(Integer requiredFloor) {
        this.requiredFloor = requiredFloor;
    }

    @Override
    public String toString() {
        return "Human{" +
                "w=" + weight +
                ", reqFl=" + requiredFloor +
                '}';
    }
}
