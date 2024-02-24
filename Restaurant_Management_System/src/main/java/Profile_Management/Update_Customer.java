package Profile_Management;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Scanner;

import User.Customer;

public class Update_Customer {

    public static void update(int customerID, Map<Integer, Customer> customers, Connection connection) {
        // Create Scanner
        Scanner sc = new Scanner(System.in);

        // Display customers for update
        View_Customer.readCustomers(customers);

        Customer customerToUpdate = customers.get(customerID);

        if (customerToUpdate != null) {
            // Update customer details
            System.out.println("Choose the field to update:");
            System.out.println("1. First Name");
            System.out.println("2. Last Name");
            System.out.println("3. Contact Number");
            System.out.println("4. Email");
            System.out.println("5. Username");
            System.out.println("6. Password");

            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("Enter the new first name:");
                    String newFirstName = sc.nextLine();
                    customerToUpdate.setFirst_Name(newFirstName);
                    break;
                case 2:
                    System.out.println("Enter the new last name:");
                    String newLastName = sc.nextLine();
                    customerToUpdate.setLast_Name(newLastName);
                    break;
                case 3:
                    System.out.println("Enter the new contact number:");
                    String newContactNo = sc.nextLine();
                    customerToUpdate.setPhone_No(newContactNo);
                    break;
                case 4:
                    System.out.println("Enter the new email:");
                    String newEmail = sc.nextLine();
                    customerToUpdate.setEmail(newEmail);
                    break;
                case 5:
                    System.out.println("Enter the new username:");
                    String newUserName = sc.nextLine();
                    customerToUpdate.setUserName(newUserName);
                    break;
                case 6:
                    System.out.println("Enter the new password:");
                    String newPassword = sc.nextLine();
                    customerToUpdate.setPassword(newPassword);
                    break;
                default:
                    System.out.println("Invalid choice. No fields updated.");
                    return;
            }

            // Update the database
            updateDatabase(customerToUpdate, connection);

            System.out.println("Customer with ID " + customerID + " updated successfully!");
        } else {
            System.out.println("Customer with ID " + customerID + " not found.");
        }
    }

    private static void updateDatabase(Customer customer, Connection connection) {
        try {
            // Update the corresponding row in the database
            String updateQuery = "UPDATE Customer SET First_Name=?, Last_Name=?, Contact_No=?, Email=?, User_Name=?, Password=? WHERE Cust_ID=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
                preparedStatement.setString(1, customer.getFirst_Name());
                preparedStatement.setString(2, customer.getLast_Name());
                preparedStatement.setString(3, customer.getPhone_No());
                preparedStatement.setString(4, customer.getEmail());
                preparedStatement.setString(5, customer.getUserName());
                preparedStatement.setString(6, customer.getPassword());
                preparedStatement.setInt(7, customer.getCustId());

                int rowsUpdated = preparedStatement.executeUpdate();
                if (rowsUpdated > 0) {
                    System.out.println("Customer updated in the database successfully!");
                } else {
                    System.out.println("Failed to update customer in the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
