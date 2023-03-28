package com.triplify.app.database;

import java.sql.Connection;

public interface IDatabase {

    Connection getDatabaseConnection() throws DatabaseExceptionHandler;

    void clearDatabaseConnection();

}
