package com.triplify.app.group.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing for select query in group!!")
public class GroupDetailsSelectQueryTest {

    @Test
    @DisplayName("valid select query for group!!")
    public void validSelectQueryForGroupTest(){
        GroupDetailsSelectQuery groupDetailsSelectQuery = new GroupDetailsSelectQuery();
        final String expectedSelectQuery = groupDetailsSelectQuery.selectQueryForGroup();
        final String actualSelectQuery = "SELECT * from group_details";
        Assertions.assertEquals(expectedSelectQuery,actualSelectQuery,"Incorrect Select Query Has been generated!!");
    }

    @Test
    @DisplayName("invalid select query for group!!")
    public void invalidSelectQueryForGroupTest(){
        GroupDetailsSelectQuery groupDetailsSelectQuery = new GroupDetailsSelectQuery();
        final String expectedSelectQuery = groupDetailsSelectQuery.selectQueryForGroup();
        final String actualSelectQuery = "SELECT * from \"group_details\"";
        Assertions.assertNotEquals(expectedSelectQuery,actualSelectQuery,"Incorrect Select Query Has been generated!!");
    }

}
