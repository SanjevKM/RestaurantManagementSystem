package com.tablemanagement;


import java.util.Map;

public class View_Table {

    public static void readTables(Map<Integer, Tables> tables) {
        // Display all tables
        System.out.println("Tables:\n");
        System.out.println("Table No        Status            Emp ID            Seats");
        for (Map.Entry<Integer, Tables> entry : tables.entrySet()) {
            int tableNo = entry.getKey();
            Tables table = entry.getValue();
            System.out.println();
            System.out.println(String.format(" %-12s  %-20s  %-15s  %-25s", tableNo, table.getTableStatus(), table.getEmpId(), table.getSeats()));
        }
    }
}


