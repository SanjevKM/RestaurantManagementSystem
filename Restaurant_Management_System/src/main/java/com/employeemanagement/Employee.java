package com.employeemanagement;

import java.sql.Date;

public class Employee {
    private int empId;
    private String empName;
    private String empRole;
    private Date hireDate;
    private double empSal;
    private Date dob;
    private int adminId;


    public Employee(int empId2, String empName2, String empRole2, Date hireDate2, double empSal2, Date dob2,int adminId2) {
    	empId = empId2;
        empName = empName2;
        empRole = empRole2;
        hireDate = hireDate2;
        empSal = empSal2;
        dob = dob2;
        adminId = adminId2;
	}

	public  int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpRole() {
        return empRole;
    }

    public void setEmpRole(String empRole) {
        this.empRole = empRole;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public double getEmpSal() {
        return empSal;
    }

    public void setEmpSal(double empSal) {
        this.empSal = empSal;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }
    
    @Override
    public String toString() {
        return String.format("\n %-8d  %-20s  %-15s  %-12s  %-10.2f  %-15s  %-8d ",
                        empId, empName, empRole, hireDate, empSal, dob, adminId);
    }


}
