package com.triplify.app.group.database;

import com.triplify.app.group.model.GroupMembersDetails;

import static com.triplify.app.group.database.GroupMemberDetailsDatabaseConstant.*;

public class GroupMemberDetailsQueryBuilder implements IGroupMemberDetailsQueryBuilder{
    public String groupMemberInsertQuery(final GroupMembersDetails groupMembersDetails){
        return "INSERT INTO "+ GROUP_MEMBER_DETAILS_TABLE + "(" +
                GROUP_MEMBER_DETAILS_GROUP_NAME + ", " +
                GROUP_MEMBER_DETAILS_DESTINATION + ", " +
                GROUP_MEMBER_DETAILS_FIRST_NAME + ", " +
                GROUP_MEMBER_DETAILS_LAST_NAME + ", " +
                GROUP_MEMBER_DETAILS_USER_ID + ") " +
                "VALUES (" +
                "\"" +groupMembersDetails.getGroupName()+ "\", " +
                "\"" +groupMembersDetails.getGroupDestination()+ "\", " +
                "\"" +groupMembersDetails.getGroupMemberFirstName() + "\", " +
                "\"" +groupMembersDetails.getGroupMemberLastName() + "\", " +
                groupMembersDetails.getUser_id() +
                ");";
    }
    public String groupMemberRelationshipInsertQuery(){
        return "INSERT INTO "+ GROUP_HAS_MEMBERS_TABLE + "(" +
                GROUP_HAS_MEMBERS_GROUP_ID + ", " +
                GROUP_HAS_MEMBERS_USERNAME +  ") " +
                "VALUES (?,?)";
    }

    public String groupMemberRelationshipGetQuery(){
        return "SELECT * FROM "+ GROUP_HAS_MEMBERS_TABLE +
                " WHERE " + GROUP_HAS_MEMBERS_GROUP_ID +
                "=?";
    }
}
