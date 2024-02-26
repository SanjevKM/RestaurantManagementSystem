package com.booking;
import java.util.Scanner;

import com.exception.CVVInvalidException;
import com.exception.CardNumberInvalidException;
import com.exception.DateInvalidException;
import com.exception.Methods;
import com.exception.UPIIDInvalidException;

public class Payment {

    static Scanner scanner = new Scanner(System.in);

    public static boolean processPayment(int paymentType, double amount) {
        switch (paymentType) {
            case 1:
                cashPayment.processCashPayment(amount);
                return true;
            case 2:
                cardPayment.processCardPayment(amount);
                return true;
            case 3:
                upiPayment.processUpiPayment(amount);
                return true;
            default:
                System.out.println("Invalid payment type. Please choose cash, card, or UPI.");
                return false;
        }
    }
}

class cashPayment extends Payment {
    static void processCashPayment(double amount) {
        System.out.println("Processing cash payment of " + amount);
    }
}

class cardPayment extends Payment {
    static void processCardPayment(double amount) {
        System.out.println("Processing card payment of $" + amount);

        // card payment process
        String cardNumber = getValidationCardNumber(scanner);

        String expirationDate = getValidationDate(scanner);

        String cvv = getValidationCVV(scanner);

        System.out.println("Payment successful! Thank you for your purchase.");
        
    }
    private static String getValidationCardNumber(Scanner scanner) {
        System.out.println("Enter the Card Number:");
        String cardNumber = scanner.nextLine();

        try {
            if (Methods.isValidCardNumber(cardNumber)) {
                return cardNumber;
            } else {
                return getValidationCardNumber(scanner); // Recursive call to get a valid card number
            }
        } catch (CardNumberInvalidException e) {
            System.out.println(e.getMessage());
            return getValidationCardNumber(scanner);
        }
    }
    private static String getValidationDate(Scanner scanner) {
        System.out.println("Enter the Expiration Date (MM/YY):");
        String expirationDate = scanner.nextLine();

        try {
            if (Methods.isValidExpirationDate(expirationDate)) {
                return expirationDate;
            } else {
                return getValidationDate(scanner); // Recursive call to get a valid expiration date
            }
        } catch (DateInvalidException e) {
            System.out.println(e.getMessage());
            return getValidationDate(scanner);
        }
    }
    private static String getValidationCVV(Scanner scanner) {
        System.out.println("Enter the CVV:");
        String cvv = scanner.nextLine();

        try {
            if (Methods.isValidCVV(cvv)) {
                return cvv;
            } else {
                return getValidationCVV(scanner); // Recursive call to get a valid CVV
            }
        } catch (CVVInvalidException e) {
            System.out.println(e.getMessage());
            return getValidationCVV(scanner);
        }
    }

}

class upiPayment extends Payment {
    static void processUpiPayment(double amount) {
        System.out.println("Processing UPI payment of $" + amount);

        // UPI payment process
        String upiId = getValidationUPIID(scanner);

            System.out.println("Payment successful! Thank you for your purchase.");
        
    }
    
    private static String getValidationUPIID(Scanner scanner) {
        System.out.println("Enter the UPI ID:");
        String upiId = scanner.nextLine();

        try {
            if (Methods.isValidUPIID(upiId)) {
                return upiId;
            } else {
                return getValidationUPIID(scanner); // Recursive call to get a valid UPI ID
            }
        } catch (UPIIDInvalidException e) {
            System.out.println(e.getMessage());
            return getValidationUPIID(scanner);
        }
    }
}
