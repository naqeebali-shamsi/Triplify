package com.triplify.app.User.controller;
import com.triplify.app.User.model.UserTableMock;
import com.triplify.app.User.model.IAuthenticationResult;
import com.triplify.app.User.model.UserTable;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Testing all User Methods for login purpose")
public class UserControllerTest {

    @Test
    @DisplayName("Testing null emailAddress and null password test")
    void nullBothEmailAddressAndPasswordTest(){
        String emailAddress = "";
        String password = "";
        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.login(emailAddress,password);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"Both emailAddress & Password are null");
    }

    @Test
    @DisplayName("Testing null emailAddress and not null password test")
    void nullEmailAddressButPasswordNotNullTest(){
        String emailAddress = null;
        String password = "Vir@l2330";
        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.login(emailAddress,password);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"Only Email Address is null");
    }

    @Test
    @DisplayName("Testing not null emailAddress and null password test")
    void notNullEmailAddressButNullPasswordTest(){
        String emailAddress = "viralsid2330@gmail.com";
        String password = null;
        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.login(emailAddress,password);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"Only Password is null");
    }

    @Test
    @DisplayName("Testing not gmail.com emailAddress and null password test")
    void notEndingWithGmailEmailAddressAndPasswordNullTest(){
        String emailAddress = "viral@yahoo.com";
        String password = null;
        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.login(emailAddress,password);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"Email Address not ending with gmail.com null");
    }


    @Test
    @DisplayName("Testing valid emailAddress and wrong password test")
    void validEmailAddressButPasswordWrongTest(){
        String emailAddress = "viralsid2330@gmail.com";
        String password = "Vir@l";
        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.login(emailAddress,password);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"EmailAddress valid but password wrong");
    }

    @Test
    @DisplayName("Testing wrong emailAddress and valid password method")
    void nullEmailAddressButPasswordNotNull(){
        String emailAddress = "viral@gmail.com";
        String password = "Vir@l2330";
        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.login(emailAddress,password);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"Email Address was wrong");
    }

    @Test
    @DisplayName("Testing valid emailAddress and valid password method")
    void validEmailAddressAndValidPasswordTest(){
        String emailAddress = "viralsid2330@gmail.com";
        String password = "Vir@l2330";
        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.login(emailAddress,password);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.SUCCESS,result,"All is well :) !!");
    }

    @Test
    @DisplayName("Testing empty users list test")
    void getEmptyUsersTest(){
        List<UserTable> userTableList = new ArrayList<>();
        if (userTableList.size() == 0){
            Assertions.assertEquals(0,0,"Yes no user is there");
        } else {
            Assertions.assertEquals(0,1,"Yes no user is there");
        }
    }

    @Test
    @DisplayName("Testing not empty users list test")
    void getValidUsersTest(){
        List<UserTable> userTableList = new ArrayList<>();
        UserTable userTable = new UserTable();
        userTable.setFirstname("Viral");
        userTable.setLastname("Siddhapura");
        userTable.setEmailAddress("viralsid2330@gmail.com");
        userTable.setPassword("Vir@l2330");
        userTable.setDob("12-12-2022");
        userTableList.add(userTable);
        if (userTableList.size() > 0){
            Assertions.assertEquals(1,1,"Yes User list is there");
        } else {
            Assertions.assertEquals(1,0,"Yes no user is there");
        }
    }

    @Test
    @DisplayName("Valid Register testing")
    void validRegisterTest(){
        String userName = "viral123";
        String firstname = "Viral";
        String lastname = "Siddhapura";
        String dob = "12-22-2022";
        String emailAddress = "viralsid2330@gmail.com";
        String password = "Vir@l2330";

        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.register(userName, firstname, lastname, emailAddress,password, dob);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.SUCCESS,result,"All is well :) !!");
    }

    @Test
    @DisplayName("first name null in register testing")
    void nullUserNameInRegisterTest(){
        String userName = null;
        String firstname = "Viral";
        String lastname = "Siddhapura";
        String dob = "12-22-2022";
        String emailAddress = "viralsid2330@gmail.com";
        String password = "Vir@l2330";

        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.register(userName, firstname, lastname, emailAddress, password, dob);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"last name null in register testing!!");
    }

    @Test
    @DisplayName("first name null in register testing")
    void nullFirstNameInRegisterTest(){
        String userName = "viral123";
        String firstname = null;
        String lastname = "Siddhapura";
        String dob = "12-22-2022";
        String emailAddress = "viralsid2330@gmail.com";
        String password = "Vir@l2330";

        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.register(userName, firstname, lastname, emailAddress, password, dob);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"last name null in register testing!!");
    }

    @Test
    @DisplayName("last name null in register testing")
    void nullLastNameInRegisterTest(){
        String userName = "viral123";
        String firstname = "Viral";
        String lastname = null;
        String dob = "12-22-2022";
        String emailAddress = "viralsid2330@gmail.com";
        String password = "Vir@l2330";

        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.register(userName, firstname, lastname, emailAddress, password, dob);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"last name null in register testing!!");
    }

    @Test
    @DisplayName("Date Of Birth null in register testing")
    void nullDateofBirthInRegisterTest(){
        String userName = "viral123";
        String firstname = "Viral";
        String lastname = "Siddhapura";
        String dob = null;
        String emailAddress = "viralsid2330@gmail.com";
        String password = "Vir@l2330";

        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.register(userName, firstname, lastname, emailAddress, password, dob);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"Date Of Birth null in register testing!!");
    }

    @Test
    @DisplayName("password null in register testing")
    void nullPasswordFieldInRegisterTest(){
        String userName = "viral123";
        String firstname = "Viral";
        String lastname = "Siddhapura";
        String dob = "12-22-2022";
        String emailAddress = "viralsid2330@gmail.com";
        String password = null;

        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.register(userName, firstname, lastname, emailAddress, password, dob);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"password null in register testing!!");
    }

    @Test
    @DisplayName("emailAddress null in register testing")
    void nullEmailAddressInRegisterTest(){
        String userName = "viral123";
        String firstname = "Viral";
        String lastname = "Siddhapura";
        String dob = "12-22-2022";
        String emailAddress = null;
        String password = "Vir@l2330";

        IAuthenticationResult iAuthenticationResult = new UserTableMock();
        IAuthenticationResult.AuthenticationResult result = iAuthenticationResult.register(userName, firstname, lastname, emailAddress, password, dob);
        Assertions.assertEquals(IAuthenticationResult.AuthenticationResult.FAILURE,result,"emailAddress null in register testing!!");
    }

}
