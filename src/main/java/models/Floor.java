package models;

import java.util.LinkedList;

public class Floor {

    private Integer number;
    private LinkedList<Human> toDown = new LinkedList<Human>();
    private LinkedList<Human> toUp = new LinkedList<Human>();

    public Floor(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public LinkedList<Human> getToDown() {
        return toDown;
    }

    public void setToDown(LinkedList<Human> toDown) {
        this.toDown = toDown;
    }

    public LinkedList<Human> getToUp() {
        return toUp;
    }

    public void setToUp(LinkedList<Human> toUp) {
        this.toUp = toUp;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "n=" + number +
                ", toDown=" + toDown +
                ", toUp=" + toUp +
                '}';
    }
}
