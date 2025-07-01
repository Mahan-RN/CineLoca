package view;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class SettingsWindow {
    private JDialog settingsDialog;
    private JFileChooser csvFileChooser;
    private JButton csvButton;
    private JButton movieDirectoryButton;
    private JButton imageDirectoryButton;

    public SettingsWindow(JFrame frame) {
        initialize(frame);
        settingsDialog.setVisible(true);
    }

    private void initialize(JFrame frame) {
        settingsDialog = new JDialog(frame, "Settings", true);
        settingsDialog.setSize(700, 600);
        settingsDialog.setLocationRelativeTo(null);
        settingsDialog.setLayout(new BoxLayout(settingsDialog.getContentPane(),
                BoxLayout.Y_AXIS));
    }
}
