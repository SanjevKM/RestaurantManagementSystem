package Employee_Management;

import java.util.Map;

public class View_Employee {
    public static void readEmployeeData(Map<Integer, Employee> employeeMap) {
        // Display all employees
        System.out.println("Employee Data:");
        for (Employee employee : employeeMap.values()) {
            System.out.println(employee);
        }
    }
}
