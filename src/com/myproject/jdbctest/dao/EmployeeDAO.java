package com.myproject.jdbctest.dao;

import com.myproject.jdbctest.model.Employee;
import com.myproject.jdbctest.dao.exception.DAOException;

import java.util.List;

public interface EmployeeDAO {

    Employee find(Long id) throws DAOException;

    List<Employee> list() throws DAOException;

    void create(Employee employee) throws IllegalArgumentException, DAOException;

    void update(Employee employee) throws IllegalArgumentException, DAOException;

    void delete(Employee employee) throws DAOException;

    boolean existEmail(String email) throws DAOException;

    void increaseSalaries(String department, Double payRaiseValue);

    int getCountForDepartment(String department);
}
