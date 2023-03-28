package com.triplify.app.utils;

import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.group.database.GroupMemberDetailsQueryBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.triplify.app.group.database.GroupMemberDetailsDatabaseConstant.GROUP_HAS_MEMBERS_USERNAME;
import static com.triplify.app.location.database.LocationDatabaseConstant.*;

public class Seeker {
    public List<Map<String, Object>> findUserLocation(List<String> usernames)
    {
        List<Map<String, Object>> response = new ArrayList<>();
        String query = "SELECT * FROM " + USER_LOCATION_TABLE +
                " WHERE " + USER_LOCATION_USERNAME + "=?";
        for(String username:usernames) {
            try {
                Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    Map<String, Object> userLocationMap = new HashMap<>();
                    userLocationMap.put("username", resultSet.getString(USER_LOCATION_USERNAME));
                    userLocationMap.put("longitude", resultSet.getDouble(USER_LOCATION_LONGITUDE));
                    userLocationMap.put("latitude", resultSet.getDouble(USER_LOCATION_LATITUDE));
                    response.add(userLocationMap);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            } catch (DatabaseExceptionHandler e) {
                throw new RuntimeException(e);
            }
        }
        return response;
    }

    public List<String> findMembersInGroup(Long group_id) {
        List<String> usernames = new ArrayList<>();
        try {
            Connection dbConnection = DatabaseConnection.getInstance().getDatabaseConnection();
            GroupMemberDetailsQueryBuilder queries = new GroupMemberDetailsQueryBuilder();
            PreparedStatement preparedStatement = dbConnection.prepareStatement(queries.groupMemberRelationshipGetQuery());
            preparedStatement.setLong(1, group_id);
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) {
                usernames.add(results.getString(GROUP_HAS_MEMBERS_USERNAME));
            }
            return usernames;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (DatabaseExceptionHandler e) {
            throw new RuntimeException(e);
        }
    }
}
