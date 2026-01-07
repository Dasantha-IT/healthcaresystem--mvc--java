package healthcare.service;

import healthcare.model.AppModel;
import healthcare.util.IdGenerator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class PrescriptionService {

    // Adds a prescription row + writes output/prescriptions/<id>.txt
    public Map<String, String> createPrescription(AppModel model, Map<String, String> data, File baseDir) throws IOException {
        String newId = IdGenerator.nextId(model.prescriptions.getRows(), "prescription_id", "RX", 3);

        Map<String, String> row = new LinkedHashMap<>();
        for (String h : model.prescriptions.getHeaders()) row.put(h, "");
        row.putAll(data);

        row.put("prescription_id", newId);
        if (row.getOrDefault("status","").isBlank()) row.put("status", "Issued");
        if (row.getOrDefault("issue_date","").isBlank()) row.put("issue_date", LocalDate.now().toString());

        model.prescriptions.add(row);
        writePrescriptionText(row, baseDir);
        return row;
    }

    private void writePrescriptionText(Map<String, String> p, File baseDir) throws IOException {
        File outDir = new File(baseDir, "output/prescriptions");
        if (!outDir.exists()) outDir.mkdirs();

        File out = new File(outDir, p.get("prescription_id") + ".txt");

        String content =
                "=== PRESCRIPTION ===\n" +
                        "Prescription ID: " + p.getOrDefault("prescription_id","") + "\n" +
                        "Issue Date: " + p.getOrDefault("issue_date","") + "\n\n" +
                        "Patient ID: " + p.getOrDefault("patient_id","") + "\n" +
                        "Clinician ID: " + p.getOrDefault("clinician_id","") + "\n" +
                        "Appointment ID: " + p.getOrDefault("appointment_id","") + "\n\n" +
                        "Condition: " + p.getOrDefault("condition","") + "\n" +
                        "Medication: " + p.getOrDefault("medication","") + "\n" +
                        "Dosage: " + p.getOrDefault("dosage","") + "\n" +
                        "Instructions: " + p.getOrDefault("instructions","") + "\n\n" +
                        "Pharmacy: " + p.getOrDefault("pharmacy_name","") + "\n" +
                        "Status: " + p.getOrDefault("status","") + "\n" +
                        "Collection Date: " + p.getOrDefault("collection_date","") + "\n";

        try (Writer w = new OutputStreamWriter(new FileOutputStream(out), StandardCharsets.UTF_8)) {
            w.write(content);
        }
    }
}
