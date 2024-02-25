package Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerUtils {

    public static int getCustomerIdByEmail(Connection connection2, String loggedInEmail) {
    	int customerID = -1;
        // SQL query to retrieve Cust_ID based on username
        String selectQuery = "SELECT Cust_ID FROM Customer WHERE Email = ?";

        try (
             PreparedStatement preparedStatement = connection2.prepareStatement(selectQuery)) {

            preparedStatement.setString(1, loggedInEmail);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    customerID = resultSet.getInt("Cust_ID");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }

        return customerID;
    }

    public static int getTableNoByCustomerId(Connection connection, int customerID) {
        int tableNo = -1;
        
        // SQL query to retrieve Table_No based on Cust_ID
        String selectQuery = "SELECT Table_No FROM Reservation WHERE Cust_ID = ? AND Status='Confirmed'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, customerID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    tableNo = resultSet.getInt("Table_No");
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }

        return tableNo;
    }
       

    public static boolean isEmailExists(Connection connection, String email) {
        String checkEmailQuery = "SELECT COUNT(*) FROM Customer WHERE email = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(checkEmailQuery)) {
            preparedStatement.setString(1, email);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

}
