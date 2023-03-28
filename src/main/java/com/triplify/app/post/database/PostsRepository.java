package com.triplify.app.post.database;

import com.triplify.app.design.builder.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post, Long> {
}
