package Table_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Delete_Table {

    public static void deleteTable(Map<Integer, Tables> tablesMap, Connection connection) {
        // Create Scanner
        Scanner sc = new Scanner(System.in);

        // Display tables for deletion
        View_Table.readTables(tablesMap);

        // Delete table
        System.out.println("Enter the table number to delete:");
        int tableNoToDelete = sc.nextInt();

        Tables tableToDelete = tablesMap.get(tableNoToDelete);

        if (tableToDelete != null) {
            // Remove the table from the map
            tablesMap.remove(tableNoToDelete);

            // Remove the table from the database
            removeFromDatabase(tableToDelete, connection);

            System.out.println("Table deleted successfully!");
        } else {
            System.out.println("Table with number " + tableNoToDelete + " not found.");
        }
    }

    private static void removeFromDatabase(Tables table, Connection connection) {
        try {
            // Delete the corresponding row from the database
            String deleteQuery = "DELETE FROM Tables WHERE Table_No=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(deleteQuery)) {
                preparedStatement.setInt(1, table.getTableNo());

                int rowsDeleted = preparedStatement.executeUpdate();
                if (rowsDeleted > 0) {
                    System.out.println("Table deleted successfully from the database!");
                } else {
                    System.out.println("Failed to update database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
