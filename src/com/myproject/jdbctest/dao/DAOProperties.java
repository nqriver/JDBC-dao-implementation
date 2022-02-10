package com.myproject.jdbctest.dao;

import com.myproject.jdbctest.dao.exception.DAOConfigurationException;
import com.myproject.jdbctest.dao.exception.DAOException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DAOProperties {
    private static final String PROPERTIES_FILE = "dao.properties";
    private static final Properties PROPERTIES = new Properties();
    private final String specificKey;


    static {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream propertiesFile = classLoader.getResourceAsStream(PROPERTIES_FILE);

        if (propertiesFile == null) {
            throw new DAOConfigurationException("Properties file '" + PROPERTIES_FILE + "' is missing in a classpath");
        }

        try {
            PROPERTIES.load(propertiesFile);
        } catch (IOException exception) {
            throw new DAOConfigurationException("Cannot load properties file '" + PROPERTIES_FILE + "'.", exception);
        }
    }


    public DAOProperties(String specificKey) {
        this.specificKey = specificKey;
    }


    public String getProperty(String key) {
        String fullKey = specificKey + "." + key;
        String property = PROPERTIES.getProperty(fullKey);

        if (property == null || property.trim().length() == 0) {
            throw new DAOException("Required property '" + fullKey +"' is missing in properties file '" + PROPERTIES_FILE + "'.");
        }
        return property;
    }

}
