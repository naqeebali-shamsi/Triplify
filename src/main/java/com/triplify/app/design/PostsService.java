package com.triplify.app.design;

import com.triplify.app.post.model.Post;

import java.util.List;

public interface PostsService {
    Post createPost(Post employee);

    List<Post> getAllPosts();

    boolean deletePost(Long id);

    Post getPostById(Long id);

    Post updatePost(Long id, Post employee);
}
