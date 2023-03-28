package com.triplify.app.group.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.triplify.app.group.database.GroupMemberDetailsDatabaseConstant.*;
import static com.triplify.app.User.database.UserDatabaseConstant.*;

@DisplayName("Find Queries Testing here for gorup!!")
public class FindTest {

    @Test
    @DisplayName("Testing valid find user id by username")
    void validFindUserIdByUsernameTest(){
        String expectedQuery = "SELECT id FROM user_table WHERE username = 'Viral@123'";
        String actualQuery = "SELECT " + user_table_id + " FROM " + user_table + " WHERE " + user_table_username + " = 'Viral@123'";
        Assertions.assertEquals(expectedQuery,actualQuery,"Yes Passed this case!!");
    }

    @Test
    @DisplayName("Testing invalid find user id by username")
    void inValidFindUserIdByUsernameTest(){
        String expectedQuery = "SELECT id FROM user_table WHERE username = 'Viral@123'";
        String actualQuery = "SELECT " + user_table_id + " FROM " + user_table + " WHERE " + user_table_username + "=Viral@123";
        Assertions.assertNotEquals(expectedQuery,actualQuery,"Not Passed this case!!");
    }

    @Test
    @DisplayName("Testing valid find users in the group")
    void validFindUsersInGroupTest(){
        String expectedQuery = "SELECT username FROM group_has_members WHERE group_id = 13";
        String actualQuery = "SELECT " + GROUP_HAS_MEMBERS_USERNAME + " FROM " + GROUP_HAS_MEMBERS_TABLE + " WHERE " + GROUP_HAS_MEMBERS_GROUP_ID + " = 13";
        Assertions.assertEquals(expectedQuery,actualQuery,"Yes Passed this case!!");
    }

    @Test
    @DisplayName("Testing invalid find users in the group")
    void inValidFindUsersInGroupTest(){
        String expectedQuery = "SELECT username FROM group_has_members WHERE group_id = 133";
        String actualQuery = "SELECT " + GROUP_HAS_MEMBERS_USERNAME + " FROM " + GROUP_HAS_MEMBERS_TABLE + " WHERE " + GROUP_HAS_MEMBERS_GROUP_ID + "=13";
        Assertions.assertNotEquals(expectedQuery,actualQuery,"Not Passed this case!!");
    }

}
