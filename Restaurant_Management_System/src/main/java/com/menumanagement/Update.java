package com.menumanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Update {

    public static void update(List<Main_Menu> menuItems, Connection connection) {
        // Create Scanner
        Scanner sc = new Scanner(System.in);

        // Display menu for update
        ReadMenu.readMenu(menuItems);

        // Update menu item
        System.out.println("Enter the ID of the item to update:");
        int itemIdToUpdate = sc.nextInt();

        for (Main_Menu item : menuItems) {
            if (item.getItemId() == itemIdToUpdate) {
                // Update item details
                System.out.println("Enter the new name:");
                sc.nextLine(); // Consume newline character
                String newName = sc.nextLine();
                item.setItemName(newName);

                System.out.println("Enter the new price:");
                double newPrice = sc.nextDouble();
                item.setPrice(newPrice);

                System.out.println("Enter the new category:");
                sc.nextLine(); // Consume newline character
                String newCategory = sc.nextLine();
                item.setCategory(newCategory);

                System.out.println("Enter the new description:");
                String newDescription = sc.nextLine();
                item.setDescription(newDescription);

                System.out.println("Enter the new admin ID:");
                int newAdminId = sc.nextInt();
                item.setAdminId(newAdminId);

                // Update the database
                updateDatabase(item, connection);

                return;
            }
        }

        System.out.println("Item with ID " + itemIdToUpdate + " not found.");
    }

    private static void updateDatabase(Main_Menu item, Connection connection) {
        try {
            // Update the corresponding row in the database
            String updateQuery = "UPDATE Menu SET Item_Name=?, Price=?, Category=?, Description=?, Admin_ID=? WHERE Item_ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, item.getItemName());
                preparedStatement.setDouble(2, item.getPrice());
                preparedStatement.setString(3, item.getCategory());
                preparedStatement.setString(4, item.getDescription());
                preparedStatement.setInt(5, item.getAdminId());
                preparedStatement.setInt(6, item.getItemId());

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Item updated successfully!");
                } else {
                    System.out.println("Failed to update Item.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

