package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    private JLabel imageDirectoryPathLabel;

    public SettingsWindow(JFrame frame) {
        initialize(frame);
        settingsDialog.setVisible(true);
    }

    private void initialize(JFrame frame) {
        settingsDialog = new JDialog(frame, "Settings", true);
        settingsDialog.setSize(888, 480);
        settingsDialog.setLocationRelativeTo(null);
        settingsDialog.setLayout(null);
        createCSVPathLabel();
        settingsDialog.add(csvPathLabel);
        createCSVButton();
        settingsDialog.add(csvButton);
        createMovieDirectoryPathLabel();
        settingsDialog.add(movieDirectoryPathLabel);
        createMovieDirectoryButton();
        settingsDialog.add(movieDirectoryButton);
        createImageDirectoryPathLabel();
        settingsDialog.add(imageDirectoryPathLabel);
        createImageDirectoryButton();
        settingsDialog.add(imageDirectoryButton);
    }

    private void createCSVPathLabel() {
        csvPathLabel = new JLabel("No CSV file selected");
        csvPathLabel.setBounds(210, 65, 144, 27);
    }

    private void createCSVButton() {
        csvButton = new JButton("Choose CSV File");
        csvButton.setFocusable(false);
        csvButton.setToolTipText("Select movie metadata CSV file");
        csvButton.setBounds(610, 65, 230, 36);
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

    private void createMovieDirectoryPathLabel() {
        movieDirectoryPathLabel = new JLabel("No Directory Selected");
        movieDirectoryPathLabel.setBounds(210, 165, 211, 29);
    }

    private void createMovieDirectoryButton() {
        movieDirectoryButton = new JButton("Choose Movie Directory");
        movieDirectoryButton.setFocusable(false);
        movieDirectoryButton.setToolTipText("Select path to movie directory");
        movieDirectoryButton.setBounds(610, 165, 230, 39);
    }

    private void createImageDirectoryPathLabel() {
        imageDirectoryPathLabel = new JLabel("No Directory Selected");
        imageDirectoryPathLabel.setBounds(210, 265, 197, 35);
    }

    private void createImageDirectoryButton() {
        imageDirectoryButton = new JButton("Choose Image Directory");
        imageDirectoryButton.setFocusable(false);
        imageDirectoryButton.setToolTipText("Select path to image directory");
        imageDirectoryButton.setBounds(610, 265, 230, 64);
    }
}
