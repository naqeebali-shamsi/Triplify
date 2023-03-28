package com.triplify.app.User.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing User Details Query")
public class UserDetailsQueryBuilderTest {

    @Test
    @DisplayName("Valid Select Query Tested!!")
    void validSelectQuery(){
        final String expectedQuery = "SELECT * FROM \" + user_table + \" WHERE \" + user_table_username + \" = \"";
        final String actualQuery = "SELECT * FROM \" + user_table + \" WHERE \" + user_table_username + \" = \"";
        Assertions.assertEquals(expectedQuery,actualQuery,"Passed!!");
    }

    @Test
    @DisplayName("InValid Select Query Tested!!")
    void inValidSelectQuery(){
        final String expectedQuery = "SELECT * FROM \" + user_table + \" WHERE \" + user_table_username + \" = \"";
        final String actualQuery = "SELECT * FROM \" + user_table + \" WHERE \" + user_table_username + \" =\"";
        Assertions.assertNotEquals(expectedQuery,actualQuery,"Failed!!");
    }

}
