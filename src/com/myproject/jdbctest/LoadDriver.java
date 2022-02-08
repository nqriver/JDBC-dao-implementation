package com.myproject.jdbctest;

import java.sql.*;


public class LoadDriver {
    public static void main(String[] args) {

        final String sqlSelectAllPersons = "SELECT * FROM employees";
        final String connectionUrl = "jdbc:mysql://localhost:3306/demo?serverTimezone=UTC";

        try {
            Connection conn = DriverManager.getConnection(connectionUrl, "lukas", "lukas");
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