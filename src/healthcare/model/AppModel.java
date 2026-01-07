package healthcare.model;

import healthcare.repo.CsvRepository;

import java.io.File;
import java.util.List;

public class AppModel {
    public final CsvRepository patients;
    public final CsvRepository clinicians;
    public final CsvRepository facilities;
    public final CsvRepository appointments;
    public final CsvRepository prescriptions;
    public final CsvRepository referrals;
    public final CsvRepository staff;

    public AppModel(File baseDir) {
        patients = new CsvRepository(new File(baseDir, "patients.csv"), List.of(
                "patient_id","first_name","last_name","date_of_birth","nhs_number","gender",
                "phone_number","email","address","postcode","emergency_contact_name","emergency_contact_phone",
                "registration_date","gp_surgery_id"
        ));

        clinicians = new CsvRepository(new File(baseDir, "clinicians.csv"), List.of(
                "clinician_id","first_name","last_name","title","speciality","qualification","registration_number",
                "phone_number","email","workplace_id","workplace_type","employment_status","start_date"
        ));

        facilities = new CsvRepository(new File(baseDir, "facilities.csv"), List.of(
                "facility_id","facility_name","facility_type","address","postcode","phone_number","email",
                "services_offered","opening_hours","manager_name","capacity","specialities_offered"
        ));

        appointments = new CsvRepository(new File(baseDir, "appointments.csv"), List.of(
                "appointment_id","patient_id","clinician_id","facility_id","appointment_date","appointment_time",
                "duration_minutes","appointment_type","status","reason_for_visit","notes","created_date","last_modified"
        ));

        prescriptions = new CsvRepository(new File(baseDir, "prescriptions.csv"), List.of(
                "prescription_id","patient_id","clinician_id","appointment_id","condition","medication","dosage",
                "instructions","pharmacy_name","status","issue_date","collection_date"
        ));

        referrals = new CsvRepository(new File(baseDir, "referrals.csv"), List.of(
                "referral_id","patient_id","referring_clinician_id","referred_to_facility_id","referred_to_clinician_id",
                "referral_type","urgency_level","clinical_summary","reason_for_referral","status","appointment_id","notes",
                "created_date","last_updated"
        ));

        staff = new CsvRepository(new File(baseDir, "staff.csv"), List.of(
                "staff_id","first_name","last_name","role","department","facility_id","phone_number","email",
                "employment_status","start_date","line_manager","access_level"
        ));
    }
}
