package healthcare.model;

public class Doctor extends User {
    protected String specialization;

    public Doctor() { this.role = "Doctor"; }

    public Doctor(String clinicianId, String name, String contactNo, String email, String address, String specialization) {
        super(clinicianId, name, contactNo, email, address, "Doctor");
        this.specialization = specialization;
    }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public void viewAppointment() {}
    public void createPrescription() {}
    public void makeReferral() {}
}
