package Employee_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Update_Employee {

    public static void updateEmployee(Map<Integer, Employee> employeeMap, Connection connection) {
        // Create Scanner
        Scanner sc = new Scanner(System.in);

        // Display employee data for update
        View_Employee.readEmployeeData(employeeMap);

        // Update employee details
        System.out.println("Enter the ID of the employee to update:");
        int empIdToUpdate = sc.nextInt();

        Employee employee = employeeMap.get(empIdToUpdate);

        if (employee != null) {
            // Update employee details
            System.out.println("Enter the new name:");
            sc.nextLine(); // Consume newline character
            String newName = sc.nextLine();
            employee.setEmpName(newName);

            System.out.println("Enter the new role:");
            String newRole = sc.nextLine();
            employee.setEmpRole(newRole);

            System.out.println("Enter the new hire date (YYYY-MM-DD):");
            String newHireDateString = sc.next();
            java.sql.Date newHireDate = java.sql.Date.valueOf(newHireDateString);
            employee.setHireDate(newHireDate);

            System.out.println("Enter the new salary:");
            double newSalary = sc.nextDouble();
            employee.setEmpSal(newSalary);

            System.out.println("Enter the new date of birth (YYYY-MM-DD):");
            String newDobString = sc.next();
            java.sql.Date newDob = java.sql.Date.valueOf(newDobString);
            employee.setDob(newDob);

            System.out.println("Enter the new admin ID:");
            int newAdminId = sc.nextInt();
            employee.setAdminId(newAdminId);

            // Update the database
            updateDatabase(employee, connection);
        } else {
            System.out.println("Employee with ID " + empIdToUpdate + " not found.");
        }
        sc.close();
    }

    private static void updateDatabase(Employee employee, Connection connection) {
        try {
            // Update the corresponding row in the database
            String updateQuery = "UPDATE Employee SET Emp_Name=?, Emp_Role=?, Hire_Date=?, Emp_Sal=?, DOB=?, Admin_ID=? WHERE Emp_ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, employee.getEmpName());
                preparedStatement.setString(2, employee.getEmpRole());
                preparedStatement.setDate(3, employee.getHireDate());
                preparedStatement.setDouble(4, employee.getEmpSal());
                preparedStatement.setDate(5, employee.getDob());
                preparedStatement.setInt(6, employee.getAdminId());
                preparedStatement.setInt(7, employee.getEmpId());

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
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
