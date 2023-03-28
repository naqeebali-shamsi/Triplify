package com.triplify.app.User.database;

import com.triplify.app.User.model.UserTable;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.triplify.app.User.database.UserDatabaseConstant.*;

public class UpdateUserTableQueryBuilder implements IUpdateUserTableQueryBuilder{
    @Override
    public int updateQuery(UserTable userTable, Connection connection) {
        String updateQuery =
                "UPDATE " + user_table + " SET " + user_table_first_name + " = ?" +
                ", " + user_table_last_name + " = ?" +
                ", " + user_table_email_address + " = ?" +
                ", " + user_image + " = ?" +
                " WHERE " + user_table_id + " = ?";

        try {
            PreparedStatement pstmt = connection.prepareStatement(updateQuery);
            pstmt.setString(1, userTable.getFirstname());
            pstmt.setString(2, userTable.getLastname());
            pstmt.setString(3, userTable.getEmailAddress());
            pstmt.setBlob(4, userTable.getProfPicBlob());
            pstmt.setLong(5,userTable.getId());
            return pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
