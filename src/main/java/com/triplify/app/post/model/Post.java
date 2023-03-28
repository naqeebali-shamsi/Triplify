package com.triplify.app.post.model;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.sql.SQLException;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name="destination")
    private String destination;

    @Lob
    @NotNull
    @Column(name = "image")
    private Blob postImage;

    @NotNull
    private byte[] postImageBytes;

    @Column(name="details")
    private String details;
    @Column(name="postedDate")
    private String postedDate;
    @Column(name="username")
    private String username;

    public Post() {
    }
    public Post(Long id, String destination, Blob postImage, String details, String postedDate, String username, byte[] postImageBytes) {
        this.id = id;
        this.destination = destination;
        this.postImage = postImage;
        this.details = details;
        this.postedDate = postedDate;
        this.username = username;
        this.postImageBytes = postImageBytes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public Blob getPostImage() {
        try {
            Blob blob = new SerialBlob(this.postImageBytes);
            return blob;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPostImageBytes(byte[] postImageBytes) {
        this.postImageBytes = postImageBytes;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
