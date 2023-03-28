package com.triplify.app.User.database;

import com.triplify.app.User.model.UserTable;

import java.sql.Connection;

public interface IUpdateUserTableQueryBuilder {

    int updateQuery(UserTable userTable, Connection connection);

}
