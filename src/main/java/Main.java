import models.Elevator;
import models.Statistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import services.ConsoleWriter;
import services.Controller;
import services.ControllerCreator;
import services.HumanSpawner;

import java.util.ArrayList;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        Controller controller = ControllerCreator.getController(5, 2);
        controller.getElevators().get(1).setSpeed(800);
        ConsoleWriter consoleWriter = new ConsoleWriter(controller, 500, controller.getStatistic());
        HumanSpawner humanSpawner = new HumanSpawner(controller.getFloors(), 500, 0.1d);
        consoleWriter.start();
        humanSpawner.start();
        for(Elevator e:controller.getElevators()){
            e.start();
        }
        try{
            while (true){
                controller.controlFloors();
                controller.clearEventListDuplicates();
                controller.checkElevatorsEvents();
                controller.humanControl();
                Thread.sleep(250);
            }
        } catch (InterruptedException ex){
            logger.error(ex.getMessage());
        }
    }
}
