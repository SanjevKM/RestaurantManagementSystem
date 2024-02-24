package Profile_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import User.Customer;

public class CustomerDataFetching {

    public static Map<Integer, Customer> fetchCustomerFromDatabase(int customerID, Connection connection) {
        Map<Integer, Customer> customers = new HashMap<>();

        try {
            String query = "SELECT Cust_ID, First_Name, Last_Name, Contact_No, Email, User_Name, Password FROM Customer WHERE Cust_ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, customerID);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        int custId = resultSet.getInt("Cust_ID");
                        String firstName = resultSet.getString("First_Name");
                        String lastName = resultSet.getString("Last_Name");
                        String contactNo = resultSet.getString("Contact_No");
                        String email = resultSet.getString("Email");
                        String userName = resultSet.getString("User_Name");
                        String password = resultSet.getString("Password");

                        // Add fetched customer to the HashMap
                        customers.put(custId, new Customer(custId, firstName, lastName, contactNo, email, userName, password));
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customers;
    }
}
