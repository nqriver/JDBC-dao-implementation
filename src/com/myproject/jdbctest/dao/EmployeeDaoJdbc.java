package com.myproject.jdbctest.dao;

import com.myproject.jdbctest.model.Employee;
import com.myproject.jdbctest.dao.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.myproject.jdbctest.dao.DaoUtil.prepareStatement;

public class EmployeeDaoJdbc implements EmployeeDAO {

    private static final String SQL_FIND_BY_ID =
            "SELECT id, first_name, last_name, email, department, salary FROM employees where id = ?";
    private static final String SQL_FIND_BY_EMAIL =
            "SELECT id, first_name, last_name, email, department, salary FROM employees where email = ?";
    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT id, first_name, last_name, email, department, salary FROM employees order by ID";
    private static final String SQL_UPDATE =
            "UPDATE Employees SET first_name = ?, last_name = ?, email = ?, department = ?, salary = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM Employees WHERE id = ?";
    private static final String SQL_INSERT =
            "INSERT INTO Employees  (first_name, last_name, email, department, salary) VALUES (?, ?, ?, ?, ?)";


    private DAOFactory daoFactory;

    public EmployeeDaoJdbc(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }


    @Override
    public Employee find(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }

    private Employee find(String sqlStatement, Object... values) {
        Employee employee = null;
        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement preparedStatement = prepareStatement(connection, sqlStatement, false, values);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            employee = map(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    @Override
    public List<Employee> list() throws DAOException {
        List<Employee> employees = new ArrayList<>();

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet resultSet = preparedStatement.executeQuery()
        ) {
            while (resultSet.next()) {
                employees.add(map(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }

    @Override
    public void create(Employee employee) throws IllegalArgumentException, DAOException {
        if (employee.getId() != null) {
            throw new IllegalArgumentException("User already exists. The user ID is not null");
        }
        Object[] values = {
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartment(),
                employee.getSalary()
        };

        try (
                Connection connection = daoFactory.getConnection();
                PreparedStatement preparedStatement = prepareStatement(connection, SQL_INSERT, true, values)
        ) {
            int affectedRows = preparedStatement.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("Creating user failed. No rows affected");
            }

            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    employee.setId(generatedKeys.getLong(1));
                } else {
                    throw new DAOException("Creating user failed, no generated key obtained");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Employee employee) throws IllegalArgumentException, DAOException {

    }

    @Override
    public void delete(Employee employee) throws DAOException {

    }

    @Override
    public boolean existEmail(String email) throws DAOException {
        return false;
    }

    @Override
    public void increaseSalaries(String department, Double payRaiseValue) {
        try (
                Connection connection = daoFactory.getConnection();
                CallableStatement increaseStatement = connection.prepareCall(
                        "{call increase_salaries_for_department(?, ?)}");
        ) {
            increaseStatement.setString(1, department);
            increaseStatement.setDouble(2, payRaiseValue);
            increaseStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getCountForDepartment(String department) {
        int employeesCount = 0;
        try (
                Connection connection = daoFactory.getConnection();
                CallableStatement statement = connection.prepareCall(
                        "{call get_count_for_department(?,?)}");
        ) {
            statement.setString(1, department);
            statement.registerOutParameter(2, Types.INTEGER);
            statement.execute();
            employeesCount = statement.getInt(2);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeesCount;
    }

    private static Employee map(ResultSet resultSet) throws SQLException {
        Employee employee = new Employee();
        employee.setId(resultSet.getLong("id"));
        employee.setFirstName(resultSet.getString("first_name"));
        employee.setLastName(resultSet.getString("last_name"));
        employee.setDepartment(resultSet.getString("department"));
        employee.setEmail(resultSet.getString("email"));
        employee.setSalary(resultSet.getDouble("salary"));

        return employee;
    }
}
