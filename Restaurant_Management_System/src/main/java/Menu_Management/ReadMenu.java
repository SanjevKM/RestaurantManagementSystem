package Menu_Management;

import java.util.List;

public class ReadMenu {
    public static void readMenu(List<Main_Menu> menuItems) {
        // Display all items in the menu
        System.out.println("Menu Items:");
        System.out.println("ItemID        ItemName           Price     Category        Availability                      Description");
        for (Main_Menu item : menuItems) {
            System.out.println(item+"\n");
        }
    }
}