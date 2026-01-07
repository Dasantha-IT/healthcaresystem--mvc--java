package healthcare.model;

public class Prescription {
    private String prescriptionId;
    private String medication;
    private String dosage;
    private String issueDate;

    public Prescription() {}
    public Prescription(String prescriptionId, String medication, String dosage, String issueDate) {
        this.prescriptionId = prescriptionId;
        this.medication = medication;
        this.dosage = dosage;
        this.issueDate = issueDate;
    }

    public void issuePrescription() {}
    public void viewPrescription() {}
}
