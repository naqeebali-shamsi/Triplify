package com.triplify.app.group.database;

import com.triplify.app.group.controller.IGroupInsertQueryBuilder;
import com.triplify.app.group.model.GroupDetails;

import static com.triplify.app.group.database.GroupDetailsDatabaseConstant.*;

public class GroupInsertQueryBuilder implements IGroupInsertQueryBuilder {

    public String insertGroupQuery(final GroupDetails groupDetails){
        return "INSERT INTO "+ GROUP_DETAILS_TABLE + "(" +
                GROUP_NAME + ", " +
                GROUP_TRIP_START_DATE + ", " +
                GROUP_TRIP_END_DATE + ", " +
                GROUP_DESTINATION + ", " +
                GROUP_DESCRIPTION + ", " +
                GROUP_TYPE + ", " +
                GROUP_MEMBER_USERNAME + ") " +
                "VALUES (" +
                "\"" +groupDetails.getGroupName()+ "\", " +
                "\"" +groupDetails.getTripStartDate()+ "\", " +
                "\"" +groupDetails.getTripEndDate() + "\", " +
                "\"" +groupDetails.getDestination() + "\", " +
                "\"" +groupDetails.getGroupDescription() + "\", " +
                "\"" +groupDetails.getTripType() + "\", " +
                "\"" +groupDetails.getUsername() + "\"" +
                ");";
    }
}
