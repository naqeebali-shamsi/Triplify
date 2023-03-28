package com.triplify.app.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("All Database Exception Handler Test")
public class DatabaseConnectionExceptionHandlerTest {

    @Test
    @DisplayName("Constructor tested here!!")
    void DatabaseExceptionHandlerTest(){
        DatabaseExceptionHandler databaseExceptionHandler =
                new DatabaseExceptionHandler("Testing", new Throwable());
        Assertions.assertEquals("DatabaseConnectionException{errorMessage='Testing', error=java.lang.Throwable}",
                databaseExceptionHandler.toString());
    }

    @Test
    @DisplayName("Get Error Message Tested!!")
    void getErrorMessageTest(){
        DatabaseExceptionHandler databaseExceptionHandler =
                new DatabaseExceptionHandler("Testing",new Throwable());
        Assertions.assertEquals("Testing", databaseExceptionHandler.getErrorMessage());
    }

}
