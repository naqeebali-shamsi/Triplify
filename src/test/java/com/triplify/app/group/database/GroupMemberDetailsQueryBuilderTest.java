package com.triplify.app.group.database;

import com.triplify.app.group.model.GroupMembersDetails;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Testing Adding group member Insert Query!!")
public class GroupMemberDetailsQueryBuilderTest {

    @Test
    @DisplayName("Testing group member insert query methods")
    public void groupMemberInsertQuery(){

        GroupMembersDetails groupMembersDetails = new GroupMembersDetails();
        groupMembersDetails.setGroupName("Australia");
        groupMembersDetails.setGroupDestination("Australia");
        groupMembersDetails.setGroupMemberFirstName("Viral");
        groupMembersDetails.setGroupMemberLastName("Siddhapura");
        groupMembersDetails.setUser_id(Long.valueOf(15));

        GroupMemberDetailsQueryBuilder groupMemberDetailsQueryBuilder =
                new GroupMemberDetailsQueryBuilder();

        final String expectedInsertQuery =
                groupMemberDetailsQueryBuilder.groupMemberInsertQuery(groupMembersDetails);

        final String actualInsertQuery =
                "INSERT INTO group_members(group_name, member_destination, member_firstname, member_lastname, user_id) VALUES (\"Australia\", \"Australia\", \"Viral\", \"Siddhapura\", 15);";

        System.out.println(expectedInsertQuery);
        System.out.println(actualInsertQuery);
        Assertions.assertEquals(expectedInsertQuery,actualInsertQuery,"Incorrect Insert Query Has been generated!!");

    }

}
