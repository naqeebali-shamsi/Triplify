package com.triplify.app.User.model;

public interface IAuthenticationResult {
    enum AuthenticationResult
    {
        SUCCESS,
        FAILURE
    }

    AuthenticationResult login(String emailAddress, String password);

    AuthenticationResult register(String username, String firstname, String lastname, String emailAddress, String password, String dob);

}
