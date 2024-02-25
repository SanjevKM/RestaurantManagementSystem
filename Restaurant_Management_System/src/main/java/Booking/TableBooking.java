package Booking;

import java.sql.Connection;





import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import Exception.CustomException;
import Exception.Methods;



public class TableBooking {

    static Scanner sc = new Scanner(System.in);

    public static void tableBooking(Connection connection2, int customerID,String email) throws CustomException {
    	// Display available tables
    	
        displayAvailableTables(connection2);
        String reservationDate = getValidDate(sc);
        
        sc.nextLine();
        System.out.println("Enter Reservation Time (Date HH:mm:ss)(24 hrs Format) ex:(2022-02-23 15:30:00):");
        String reservationTime = sc.nextLine();

        System.out.println("Enter Table Number:");
        int tableNumber = sc.nextInt();
        
        // Check if the selected table is available
        if (isTableAvailable(connection2, tableNumber, reservationDate, reservationTime)) {
            String status = "Confirmed";

            // SQL query to insert a new reservation into the 'Reservation' table
            String insertQuery = "INSERT INTO Reservation (Cust_ID, Reservation_Time, Table_No, Status) VALUES (?,  TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS'), ?, ?)";

            try (PreparedStatement preparedStatement = connection2.prepareStatement(insertQuery)) {

                preparedStatement.setInt(1, customerID);
                preparedStatement.setString(2, reservationTime);
                preparedStatement.setInt(3, tableNumber);
                preparedStatement.setString(4, status);

                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                	double price=getTablePrice(connection2,tableNumber);
                	System.out.println("Price for Your table:"+price);
                	System.out.println("Enter the Payment Type:\n1.Cash\n2.Card\n3.UPI");
                	int type=sc.nextInt();
                    String paymentMethod = (type == 1) ? "Cash" : (type == 2) ? "Card"
                    		+ ""
                    		+ "" : (type == 3) ? "UPI" : "Invalid";
                	boolean ispay=Payment.processPayment(type,price);
                	if(ispay) {
	                	int orderid=getOrderIdByCustomerId(connection2,customerID);
	                	Bill.insertPaymentDetails(connection2,orderid,price,paymentMethod);
	                    System.out.println("Table booked Successfully.");
	                    System.out.println("\nNotification will send to :"+email+"\n");
	                    Bill.displayBillDetails(connection2,orderid);
	                    System.out.println("Reservation Details:");
	                    displayConfirmedReservations(connection2,customerID);
                	}
                	else
                		System.out.println("Your payment is failed unable to book table");
                                       

                } else {
                    System.out.println("Failed to book the table.");
                }
            } catch (SQLException e) {
                System.err.println("Database Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // If not available, display a message
            System.out.println("The selected table is unavailable for the specific date and time. Please choose another table.");
        }
    	
    
    }
    
    
    public static void tableBookingAdmin(Connection connection2, int customerID,String email)throws CustomException {
        // Display available tables
        displayAvailableTables(connection2);

        // Get reservation details
        System.out.println("Enter Admin ID:");
        int adminId = sc.nextInt();
        
        String reservationDate = getValidDate(sc);

        sc.nextLine();
        System.out.println("Enter Reservation Time (Date HH:mm:ss)(24 hrs Format) ex:(2022-02-23 15:30:00):");
        String reservationTime = sc.nextLine();

        System.out.println("Enter Table Number:");
        int tableNumber = sc.nextInt();

        // Check if the selected table is available
        if (isTableAvailable(connection2, tableNumber, reservationDate, reservationTime)) {
            // If available, proceed with the reservation
            String status = "Confirmed";

            // SQL query to insert a new reservation into the 'Reservation' table
            String insertQuery = "INSERT INTO Reservation (Cust_ID, Admin_ID, Reservation_Time, Table_No, Status) VALUES (?, ?, TO_TIMESTAMP(? ,'YYYY-MM-DD HH24:MI:SS'), ?, ?)";

            try (PreparedStatement preparedStatement = connection2.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS)) {
            	preparedStatement.setInt(1, customerID);
                preparedStatement.setInt(2, adminId);
                preparedStatement.setString(3, reservationTime);
                preparedStatement.setInt(4, tableNumber);
                preparedStatement.setString(5, status);

                // Execute the update
                int rowsAffected = preparedStatement.executeUpdate();


                if (rowsAffected > 0) {
                	double price=getTablePrice(connection2,tableNumber);
                	System.out.println("Price for Your table:"+price);
                	System.out.println("Enter the Payment Type:\n1.cash\n2.card\n3.UPI");
                	int type=sc.nextInt();
                    String paymentMethod = (type == 1) ? "Cash" : (type == 2) ? "Card" : (type == 3) ? "UPI" : "Invalid";
                    boolean ispay=Payment.processPayment(type,price);
                	if(ispay) {
	                	int orderid=getOrderIdByCustomerId(connection2,customerID);
	                	Bill.insertPaymentDetails(connection2,orderid,price,paymentMethod);
	                    System.out.println("Table booked Successfully.");
	                    System.out.println("\nNotification will send to :"+email+"\n");
	                    Bill.displayBillDetails(connection2,orderid);
	                    System.out.println("Reservation Details:");
	                    displayConfirmedReservations(connection2,customerID);
                	}
                   
                } else {
                    System.out.println("Failed to reserve the table.");
                }
            } catch (SQLException e) {
                System.err.println("Database Error: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            // If not available, display a message
            System.out.println("The selected table is unavailable for the specific date and time. Please choose another table.");
        }
    }

    public static void displayAvailableTables(Connection connection) {
        // SQL query to select available tables
        String selectQuery = "SELECT Table_No,Price,Seats FROM Tables WHERE Table_Status = 'Available'";

        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(selectQuery)) {

            System.out.println("Available Tables:\n");
            System.out.println("Table No       Price               Seats");
            while (resultSet.next()) {
                int tableNo = resultSet.getInt("Table_No");
                double price=resultSet.getDouble("Price");
                int seats=resultSet.getInt("Seats");
                System.out.println();
                System.out.println(String.format(" %-12s  %-20s  %-5s ", tableNo, price, seats));

            }

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static boolean isTableAvailable(Connection connection, int tableNumber, String reservationDate, String reservationTime) {
        // SQL query to check if the selected table is available for the specified date and time
        String checkAvailabilityQuery = "SELECT 1 FROM Tables t " +
                "WHERE t.Table_No = ? AND t.Table_Status = 'Available' " +
                "AND NOT EXISTS (" +
                "   SELECT 1 FROM Reservation r " +
                "   WHERE r.Table_No = t.Table_No " +
                "   AND r.Reservation_Time = TO_TIMESTAMP(?, 'YYYY-MM-DD HH24:MI:SS')" +
                ")";

        try (PreparedStatement preparedStatement = connection.prepareStatement(checkAvailabilityQuery)) {

            preparedStatement.setInt(1, tableNumber);
            preparedStatement.setString(2, reservationTime);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next(); // Returns true if the table is available, false otherwise
            }

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static boolean displayConfirmedReservations(Connection connection, int custID) {
    	boolean isFound=false;
        String selectQuery = "SELECT Reservation_ID, Reservation_Time, Table_No, Status FROM Reservation WHERE Cust_ID = ? AND Status = 'Confirmed'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setInt(1, custID);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    System.out.println("No confirmed reservations found for CustID " + custID);
                } else {
                    System.out.println("Confirmed Reservation Details for CustID " + custID + ":");
                    isFound=true;
                    do {
                        int reservationID = resultSet.getInt("Reservation_ID");
                        String reservationTime = resultSet.getString("Reservation_Time");
                        int reservationTable = resultSet.getInt("Table_No");
                        String reservationStatus = resultSet.getString("Status");

                        System.out.println("Reservation ID: " + reservationID);
                        System.out.println("Reservation Time: " + reservationTime);
                        System.out.println("Reservation TableNo: " + reservationTable);
                        System.out.println("Reservation Status: " + reservationStatus);
                        System.out.println("-----------");
                    } while (resultSet.next());
                }
            } catch (SQLException e) {
                System.err.println("ResultSet Error: " + e.getMessage());
                e.printStackTrace();
            }

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
        return isFound;
    }



    
    public static double getTablePrice(Connection connection, int tableNumber) {
        String selectPriceQuery = "SELECT Price FROM Tables WHERE Table_No = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectPriceQuery)) {
            preparedStatement.setInt(1, tableNumber);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getDouble("Price");
                } else {
                    System.out.println("Table with Table_No " + tableNumber + " not found.");
                    return -1; 
                }
            }

        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return -1; 
        }
    }
    
    public static int getOrderIdByCustomerId(Connection connection, int customerId) {
        String selectOrderIdQuery = "SELECT Order_ID FROM Order_Table WHERE Cust_ID = ?";
        
        try (PreparedStatement preparedStatement = connection.prepareStatement(selectOrderIdQuery)) {
            preparedStatement.setInt(1, customerId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int orderId = resultSet.getInt("Order_ID");
                    return orderId;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving order ID: " + e.getMessage());
        }

        return -1;
    }
    
    private static String getValidDate(Scanner sc) {
        System.out.println("Enter Reservation Date (YYYY-MM-DD):");
        String date = sc.next();

        try {
			if (Methods.isvalidateDateInput(date)) {
			    return date;
			} else {
			    System.out.println("Invalid Date Format. Please try again.");
			    return getValidDate(sc); // Recursive call to get a valid phone number
			}
		} catch (CustomException e) {
			System.out.println(e.getMessage());
			return getValidDate(sc);
		}
    }
    
    
   
}
