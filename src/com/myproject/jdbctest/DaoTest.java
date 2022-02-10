package com.myproject.jdbctest;

import com.myproject.jdbctest.dao.DAOFactory;
import com.myproject.jdbctest.dao.EmployeeDAO;
import com.myproject.jdbctest.model.Employee;

public class DaoTest {
    public static void main(String[] args) {
        DAOFactory javabase = DAOFactory.getInstance("demo");
        System.out.println("DAOFActory successfully obtained");

        EmployeeDAO employeeDAO = javabase.getEmployeeDAO();

        Employee employee = new Employee();
        employee.setEmail("lukas@foobar.com");
        employee.setDepartment("IT");
        employee.setFirstName("Lukas");
        employee.setLastName("Karas");
        employee.setSalary(9000.0);
//        employeeDAO.create(employee);


        System.out.println(employeeDAO.getCountForDepartment("IT"));
//        employeeDAO.increaseSalaries("IT", 1000.0);
    }
}
