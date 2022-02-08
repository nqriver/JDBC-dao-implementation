package com.myproject.jdbctest;

import java.sql.*;


public class LoadDriver {

    private static final String sqlSelectAllPersons = "SELECT * FROM employees";
    private static final String setJohnAsJohnny = "UPDATE EMPLOYEES SET FIRST_NAME = ? WHERE FIRST_NAME = ?";
    private static final String dbUrl = "jdbc:mysql://localhost:3306/demo?serverTimezone=UTC";
    private static final String password = "lukas";
    private static final String username = "lukas";

    public static void main(String[] args) {
        try {
            Connection conn = DriverManager.getConnection(dbUrl, username, password);
            PreparedStatement updateStatement = conn.prepareStatement(setJohnAsJohnny);
            updateStatement.setString(1, "Johnny");
            updateStatement.setString(2, "John");

            updateStatement.executeUpdate();

            PreparedStatement ps = conn.prepareStatement(sqlSelectAllPersons);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                long id = rs.getLong("ID");
                String lastName = rs.getString("LAST_NAME");
                String first_name = rs.getString("FIRST_NAME");
                String email = rs.getString("EMAIL");
                String department = rs.getString("DEPARTMENT");
                    double salary = rs.getDouble("SALARY");

                    System.out.printf("We have got you: %s %s\n", first_name, lastName);
                }

            } catch(SQLException e){
                e.printStackTrace();
            }

        }
    }