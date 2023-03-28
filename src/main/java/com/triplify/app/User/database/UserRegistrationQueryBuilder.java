package com.triplify.app.User.database;

import com.triplify.app.User.model.UserTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.triplify.app.User.database.UserDatabaseConstant.*;

public class UserRegistrationQueryBuilder {
    public int insertQuery(final UserTable userTable, Connection connection){
        String query = "INSERT INTO "+ user_table + "(" +
                user_table_username + ", " +
                user_table_first_name + ", " +
                user_table_last_name + ", " +
                user_table_email_address + ", " +
                user_table_password + ", " +
                user_table_is_logged_in + ", " +
                user_image + ") " +
                "VALUES (?,?,?,?,?,?,?);";
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, userTable.getUsername());
            pstmt.setString(2, userTable.getFirstname());
            pstmt.setString(3, userTable.getLastname());
            pstmt.setString(4, userTable.getEmailAddress());
            pstmt.setString(5, userTable.getPassword());
            pstmt.setBoolean(6, false);
            pstmt.setBlob(7, userTable.getProfPicBlob());
            return pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
