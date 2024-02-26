package com.profilemanagement;

import java.util.Map;

import com.user.Customer;

public class View_Customer {

    public static void readCustomers(Map<Integer, Customer> customers) {
        // Display all customers
        System.out.println("Customer:");
        for (Customer customer : customers.values()) {
            System.out.println(customer);
        }
    }
}
