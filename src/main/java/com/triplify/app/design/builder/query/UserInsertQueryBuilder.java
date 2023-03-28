package com.triplify.app.design.builder.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.triplify.app.User.database.UserDatabaseConstant.*;

public class UserInsertQueryBuilder implements QueryBuilder {
    private final String baseQuery = "INSERT INTO "+ user_table + "(" +
            user_table_username + ", " +
            user_table_first_name + ", " +
            user_table_last_name + ", " +
            user_table_email_address + ", " +
            user_table_password + ", " +
            user_table_is_logged_in + ", " +
            user_image + ") " +
            "VALUES (?,?,?,?,?,?,?);";
    private String type;
    private String table;
    private List<Object> columns;
    private String user;
    private Connection connection;

    public PreparedStatement getPreparedStatement() {
        int count = 1;
        if(null != columns){
            for(Object column: columns){
                try {
                    preparedStatement.setObject(count,column);
                    count += 1;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return preparedStatement;
    }

    private PreparedStatement preparedStatement;

    public UserInsertQueryBuilder(QueryBuilder builder) {
    }

    @Override
    public UserInsertQueryBuilder setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public UserInsertQueryBuilder setTable(String table) {
        this.table = table;
        return this;
    }

    @Override
    public UserInsertQueryBuilder setValues(List<Object> columns) {
        this.columns=columns;
        return this;
    }

    @Override
    public UserInsertQueryBuilder setConnection(Connection connection) {
        this.connection = connection;
        try {
            preparedStatement = connection.prepareStatement(baseQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public UserInsertQueryBuilder build(){
        return new UserInsertQueryBuilder(this);
    }
}
