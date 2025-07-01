package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SettingsWindow {
    private String csvPath;
    private JDialog settingsDialog;
    private JFileChooser csvFileChooser;
    private JButton csvButton;
    private JButton movieDirectoryButton;
    private JButton imageDirectoryButton;
    private JLabel csvPathLabel;
    private JLabel movieDirectoryPathLabel;

    public SettingsWindow(JFrame frame) {
        initialize(frame);
        settingsDialog.setVisible(true);
    }

    private void initialize(JFrame frame) {
        settingsDialog = new JDialog(frame, "Settings", true);
        settingsDialog.setSize(700, 600);
        settingsDialog.setLocationRelativeTo(null);
        // settingsDialog.setLayout(new BoxLayout(settingsDialog.getContentPane(),
        //         BoxLayout.Y_AXIS));
        settingsDialog.setLayout(new GridLayout(0, 2, 40, 20));
        createCSVPathLabel();
        settingsDialog.add(csvPathLabel);
        createCSVButton();
        settingsDialog.add(csvButton);
    }

    private void createCSVPathLabel() {
        csvPathLabel = new JLabel("No CSV file selected");
    }

    private void createCSVButton() {
        csvButton = new JButton("Choose CSV File");
        csvButton.setFocusable(false);
        csvButton.setToolTipText("Select movie metadata CSV file");
        csvButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                csvFileChooser = new JFileChooser();
                int response = csvFileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    csvPath = csvFileChooser.getSelectedFile().getAbsolutePath();
                    csvPathLabel.setText("CSV File: " + csvPath);
                } else {
                    csvPathLabel.setText("No CSV file selected");
                }
            }

        });

    }
}
