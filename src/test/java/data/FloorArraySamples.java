package data;

import models.Floor;
import models.Human;

import java.util.ArrayList;

public class FloorArraySamples {

    public static ArrayList<Floor> getFloors(){
        ArrayList<Floor> floors = new ArrayList<Floor>();
        for (int i = 0; i < 5; i++){
            floors.add(new Floor(i));
        }
        Human human1 = new Human(60, 1);//0 to 1
        Human human2 = new Human(60, 0);//1 to 0
        Human human3 = new Human(60, 3);//1 to 3
        Human human4 = new Human(60, 4);//3 to 4
        floors.get(0).getToUp().add(human1);
        floors.get(1).getToDown().add(human2);
        floors.get(1).getToUp().add(human3);
        floors.get(3).getToUp().add(human4);
        return floors;
    }

}
