package Order_Management;

public class Order_Item {

    private int orderItemID;
    private int custID;
    private int itemID;
    private int quantity;
    private String specialRequest;

   

    public Order_Item( int custID, int itemID, int quantity, String specialRequest) {
        this.custID = custID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.specialRequest = specialRequest;
    }

    // Getter and Setter methods

    public int getOrderItemID() {
        return orderItemID;
    }

    public void setOrderItemID(int orderItemID) {
        this.orderItemID = orderItemID;
    }

    public int getCustID() {
        return custID;
    }

    public void setCustID(int custID) {
        this.custID = custID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSpecialRequest() {
        return specialRequest;
    }

    public void setSpecialRequest(String specialRequest) {
        this.specialRequest = specialRequest;
    }

    @Override
    public String toString() {
        return String.format("\n %-10s  %-10s  %-10s  %-20s ",
                        itemID, custID, quantity, specialRequest);
    }

}


