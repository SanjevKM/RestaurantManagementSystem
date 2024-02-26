package com.menumanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Add_Menu {

    public static void addMenuItem(List<Main_Menu> menuItems, Connection connection) {
        ReadMenu.readMenu(menuItems);
    	Scanner sc = new Scanner(System.in);

        // Get details for the new menu item
    	System.out.println("Enter the id of the new item:");
        int itemId = sc.nextInt();
        
        System.out.println("Enter the name of the new item:");
        sc.nextLine();
        String itemName = sc.nextLine();

        System.out.println("Enter the price of the new item:");
        double price = sc.nextDouble();

        System.out.println("Enter the category of the new item:");
        sc.nextLine(); // Consume newline character
        String category = sc.nextLine();

        System.out.println("Enter the availability of the new item:");
        String availability = sc.nextLine();
        
        System.out.println("Enter the description of the new item:");
        String description = sc.nextLine();

        System.out.println("Enter the admin ID for the new item:");
        int adminId = sc.nextInt();

        // Create a new menu item and add it to the menu
        Main_Menu newItem = new Main_Menu(itemId,itemName, price, category, availability, description, adminId); 
        menuItems.add(newItem);

        // Add the new menu item to the database
        addToDatabase(newItem, connection);

    }

    private static void addToDatabase(Main_Menu newItem, Connection connection) {
        try {
            // Insert the new menu item into the database
            String insertQuery = "INSERT INTO Menu (Item_ID,Item_Name, Price, Category, Availability, Description, Admin_ID) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, newItem.getItemId());
                preparedStatement.setString(2, newItem.getItemName());
                preparedStatement.setDouble(3, newItem.getPrice());
                preparedStatement.setString(4, newItem.getCategory());
                preparedStatement.setString(5, newItem.getAvailability());
                preparedStatement.setString(6, newItem.getDescription());
                preparedStatement.setInt(7, newItem.getAdminId());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Item added to the menu successfully!");
                } else {
                    System.out.println("Failed to update Item.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
