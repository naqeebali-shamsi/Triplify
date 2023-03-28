package com.triplify.app.User.controller;

import com.triplify.app.User.model.UserTable;

public class UserFactory implements IUserCreationFactory{

    private static UserFactory userFactory = null;

    public static IUserCreationFactory factorySingleton(){
        if (userFactory == null){
            userFactory = new UserFactory();
        }
        return userFactory;
    }

    @Override
    public UserTable makeUserTable() {
        return new UserTable();
    }
}
