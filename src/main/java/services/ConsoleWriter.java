package services;

import models.Statistic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConsoleWriter extends Thread{

    private static final Logger logger = LoggerFactory.getLogger(ConsoleWriter.class);
    private final Controller controller;
    private final Integer timeToSleep;
    private final Statistic statistic;

    public ConsoleWriter(Controller controller, Integer timeToSleep, Statistic statistic) {
        this.controller = controller;
        this.timeToSleep = timeToSleep;
        this.statistic = statistic;
    }

    @Override
    public void run() {
        try {
            while (true){
                logger.info("Floors - "+controller.getFloors());
                logger.info("Elevators - "+controller.getElevators());
                logger.info("Stats - "+statistic);
                sleep(timeToSleep);
            }
        } catch (InterruptedException ex){
            logger.error(ex.getMessage());
        }
    }
}
