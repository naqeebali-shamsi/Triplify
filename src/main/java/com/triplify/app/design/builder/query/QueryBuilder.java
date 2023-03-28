package com.triplify.app.design.builder.query;

import java.sql.Connection;
import java.util.List;

public interface QueryBuilder {
    QueryBuilder setType(String type);
    QueryBuilder setTable(String name);
    QueryBuilder setValues(List<Object> values);
    QueryBuilder setConnection(Connection connection);
}
