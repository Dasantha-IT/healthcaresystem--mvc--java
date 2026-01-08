package healthcare.service;

// Singleton class used to manage referrals

import healthcare.model.AppModel;
import healthcare.util.IdGenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

public final class ReferralManager {
    private static ReferralManager instance;

    // Queue to show "single consistent referral processing"
    private final List<Map<String, String>> referralQueue = new ArrayList<>();

    private ReferralManager() {}

    public static synchronized ReferralManager getInstance() {
        if (instance == null) instance = new ReferralManager();
        return instance;
    }

    public List<Map<String, String>> getReferralQueueSnapshot() {
        return new ArrayList<>(referralQueue);
    }

    // Adds a referral row + writes output/referrals + output/emails
    public Map<String, String> createReferral(AppModel model, Map<String, String> referralData, File baseDir) throws IOException {
        String newId = IdGenerator.nextId(model.referrals.getRows(), "referral_id", "R", 3);

        Map<String, String> row = new LinkedHashMap<>();
        for (String h : model.referrals.getHeaders()) row.put(h, "");
        row.putAll(referralData);

        row.put("referral_id", newId);

        String today = LocalDate.now().toString();
        if (row.getOrDefault("created_date","").isBlank()) row.put("created_date", today);
        row.put("last_updated", today);

        if (row.getOrDefault("status","").isBlank()) row.put("status", "Pending");

        model.referrals.add(row);
        referralQueue.add(row);

        writeReferralText(row, baseDir);
        writeSimulatedEmail(row, baseDir);

        return row;
    }

    public void markProcessed(String referralId) {
        referralQueue.removeIf(r -> referralId.equals(r.get("referral_id")));
    }

    private void writeReferralText(Map<String, String> r, File baseDir) throws IOException {
        File outDir = new File(baseDir, "output/referrals");
        if (!outDir.exists()) outDir.mkdirs();
        File out = new File(outDir, r.get("referral_id") + ".txt");

        try (Writer w = new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8)) {
            w.write(buildReferralText(r));
        }
    }

    private void writeSimulatedEmail(Map<String, String> r, File baseDir) throws IOException {
        File outDir = new File(baseDir, "output/emails");
        if (!outDir.exists()) outDir.mkdirs();
        File out = new File(outDir, r.get("referral_id") + "_email.txt");

        String subject = "Referral " + r.get("referral_id") + " - Urgency: " + r.getOrDefault("urgency_level", "N/A");

        String body =
                "To: " + r.getOrDefault("referred_to_facility_id","") + " / " + r.getOrDefault("referred_to_clinician_id","") + "\n" +
                        "From: " + r.getOrDefault("referring_clinician_id","") + "\n" +
                        "Subject: " + subject + "\n\n" +
                        buildReferralText(r);

        try (Writer w = new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8)) {
            w.write(body);
        }
    }

    private String buildReferralText(Map<String, String> r) {
        return
                "=== REFERRAL DOCUMENT ===\n" +
                        "Referral ID: " + r.getOrDefault("referral_id","") + "\n" +
                        "Created Date: " + r.getOrDefault("created_date","") + "\n" +
                        "Last Updated: " + r.getOrDefault("last_updated","") + "\n\n" +

                        "Patient ID: " + r.getOrDefault("patient_id","") + "\n" +
                        "Referring Clinician: " + r.getOrDefault("referring_clinician_id","") + "\n" +
                        "Referred To Facility: " + r.getOrDefault("referred_to_facility_id","") + "\n" +
                        "Referred To Clinician: " + r.getOrDefault("referred_to_clinician_id","") + "\n\n" +

                        "Referral Type: " + r.getOrDefault("referral_type","") + "\n" +
                        "Urgency: " + r.getOrDefault("urgency_level","") + "\n" +
                        "Status: " + r.getOrDefault("status","") + "\n" +
                        "Appointment ID: " + r.getOrDefault("appointment_id","") + "\n\n" +

                        "Clinical Summary:\n" + r.getOrDefault("clinical_summary","") + "\n\n" +
                        "Reason For Referral:\n" + r.getOrDefault("reason_for_referral","") + "\n\n" +
                        "Notes:\n" + r.getOrDefault("notes","") + "\n";
    }
}
