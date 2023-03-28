package com.triplify.app.design.builder.query;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import static com.triplify.app.group.database.GroupDetailsDatabaseConstant.*;

public class GroupInsertQueryBuilder implements QueryBuilder{
    private final String baseQuery = "INSERT INTO "+ GROUP_DETAILS_TABLE + "(" +
            GROUP_NAME + ", " +
            GROUP_TRIP_START_DATE + ", " +
            GROUP_TRIP_END_DATE + ", " +
            GROUP_DESTINATION + ", " +
            GROUP_TYPE + ", " +
            GROUP_MEMBER_USERNAME + ") " +
            "VALUES (?,?,?,?,?,?);";
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

    public GroupInsertQueryBuilder(QueryBuilder builder) {
    }

    @Override
    public GroupInsertQueryBuilder setType(String type) {
        this.type = type;
        return this;
    }

    @Override
    public GroupInsertQueryBuilder setTable(String table) {
        this.table = table;
        return this;
    }

    @Override
    public GroupInsertQueryBuilder setValues(List<Object> columns) {
        this.columns=columns;
        return this;
    }

    @Override
    public GroupInsertQueryBuilder setConnection(Connection connection) {
        this.connection = connection;
        try {
            preparedStatement = connection.prepareStatement(baseQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public GroupInsertQueryBuilder build(){
        return new GroupInsertQueryBuilder(this);
    }
}
