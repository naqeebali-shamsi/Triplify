package com.triplify.app.group.controller;

import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.group.controller.algo.Find;
import com.triplify.app.group.database.GroupMemberDetailsQueryBuilder;
import com.triplify.app.group.model.GroupDetails;
import org.springframework.web.bind.annotation.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.triplify.app.group.database.GroupDetailsDatabaseConstant.*;
import static com.triplify.app.group.database.GroupMemberDetailsDatabaseConstant.GROUP_HAS_MEMBERS_GROUP_ID;
import static com.triplify.app.group.database.GroupMemberDetailsDatabaseConstant.GROUP_HAS_MEMBERS_USERNAME;

@RestController
@CrossOrigin
public class GroupController implements IGroupController {

    private GroupDetails createGroupDetails() {
        IGroupCreationFactory iGroupCreationFactory = GroupFactory.factorySingleton();
        return iGroupCreationFactory.makeGroupDetails();
    }

    @GetMapping("/groups")
    public List<GroupDetails> getAllGroupDetails() throws DatabaseExceptionHandler {
        GroupDetails groupDetails = createGroupDetails();
        return groupDetails.createAllGroupDetailsList();
    }

    @PostMapping("/groups/groupMemberDelete")
    public Map<String, Object> memberDelete(@RequestParam("username") String username,
                                            @RequestParam("group_id") Long group_id)
            throws DatabaseExceptionHandler, SQLException {

        Map<String, Object> responseMemberDelete = new HashMap<>();
        Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();

        Statement statement = connection.createStatement();
        DeleteGroupMemberFromGroupQueryBuilder deleteGroupMemberFromGroupQueryBuilder
                = new DeleteGroupMemberFromGroupQueryBuilder();
        final int rowDeleted = statement.executeUpdate(deleteGroupMemberFromGroupQueryBuilder.deleteMember(username,group_id));

        if(rowDeleted > 0){
            responseMemberDelete.put("SUCCESS",true);
            responseMemberDelete.put("MESSAGE","USER DELETED");
        }else {
            responseMemberDelete.put("SUCCESS",false);
            responseMemberDelete.put("MESSAGE","USER NOT DELETED!!");
        }
        if(connection!=null){
            connection.close();
        }
        return responseMemberDelete;
    }

    @GetMapping("/groups/{group_id}")
    public Map<String, Object> getGroup(@PathVariable("group_id") long group_id)
            throws DatabaseExceptionHandler, SQLException {

        Connection dbConnection = DatabaseConnection.getInstance().getDatabaseConnection();
        String query = "SELECT * FROM "+GROUP_DETAILS_TABLE +
                " WHERE "+ GROUP_DETAILS_ID +
                "=?";

        PreparedStatement preparedStatement = dbConnection.prepareStatement(query);
        preparedStatement.setLong(1,group_id);
        ResultSet result = preparedStatement.executeQuery();

        Map<String, Object> group = new HashMap<>();
        while (result.next()){
            group.put("group_id", result.getLong(GROUP_DETAILS_ID));
            group.put("name", result.getString(""+GROUP_NAME));
            group.put("start_date", result.getString(""+GROUP_TRIP_START_DATE));
            group.put("end-date", result.getString(""+GROUP_TRIP_END_DATE));
            group.put("destination", result.getString(""+GROUP_DESTINATION));
            group.put("description", result.getString(""+GROUP_DESCRIPTION));
            group.put("type", result.getString(""+GROUP_TYPE));
            group.put("username", result.getString(""+GROUP_MEMBER_USERNAME));
        }

        if(dbConnection!=null){
            dbConnection.close();
        }

        return group;
    }

    @GetMapping("/groups/{group_id}/members")
    public Map<String ,Object> getGroupMembers(@PathVariable("group_id") long group_id) throws DatabaseExceptionHandler{

        Map<String, Object> response = new HashMap<>();

        try {
            Connection dbConnection = DatabaseConnection.getInstance().getDatabaseConnection();

            GroupMemberDetailsQueryBuilder queries = new GroupMemberDetailsQueryBuilder();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(queries.groupMemberRelationshipGetQuery());
            preparedStatement.setLong(1,group_id);
            ResultSet results = preparedStatement.executeQuery();

            List<Map<String, Object>> members = new ArrayList<>();
            Find seeker = new Find();

            while(results.next()){
                Map<String, Object> userMap= new HashMap<>();
                String username = results.getString(GROUP_HAS_MEMBERS_USERNAME);
                userMap.put("username",username);
                members.add(userMap);
            }

            for(Map<String, Object> member: members){
                member.put("id", seeker.findUserIdByUsername((String) member.get("username")));
            }

            if(dbConnection!=null){
                dbConnection.close();
            }

            response.put("members", members);

        } catch (SQLIntegrityConstraintViolationException e){
            response.put("Error", "User does not exist.");
            return response;
        } catch (SQLException sqlException){
            response.put("Error", sqlException.getMessage());
        }

        return response;
    }

    @PostMapping("/groups/groupsByUsername")
    public Map<String,Object> getGroupsByUsername(@RequestParam("username") String username) throws DatabaseExceptionHandler, SQLException {

        Map<String,Object> responseObject = new HashMap<>();

        List<GroupDetails> groupDetailsList = new ArrayList<>();
        Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();

        GroupHasMembersSelectQuery groupHasMembersSelectQuery = new GroupHasMembersSelectQuery();

        ResultSet resultSet = connection.createStatement().executeQuery(groupHasMembersSelectQuery.selectQuery());
        List<Long> groupIds = new ArrayList<>();

        while (resultSet.next()){
            if (resultSet.getString(""+GROUP_HAS_MEMBERS_USERNAME).equalsIgnoreCase(""+username)){
                groupIds.add(resultSet.getLong(""+GROUP_HAS_MEMBERS_GROUP_ID));
            }
        }

        Connection connectionToGroupTable = DatabaseConnection.getInstance().getDatabaseConnection();
        for(int i = 0 ; i < groupIds.size() ; i++){
            ResultSet resultSetGroupDetails = connectionToGroupTable.createStatement().executeQuery("select * from group_details where id_group_details = "+groupIds.get(i));
            GroupDetails groupDetails = createGroupDetails();
            while (resultSetGroupDetails.next()){
                groupDetails.setId(resultSetGroupDetails.getLong(""+GROUP_DETAILS_ID));
                groupDetails.setGroupName(resultSetGroupDetails.getString(""+GROUP_NAME));
                groupDetails.setTripStartDate(resultSetGroupDetails.getString(""+GROUP_TRIP_START_DATE));
                groupDetails.setTripEndDate(resultSetGroupDetails.getString(""+GROUP_TRIP_END_DATE));
                groupDetails.setDestination(resultSetGroupDetails.getString(""+GROUP_DESTINATION));
                groupDetails.setGroupDescription(resultSetGroupDetails.getString(""+GROUP_DESCRIPTION));
                groupDetails.setTripType(resultSetGroupDetails.getString(""+GROUP_TYPE));
                groupDetails.setUsername(resultSetGroupDetails.getString(""+ GROUP_MEMBER_USERNAME));

                groupDetailsList.add(groupDetails);
            }
        }

        if(groupDetailsList.size() > 0){
            responseObject.put("SUCCESS",true);
            responseObject.put("MESSAGE","Congratulations!! you're in the group");
            responseObject.put("groupDetails",groupDetailsList);
        } else {
            responseObject.put("SUCCESS",false);
            responseObject.put("MESSAGE","Bad luck!! you're not in any of the group");
            responseObject.put("groupDetails", new ArrayList<>());
        }

        if(connection!=null){
            connection.close();
        }
        return responseObject;
    }

    @PostMapping("/groups/{group_id}/add/member")
    public Map<Long, List<String>> addGroupMembers(@PathVariable("group_id") long group_id,
                                                   @RequestParam("username") String username) throws DatabaseExceptionHandler, SQLException{

        Find seeker = new Find();
        GroupMemberDetailsQueryBuilder queries = new GroupMemberDetailsQueryBuilder();

        Connection dbConnection = DatabaseConnection.getInstance().getDatabaseConnection();
        PreparedStatement insertStatement = dbConnection.prepareStatement(queries.groupMemberRelationshipInsertQuery());
        insertStatement.setLong(1,group_id);
        insertStatement.setString(2, username);

        try {
            insertStatement.execute();
            Map<Long, List<String>> response = new HashMap<>();
            List<String> usernames = seeker.findUsersInGroup(group_id);
            response.put(group_id, usernames);
            return response;
        } catch (Exception e){
            e.printStackTrace();
            Map<Long,List<String>> failureResponse = new HashMap<>();
            failureResponse.put(Long.valueOf(1),new ArrayList<String>());
            return failureResponse;
        }
    }

    @PostMapping("/groups/createGroup")
    public Map<String, Object> createGroup(@RequestParam("groupName") String groupName,
                                           @RequestParam("groupStartDate") String tripStartDate,
                                           @RequestParam("groupEndDate") String tripEndDate,
                                           @RequestParam("groupDestination") String destination,
                                           @RequestParam("groupDescription") String groupDescription,
                                           @RequestParam("groupType") String tripType,
                                           @RequestParam("username") String username) throws DatabaseExceptionHandler, SQLException {

        GroupDetails groupDetails = createGroupDetails();
        groupDetails.setGroupName(groupName);
        groupDetails.setTripStartDate(tripStartDate);
        groupDetails.setTripEndDate(tripEndDate);
        groupDetails.setDestination(destination);
        groupDetails.setGroupDescription(groupDescription);
        groupDetails.setTripType(tripType);
        groupDetails.setUsername(username);

        List<GroupDetails> listOfGroups = groupDetails.createAllGroupDetailsList();
        Map<String, Object> response = new HashMap<>();

        for(GroupDetails group : listOfGroups){
            if(group.getGroupName().equalsIgnoreCase(groupDetails.getGroupName()) &&
                group.getGroupDescription().equalsIgnoreCase(groupDetails.getGroupDescription()) &&
                group.getTripStartDate().equalsIgnoreCase(groupDetails.getTripStartDate()) &&
                group.getTripEndDate().equalsIgnoreCase(groupDetails.getTripEndDate()) &&
                group.getTripType().equalsIgnoreCase(groupDetails.getTripType()) &&
                group.getDestination().equalsIgnoreCase(groupDetails.getDestination())){

                response.put("SUCCESS", false);
                response.put("MESSAGE", "group is already exists!!");
                return response;
            }
        }

        GroupDetails groupDetailsForCreateGroupResponse = createGroupDetails();

        Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();
        ResultSet resultSet = connection.createStatement().executeQuery("select * from `group_details`");
        Long group_id = Long.valueOf(0);
        while (resultSet.next()){
            if(resultSet.getString(""+GROUP_MEMBER_USERNAME).equals(""+username)){
                group_id = resultSet.getLong(""+GROUP_DETAILS_ID);
            }
        }
        connection = DatabaseConnection.getInstance().getDatabaseConnection();
        GroupMemberDetailsQueryBuilder groupMemberDetailsQueryBuilder = new GroupMemberDetailsQueryBuilder();
        PreparedStatement insertStatement = connection.prepareStatement(groupMemberDetailsQueryBuilder.groupMemberRelationshipInsertQuery());
        insertStatement.setLong(1,group_id);
        insertStatement.setString(2,username);
        insertStatement.execute();

        response = groupDetailsForCreateGroupResponse.createGroupResponse(groupDetails);
        return response;
    }

}
