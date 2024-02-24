package Menu_Management;

import java.util.Comparator;

public class Main_Menu {
	
	    private int itemId;
	    private String itemName;
	    private double price;
	    private String category;
	    private String availability;
	    private String description;
	    private int adminId;
	    

	    public Main_Menu(int itemId2, String itemName2, double price2, String category2, String availability2,String description2,int adminId2) {
			itemId=itemId2;
			itemName=itemName2;
			price=price2;
			category=category2;
			availability=availability2;
			description=description2;
			adminId=adminId2;
		}


		public String getDescription() {
			return description;
		}


		public void setDescription(String description) {
			this.description = description;
		}


		public int getAdminId() {
			return adminId;
		}


		public void setAdminId(int adminId) {
			this.adminId = adminId;
		}


		public int getItemId() {
			return itemId;
		}


		public void setItemId(int itemId) {
			this.itemId = itemId;
		}


		public String getItemName() {
			return itemName;
		}


		public void setItemName(String itemName) {
			this.itemName = itemName;
		}


		public double getPrice() {
			return price;
		}


		public void setPrice(double price) {
			this.price = price;
		}


		public String getCategory() {
			return category;
		}


		public void setCategory(String category) {
			this.category = category;
		}


		public String getAvailability() {
			return availability;
		}


		public void setAvailability(String availability) {
			this.availability = availability;
		}

		class MenuComparator implements Comparator<Main_Menu> {
	        @Override
	        public int compare(Main_Menu item1, Main_Menu item2) {
	            // Compare items based on price in ascending order
	            return Double.compare(item1.getPrice(), item2.getPrice());
	        }
	    }


		@Override
		public String toString() {
		    return String.format("| %-8s | %-20s | %-8s | %-15s | %-15s | %-60s |",
		            "ItemID", "ItemName", "Price", "Category", "Availability", "Description") +
		            String.format("\n| %-8d | %-20s | %-8.2f | %-15s | %-15s | %-60s |",
		                    itemId, itemName, price, category, availability, description);
		}

	}

