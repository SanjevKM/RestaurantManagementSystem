package User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Admin {
	
	public static boolean adminLogin(Connection connection,Scanner sc) {
	    System.out.println("Enter Admin Name:");
	    String enteredUserName = sc.next();

	    System.out.println("Enter Password:");
	    String enteredPassword = sc.next();

	    // SQL query to check admin credentials
	    String selectQuery = "SELECT * FROM admin WHERE Admin_Name = ? AND Password = ?";

	    try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
	        preparedStatement.setString(1, enteredUserName);
	        preparedStatement.setString(2, enteredPassword);

	        ResultSet resultSet = preparedStatement.executeQuery();

	        if (resultSet.next()) {
	            System.out.println("Admin Login Successful!");
	            return true;
	        } else {
	            System.out.println("Invalid admin credentials. Login failed.");
	            return false;
	        }
	    } catch (SQLException e) {
	        System.err.println("Database Error: " + e.getMessage());
	        e.printStackTrace();
	        return false;
	    }
	}


	public static double cancelReservation(Connection connection, int reservationID, double tablePrice) {
	    // Fetch reservation details including Reservation_Date and Reservation_Time
	    String fetchReservationQuery = "SELECT Reservation_Time FROM Reservation WHERE Reservation_ID = ?";
	    String updateStatusQuery = "UPDATE Reservation SET Status = 'Cancelled' WHERE Reservation_ID = ?";

	    try (PreparedStatement fetchStatement = connection.prepareStatement(fetchReservationQuery);
	         PreparedStatement updateStatusStatement = connection.prepareStatement(updateStatusQuery)) {

	        fetchStatement.setInt(1, reservationID);

	        try (ResultSet resultSet = fetchStatement.executeQuery()) {
	            if (resultSet.next()) {
	                LocalDateTime reservationDateTime = resultSet.getTimestamp("Reservation_Time").toLocalDateTime();
	                LocalDateTime currentDateTime = LocalDateTime.now();
	                if (currentDateTime.isAfter(reservationDateTime)) {
	                    // Reservation date and time are in the past
	                    System.out.println("Notification:\nTable booking with Reservation ID " + reservationID + " is already in the past. No refund will be provided.");
	                    return 0.0;
	                }

	                long hoursDifference = ChronoUnit.HOURS.between(currentDateTime, reservationDateTime);
	                if (hoursDifference > 24) {
	                    // Less than 24 hours before reservation, full amount refund
	                    System.out.println("Notification:\nTable booking with Reservation ID " + reservationID + " has been cancelled. Full amount will be refunded within 2 days. Amount " + tablePrice);
	                    updateStatusStatement.setInt(1, reservationID);
	                    updateStatusStatement.executeUpdate();
	                    return calculateRefundAmount(tablePrice, 1.0);

	                } else if (hoursDifference > 12) {
	                    // Less than 12 hours before reservation, half amount refund
	                    System.out.println("Notification:\nTable booking with Reservation ID " + reservationID + " has been cancelled. Half amount will be refunded within 2 days. Amount " + tablePrice);
	                    updateStatusStatement.setInt(1, reservationID);
	                    updateStatusStatement.executeUpdate();
	                    return calculateRefundAmount(tablePrice, 0.5);

	                } else {
	                    // More than 12 hours before reservation, no amount refund
	                    System.out.println("Notification:\nTable booking with Reservation ID " + reservationID + " has been cancelled. No amount will be refunded.");
	                    updateStatusStatement.setInt(1, reservationID);
	                    updateStatusStatement.executeUpdate();
	                    return calculateRefundAmount(tablePrice, 0.0);
	                }
	            } else {
	                System.out.println("Reservation not found for ID " + reservationID);
	                return 0.0; // Return 0.0 if the reservation is not found
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("Database Error: " + e.getMessage());
	        e.printStackTrace();
	        return 0.0; // Return 0.0 in case of a database error
	    }
	}

	


    private static double calculateRefundAmount(double tablePrice, double refundPercentage) {
        // Assume the total booking amount is 100. Adjust this based on your actual pricing.
        double totalAmount = tablePrice;
        return totalAmount * refundPercentage;
    }
}
