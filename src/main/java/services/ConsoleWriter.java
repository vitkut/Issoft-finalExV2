package services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleWriter extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(ConsoleWriter.class);
    private Controller controller;
    private Integer timeToSleep;

    public ConsoleWriter(Controller controller, Integer timeToSleep) {
        this.controller = controller;
        this.timeToSleep = timeToSleep;
    }

    @Override
    public void run() {
        try {
            while (true){
                logger.debug("Floors - "+controller.getFloors());
                logger.debug("Elevators - "+controller.getElevators());
                logger.debug("Events - "+controller.getEvents());
                sleep(timeToSleep);
            }
        } catch (InterruptedException ex){
            logger.error(ex.getMessage());
        }
    }
}
