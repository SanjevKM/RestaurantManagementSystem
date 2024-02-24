package Table_Management;


import java.util.Map;

public class View_Table {

    public static void readTables(Map<Integer, Tables> tables) {
        // Display all tables
        System.out.println("Tables:");
        for (Map.Entry<Integer, Tables> entry : tables.entrySet()) {
            int tableNo = entry.getKey();
            Tables table = entry.getValue();
            System.out.println(String.format("Table No: %-5s | Status: %-10s | Emp ID: %-5s", tableNo, table.getTableStatus(), table.getEmpId()));
        }
    }
}


