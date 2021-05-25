package services;

import models.Floor;
import models.Human;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class HumanSpawner extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(HumanSpawner.class);
    private ArrayList<Floor> floors;
    private Integer timeToSleep;
    private Double chanceToSpawn;

    public HumanSpawner(ArrayList<Floor> floors, Integer timeToSleep, Double chanceToSpawn) {
        this.floors = floors;
        this.timeToSleep = timeToSleep;
        this.chanceToSpawn = chanceToSpawn;
    }

    @Override
    public void run() {
        try {
            while (true){
                if(Math.random() <= chanceToSpawn){
                    int floor = (int) (Math.random()*floors.size());
                    int weight = ((int) (Math.random()*80))+40;
                    int reqFloor = (int) (Math.random()*floors.size());
                    if(floors.size() > 1){
                        while (reqFloor == floor){
                            reqFloor = (int) (Math.random()*floors.size());
                        }
                    }
                    Human newHuman = new Human(weight, reqFloor);
                    if(floor < reqFloor){
                        //up
                        floors.get(floor).getToUp().add(newHuman);
                    } else {
                        //down
                        floors.get(floor).getToDown().add(newHuman);
                    }
                    logger.debug("Human created - "+newHuman+" - in floor - "+floor);
                }
                sleep(timeToSleep);
            }
        } catch (InterruptedException ex){
            logger.error(ex.getMessage());
        }
    }
}
