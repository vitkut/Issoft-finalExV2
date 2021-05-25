package services;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerCreatorTest {

    @Test
    void getControllerTest(){
        //given
        int numOfFloors = 6;
        int numOfElevators = 4;

        //when
        Controller controller = ControllerCreator.getController(numOfFloors, numOfElevators);

        //then
        assertEquals(numOfFloors, controller.getFloors().size());
        assertEquals(numOfElevators, controller.getElevators().size());
        assertEquals(numOfElevators, controller.getStatistic().getNumOfTransportedHumans().size());
        assertEquals(numOfFloors, controller.getStatistic().getNumOfPickedUp().size());
        assertEquals(numOfFloors, controller.getStatistic().getNumOfBroughtTo().size());
    }
}
