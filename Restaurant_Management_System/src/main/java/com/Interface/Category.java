package com.Interface;

import java.sql.*;

import com.menumanagement.Main_Menu;

public class Category implements Searchable {

    
    public void searchByCategory(Connection connection, int category) {
        String searchCategory = (category == 1) ? "Veg" : "Non-Veg";

        System.out.println("Search Results for Category: " + searchCategory);

        String selectQuery = "SELECT * FROM Menu WHERE category = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, searchCategory);
            
            System.out.println(String.format(" %-8s  %-20s  %-60s  %-10s  %-10s ",
                    "ItemID", "ItemName", "Description", "Price", "Category"));


            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                	int itemid=resultSet.getInt("Item_ID");
                    String itemName = resultSet.getString("Item_Name");
                    String description = resultSet.getString("Description");
                    double price = resultSet.getDouble("Price");
                    String categoryResult = resultSet.getString("Category");
                    System.out.println();
                    System.out.println(String.format(" %-8d  %-20s  %-60s  $%-9.2f  %-10s ",
                            itemid, itemName, description, price, categoryResult));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void searchByPrice(Connection connection, double minPrice, double maxPrice) {
        System.out.println("Search Results for Price Range: " + minPrice + " to " + maxPrice);

        String selectQuery = "SELECT * FROM Menu WHERE price BETWEEN ? AND ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setDouble(1, minPrice);
            preparedStatement.setDouble(2, maxPrice);
            
            System.out.println(String.format(" %-8s  %-20s  %-60s  %-10s  %-10s ",
                    "ItemID", "ItemName", "Description", "Price", "Category"));

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                	int itemid=resultSet.getInt("Item_ID");
                    String itemName = resultSet.getString("Item_Name");
                    String description = resultSet.getString("Description");
                    double itemPrice = resultSet.getDouble("Price");
                    String category = resultSet.getString("Category");
                    System.out.println();
                    System.out.println(String.format(" %-8d  %-20s  %-60s  $%-9.2f  %-10s ",
                            itemid, itemName, description, itemPrice, category));
                }
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
}
