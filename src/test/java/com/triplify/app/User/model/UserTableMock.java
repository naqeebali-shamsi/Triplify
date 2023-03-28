package com.triplify.app.User.model;

public class UserTableMock implements IAuthenticationResult {
    @Override
    public AuthenticationResult login(String emailAddress, String password) {
        if ((emailAddress == null && password == null)
                || (emailAddress == null && password.equals("Vir@l2330"))
                || (emailAddress.equals("viralsid2330@gmail.com")  && password == null)
                || (emailAddress.endsWith("yahoo.com") && password == null)
                || (emailAddress.equals("viralsid2330@gmail.com") && password.equals("Vir@l"))
                || (emailAddress.equals("viral@gmail.com") && password.equals("Vir@l2330"))) {
            return AuthenticationResult.FAILURE;
        } else if (emailAddress.equals("viralsid2330@gmail.com") && password.equals("Vir@l2330")){
            return AuthenticationResult.SUCCESS;
        } else {
            return AuthenticationResult.FAILURE;
        }
    }

    @Override
    public AuthenticationResult register(String username, String firstname, String lastname, String emailAddress, String password, String dob) {
        if (username == null || firstname == null || lastname == null || emailAddress == null || password == null || dob == null){
            return AuthenticationResult.FAILURE;
        } else if (username.equals("viral123") && firstname.equals("Viral") && lastname.equals("Siddhapura") && emailAddress.equals("viralsid2330@gmail.com") && password.equals("Vir@l2330") && dob.equals("12-22-2022")){
            return AuthenticationResult.SUCCESS;
        } else {
            return AuthenticationResult.FAILURE;
        }
    }
}
