package com.employeemanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Add_Employee {

    public static void addNewEmployee(Map<Integer, Employee> employeeMap, Connection connection) {
        View_Employee.readEmployeeData(employeeMap);

        Scanner sc = new Scanner(System.in);

        // Get details for the new employee
        System.out.println("Enter the ID of the new employee:");
        int empId = sc.nextInt();

        System.out.println("Enter the name of the new employee:");
        sc.nextLine(); // Consume newline character
        String empName = sc.nextLine();

        System.out.println("Enter the role of the new employee:");
        String empRole = sc.nextLine();

        System.out.println("Enter the hire date of the new employee (YYYY-MM-DD):");
        String hireDateString = sc.next();
        java.sql.Date hireDate = java.sql.Date.valueOf(hireDateString);

        System.out.println("Enter the salary of the new employee:");
        double empSal = sc.nextDouble();

        System.out.println("Enter the date of birth of the new employee (YYYY-MM-DD):");
        String dobString = sc.next();
        java.sql.Date dob = java.sql.Date.valueOf(dobString);

        System.out.println("Enter the admin ID for the new employee:");
        int adminId = sc.nextInt();

        // Create a new employee and add it to the HashMap
        Employee newEmployee = new Employee(empId, empName, empRole, hireDate, empSal, dob, adminId);
        employeeMap.put(empId, newEmployee);

        // Add the new employee to the database
        addToDatabase(newEmployee, connection);
        sc.close();
    }

    private static void addToDatabase(Employee newEmployee, Connection connection) {
        try {
            // Insert the new employee into the database
            String insertQuery = "INSERT INTO Employee (Emp_ID, Emp_Name, Emp_Role, Hire_Date, Emp_Sal, DOB, Admin_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, newEmployee.getEmpId());
                preparedStatement.setString(2, newEmployee.getEmpName());
                preparedStatement.setString(3, newEmployee.getEmpRole());
                preparedStatement.setDate(4, newEmployee.getHireDate());
                preparedStatement.setDouble(5, newEmployee.getEmpSal());
                preparedStatement.setDate(6, newEmployee.getDob());
                preparedStatement.setInt(7, newEmployee.getAdminId());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Database updated successfully!");
                } else {
                    System.out.println("Failed to update database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
