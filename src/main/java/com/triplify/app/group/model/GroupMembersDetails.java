package com.triplify.app.group.model;

import com.triplify.app.User.controller.UserController;
import com.triplify.app.User.model.UserTable;
import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.group.controller.GroupController;
import com.triplify.app.group.controller.GroupFactory;
import com.triplify.app.group.controller.GroupMembersDetailsSelectQuery;
import com.triplify.app.group.database.GroupMemberDetailsQueryBuilder;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.triplify.app.group.database.GroupMemberDetailsDatabaseConstant.*;

public class GroupMembersDetails {

    private Long id;
    private String groupName;
    private String groupDestination;
    private String groupMemberFirstName;
    private String groupMemberLastName;
    private Long user_id;

    public GroupMembersDetails() {

    }

    public GroupMembersDetails(Long id, String groupName, String groupDestination, String groupMemberFirstName, String groupMemberLastName, Long user_id) {
        this.id = id;
        this.groupName = groupName;
        this.groupDestination = groupDestination;
        this.groupMemberFirstName = groupMemberFirstName;
        this.groupMemberLastName = groupMemberLastName;
        this.user_id = user_id;
    }

    public GroupMembersDetails(String groupName, String groupDestination, String groupMemberFirstName, String groupMemberLastName, Long user_id) {
        this.groupName = groupName;
        this.groupDestination = groupDestination;
        this.groupMemberFirstName = groupMemberFirstName;
        this.groupMemberLastName = groupMemberLastName;
        this.user_id = user_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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

    public String getGroupDestination() {
        return groupDestination;
    }

    public void setGroupDestination(String groupDestination) {
        this.groupDestination = groupDestination;
    }

    public String getGroupMemberFirstName() {
        return groupMemberFirstName;
    }

    public void setGroupMemberFirstName(String groupMemberFirstName) {
        this.groupMemberFirstName = groupMemberFirstName;
    }

    public String getGroupMemberLastName() {
        return groupMemberLastName;
    }

    public void setGroupMemberLastName(String groupMemberLastName) {
        this.groupMemberLastName = groupMemberLastName;
    }

    @Override
    public String toString() {
        return "GroupMembersDetails{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                ", groupDestination='" + groupDestination + '\'' +
                ", groupMemberFirstName='" + groupMemberFirstName + '\'' +
                ", groupMemberLastName='" + groupMemberLastName + '\'' +
                '}';
    }

    public Connection makeDBConnection() throws DatabaseExceptionHandler {
        return DatabaseConnection.getInstance().getDatabaseConnection();
    }

    public List<GroupMembersDetails> getAllMembersForGroup(GroupMembersDetails groupMembersDetails) throws DatabaseExceptionHandler {
        Connection connection = makeDBConnection();
        List<GroupMembersDetails> groupMembersDetailsList = new ArrayList<>();

        try {
            GroupMembersDetailsSelectQuery groupMembersDetailsSelectQuery = new GroupMembersDetailsSelectQuery();
            ResultSet resultSet = connection.createStatement().executeQuery(groupMembersDetailsSelectQuery.selectGroupMemberDetailsQuery());

            while (resultSet.next()){
                groupMembersDetails.setId(resultSet.getLong(""+GROUP_MEMBER_DETAILS_ID));
                groupMembersDetails.setGroupName(resultSet.getString(""+GROUP_MEMBER_DETAILS_GROUP_NAME));
                groupMembersDetails.setGroupDestination(resultSet.getString(""+GROUP_MEMBER_DETAILS_DESTINATION));
                groupMembersDetails.setGroupMemberFirstName(resultSet.getString(""+GROUP_MEMBER_DETAILS_FIRST_NAME));
                groupMembersDetails.setGroupMemberLastName(resultSet.getString(""+GROUP_MEMBER_DETAILS_LAST_NAME));
                groupMembersDetails.setUser_id(resultSet.getLong(""+GROUP_MEMBER_DETAILS_USER_ID));
                groupMembersDetailsList.add(groupMembersDetails);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return groupMembersDetailsList;
    }

    public Map<String, Object> addMemberInGroup(String username) throws DatabaseExceptionHandler {

        Map<String,Object> response  = new HashMap<>();
        List<UserTable> userTableList = new UserController().getAllUsers();
        List<GroupDetails> groupDetailsList = new GroupController().getAllGroupDetails();

        List<GroupMembersDetails> groupMembersDetailsList = getAllMembersForGroup(GroupFactory.factorySingleton().makeGroupMemberDetails());
        GroupMembersDetails groupMembersDetails = GroupFactory.factorySingleton().makeGroupMemberDetails();

        String groupMemberUsername = "";
        Long user_id = Long.valueOf(0);
        String userFirstName = "";
        String userLastName = "";

        for (int i = 0 ; i < userTableList.size() ; i++){
            if(userTableList.get(i).getUsername().equalsIgnoreCase(username)){
                user_id = userTableList.get(i).getId();
                groupMemberUsername = userTableList.get(i).getUsername();
                userFirstName = userTableList.get(i).getFirstname();
                userLastName = userTableList.get(i).getLastname();
            }
        }

        for(int i = 0 ; i < groupDetailsList.size() ; i++){

            for(int j = 0 ; j < groupMembersDetailsList.size() ; j++){
                if (groupDetailsList.get(i).getUsername().equals(groupMemberUsername) &&
                        groupDetailsList.get(i).getGroupName().equalsIgnoreCase(groupMembersDetailsList.get(j).getGroupName())) {
                    response.put("SUCCESS",false);
                    response.put("MESSAGE","Already " + groupMembersDetailsList.get(j).getGroupMemberFirstName() + " is added");
                    return response;
                } else if(groupMembersDetailsList.get(j).getUser_id().equals(user_id)){
                    response.put("SUCCESS",false);
                    response.put("MESSAGE","Already "+groupMembersDetailsList.get(j).getGroupMemberFirstName()+" is added");
                    return response;
                }
            }

            String groupName = groupDetailsList.get(i).getGroupName();
            String groupDestination = groupDetailsList.get(i).getDestination();

            groupMembersDetails.setGroupName(groupName);
            groupMembersDetails.setGroupDestination(groupDestination);
            groupMembersDetails.setGroupMemberFirstName(userFirstName);
            groupMembersDetails.setGroupMemberLastName(userLastName);
            groupMembersDetails.setUser_id(user_id);
            break;
        }

        try{
            Connection connection = makeDBConnection();
            Statement statement = connection.createStatement();

            GroupMemberDetailsQueryBuilder groupMemberDetailsQueryBuilder = new GroupMemberDetailsQueryBuilder();
            final String insertQueryGroupMember = groupMemberDetailsQueryBuilder.groupMemberInsertQuery(groupMembersDetails);
            final int rawInserted = statement.executeUpdate(insertQueryGroupMember, Statement.RETURN_GENERATED_KEYS);

            if(rawInserted > 0){
                response.put("SUCCESS", true);
                response.put("MESSAGE", "group Member created!!");
            }else{
                response.put("SUCCESS", false);
                response.put("MESSAGE", "Something went wrong!!");
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
            response.put("SUCCESS", false);
            response.put("MESSAGE", "group Member not created yet!! Something went wrong!!");
        }
        return response;
    }
}
