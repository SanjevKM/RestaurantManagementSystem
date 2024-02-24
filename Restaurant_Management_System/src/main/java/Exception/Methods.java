package Exception;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Methods {

	public static boolean isvalidateDateInput(String message) throws CustomException {
        LocalDate currentDate = LocalDate.now();
        String dateRegex = "^\\d{4}-\\d{2}-\\d{2}$";

		        if (!message.matches(dateRegex)) {
		            throw new CustomException("You may have entered an incorrect date format. Please ensure it follows the correct format (YYYY-MM-DD).");
		        }
                LocalDate inputDate = LocalDate.parse(message);
                boolean isMatch=inputDate.isAfter(currentDate);
                if (!isMatch) {
                    throw new CustomException("Please enter a future date.");
                }
                else
                    return isMatch;
    }
	
	
    public static boolean isValidEmail(String email) throws emailInvalidException {
        String emailRegex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        boolean isMatch=matcher.matches();
        if(!isMatch) {
        	throw new emailInvalidException("Invalid EmailId.Enter valid EmailID like sanjay123@gmail.com");
        }
        else
        	return isMatch;
    }

    public static boolean isValidPassword(String password) throws passwordInvalidException {
        boolean isMatch=password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()-+]).{8,}$");
        if(!isMatch) {
        	throw new passwordInvalidException("Invalid Password.Password should be at least 8 characters and include lowercase, uppercase, digit, and special character.");
        }
        else
        	return isMatch;
    }

    public static boolean isValidContactNumber(String contactNumber) throws phoneNoInvalidException {
        boolean isMatch=contactNumber.matches("[6-9]\\d{9}");
        if(!isMatch) {
        	throw new phoneNoInvalidException("Invalid PhoneNo.Please enter a 10-digit number starting with the first digit greater than 5");
        }
        else
        	return isMatch;
    }
    
    public static boolean isValidName(String name) throws NameInvalidException {
        boolean isMatch = name.matches("[a-zA-Z]+");
        if (!isMatch) {
            throw new NameInvalidException("Invalid name. Please enter only letters (both uppercase and lowercase).");
        } else {
            return isMatch;
        }
    }
    
    public static boolean isValidUserName(String username) throws UserNameInvalidException {
        boolean isMatch = username.matches("[a-zA-Z0-9\\p{Punct}]+");
        if (!isMatch) {
            throw new UserNameInvalidException("Invalid username. Please enter a valid username with letters, numbers, and special characters.");
        } else {
            return isMatch;
        }
    }
    
    public static boolean isValidCardNumber(String cardNumber) throws CardNumberInvalidException {
        boolean isMatch = cardNumber.matches("\\d{12}");
        if (!isMatch) {
            throw new CardNumberInvalidException("Invalid card number. Please enter exactly 12 digits.");
        } else {
            return isMatch;
        }
    }
    
    public static boolean isValidExpirationDate(String expirationDate) throws DateInvalidException {
        boolean isMatch = expirationDate.matches("\\d{2}/\\d{2}");
        if (!isMatch) {
            throw new DateInvalidException("Invalid  date. Please enter the date in the format MM/YY.");
        } else {
            return isMatch;
        }
    }
    
    public static boolean isValidCVV(String cvv) throws CVVInvalidException {
        // Allow only digits for CVV and ensure a specific length (e.g., 3 digits)
        boolean isMatch = cvv.matches("\\d{3}");
        if (!isMatch) {
            throw new CVVInvalidException("Invalid CVV. Please enter a 3-digit CVV.");
        } else {
            return isMatch;
        }
    }
    
    public static boolean isValidUPIID(String upiId) throws UPIIDInvalidException {
        // Allow alphanumeric characters, dots, and underscores in UPI ID
        boolean isMatch = upiId.matches("[a-zA-Z0-9._]+");
        
        if (!isMatch) {
            throw new UPIIDInvalidException("Invalid UPI ID. Please enter a valid UPI ID with alphanumeric characters, dots, and underscores.");
        } else {
            return isMatch;
        }
    }
}
