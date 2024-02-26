package com.ordermanagement;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.Interface.Category;
import com.booking.Bill;
import com.booking.Payment;
import com.booking.TableBooking;
import com.menumanagement.DataFetching;
import com.menumanagement.Main_Menu;
import com.menumanagement.ReadMenu;
import com.restaurantmanagement.Banners;
import com.user.Chef;
import com.user.Waiter;

public class Place_Order {

    public static void placeOrder(Connection connection, Waiter waiter, Chef chef, int customerID, int tableNo) {
        Scanner sc = new Scanner(System.in);
          
        try {
    		List<Main_Menu> menuItems = DataFetching.fetchMenuFromDatabase(connection);

    		Banners.displayMenuDetails();
        	int option=sc.nextInt();
        	Category categorySearch = new Category();
        	switch(option) {
        	case 1:
        		System.out.println("Enter the category:\n1.veg\n2.non veg");
        		int category=sc.nextInt();
        		categorySearch.searchByCategory(connection,category);
        		break;
        		
        	case 2:
        		
        		System.out.println("Enter the price difference:\nMinimum Value:");
        		double min=sc.nextDouble();
        		System.out.println("Maximum Value:");
        		double max=sc.nextDouble();
        		categorySearch.searchByPrice(connection,min,max);
        		break;
        		
        	case 3:
                System.out.println();
                if (!menuItems.isEmpty()) {
                    ReadMenu.readMenu(menuItems);
                }
                break;
                
        	default:
        		System.out.println("Enter valid option");
        	}
        	
        	List<Order_Item> orderDetails = new ArrayList<>();
            while (true) {
                System.out.println("\nEnter the item ID to order (or enter -1 to finish ordering):");
                int orderItemID = sc.nextInt();

                if (orderItemID == -1) {
                    break; 
                }

                // Verify if the selected item is in the menu
                boolean isValidItem = menuItems.stream().anyMatch(item -> item.getItemId() == orderItemID);

                if (isValidItem) {
                    System.out.println("\nEnter the quantity for item " + orderItemID + ":");
                    int quantity = sc.nextInt();
                    
                    sc.nextLine();

                    System.out.println("\nEnter special request for item " + orderItemID + " (or enter 'null' for no special request):");
                    String specialRequest = sc.nextLine();

                    Order_Item orderItem = new Order_Item(customerID, orderItemID, quantity, specialRequest);

                    orderDetails.add(orderItem);
                } else {
                    System.out.println("Invalid item ID. Please choose a valid item from the menu.");
                }
            }

            if (!orderDetails.isEmpty()) {

                Order_Table orderTable = new Order_Table(customerID, tableNo, "Placed");

                waiter.takeOrder(orderTable);

                chef.prepareOrder(orderDetails);

                System.out.println("\nOrder placed successfully!");

                double totalAmount = calculateTotalAmount(orderDetails, menuItems);
                System.out.println("\nTotal Amount : " + totalAmount);
                insertOrderItems(connection, orderDetails);
                insertOrderDetails(sc,connection,orderTable,totalAmount,customerID,orderDetails);


            } else {
                System.out.println("No items selected. Order not placed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private static void insertOrderDetails(Scanner sc,Connection connection, Order_Table orderTable,double Amount,int customerID,List<Order_Item> orderDetails) {

        try {
            String insertOrderQuery = "INSERT INTO Order_Table (Cust_ID, Table_No, Status) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrderQuery)) {
                preparedStatement.setInt(1, orderTable.getCustomerId());
                preparedStatement.setInt(2, orderTable.getTableNumber());
                preparedStatement.setString(3, orderTable.getStatus());
                int affectedRows = preparedStatement.executeUpdate();
                if (affectedRows > 0) {
                	
                    System.out.println("\nOrder served successfully");
                	System.out.println("\nEnter the Payment Type:\n1.Cash\n2.Card\n3.UPI");
                	int type=sc.nextInt();
                    String paymentMethod = (type == 1) ? "Cash" : (type == 2) ? "Card" : (type == 3) ? "UPI" : "Invalid";
                	boolean ispay=Payment.processPayment(type,Amount);
                	if(ispay) {
                    	int orderid=TableBooking.getOrderIdByCustomerId(connection,customerID);
                    	Bill.insertPaymentDetails(connection,orderid,Amount,paymentMethod);
                    	System.out.println("Notification:\n");
                    	System.out.println("Item ID       Cust ID      Quantity      Special Request");
                    	for (Order_Item orderItem : orderDetails) {
                            System.out.println(orderItem);
                        }
                	}
                    
                } else {
                    System.out.println("Error inserting order details. No rows affected.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error inserting order details into Order_Table: " + e.getMessage());
        }

    }


    

    public static void insertOrderItems(Connection connection, List<Order_Item> orderItems) {
        String insertOrderItemQuery = "INSERT INTO Order_Item (Cust_ID, Item_ID, Quantity, SpecialRequest) VALUES (?, ?, ?, ?)";
        
        try {
            for (Order_Item orderItem : orderItems) {
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertOrderItemQuery)) {
                    preparedStatement.setInt(1, orderItem.getCustID());
                    preparedStatement.setInt(2, orderItem.getItemID());
                    preparedStatement.setInt(3, orderItem.getQuantity());
                    preparedStatement.setString(4, orderItem.getSpecialRequest());
                    
                    int affectedRows = preparedStatement.executeUpdate();

                    if (affectedRows <= 0) {
                        System.out.println("Error inserting order item into Order_Item table. No rows affected.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Error inserting order items into Order_Item table: " + e.getMessage());
        }
    }
    
    private static double calculateTotalAmount(List<Order_Item> orderItems, List<Main_Menu> menuItems) {
        double totalAmount = 0.0;

        for (Order_Item orderItem : orderItems) {
            // Find the corresponding menu item
            Main_Menu menuItem = menuItems.stream().filter(item -> item.getItemId() == orderItem.getItemID()).findFirst().orElse(null);

            if (menuItem != null) {
                // Calculate the total price for the ordered item
                double totalPrice = calculateTotalPrice(menuItem, orderItem.getQuantity(), orderItem.getSpecialRequest());
                totalAmount += totalPrice;
            }
        }

        return totalAmount;
    }
    
    private static double calculateTotalPrice(Main_Menu menuItem, int quantity, String specialRequest) {
        double itemPrice = menuItem.getPrice();
        double additionalAmount = calculateAdditionalAmount(specialRequest);
        return itemPrice * quantity + additionalAmount;
    }

    private static double calculateAdditionalAmount(String specialRequest) {
        return ("null".equalsIgnoreCase(specialRequest)) ? 0.0 : 100.0;
    }
}

