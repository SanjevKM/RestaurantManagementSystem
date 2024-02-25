package Table_Management;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class DataFetchingTable {

    public static Map<Integer, Tables> fetchTablesFromDatabase(Connection connection) {
        Map<Integer, Tables> tablesMap = new HashMap<>();

        try {
            String query = "SELECT Table_No, Table_Status, Emp_ID,Seats FROM Tables";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int tableNo = resultSet.getInt("Table_No");
                    String tableStatus = resultSet.getString("Table_Status");
                    int empId = resultSet.getInt("Emp_ID");
                    int seats = resultSet.getInt("Seats");

                    // Create Tables object
                    Tables table = new Tables(tableNo, tableStatus, empId, seats);

                    // Add fetched table to the HashMap
                    tablesMap.put(tableNo, table);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tablesMap;
    }
}


