package com.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DatabaseConnection {

    private static Connection connection = null;

    // Method to create and return the connection
    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/tourist_guide_system",
                    "root",
                    "Kartik@1806"
                );
            }
        } catch (Exception e) {
            System.out.println("Database Connection Error:");
            e.printStackTrace();
        }
        return connection;
    }

    // Method to close the connection
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // For SELECT queries
    public static ResultSet getResultFromSqlQuery(String query) {
        ResultSet rs = null;
        try {
            Connection con = getConnection();
            PreparedStatement ps = con.prepareStatement(query);
            rs = ps.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception
        }
        return rs;
    }


    // For INSERT/UPDATE/DELETE queries
    public static int insertUpdateFromSqlQuery(String sqlQuery) {
        int result = 0;
        try {
            getConnection();
            Statement stmt = connection.createStatement();
            result = stmt.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    // ID Generators
    public static String generateTourFeedbackId() {
        return "F" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String generateTourUserId() {
        return "U" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static String generateTourPlaceId() {
        return "T" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
}
