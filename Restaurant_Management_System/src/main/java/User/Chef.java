package User;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import Employee_Management.Employee;
import Order_Management.Order_Item;

public class Chef extends Employee {

    public Chef(int empId, String empName, String empRole, Date hireDate, double empSal, Date dob, int adminId) {
        super(empId, empName, empRole, hireDate, empSal, dob, adminId);
    }

    public void prepareOrder(List<Order_Item> orderDetails) {
        System.out.println("Chef " + getEmpName() + " is preparing the order.");
        System.out.println("\nPreparing item: ");
        System.out.println("ItemID     Cust_ID    Quantity    Special Request");
        for (Order_Item orderItem : orderDetails) {
            System.out.println(orderItem);
        }
    }

    public static Chef fetchChefDetailsByEmpId(int empId, Connection connection) {
        Chef chef = null;

        try {
            String query = "SELECT Emp_ID, Emp_Name, Emp_Role, Hire_Date, Emp_Sal, DOB, Admin_ID " +
                           "FROM Employee " +
                           "WHERE Emp_ID = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, empId);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int fetchedEmpId = resultSet.getInt("Emp_ID");
                        String empName = resultSet.getString("Emp_Name");
                        String empRole = resultSet.getString("Emp_Role");
                        Date hireDate = resultSet.getDate("Hire_Date");
                        double empSal = resultSet.getDouble("Emp_Sal");
                        Date dob = resultSet.getDate("DOB");
                        int adminId = resultSet.getInt("Admin_ID");

                        chef = new Chef(fetchedEmpId, empName, empRole, hireDate, empSal, dob, adminId);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (chef == null) {
            // If no Chef found, you might want to handle this situation accordingly.
            System.out.println("No Chef found for the given Emp_ID.");
        }

        return chef;
    }
}
