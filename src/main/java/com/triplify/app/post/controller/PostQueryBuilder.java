package com.triplify.app.post.controller;

import com.triplify.app.post.model.Post;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import static com.triplify.app.post.database.PostDatabaseConstant.*;

public class PostQueryBuilder {
    public static int insertPostQuery(final Post post, Connection connection) {
        String query = "INSERT INTO " + post_table + "(" +
                post_table_destination + ", " +
                post_table_image + ", " +
                post_table_details + ", " +
                post_table_posted_date + ", " +
                post_table_username + ") " +
                "VALUES (?,?,?,?,?);";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, post.getDestination());
            preparedStatement.setBlob(2, post.getPostImage());
            preparedStatement.setString(3, post.getDetails());
            preparedStatement.setString(4, post.getPostedDate());
            preparedStatement.setString(5, post.getUsername());
            return preparedStatement.executeUpdate();

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
