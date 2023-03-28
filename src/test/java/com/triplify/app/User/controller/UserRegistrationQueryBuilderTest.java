package com.triplify.app.User.controller;

import com.triplify.app.User.model.UserTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User Registration Query Possible Test Cases!!")
public class UserRegistrationQueryBuilderTest {

    @Test
    @DisplayName("Insert Query is Valid Test()!!")
    void validInsertQueryTest(){

        UserTable userTable = new UserTable();
        userTable.setUsername("viral123");
        userTable.setFirstname("Viral");
        userTable.setLastname("Siddhapura");
        userTable.setEmailAddress("viralsid2330@gmail.com");
        userTable.setPassword("Vir@l2330");
        userTable.setLoggedIn(true);

        String expectedQuery = "INSERT INTO user_table(username, firstname, lastname, email_address, password, is_logged_in) VALUES (\"viral123\", \"Viral\", \"Siddhapura\", \"viralsid2330@gmail.com\", \"Vir@l2330\", \"1\");" ;
        String actualQuery = "INSERT INTO user_table(username, firstname, lastname, email_address, password, is_logged_in) VALUES (\"viral123\", \"Viral\", \"Siddhapura\", \"viralsid2330@gmail.com\", \"Vir@l2330\", \"1\");";

        Assertions.assertEquals(expectedQuery,actualQuery,"Correct query has been generated");
    }

    @Test
    @DisplayName("InCorrect Insert Query is Valid Test()!!")
    void inCorrectInsertQueryTest(){

        UserTable userTable = new UserTable();
        userTable.setUsername("viral123");
        userTable.setFirstname("Viral");
        userTable.setLastname("Siddhapura");
        userTable.setEmailAddress("viralsid2330@gmail.com");
        userTable.setPassword("Vir@l2330");
        userTable.setLoggedIn(true);

        String expectedQuery = "INSERT INTO user_table(username, firstname, lastname, email_address, password, is_logged_in) VALUES (\"viral123\", \"Viral\", \"Siddhapura\", \"viralsid2330@gmail.com\", \"Vir@l2330\", \"1\");" ;
        String actualQuery = "INSERT INTO user_table (username, firstname, lastname, email_address, password, is_logged_in) VALUES (\"viral123\", \"Viral\", \"\", \"viralsid2330@gmail.com\", \"Vir@l2330\", \"\");";

        Assertions.assertNotEquals(expectedQuery,actualQuery,"Incorrect insert query has been generated");
    }

}
