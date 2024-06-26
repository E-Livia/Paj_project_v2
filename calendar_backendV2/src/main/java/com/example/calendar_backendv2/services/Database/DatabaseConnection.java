package com.example.calendar_backendv2.services.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://db-calendar-app.c72uccwwakb7.us-east-1.rds.amazonaws.com:3306/calendar_app";
    private static final String USER = "admin";
    private static final String PASSWORD = "rootadmin";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection connection) throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
