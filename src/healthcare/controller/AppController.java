package healthcare.controller;

import healthcare.model.AppModel;
import healthcare.service.PrescriptionService;
import healthcare.service.ReferralManager;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class AppController {
    private final File baseDir = new File(System.getProperty("user.dir"));
    private final AppModel model = new AppModel(baseDir);

    private final PrescriptionService prescriptionService = new PrescriptionService();
    private final ReferralManager referralManager = ReferralManager.getInstance();

    public AppModel getModel() { return model; }
    public File getBaseDir() { return baseDir; }

    public void loadAll(JFrame parent) {
        try {
            model.patients.load();
            model.clinicians.load();
            model.facilities.load();
            model.appointments.load();
            model.prescriptions.load();
            model.referrals.load();
            model.staff.load();
            JOptionPane.showMessageDialog(parent, "Loaded CSV files from:\n" + baseDir.getAbsolutePath());
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent, "Load failed:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void saveAll(JFrame parent) {
        try {
            model.patients.save();
            model.clinicians.save();
            model.facilities.save();
            model.appointments.save();
            model.prescriptions.save();
            model.referrals.save();
            model.staff.save();
            JOptionPane.showMessageDialog(parent, "Saved all CSV files.");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(parent, "Save failed:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public Map<String, String> createPrescription(Map<String, String> data) throws IOException {
        return prescriptionService.createPrescription(model, data, baseDir);
    }

    public Map<String, String> createReferral(Map<String, String> data) throws IOException {
        return referralManager.createReferral(model, data, baseDir);
    }
}
