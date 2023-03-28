package com.triplify.app.User.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing UPDATE User Table Query!! ")
public class UpdateUserTableQueryBuilderTest {

    @Test
    @DisplayName("Valid update query!!")
    void isValidUpdateQuery(){
        final String expectedQuery = "update user_Table set firstname = 'viral' where user_table_id = 12";
        final String actualQuery = "update user_Table set firstname = 'viral' where user_table_id = 12";
        Assertions.assertEquals(expectedQuery,actualQuery,"Passes!!");
    }

    @Test
    @DisplayName("InValid update query!!")
    void inValidUpdateQuery(){
        final String expectedQuery = "update user_Table set firstname = 'viral' where user_table_id =12";
        final String actualQuery = "update user_Table set firstname = 'viral' where user_table_id = 12";
        Assertions.assertNotEquals(expectedQuery,actualQuery,"Failed!!");
    }

}
