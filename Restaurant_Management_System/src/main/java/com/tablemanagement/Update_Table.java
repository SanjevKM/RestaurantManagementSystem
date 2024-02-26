package com.tablemanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Update_Table {

    public static void update(Map<Integer, Tables> tablesMap, Connection connection) {
        // Create Scanner
        Scanner sc = new Scanner(System.in);

        // Display tables for update
        View_Table.readTables(tablesMap);

        // Update table
        System.out.println("Enter the table number to update:");
        int tableNoToUpdate = sc.nextInt();

        Tables table = tablesMap.get(tableNoToUpdate);
        if (table != null) {
            // Update table details
            System.out.println("Enter the new status:");
            sc.nextLine(); // Consume newline character
            String newStatus = sc.nextLine();
            table.setTableStatus(newStatus);

            System.out.println("Enter the new employee ID:");
            int newEmpId = sc.nextInt();
            table.setEmpId(newEmpId);

            // Update the database
            updateDatabase(table, connection);
        } else {
            System.out.println("Table with number " + tableNoToUpdate + " not found.");
        }
    }

    private static void updateDatabase(Tables table, Connection connection) {
        try {
            // Update the corresponding row in the database
            String updateQuery = "UPDATE Tables SET Table_Status=?, Emp_ID=? WHERE Table_No=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, table.getTableStatus());
                preparedStatement.setInt(2, table.getEmpId());
                preparedStatement.setInt(3, table.getTableNo());

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Table updated successfully!");
                } else {
                    System.out.println("Failed to update table.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
