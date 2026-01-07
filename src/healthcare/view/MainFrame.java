package healthcare.view;

import healthcare.controller.AppController;
import healthcare.model.AppModel;
import healthcare.repo.CsvRepository;
import healthcare.util.IdGenerator;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;

public class MainFrame extends JFrame {
    private final AppController controller;
    private final AppModel model;
    private final Map<String, TablePanel> panels = new LinkedHashMap<>();

    public MainFrame(AppController controller) {
        super("Healthcare Management System (MVC + Swing)");
        this.controller = controller;
        this.model = controller.getModel();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(buildTopBar(), BorderLayout.NORTH);
        add(buildTabs(), BorderLayout.CENTER);
    }

    private JComponent buildTopBar() {
        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton load = new JButton("Load CSVs");
        JButton save = new JButton("Save CSVs");

        load.addActionListener(e -> { controller.loadAll(this); refreshAll(); });
        save.addActionListener(e -> controller.saveAll(this));

        top.add(load);
        top.add(save);
        return top;
    }

    private JComponent buildTabs() {
        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Patients", buildCrudTab(model.patients, "patient_id", "P"));
        tabs.addTab("Clinicians", buildCrudTab(model.clinicians, "clinician_id", "C"));
        tabs.addTab("Appointments", buildCrudTab(model.appointments, "appointment_id", "A"));
        tabs.addTab("Prescriptions", buildCrudTab(model.prescriptions, "prescription_id", "RX"));
        tabs.addTab("Referrals", buildCrudTab(model.referrals, "referral_id", "R"));
        tabs.addTab("Facilities", buildCrudTab(model.facilities, "facility_id", "F"));
        tabs.addTab("Staff", buildCrudTab(model.staff, "staff_id", "ST"));

        return tabs;
    }

    private JPanel buildCrudTab(CsvRepository repo, String idColumn, String prefix) {
        JPanel root = new JPanel(new BorderLayout());
        TablePanel tp = new TablePanel(repo);
        panels.put(idColumn, tp);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton add = new JButton("Add");
        JButton edit = new JButton("Edit");
        JButton del = new JButton("Delete");
        JButton refresh = new JButton("Refresh");

        add.addActionListener(e -> onAdd(repo, idColumn, prefix));
        edit.addActionListener(e -> onEdit(repo, tp, idColumn));
        del.addActionListener(e -> onDelete(repo, tp));
        refresh.addActionListener(e -> tp.refresh());

        buttons.add(add);
        buttons.add(edit);
        buttons.add(del);
        buttons.add(refresh);

        root.add(buttons, BorderLayout.NORTH);
        root.add(tp, BorderLayout.CENTER);
        return root;
    }

    private void onAdd(CsvRepository repo, String idColumn, String prefix) {
        Map<String, String> init = blankRow(repo);
        RecordDialog dlg = new RecordDialog(this, "Add Record", repo.getHeaders(), init, List.of(idColumn));
        dlg.setVisible(true);
        if (!dlg.isConfirmed()) return;

        Map<String, String> row = dlg.getValues();
        if (row.getOrDefault(idColumn, "").isBlank()) {
            int width = 3;
            row.put(idColumn, IdGenerator.nextId(repo.getRows(), idColumn, prefix, width));
        }

        repo.add(row);
        refreshAll();
    }

    private void onEdit(CsvRepository repo, TablePanel tp, String idColumn) {
        int idx = tp.getSelectedRowIndex();
        if (idx < 0) { JOptionPane.showMessageDialog(this, "Select a row first."); return; }

        Map<String, String> existing = repo.getRows().get(idx);
        RecordDialog dlg = new RecordDialog(this, "Edit Record", repo.getHeaders(), existing, List.of(idColumn));
        dlg.setVisible(true);
        if (!dlg.isConfirmed()) return;

        Map<String, String> updated = dlg.getValues();
        if (repo.getHeaders().contains("last_modified")) updated.put("last_modified", LocalDate.now().toString());
        if (repo.getHeaders().contains("last_updated")) updated.put("last_updated", LocalDate.now().toString());

        repo.update(idx, updated);
        refreshAll();
    }

    private void onDelete(CsvRepository repo, TablePanel tp) {
        int idx = tp.getSelectedRowIndex();
        if (idx < 0) { JOptionPane.showMessageDialog(this, "Select a row first."); return; }

        int ok = JOptionPane.showConfirmDialog(this, "Delete selected row?", "Confirm", JOptionPane.YES_NO_OPTION);
        if (ok != JOptionPane.YES_OPTION) return;

        repo.delete(idx);
        refreshAll();
    }

    private Map<String, String> blankRow(CsvRepository repo) {
        Map<String, String> init = new LinkedHashMap<>();
        for (String h : repo.getHeaders()) init.put(h, "");

        if (repo.getHeaders().contains("created_date")) init.put("created_date", LocalDate.now().toString());
        if (repo.getHeaders().contains("registration_date")) init.put("registration_date", LocalDate.now().toString());
        if (repo.getHeaders().contains("last_modified")) init.put("last_modified", LocalDate.now().toString());

        return init;
    }

    private void refreshAll() {
        for (TablePanel p : panels.values()) p.refresh();
    }
}
