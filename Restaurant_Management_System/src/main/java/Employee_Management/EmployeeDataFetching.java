package Employee_Management;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class EmployeeDataFetching {

    public static Map<Integer, Employee> fetchEmployeeDataFromDatabase(Connection connection) {
        Map<Integer, Employee> employeeMap = new HashMap<>();

        try {
            String query = "SELECT Emp_ID, Emp_Name, Emp_Role, Hire_Date, Emp_Sal, DOB, Admin_ID FROM Employee";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int empId = resultSet.getInt("Emp_ID");
                    String empName = resultSet.getString("Emp_Name");
                    String empRole = resultSet.getString("Emp_Role");
                    Date hireDate = resultSet.getDate("Hire_Date");
                    double empSal = resultSet.getDouble("Emp_Sal");
                    java.sql.Date dob = resultSet.getDate("DOB");
                    int adminId = resultSet.getInt("Admin_ID");

                    // Create Employee object
                    Employee employee = new Employee(empId, empName, empRole, hireDate, empSal, dob, adminId);

                    // Add to the HashMap with empId as key
                    employeeMap.put(empId, employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeMap;
    }
}
