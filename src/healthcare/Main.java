package healthcare;

import healthcare.controller.AppController;
import healthcare.view.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Create the main controller
            AppController controller = new AppController();

            // Create and show the main application window
            MainFrame frame = new MainFrame(controller);
            frame.setVisible(true);
        });
    }
}
