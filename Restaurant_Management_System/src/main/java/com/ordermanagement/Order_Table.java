package com.ordermanagement;


public class Order_Table {
    private int orderId;
    private int customerId;
    private int tableNumber;
    private String status;

    // Constructor
    public Order_Table( int customerId, int tableNumber, String status) {
        this.customerId = customerId;
        this.tableNumber = tableNumber;
        this.status = status;
    }

    // Getter and Setter methods
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }
    

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OrderTable{" +
                "orderId=" + orderId +
                ", customerId=" + customerId +
                ", tableNumber=" + tableNumber +
                ", status='" + status + '\'' +
                '}';
    }
}
