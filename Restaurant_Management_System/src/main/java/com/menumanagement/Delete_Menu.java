package com.menumanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Delete_Menu {

    public static void deleteMenuItem(List<Main_Menu> menuItems, Connection connection) {
        // Create Scanner
        Scanner sc = new Scanner(System.in);

        // Display menu for deletion
        ReadMenu.readMenu(menuItems);

        // Delete menu item
        System.out.println("Enter the ID of the item to delete:");
        int itemIdToDelete = sc.nextInt();

        Main_Menu itemToDelete = null;

        for (Main_Menu item : menuItems) {
            if (item.getItemId() == itemIdToDelete) {
                itemToDelete = item;
                break;
            }
        }

        if (itemToDelete != null) {
            // Remove the item from the menu
            menuItems.remove(itemToDelete);

            // Remove the item from the database
            removeFromDatabase(itemToDelete, connection);

            System.out.println("Item deleted successfully!");
        } else {
            System.out.println("Item with ID " + itemIdToDelete + " not found.");
        }
    }

    private static void removeFromDatabase(Main_Menu item, Connection connection) {
        try {
            // Delete the corresponding row from the database
            String deleteQuery = "DELETE FROM Menu WHERE Item_ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, item.getItemId());

                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Database updated successfully!");
                } else {
                    System.out.println("Failed to update database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
