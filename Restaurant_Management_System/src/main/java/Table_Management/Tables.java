
package Table_Management;

public class Tables {
    
    private int tableNo;
    private String tableStatus;
    private int empId;

    public Tables(int tableNo, String tableStatus, int empId) {
        this.tableNo = tableNo;
        this.tableStatus = tableStatus;
        this.empId = empId;
    }

    public int getTableNo() {
        return tableNo;
    }

    public void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    public String getTableStatus() {
        return tableStatus;
    }

    public void setTableStatus(String tableStatus) {
        this.tableStatus = tableStatus;
    }

    public int getEmpId() {
        return empId;
    }

    public void setEmpId(int empId) {
        this.empId = empId;
    }

}
