package com.employeemanagement;

import java.util.Map;

public class View_Employee {
    public static void readEmployeeData(Map<Integer, Employee> employeeMap) {
        // Display all employees
        System.out.println("Employee Data:\n");
        System.out.println("EmpID       EmpName             EmpRole            HireDate      EmpSal        DOB        AdminID      ");
        for (Employee employee : employeeMap.values()) {
            System.out.println(employee);
        }
    }
}
