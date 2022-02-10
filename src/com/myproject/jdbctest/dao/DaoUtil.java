package com.myproject.jdbctest.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public final class DaoUtil {

    private DaoUtil() {

    }

    public static PreparedStatement prepareStatement(
            Connection connection, String sqlStatement,
            boolean returnGeneratedKeys, Object... values) throws SQLException {

        PreparedStatement statement = connection.prepareStatement(sqlStatement,
                returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        setValues(statement, values);
        return statement;
    }

    private static void setValues(PreparedStatement statement, Object[] values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            statement.setObject(i + 1, values[i]);
        }
    }


}
