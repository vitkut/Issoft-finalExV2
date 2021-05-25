package services;

import data.ControllerSamples;
import models.ControllerEvent;
import models.DirectionAction;
import models.ElevatorEvent;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {

    @Test
    void controlFloorsTest(){
        //given
        Controller controller = ControllerSamples.getController();

        //when
        controller.controlFloors();
        ControllerEvent event = controller.getEvents().get(0);
        ControllerEvent event1 = controller.getEvents().get(1);
        ControllerEvent event2 = controller.getEvents().get(2);
        ControllerEvent event3 = controller.getEvents().get(3);

        //then
        assertEquals(0, event.getFloor());
        assertEquals(DirectionAction.Up, event.getDirection());
        assertEquals(1, event1.getFloor());
        assertEquals(DirectionAction.Up, event1.getDirection());
        assertEquals(1, event2.getFloor());
        assertEquals(DirectionAction.Down, event2.getDirection());
        assertEquals(3, event3.getFloor());
        assertEquals(DirectionAction.Up, event3.getDirection());
    }

    @Test
    void clearEventListDuplicatesTest(){
        //given
        Controller controller = ControllerSamples.getController();

        //when
        controller.controlFloors();
        controller.getElevators().get(0).getEvents().add(controller.getEvents().get(0).toElevatorEvent());
        controller.clearEventListDuplicates();

        //then
        assertNotEquals(controller.getEvents().get(0).toElevatorEvent(), controller.getElevators().get(0).getEvents().get(0));
    }

    @Test
    void checkElevatorsEventsTest(){
        //given
        Controller controller = ControllerSamples.getController();

        //when
        controller.controlFloors();
        controller.clearEventListDuplicates();
        controller.checkElevatorsEvents();
        ElevatorEvent event = new ElevatorEvent(DirectionAction.Up, 0);
        ElevatorEvent event1 = new ElevatorEvent(DirectionAction.Up, 1);
        ElevatorEvent event2 = new ElevatorEvent(DirectionAction.Up, 3);
        ElevatorEvent event3 = new ElevatorEvent(DirectionAction.Down, 1);

        //then
        assertTrue(controller.getEvents().isEmpty());

        assertEquals(event.getToFloor(), controller.getElevators().get(0).getEvents().get(0).getToFloor());
        assertEquals(event.getDirection(), controller.getElevators().get(0).getEvents().get(0).getDirection());

        assertEquals(event1.getToFloor(), controller.getElevators().get(0).getEvents().get(1).getToFloor());
        assertEquals(event1.getDirection(), controller.getElevators().get(0).getEvents().get(1).getDirection());

        assertEquals(event2.getToFloor(), controller.getElevators().get(0).getEvents().get(2).getToFloor());
        assertEquals(event2.getDirection(), controller.getElevators().get(0).getEvents().get(2).getDirection());

        assertEquals(event3.getToFloor(), controller.getElevators().get(1).getEvents().get(0).getToFloor());
        assertEquals(event3.getDirection(), controller.getElevators().get(1).getEvents().get(0).getDirection());

    }

    @Test
    void humanControlTest(){
        //given
        Controller controller = ControllerSamples.getController();

        //when
        controller.controlFloors();
        controller.clearEventListDuplicates();
        controller.checkElevatorsEvents();
        controller.getElevators().get(0).setDoorsOpen(true);
        controller.humanControl();

        //then
        System.out.println(controller.getElevators());
        System.out.println(controller.getEvents());
        System.out.println(controller.getFloors());
        assertEquals(1, controller.getElevators().get(0).getHumans().size());
    }
}
