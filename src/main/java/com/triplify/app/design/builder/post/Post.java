package com.triplify.app.design.builder.post;

import java.sql.Blob;

public class Post extends Builder{
    private final String creator;
    private final String location;
    private final String description;
    private final String createdAt;
    private final Blob media;

    private Post(Builder builder){
        this.creator = builder.creator;
        this.location = builder.location;
        this.description = builder.description;
        this.createdAt = builder.createdAt;
        this.media = builder.media;
    }

    public String getCreator() {
        return creator;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public Blob getMedia() {
        return media;
    }
    public Post build(){
        return new Post(this);
    }
}
