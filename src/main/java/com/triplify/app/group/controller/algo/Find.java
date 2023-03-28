package com.triplify.app.group.controller.algo;

import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.triplify.app.User.database.UserDatabaseConstant.*;
import static com.triplify.app.group.database.GroupMemberDetailsDatabaseConstant.*;

public class Find {
    public Long findUserIdByUsername(String username) throws DatabaseExceptionHandler, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;
        try {
            connection = DatabaseConnection.getInstance().getDatabaseConnection();
            String query = "SELECT " + user_table_id + " FROM " + user_table +
                    " WHERE " + user_table_username + "=?";

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);

            result = preparedStatement.executeQuery();
            while (result.next()) {
                return result.getLong(user_table_id);
            }

            return null;
        } finally {
            if (result != null) {
                result.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                DatabaseConnection.getInstance().clearDatabaseConnection();
            }
        }
    }

    public List<String> findUsersInGroup(Long group_id) throws DatabaseExceptionHandler, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet result = null;

        String query = "SELECT " + GROUP_HAS_MEMBERS_USERNAME +
                " FROM " + GROUP_HAS_MEMBERS_TABLE +
                " WHERE " + GROUP_HAS_MEMBERS_GROUP_ID +
                "=?";

        try {
            connection = DatabaseConnection.getInstance().getDatabaseConnection();
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setLong(1, group_id);
            result = preparedStatement.executeQuery();
            List<String> usernames = new ArrayList<>();

            while (result.next()) {
                usernames.add(result.getString(GROUP_HAS_MEMBERS_USERNAME));
            }

            return usernames;
        } finally {
            if (result != null) {
                result.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                DatabaseConnection.getInstance().clearDatabaseConnection();
            }
        }
    }
}
