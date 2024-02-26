package com.user;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.employeemanagement.Employee;
import com.ordermanagement.Order_Table;

public class Waiter extends Employee {

   

    public Waiter(int empId, String empName, String empRole, Date hireDate, double empSal, Date dob, int adminId) {
        super(empId, empName, empRole, hireDate, empSal, dob, adminId);
        
    }

    public void takeOrder(Order_Table orderTable) {
        System.out.println("Waiter " + getEmpName() + " is taking the order for table " + orderTable.getTableNumber());
    }

    public static Waiter fetchWaiterDetailsByTableNo(int tableNo, Connection connection) {
        Waiter waiter = null;

        try {
            // Assuming the foreign key in Tables table is Emp_ID
            String query = "SELECT e.Emp_ID, e.Emp_Name, e.Emp_Role, e.Hire_Date, e.Emp_Sal, e.DOB, e.Admin_ID "
            		+ " FROM Employee e JOIN Tables t ON e.Emp_ID = t.Emp_ID \r\n"
            		+ " WHERE t.Table_No=? AND e.Emp_Role='Waiter'";

            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, tableNo);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int empId = resultSet.getInt("Emp_ID");
                        String empName = resultSet.getString("Emp_Name");
                        String empRole = resultSet.getString("Emp_Role");
                        Date hireDate = resultSet.getDate("Hire_Date");
                        double empSal = resultSet.getDouble("Emp_Sal");
                        Date dob = resultSet.getDate("DOB");
                        int adminId = resultSet.getInt("Admin_ID");

                        // Create a Waiter object with fetched details
                        waiter = new Waiter(empId, empName, empRole, hireDate, empSal, dob, adminId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (waiter == null) {
            // If no Chef found, you might want to handle this situation accordingly.
            System.out.println("No Waiter found for the given Emp_ID.");
        }

        return waiter;
    }
    
}
