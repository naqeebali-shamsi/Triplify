package com.triplify.app.post.controller;

import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.database.DatabaseExceptionHandler;
import com.triplify.app.post.model.Post;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.triplify.app.post.database.PostDatabaseConstant.*;

@RestController
@CrossOrigin
public class PostsController {

    PostQueryBuilder postQueryBuilder = new PostQueryBuilder();

    @GetMapping("/posts")
    public List<Post> getPosts() throws DatabaseExceptionHandler {
        Connection connection =
                DatabaseConnection.getInstance().getDatabaseConnection();
        List<Post> posts = new ArrayList<>();
        try {
            ResultSet postsResultSet =
                    connection.createStatement().executeQuery("select * from post");

            while (postsResultSet.next()) {
                Long id = postsResultSet.getLong("" + post_table_id);
                String destination = postsResultSet.getString("" + post_table_destination);
                Blob image = postsResultSet.getBlob("" + post_table_image);
                String details = postsResultSet.getString("" + post_table_details);
                String posted_date = postsResultSet.getString("" + post_table_posted_date);
                String username = postsResultSet.getString("" + post_table_username);
                System.out.println("karan" + image);
                Post post = new Post(id, destination, image, details, posted_date, username, image.getBytes(1, (int) image.length()));
                posts.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return posts;
    }

    @PostMapping(value = "/save/post", consumes = {"multipart/form-data"})
    public Map<String, Object> savePost(@RequestParam("destination") String destination,
                                        @RequestParam("details") String details,
                                        @RequestParam("postedDate") String postedDate,
                                        @RequestParam("username") String username,
                                        @RequestParam("image") MultipartFile imageFile) throws DatabaseExceptionHandler, IOException {
        Map<String, Object> response = new HashMap<>();
        response.put("SUCCESS", false);
        Post post = new Post();
        post.setDestination(destination);
        post.setDetails(details);
        post.setPostedDate(postedDate);
        post.setUsername(username);
        post.setPostImageBytes(imageFile.getBytes());
        try (final Connection connection = DatabaseConnection.getInstance().getDatabaseConnection();
             final Statement statement = connection.createStatement()) {
            final int rowInserted =
                    PostQueryBuilder.insertPostQuery(post, connection);

            if (rowInserted > 0) {
                response.put("SUCCESS", true);
            }
        } catch (SQLException e) {
            response.put("MESSAGE", e.getMessage());
        }
        return response;
    }
}
