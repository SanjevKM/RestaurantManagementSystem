package User;

import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import Booking.CustomerUtils;
import Exception.*;

public class Customer extends Person {
	
	private int custId;
	private String userName;
    private String password;
	
    public Customer(int custId, String firstName, String lastName, String contactNo, String email, String userName, String password) {
        this.custId = custId;
        this.first_Name = firstName;
        this.last_Name = lastName;
        this.phone_No = contactNo;
        this.email = email;
        this.userName = userName;
        this.password = password;
    }
    
    public int getCustId() {
		return custId;
	}

	public void setCustId(int custId) {
		this.custId = custId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Override
	public String toString() {
	    return String.format(" %-8s  %-15s  %-15s  %-15s  %-30s  %-15s  %-15s ",
	            "CustID", "First Name", "Last Name", "Contact No", "Email", "UserName", "Password") +
	            String.format("\n %-8d  %-15s  %-15s  %-15s  %-30s  %-15s  %-15s ",
	                    custId, first_Name, last_Name, phone_No, email, userName, password);
	}



	static Scanner sc = new Scanner(System.in);


	public static void registerNewCustomer(Connection connection)
            throws emailInvalidException, passwordInvalidException, phoneNoInvalidException {
        
            // Get customer details with validation

            String first_Name = getValidationName(sc);

            String last_Name = getValidationLastName(sc);

            System.out.println("Please enter a 10-digit number starting with the first digit greater than 5");
            String phone_No = getValidationContact(sc);

            System.out.println("Enter valid Email ID like sanjay123@gmail.com");
            String email = getValidationEmail(sc);
            email=checkForDuplicate(connection,email);

            String userName = getValidationUserName(sc);

            System.out.println("Password should be at least 8 characters and include lowercase, uppercase, digit, and special character.");
            String password = getValidationPassword(sc);

        

        // SQL query to insert a new customer into the 'Customer' table
        String insertQuery = "INSERT INTO Customer (First_Name, Last_Name, Contact_No, Email, User_Name, Password) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
            // Set parameters in the prepared statement
        	preparedStatement.setString(1, first_Name);
            preparedStatement.setString(2, last_Name);
            preparedStatement.setString(3, phone_No);
            preparedStatement.setString(4, email);
            preparedStatement.setString(5, userName);
            preparedStatement.setString(6, password);

            // Execute the update
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("New Customer Registered Successfully.");
            } else {
                System.out.println("Failed to register the new customer.");
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
        }
    
	}
	

    
    

    public static boolean login(Connection connection) {
        System.out.println("Enter User Name:");
        String enteredUserName = sc.next();

        System.out.println("Enter Password:");
        String enteredPassword = sc.next();

        // SQL query to check user credentials
        String selectQuery = "SELECT * FROM Customer WHERE User_Name = ? AND Password = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
            preparedStatement.setString(1, enteredUserName);
            preparedStatement.setString(2, enteredPassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Login Successful!");
                return true;
            } else {
                System.out.println("Invalid credentials. Login failed.");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Database Error: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
    
    private static String getValidationContact(Scanner sc) {
        System.out.println("Enter the Phone No:");
        String phoneNo = sc.next();

        
        try {
			if (Methods.isValidContactNumber(phoneNo)) {
			    return phoneNo;
			} else {
			    return getValidationContact(sc); // Recursive call to get a valid phone number
			}
		} catch (phoneNoInvalidException e) {
			System.out.println(e.getMessage());
			return getValidationContact(sc);
		}
        
       }
    
    private static String getValidationEmail(Scanner sc) {
        System.out.println("Enter the Email :");
        String input = sc.next();

        try {
            if (Methods.isValidEmail(input)) {
                return input;
            } else {
                System.out.println("Invalid Email. Please try again.");
                return getValidationEmail(sc); // Recursive call to get a valid input
            }
        } catch (emailInvalidException e) {
            System.out.println(e.getMessage());
            return getValidationEmail(sc);
        }
        
    }
    
    private static String getValidationPassword(Scanner sc) {
        System.out.println("Enter the Password :");
        String input = sc.next();

        try {
            if (Methods.isValidPassword(input)) {
                return input;
            } else {
                System.out.println("Invalid Password. Please try again.");
                return getValidationPassword(sc); 
            }
        } catch (passwordInvalidException e) {
            System.out.println(e.getMessage());
            return getValidationPassword(sc); 
        }
    }
    
    private static String checkForDuplicate(Connection connection,String email) {
        
			if (!CustomerUtils.isEmailExists(connection,email)) {
			    return email;
			} else {
			    System.out.println("This email is already in use. Please choose another.");
				System.out.println("Enter the Email:");
		        email = sc.next();
			    return getValidationContact(sc); // Recursive call to get a valid phone number
			}
        
       }
    
    private static String getValidationName(Scanner sc) {
        System.out.println("Enter the Name:");
        String name = sc.next();

        try {
            if (Methods.isValidName(name)) {
                return name;
            } else {
                return getValidationName(sc); // Recursive call to get a valid name
            }
        } catch (NameInvalidException e) {
            System.out.println(e.getMessage());
            return getValidationName(sc);
        }
    }
    
    private static String getValidationLastName(Scanner sc) {
        System.out.println("Enter the Name:");
        String name = sc.next();

        try {
            if (Methods.isValidName(name)) {
                return name;
            } else {
                return getValidationName(sc); // Recursive call to get a valid name
            }
        } catch (NameInvalidException e) {
            System.out.println(e.getMessage());
            return getValidationName(sc);
        }
    }
    
    private static String getValidationUserName(Scanner sc) {
        System.out.println("Enter the User Name:");
        String username = sc.next();

        try {
            if (Methods.isValidUserName(username)) {
                return username;
            } else {
                return getValidationUserName(sc); // Recursive call to get a valid username
            }
        } catch (UserNameInvalidException e) {
            System.out.println(e.getMessage());
            return getValidationUserName(sc);
        }
    }

}
