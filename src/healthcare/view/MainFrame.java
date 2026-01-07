package healthcare.view;

import healthcare.controller.AppController;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame(AppController controller) {
        setTitle("Healthcare Management System");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton load = new JButton("Load CSVs");
        JButton save = new JButton("Save CSVs");

        load.addActionListener(e -> controller.loadAll(this));
        save.addActionListener(e -> controller.saveAll(this));

        top.add(load);
        top.add(save);

        add(top, BorderLayout.NORTH);
        add(new JLabel("GUI skeleton ready. Next commit will add tables & CRUD.", SwingConstants.CENTER),
                BorderLayout.CENTER);
    }
}
