package com.myproject.jdbctest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcUtils {
    private static final String sqlSelectAllPersons = "SELECT * FROM employees";
    private static final String setJohnAsJohnny = "UPDATE EMPLOYEES SET FIRST_NAME = ? WHERE FIRST_NAME = ?";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/demo?serverTimezone=UTC";
    private static final String password = "lukas";
    private static final String username = "lukas";

    private static void connectToDb() {
        try (Connection connection = DriverManager.getConnection(dbUrl, username, password)) {
            System.out.println("Database connected");
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
