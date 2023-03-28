package com.triplify.app.User.database;

import static com.triplify.app.User.database.UserDatabaseConstant.user_table;
import static com.triplify.app.User.database.UserDatabaseConstant.user_table_username;

public class UserDetailsQueryBuilder implements IUserDetailsQueryBuilder{

    @Override
    public String selectQuery() {
        return "SELECT * FROM " + user_table + " WHERE " + user_table_username + " = ";
    }
}
