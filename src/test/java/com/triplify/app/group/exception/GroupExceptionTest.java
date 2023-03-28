package com.triplify.app.group.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("group exception Handling Testing()!!")
public class GroupExceptionTest {

    @Test
    @DisplayName("Exploration Constructor Tested")
    public void GroupExceptionTest(){
        GroupException groupException = new GroupException("Testing");
        Assertions.assertEquals("GroupException{errorMessage='Testing'}", groupException.toString());
    }

    @Test
    @DisplayName("Exploration exception getErrorMessage() Tested!!")
    public void getErrorMessageTest(){
        GroupException groupException = new GroupException("Testing");
        Assertions.assertEquals("Testing", groupException.getErrorMessage());
    }
}
