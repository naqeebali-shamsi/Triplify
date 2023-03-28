package com.triplify.app.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection implements IDatabase{

    private static final String DB_CONFIG_FILE =
            "./databaseConfiguration.properties";

    private Connection connection = null;

    private static DatabaseConnection databaseConnection;

    private DatabaseConnection() {
        // Private Constructor here - Singleton Design Pattern
    }

    public static DatabaseConnection getInstance(){
        if(databaseConnection == null){
            databaseConnection = new DatabaseConnection();
        }
        return databaseConnection;
    }

    @Override
    public Connection getDatabaseConnection() throws DatabaseExceptionHandler {
        clearDatabaseConnection();
        connection = connectToDatabase();
        return connection;
    }

    private Connection connectToDatabase() throws DatabaseExceptionHandler {
        try {
            InputStream inputStream = new FileInputStream(DB_CONFIG_FILE);
            final Properties dbConfigProperties = new Properties();

            dbConfigProperties.load(inputStream);

            Class.forName(dbConfigProperties.getProperty("mysqlJDBCDriver")).getDeclaredConstructor().newInstance();

            final String dbType =
                    dbConfigProperties.getProperty("databaseType");

            final String dbURL =
                    dbConfigProperties.getProperty("databaseURL")
                    + dbConfigProperties.getProperty(dbType + "Database");

            final String dbUsername =
                    dbConfigProperties.getProperty(dbType+"Username");

            final String dbPassword =
                    dbConfigProperties.getProperty(dbType+"Password");

            return DriverManager.getConnection(dbURL,dbUsername,dbPassword);

        } catch (IOException | ClassNotFoundException | SQLException | NoSuchMethodException | InstantiationException |
                 IllegalAccessException | InvocationTargetException e) {
            throw new DatabaseExceptionHandler(e.getMessage(),e);
        }
    }

    @Override
    public void clearDatabaseConnection() {
        if(connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }else{
            connection = null;
        }
    }
}
