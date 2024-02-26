package com.booking;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.user.Admin;

public class Cancellation {

	 public static void cancelTableBooking(Scanner sc,Connection connection, int custID) {
	        // Display existing reservations for the customer
	        boolean isFound=TableBooking.displayConfirmedReservations(connection, custID);
	        if(isFound) {
	        // Get reservation details to cancel
	        System.out.println("Enter the Reservation ID you want to cancel:");
	        int reservationID = sc.nextInt();

	        // Check if the reservation exists
	        if (isReservationExists(connection, custID, reservationID)) {
	        	double price=getTablePrice(connection, custID, reservationID);
	        	Admin.cancelReservation(connection,reservationID,price);
	        }
	        }
	 }

	    private static boolean isReservationExists(Connection connection, int custID, int reservationID) {
	        // Check if the reservation exists for the given customer and Reservation ID
	        String checkReservationQuery = "SELECT 1 FROM Reservation WHERE Cust_ID = ? AND Reservation_ID = ?";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(checkReservationQuery)) {
	            preparedStatement.setInt(1, custID);
	            preparedStatement.setInt(2, reservationID);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                return resultSet.next(); // Returns true if the reservation exists, false otherwise
	            }
	        } catch (SQLException e) {
	            System.err.println("Database Error: " + e.getMessage());
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public static double getTablePrice(Connection connection, int custID, int reservationID) {
	        double tablePrice = 0.0;

	        // Query to fetch the table price for the given Cust_ID and Reservation_ID
	        String query = "SELECT t.Price " +
	                       "FROM Reservation r " +
	                       "JOIN Tables t ON r.Table_No = t.Table_No " +
	                       "WHERE r.Cust_ID = ? AND r.Reservation_ID = ?";

	        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
	            preparedStatement.setInt(1, custID);
	            preparedStatement.setInt(2, reservationID);

	            try (ResultSet resultSet = preparedStatement.executeQuery()) {
	                if (resultSet.next()) {
	                    tablePrice = resultSet.getDouble("Price");
	                }
	            }
	        } catch (SQLException e) {
	            System.err.println("Database Error: " + e.getMessage());
	            e.printStackTrace();
	        }

	        return tablePrice;
	    }

}
