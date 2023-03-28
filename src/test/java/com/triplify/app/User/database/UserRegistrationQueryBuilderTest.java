package com.triplify.app.User.database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("User Registration Insert Query Tested!!")
public class UserRegistrationQueryBuilderTest {

    @Test
    @DisplayName("Valid Query for inserting user registration table")
    void validInsertQuery(){
        final String expectedQuery = "INSERT INTO \"+ user_table + \"(\" +\n" +
                "                user_table_username + \", \" +\n" +
                "                user_table_first_name + \", \" +\n" +
                "                user_table_last_name + \", \" +\n" +
                "                user_table_email_address + \", \" +\n" +
                "                user_table_password + \", \" +\n" +
                "                user_table_is_logged_in + \", \" +\n" +
                "                user_image + \") \" +\n" +
                "                \"VALUES (?,?,?,?,?,?,?);";
        final String actualQuery = "INSERT INTO \"+ user_table + \"(\" +\n" +
                "                user_table_username + \", \" +\n" +
                "                user_table_first_name + \", \" +\n" +
                "                user_table_last_name + \", \" +\n" +
                "                user_table_email_address + \", \" +\n" +
                "                user_table_password + \", \" +\n" +
                "                user_table_is_logged_in + \", \" +\n" +
                "                user_image + \") \" +\n" +
                "                \"VALUES (?,?,?,?,?,?,?);";
        Assertions.assertEquals(expectedQuery,actualQuery,"Passed!!");
    }

    @Test
    @DisplayName("InValid Query for inserting user registration table")
    void inValidInsertQuery(){
        final String expectedQuery = "INSERT INTO \"+ user_table + \"(\" +\n" +
                "                user_table_username + \", \" +\n" +
                "                user_table_first_name + \", \" +\n" +
                "                user_table_last_name + \", \" +\n" +
                "                user_table_email_address + \", \" +\n" +
                "                user_table_password + \", \" +\n" +
                "                user_table_is_logged_in + \", \" +\n" +
                "                user_image + \") \" +\n" +
                "                \"VALUES (?,?,?,?,?,?,?);";
        final String actualQuery = "INSERT INTO \"+ user_table + \"(\" +\n" +
                "                user_table_username + \", \" +\n" +
                "                user_table_first_name + \", \" +\n" +
                "                user_table_last_name + \", \" +\n" +
                "                user_table_email_address + \", \" +\n" +
                "                user_table_password + \", \" +\n" +
                "                user_table_is_logged_in + \", \" +\n" +
                "                user_image + \") \" +\n" +
                "                \"VALUES (?,?,?,?,?,?,?); ";
        Assertions.assertNotEquals(expectedQuery,actualQuery,"Failed!!");
    }



}
