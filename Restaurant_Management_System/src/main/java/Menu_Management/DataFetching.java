package Menu_Management;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DataFetching {

    public static List<Main_Menu> fetchMenuFromDatabase(Connection connection) {
        List<Main_Menu> menuItems = new ArrayList<>();

        try {
            String query = "SELECT Item_ID, Item_Name, Price, Category, Availability, Description, Admin_ID FROM Menu";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(query)) {

                while (resultSet.next()) {
                    int itemId = resultSet.getInt("Item_ID");
                    String itemName = resultSet.getString("Item_Name");
                    double price = resultSet.getDouble("Price");
                    String category = resultSet.getString("Category");
                    String availability = resultSet.getString("Availability");
                    String description = resultSet.getString("Description");
                    int adminId = resultSet.getInt("Admin_ID");

                    // Add fetched menu item to the ArrayList
                    menuItems.add(new Main_Menu(itemId, itemName, price, category, availability, description, adminId));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menuItems;
    }
}
