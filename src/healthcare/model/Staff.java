package healthcare.model;

public class Staff extends User {
    protected String staffId;

    public Staff() {}

    public Staff(String staffId, String name, String contactNo, String email, String address, String role) {
        super(staffId, name, contactNo, email, address, role);
        this.staffId = staffId;
    }

    public String getStaffId() { return staffId; }
    public void setStaffId(String staffId) { this.staffId = staffId; }

    public String viewSchedule() { return "Schedule not implemented"; }
}
