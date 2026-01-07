package healthcare.model;

public class MedicalRecord {
    private String recordId;
    private String treatment;
    private String notes;

    public MedicalRecord() {}
    public MedicalRecord(String recordId, String treatment, String notes) {
        this.recordId = recordId;
        this.treatment = treatment;
        this.notes = notes;
    }

    public void updateRecord() {}
    public void viewRecord() {}
}
