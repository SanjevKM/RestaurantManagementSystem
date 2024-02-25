
package Table_Management;

public class Tables {
    
    private int tableNo;
    private String tableStatus;
    private int empId;
    private int seats;

   

	public Tables(int tableNo, String tableStatus, int empId, int seats) {
        this.tableNo = tableNo;
        this.tableStatus = tableStatus;
        this.empId = empId;
        this.seats=seats;
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

    public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}
}
