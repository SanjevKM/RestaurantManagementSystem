package Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Bill {

	 public static boolean insertPaymentDetails(Connection connection, int orderID, double amount, String paymentType) {
	        String insertQuery = "INSERT INTO Bill (Order_ID, Amount, Payment_Status, Payment_Type) VALUES (?, ?, 'Paid', ?)";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery, new String[]{"Bill_ID"})) {
	            preparedStatement.setInt(1, orderID);
	            preparedStatement.setDouble(2, amount);
	            preparedStatement.setString(3, paymentType);

	            int rowsAffected = preparedStatement.executeUpdate();

	            if (rowsAffected > 0) {
	                try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
	                    if (generatedKeys.next()) {
	                        int generatedBillID = generatedKeys.getInt(1);
	                        System.out.println("Payment successful. Bill_ID: " + generatedBillID);
	                    	Bill.displayBillDetails(connection,generatedBillID);
	                        return true;
	                    } else {
	                        System.out.println("Failed to retrieve generated Bill_ID.");
	                        return false;
	                    }
	                }
	            } else {
	                System.out.println("Failed to insert payment details.");
	                return false;
	            }
	        } catch (SQLException e) {
	            System.err.println("Database Error: " + e.getMessage());
	            e.printStackTrace();
	            return false;
	        }
	    }
	 public static void displayBillDetails(Connection connection, int orderID) {
		    String selectQuery = "SELECT * FROM Bill WHERE Bill_ID = ?";

		    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
		        preparedStatement.setInt(1, orderID);

		        try (ResultSet resultSet = preparedStatement.executeQuery()) {
		            if (resultSet.next()) {
		                int billID = resultSet.getInt("Bill_ID");
		                double amount = resultSet.getDouble("Amount");
		                String paymentStatus = resultSet.getString("Payment_Status");
		                String paymentType = resultSet.getString("Payment_Type");

		                System.out.println("\nBill Details:");
		                System.out.println("********************************");
		                System.out.println("\nBill_ID      : " + billID);
		                System.out.println("Order_ID      : " + orderID);
		                System.out.println("Amount        : $" + amount);
		                System.out.println("Payment Status: " + paymentStatus);
		                System.out.println("Payment Type  : " + paymentType);
		                System.out.println("********************************");

		            } else {
		                System.out.println("No bill found for Bill_ID " + orderID);
		            }
		        }
		    } catch (SQLException e) {
		        System.err.println("Database Error: " + e.getMessage());
		        e.printStackTrace();
		    }
		}

}
