package healthcare;

import healthcare.controller.AppController;
import healthcare.view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppController controller = new AppController();
            MainFrame frame = new MainFrame(controller);
            frame.setVisible(true);
        });
    }
}
