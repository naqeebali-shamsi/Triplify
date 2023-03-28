package com.triplify.app.User.model;

import javax.persistence.*;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

@Entity
public class UserTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String firstname;
    private String lastname;
    @NotNull
    @Email(message = "Email is not formatted well here")
    private String emailAddress;
    private boolean isLoggedIn;
    @NotNull
    private String password;
    private String username;
    @Lob
    @Column(name="prof_pic")
    private Blob profPic;
    private byte[] profPicBytes;
    private String dob;

    public Blob getProfPicBlob() {
        try {
            Blob blob = new SerialBlob(this.profPicBytes);
            return blob;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setProfPic(byte[] profPic) {
        this.profPicBytes = profPic;
    }

    public String getUsername() {
        return this.username;
    }

    public String getDob() {
        return dob;
    }

    public UserTable(){

    }

    public UserTable(Long id, String username, String firstname, String lastname, String emailAddress, String password, boolean isLoggedIn, Blob profPic) {
        this.id = id;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.emailAddress = emailAddress;
        this.password = password;
        this.profPic = profPic;
        this.isLoggedIn = isLoggedIn;
    }

    public UserTable(@NotBlank String emailAddress, @NotBlank String password){
        this.emailAddress = emailAddress;
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserTable{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", isLoggedIn=" + isLoggedIn +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                ", profPic=" + Arrays.toString(new Blob[]{profPic}) +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTable user)) return false;
        return Objects.equals(emailAddress, user.emailAddress) &&
                Objects.equals(password, user.password);
    }

    public boolean isLoggedIn() {
        return this.isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.isLoggedIn = loggedIn;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }
}
