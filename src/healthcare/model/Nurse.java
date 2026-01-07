package healthcare.model;

public class Nurse extends Staff {
    private String ward;

    public Nurse() { this.role = "Nurse"; }
    public String getWard() { return ward; }
    public void setWard(String ward) { this.ward = ward; }

    public void assistDoctor() {}
    public void viewPatientList() {}
}
