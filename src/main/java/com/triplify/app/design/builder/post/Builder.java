package com.triplify.app.design.builder.post;

import java.sql.Blob;

public class Builder {
    protected String creator;
    protected String location;
    protected String description;
    protected String createdAt;
    protected Blob media;

    public Builder(){

    }
    public Builder creator(String creator){
        this.creator = creator;
        return this;
    }
    public Builder location(String location){
        this.location = location;
        return this;
    }
    public Builder description(String description){
        this.description = description;
        return this;
    }
    public Builder createdAt(String createdAt){
        this.createdAt = createdAt;
        return this;
    }
    public Builder media(Blob media){
        this.media = media;
        return this;
    }
}
