package com.triplify.app.design;

import com.triplify.app.post.database.PostsRepository;
import com.triplify.app.post.model.Post;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class PostalService implements PostsService{
    private final PostsRepository postsRepository;

    public PostalService(PostsRepository postsRepository){
        this.postsRepository = postsRepository;
    }

    @Override
    public Post createPost(Post post){
        Post postEntity = new Post();
        BeanUtils.copyProperties(post, postEntity);
        // postsRepository.save(postEntity);
        return post;
    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }

    @Override
    public boolean deletePost(Long id) {
        return false;
    }

    @Override
    public Post getPostById(Long id) {
        return null;
    }

    @Override
    public Post updatePost(Long id, Post employee) {
        return null;
    }
}
