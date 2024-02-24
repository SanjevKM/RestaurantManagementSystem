package Restaurant_Management;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Booking.Cancellation;
import Booking.CustomerUtils;
import Booking.TableBooking;
import Employee_Management.Add_Employee;
import Employee_Management.Delete_Employee;
import Employee_Management.Employee;
import Employee_Management.EmployeeDataFetching;
import Employee_Management.Update_Employee;
import Employee_Management.View_Employee;
import Exception.CustomException;
import Exception.emailInvalidException;
import Exception.passwordInvalidException;
import Exception.phoneNoInvalidException;
import Menu_Management.Add_Menu;
import Menu_Management.DataFetching;
import Menu_Management.Delete_Menu;
import Menu_Management.Main_Menu;
import Menu_Management.ReadMenu;
import Menu_Management.Update;
import Order_Management.Place_Order;
import Profile_Management.CustomerDataFetching;
import Profile_Management.Update_Customer;
import Profile_Management.View_Customer;
import Table_Management.Add_Table;
import Table_Management.DataFetchingTable;
import Table_Management.Delete_Table;
import Table_Management.Tables;
import Table_Management.Update_Table;
import Table_Management.View_Table;
import User.Admin;
import User.Chef;
import User.Customer;
import User.Waiter;

public class Restaurant {
    static Scanner sc = new Scanner(System.in);

    static String url = "jdbc:oracle:thin:@localhost:1521:xe";
    public static String username = "DATABASE";
    public static String dbPassword = "DATABASE";
    public static Connection connection;

    public static void main(String[] args) throws CustomException,emailInvalidException,passwordInvalidException,phoneNoInvalidException {
        try {
            connection = DriverManager.getConnection(url, username, dbPassword);
        } catch (SQLException e) {
            System.err.println("Error connecting to the database: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
        
        try {
        boolean isLoggedIn = false;
        
        	while(true) {
            Banners.displayMenu();
            try {
            int option = sc.nextInt();

            
                switch (option) {
                    case 1:
                        Customer.registerNewCustomer(connection);
                        break;

                    case 2:
                        isLoggedIn = Customer.login(connection);
                        if(!isLoggedIn) break;
                        while (isLoggedIn) {
                        	Banners.displayMainMenu();
                        	try {
                            int option1 = sc.nextInt();
                            switch (option1) {
                                case 1:
                                    System.out.println("Enter your email:");
                                    String email = sc.next();
                                    int customerID = CustomerUtils.getCustomerIdByEmail(connection, email);
                                    if (customerID != -1) {
                                        System.out.println("Customer ID for " + email + ": " + customerID);
                                        TableBooking.tableBooking(connection, customerID,email);
                                    } else {
                                        System.out.println("Customer ID not found for email: " + email);
                                    }
                                    break;

                                case 2:
                                    System.out.println("Enter your email:");
                                    String email3 = sc.next();
                                    int customerID3 = CustomerUtils.getCustomerIdByEmail(connection, email3);
                                    if (customerID3 != -1) {
                                        System.out.println("Customer ID for " + email3 + ": " + customerID3);
                                        Cancellation.cancelTableBooking(sc,connection, customerID3);
                                    } else {
                                        System.out.println("Customer ID not found for email: " + email3);
                                    }
                                    break;

                                    
                                case 3:
                                    System.out.println("Enter your email:");
                                    String email2 = sc.next();
                                    int customerID2 = CustomerUtils.getCustomerIdByEmail(connection, email2);

                                    if (customerID2 != -1) {
                                        System.out.println("Customer ID for " + email2 + ": " + customerID2);
                                        int tableNo = CustomerUtils.getTableNoByCustomerId(connection, customerID2);

                                        if (tableNo != -1) {
                                        	
                                            Waiter w = Waiter.fetchWaiterDetailsByTableNo(tableNo, connection);
                                            int empId = w.getEmpId(); 
                                            Chef c = Chef.fetchChefDetailsByEmpId(empId-1, connection);                                            System.out.println("You can now place your order.");
                                            Place_Order.placeOrder(connection, w, c, customerID2, tableNo);
                                            
                                        } else {
                                        	
                                            System.out.println("Please choose a table to order.");
                                            TableBooking.displayAvailableTables(connection);
                                            System.out.println("Enter the table:");
                                            int tableNo1 = sc.nextInt();
                                            Waiter w = Waiter.fetchWaiterDetailsByTableNo(tableNo1, connection);
                                            int empId =w.getEmpId(); 
                                            Chef c = Chef.fetchChefDetailsByEmpId(empId-1, connection);
                                            System.out.println("You can now place your order.");
                                            Place_Order.placeOrder(connection, w, c, customerID2, tableNo1);
                                            
                                        }
                                    } else {
                                        System.out.println("Customer ID not found for email: " + email2);
                                    }
                                    break;

                                case 4:
                                    System.out.println("Enter your email:");
                                    String email1 = sc.next();
                                    int customerID1 = CustomerUtils.getCustomerIdByEmail(connection, email1);
                                    if (customerID1 != -1) {
                                        System.out.println("Customer ID for " + email1 + ": " + customerID1);
                                        Map<Integer, Customer> c = CustomerDataFetching.fetchCustomerFromDatabase(customerID1, connection);
                                        while (true) {
                                        	Banners.displayProfileMenu();
                                            try {
                                            int option3 = sc.nextInt();
                                            switch (option3) {
                                                case 1:
                                                    View_Customer.readCustomers(c);
                                                    break;
                                                case 2:
                                                    Update_Customer.update(customerID1, c, connection);
                                                    break;
                                                case 3:
                                                    System.out.println("Exiting Profile Management System.");
                                                    break;
                                                default:
                                                    System.out.println("Invalid option. Please choose a valid option.");
                                            }

                                            if (option3 == 3) {
                                                break;
                                            }
                                        }catch (InputMismatchException e) {
                                            System.out.println("Invalid input. Please enter a number.");
                                            sc.nextLine();
                                        }
                                        }
                                    } else {
                                        System.out.println("Customer ID not found for email: " + email1);
                                    }
                                    break;
                                    
                                case 5:
                                	System.out.println("Enter your email:");
                                    String email5 = sc.next();
                                    int customerID5 = CustomerUtils.getCustomerIdByEmail(connection, email5);
                                    if (customerID5 != -1) {
                                        System.out.println("Customer ID for " + email5 + ": " + customerID5);
                                        TableBooking.displayConfirmedReservations(connection,customerID5);

                                    } else {
                                        System.out.println("Customer ID not found for email: " + email5);
                                    }
                                    break;
                                case 6:
                                    // Exit the inner loop and return to the main menu
                                    isLoggedIn = false;
                                    break;
                                    
                                case 7:
                                	System.out.println("Exiting from Saravana Bhavan Restaurant. Thank You!");
                                    connection.close(); // close the connection before exiting
                                    System.exit(0);
                                default:
                                    System.out.println("Invalid option");
                            }
                        	}catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a number.");
                                sc.nextLine();
                            }
                        
                        }
                        break;

                    case 3:
                    	isLoggedIn = Admin.adminLogin(connection,sc);
                        if(!isLoggedIn) break;
                        while (true) {
                        	Banners.displayAdminMenu();
                        	try {
                            int option2 = sc.nextInt();
                            List<Main_Menu> k = DataFetching.fetchMenuFromDatabase(connection);
                            Map<Integer, Employee> g = EmployeeDataFetching.fetchEmployeeDataFromDatabase(connection);
                            Map<Integer, Tables> t = DataFetchingTable.fetchTablesFromDatabase(connection);
                            switch (option2) {
                                case 1:
                                    while (true) {
                                    	Banners.displayMenuManagementMenu();
                                    	try {
                                        int option3 = sc.nextInt();
                                        switch (option3) {
                                            case 1:
                                                ReadMenu.readMenu(k);
                                                break;
                                            case 2:
                                                Update.update(k, connection);
                                                break;
                                            case 3:
                                                Add_Menu.addMenuItem(k, connection);
                                                break;
                                            case 4:
                                                Delete_Menu.deleteMenuItem(k, connection);
                                                break;
                                            case 5:
                                                System.out.println("Exiting Menu Management System.");
                                                break;
                                            default:
                                                System.out.println("Invalid option. Please choose a valid option.");
                                        }

                                        if (option3 == 5) {
                                            break;
                                        }
                                    }catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a number.");
                                        sc.nextLine();
                                    }
                                    }
                                    break;
                                case 2:
                                	Banners.displayCustomerMenu();
                                    int option4 = sc.nextInt();
                                    if (option4 == 1)
                                        Customer.registerNewCustomer(connection);
                                    else {
                                        System.out.println("Welcome to reservation");
                                        System.out.println("Enter your email:");
                                        String email = sc.next();
                                        int customerID = CustomerUtils.getCustomerIdByEmail(connection, email);

                                        if (customerID != -1) {
                                            System.out.println("Customer ID for " + email + ": " + customerID);
                                            TableBooking.tableBookingAdmin(connection, customerID,email);
                                        } else
                                            System.out.println("Login Failed");
                                    }
                                    break;
                                case 3:
                                    while (true) {
                                    	Banners.displayEmployeeManagementMenu();
                                    	try {
                                        int option3 = sc.nextInt();
                                        switch (option3) {
                                            case 1:
                                                View_Employee.readEmployeeData(g);
                                                break;
                                            case 2:
                                                Update_Employee.updateEmployee(g, connection);
                                                break;
                                            case 3:
                                                Add_Employee.addNewEmployee(g, connection);
                                                break;
                                            case 4:
                                                Delete_Employee.deleteEmployee(g, connection);
                                                break;
                                            case 5:
                                                System.out.println("Exiting Employee Management System.");
                                                break;
                                            default:
                                                System.out.println("Invalid option. Please choose a valid option.");
                                        }

                                        if (option3 == 5) {
                                            break;
                                        }
                                    }catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a number.");
                                        sc.nextLine();
                                    }
                                    }
                                    break;
                                case 4:
                                    while (true) {
                                    	Banners.displayTableManagementMenu();
                                    	try {
                                        int option3 = sc.nextInt();
                                        switch (option3) {
                                            case 1:
                                                View_Table.readTables(t);
                                                break;
                                            case 2:
                                                Update_Table.update(t, connection);
                                                break;
                                            case 3:
                                                Add_Table.addTable(t, connection);
                                                break;
                                            case 4:
                                                Delete_Table.deleteTable(t, connection);
                                                break;
                                            case 5:
                                                System.out.println("Exiting Table Management System.");
                                                break;
                                            default:
                                                System.out.println("Invalid option. Please choose a valid option.");
                                        }

                                        if (option3 == 5) {
                                            break;
                                        }
                                    }catch (InputMismatchException e) {
                                        System.out.println("Invalid input. Please enter a number.");
                                        sc.nextLine();
                                    }
                                    }
                                    break;
                                    
                                case 6:
                                	System.out.println("Exiting from Saravana Bhavan Restaurant. Thank You!");
                                    connection.close(); // close the connection before exiting
                                    System.exit(0);
                            }
                            if (option2 == 5) {
                                break;
                            }
                        	}catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a number.");
                                sc.nextLine();
                            }
                        }
                        break;

                    case 4:
                        System.out.println("Exiting from Saravana Bhavan Restaurant. Thank You!");
                        connection.close(); // close the connection before exiting
                        System.exit(0);

                    default:
                        System.out.println("Invalid option. Please choose a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine();
            }
        }
            } catch (SQLException e) {
                System.err.println("Database Error: " + e.getMessage());
                e.printStackTrace();
            }
    }
        	
}



