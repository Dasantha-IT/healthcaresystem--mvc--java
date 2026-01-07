package healthcare.model;

public class Specialist extends Doctor {
    private String field;

    public Specialist() { this.role = "Specialist"; }
    public String getField() { return field; }
    public void setField(String field) { this.field = field; }

    public void viewReferrals() {}
    public void updateReferralStatus() {}
}
