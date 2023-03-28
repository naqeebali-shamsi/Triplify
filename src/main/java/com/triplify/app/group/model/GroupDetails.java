package com.triplify.app.group.model;

import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.group.controller.GroupDetailsSelectQuery;
import com.triplify.app.group.database.GroupInsertQueryBuilder;

import javax.persistence.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.triplify.app.group.database.GroupDetailsDatabaseConstant.*;

@Entity
public class GroupDetails implements IGroupDetails{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "group_id", nullable = false)
    private Long id;
    private String groupName;
    private String tripStartDate;
    private String tripEndDate;
    private String destination;
    private String groupDescription;
    private String tripType;
    private String groupMemberUsername;

    public GroupDetails(){

    }

    public GroupDetails(String groupName, String tripStartDate, String tripEndDate, String destination, String tripType, String groupDescription, String username) {
        this.groupName = groupName;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.destination = destination;
        this.tripType = tripType;
        this.groupDescription = groupDescription;
        this.groupMemberUsername = username;
    }

    public GroupDetails(Long id, String groupName, String tripStartDate, String tripEndDate, String destination, String tripType, String groupDescription, String username) {
        this.id = id;
        this.groupName = groupName;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.destination = destination;
        this.tripType = tripType;
        this.groupDescription = groupDescription;
        this.groupMemberUsername = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(String tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public String getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(String tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }

    public String getTripType() {
        return tripType;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getUsername() {
        return groupMemberUsername;
    }

    public void setUsername(String groupMemberUsername) {
        this.groupMemberUsername = groupMemberUsername;
    }

    public Connection makeDBConnection() throws DatabaseExceptionHandler {
        return DatabaseConnection.getInstance().getDatabaseConnection();
    }

    public List<GroupDetails> createAllGroupDetailsList() throws DatabaseExceptionHandler {

        Connection connection = makeDBConnection();
        List<GroupDetails> groupDetailsList = new ArrayList<>();

        try {
            GroupDetailsSelectQuery groupDetailsSelectQuery = new GroupDetailsSelectQuery();
            ResultSet groupDetailsResultSet = connection.createStatement().executeQuery(groupDetailsSelectQuery.selectQueryForGroup());
            while (groupDetailsResultSet.next()){
                Long id = groupDetailsResultSet.getLong(""+GROUP_DETAILS_ID);
                String groupName = groupDetailsResultSet.getString(""+GROUP_NAME);
                String groupStartDate = groupDetailsResultSet.getString(""+GROUP_TRIP_START_DATE);
                String groupEndDate = groupDetailsResultSet.getString(""+GROUP_TRIP_END_DATE);
                String groupDestination = groupDetailsResultSet.getString(""+GROUP_DESTINATION);
                String groupDescription = groupDetailsResultSet.getString(""+GROUP_DESCRIPTION);
                String groupType = groupDetailsResultSet.getString(""+GROUP_TYPE);
                String groupMemberUsername = groupDetailsResultSet.getString(""+ GROUP_MEMBER_USERNAME);

                GroupDetails groupDetails = new GroupDetails(id,groupName,groupStartDate,groupEndDate,groupDestination,groupType,groupDescription,groupMemberUsername);
                groupDetailsList.add(groupDetails);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupDetailsList;
    }

    public Map<String, Object> createGroupResponse(GroupDetails groupDetails) throws DatabaseExceptionHandler {

        Map<String, Object> response = new HashMap<>();
        try{
            Connection connection = makeDBConnection();
            Statement statement = connection.createStatement();

            GroupInsertQueryBuilder groupCreationQueryBuilder = new GroupInsertQueryBuilder();
            final String insertQuery = groupCreationQueryBuilder.insertGroupQuery(groupDetails);
            final int rowInserted = statement.executeUpdate(insertQuery, Statement.RETURN_GENERATED_KEYS);

            if(rowInserted > 0){
                System.out.println("Yes row is inserted !!");
            }

            response.put("SUCCESS", true);
            response.put("MESSAGE", "SUCCESSFULLY_GROUP_CREATED");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            response.put("SUCCESS", false);
            response.put("MESSAGE", "GROUP_CREATION_FAILED");
        }
        return response;
    }
}
