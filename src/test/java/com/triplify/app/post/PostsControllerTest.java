package com.triplify.app.post;

import com.triplify.app.post.controller.PostQueryBuilder;
import com.triplify.app.database.DatabaseConnection;
import com.triplify.app.post.model.Post;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;

@DisplayName("Posts Controller Test")
public class PostsControllerTest {
    @InjectMocks
    private DatabaseConnection dbConnection;
    @Mock private Connection mockConnection;
    @Mock private PreparedStatement mockStatement;

    @Before("")
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    @DisplayName("post controller negative scenario: ")
    public void postQueryBuilderNegativeTest(){
        PostQueryBuilder postQueryBuilder = new PostQueryBuilder();
        Post post = new Post();
        String temp = "";
        post.setDestination("Australia");
        post.setDetails("details");
        post.setPostedDate("12-12-2022");
        post.setUsername("user");
        post.setPostImageBytes(temp.getBytes());
        postQueryBuilder.insertPostQuery(post, mockConnection);
    }
}
