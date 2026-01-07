package healthcare.model;

public class Referral {
    private String referralId;
    private String referredBy;
    private String referredTo;
    private String reason;
    private String status;

    public Referral() {}
    public Referral(String referralId, String referredBy, String referredTo, String reason, String status) {
        this.referralId = referralId;
        this.referredBy = referredBy;
        this.referredTo = referredTo;
        this.reason = reason;
        this.status = status;
    }

    public void createReferral() {}
    public void updateStatus() {}
}
