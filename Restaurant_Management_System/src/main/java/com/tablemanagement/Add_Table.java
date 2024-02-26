package com.tablemanagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

public class Add_Table {

    public static void addTable(Map<Integer, Tables> tablesMap, Connection connection) {
        View_Table.readTables(tablesMap);

        Scanner sc = new Scanner(System.in);

        // Get details for the new table
        System.out.println("Enter the table number for the new table:");
        int tableNo = sc.nextInt();

        System.out.println("Enter the status of the new table:");
        sc.nextLine();
        String tableStatus = sc.nextLine();

        System.out.println("Enter the employee ID for the new table:");
        int empId = sc.nextInt();
        
        System.out.println("Enter the no of seats for the new table:");
        int seats = sc.nextInt();

        // Create a new table and add it to the map
        Tables newTable = new Tables(tableNo, tableStatus, empId, seats);
        tablesMap.put(tableNo, newTable);

        // Add the new table to the database
        addToDatabase(newTable, connection);
    }

    private static void addToDatabase(Tables newTable, Connection connection) {
        try {
            // Insert the new table into the database
            String insertQuery = "INSERT INTO Tables (Table_No, Table_Status, Emp_ID) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setInt(1, newTable.getTableNo());
                preparedStatement.setString(2, newTable.getTableStatus());
                preparedStatement.setInt(3, newTable.getEmpId());

                int rowsInserted = preparedStatement.executeUpdate();
                if (rowsInserted > 0) {
                    System.out.println("Table added successfully!");
                } else {
                    System.out.println("Failed to add table.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
