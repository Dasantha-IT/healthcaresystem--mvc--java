package healthcare.model;

// This class stores basic patient information

public class Patient extends User {
    private String gender;
    private String dateOfBirth;

    public Patient() { this.role = "Patient"; }

    public Patient(String patientId, String name, String contactNo, String email, String address,
                   String gender, String dateOfBirth) {
        super(patientId, name, contactNo, email, address, "Patient");
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() { return gender; }
    public String getDateOfBirth() { return dateOfBirth; }

    public void setGender(String gender) { this.gender = gender; }
    public void setDate_of_birth(String dob) { this.dateOfBirth = dob; }

    public void viewProfile() {}
    public void viewMedicalRecords() {}
    public void bookAppointment() {}
}
