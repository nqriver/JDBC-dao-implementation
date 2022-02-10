package com.myproject.jdbctest.dao;

import com.myproject.jdbctest.dao.exception.DAOConfigurationException;

import java.sql.Connection;
import java.sql.SQLException;

public abstract class DAOFactory {
    private static final String PROPERTY_URL = "url";
    private static final String PROPERTY_USERNAME = "username";
    private static final String PROPERTY_PASSWORD = "password";


    public static DAOFactory getInstance(String name) {
        if (name == null) {
            throw new DAOConfigurationException("Database name is null");
        }

        DAOProperties properties = new DAOProperties(name);
        String url = properties.getProperty(PROPERTY_URL);
        String password = properties.getProperty(PROPERTY_PASSWORD);
        String username = properties.getProperty(PROPERTY_USERNAME);

        return new DriverManagerDAOFactory(url, username, password);
    }

    abstract Connection getConnection() throws SQLException;


    public  EmployeeDAO getEmployeeDAO() {
        return new EmployeeDaoJdbc(this);
    }
}
