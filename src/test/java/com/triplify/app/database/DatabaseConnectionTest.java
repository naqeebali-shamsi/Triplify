package com.triplify.app.database;

import org.junit.jupiter.api.*;

@DisplayName("Database connection test suite")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DatabaseConnectionTest {

    public static DatabaseConnection databaseConnection;

    @DisplayName("Database Connection Setting Up here...")
    @BeforeAll
    @Test
    public static void setDatabaseConnection(){
        databaseConnection = DatabaseConnection.getInstance();
    }

    @Test
    @DisplayName("Test database connection")
    @Order(1)
    public void getDatabaseConnection() {
        Assertions.assertDoesNotThrow(() -> databaseConnection.getDatabaseConnection(),
                "Failed to connect to the database...");
    }

    @Test
    @DisplayName("Clear the database Connection")
    @Order(2)
    public void clearDatabaseConnection(){
        Assertions.assertDoesNotThrow(() -> databaseConnection.getDatabaseConnection(),
                "Failed to clear the database connection...");
    }
}
