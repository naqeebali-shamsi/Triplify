package com.triplify.app.exploreFeature.exception;

import com.triplify.app.explore.exception.ExplorationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Exploration Exception Handling Testing()!!")
public class ExplorationExceptionTest {

    @Test
    @DisplayName("Exploration Constructor Tested")
    public void ExplorationExceptionTest(){
        ExplorationException explorationException =
                new ExplorationException("Testing");
        Assertions.assertEquals("ExplorationException{errorMessage='Testing'}",
                explorationException.toString());
    }

    @Test
    @DisplayName("Exploration exception getErrorMessage() Tested!!")
    public void getErrorMessageTest(){
        ExplorationException explorationException =
                new ExplorationException("Testing");
        Assertions.assertEquals("Testing", explorationException.getErrorMessage());
    }

}
