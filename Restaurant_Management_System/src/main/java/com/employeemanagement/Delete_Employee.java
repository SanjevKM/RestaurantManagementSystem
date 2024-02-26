package com.employeemanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Delete_Employee {

    public static void deleteEmployee(Map<Integer, Employee> employeeMap, Connection connection) {
        // Create Scanner
        Scanner sc = new Scanner(System.in);

        // Display employee data for deletion
        View_Employee.readEmployeeData(employeeMap);

        // Delete employee
        System.out.println("Enter the ID of the employee to delete:");
        int empIdToDelete = sc.nextInt();

        Employee employeeToDelete = employeeMap.get(empIdToDelete);

        if (employeeToDelete != null) {
            // Remove the employee from the HashMap
            employeeMap.remove(empIdToDelete);

            // Remove the employee from the database
            removeFromDatabase(employeeToDelete, connection);
        } else {
            System.out.println("Employee with ID " + empIdToDelete + " not found.");
        }
        sc.close();
    }

    private static void removeFromDatabase(Employee employee, Connection connection) {
        try {
            // Delete the corresponding row from the database
            String deleteQuery = "DELETE FROM Employee WHERE Emp_ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, employee.getEmpId());

                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Employee deleted successfully!");
                } else {
                    System.out.println("Failed to delete employee.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
