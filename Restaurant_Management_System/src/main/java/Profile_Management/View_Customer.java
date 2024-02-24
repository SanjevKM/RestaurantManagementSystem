package Profile_Management;

import java.util.Map;

import User.Customer;

public class View_Customer {

    public static void readCustomers(Map<Integer, Customer> customers) {
        // Display all customers
        System.out.println("Customer:");
        for (Customer customer : customers.values()) {
            System.out.println(customer);
        }
    }
}
