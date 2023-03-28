package com.triplify.app.group.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("group Has Members - Select Query testing()")
public class GroupHasMembersSelectQueryTest {

    @Test
    @DisplayName("Valid Select Query Statement Generated!")
    void isValidSelectQuery(){
        GroupHasMembersSelectQuery groupHasMembersSelectQuery = new GroupHasMembersSelectQuery();
        final String expectedQuery = groupHasMembersSelectQuery.selectQuery();
        final String actualQuery = "select * from group_has_members";
        Assertions.assertEquals(expectedQuery,actualQuery,"Passed - Valid Select Query Statement Generated!");
    }

    @Test
    @DisplayName("InValid Select Query Statement Generated!")
    void isNotValidSelectQuery(){
        GroupHasMembersSelectQuery groupHasMembersSelectQuery = new GroupHasMembersSelectQuery();
        final String expectedQuery = groupHasMembersSelectQuery.selectQuery();
        final String actualQuery = "select  from group_has_members";
        Assertions.assertNotEquals(expectedQuery,actualQuery,"Failed - InValid Select Query Statement Generated!");
    }

}
