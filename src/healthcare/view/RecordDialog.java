package healthcare.view;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.Color;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class RecordDialog extends JDialog {
    private boolean confirmed = false;
    private final Map<String, JTextField> fields = new LinkedHashMap<>();

    public RecordDialog(Window owner, String title, List<String> headers,
                        Map<String, String> initialValues, List<String> readOnlyColumns) {
        super(owner, title, ModalityType.APPLICATION_MODAL);

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(4,4,4,4);
        gc.fill = GridBagConstraints.HORIZONTAL;

        int r = 0;
        for (String h : headers) {
            gc.gridy = r;

            gc.gridx = 0; gc.weightx = 0;
            form.add(new JLabel(h), gc);

            gc.gridx = 1; gc.weightx = 1;
            JTextField tf = new JTextField(30);
            tf.setText(initialValues.getOrDefault(h, ""));

            if (readOnlyColumns != null && readOnlyColumns.contains(h)) {
                tf.setEditable(false);
                tf.setBackground(new Color(245,245,245));
            }

            fields.put(h, tf);
            form.add(tf, gc);
            r++;
        }

        JButton ok = new JButton("OK");
        JButton cancel = new JButton("Cancel");
        ok.addActionListener(e -> { confirmed = true; setVisible(false); });
        cancel.addActionListener(e -> { confirmed = false; setVisible(false); });

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttons.add(ok);
        buttons.add(cancel);

        setLayout(new BorderLayout());
        add(new JScrollPane(form), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        setSize(750, 500);
        setLocationRelativeTo(owner);
    }

    public boolean isConfirmed() { return confirmed; }

    public Map<String, String> getValues() {
        Map<String, String> out = new LinkedHashMap<>();
        for (Map.Entry<String, JTextField> e : fields.entrySet()) {
            out.put(e.getKey(), e.getValue().getText().trim());
        }
        return out;
    }
}
