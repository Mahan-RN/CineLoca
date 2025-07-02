package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

// Represents the settings menue of the UI
public class SettingsWindow {
    private String csvPath;
    private String movieDirectoryPath;
    private JDialog settingsDialog;
    private JFileChooser csvFileChooser;
    private JFileChooser movieDirectoryChooser;
    private JButton csvButton;
    private JButton movieDirectoryButton;
    private JButton imageDirectoryButton;
    private JLabel csvPathLabel;
    private JLabel movieDirectoryPathLabel;
    private JLabel imageDirectoryPathLabel;

    // EFFECTS: initializes the settings menue and its associated components
    public SettingsWindow(JFrame frame) {
        initialize(frame);
        settingsDialog.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: takes a parent container (JFrame of the MainWindow) and creates
    // a JDialog on top of it. Adds buttons and labels to the JDialog.
    // The JDialog:
    // - Is modal (when opened, user can't click on the main page)
    // - Has no layout manager to allow for custome positioning of components
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

    // EFFECTS: creates a JLabel to display the path to the CSV file contaning
    // movie metadata as choosen by the user. Displays "No CSV file selected"
    // when user hasn't chosen anything yet
    private void createCSVPathLabel() {
        csvPathLabel = new JLabel("No CSV file selected");
        csvPathLabel.setBounds(210, 65, 144, 27);
    }

    // EFFECTS: creates a CSV button that upon being clicked on will open
    // a JFileChooser for the user to select the desired movie metadata CSV
    // file. JFileChooser only allows selection of CSV files
    // The text of the CSVPathLabel will change to the absolute path of the
    // selected file.
    // If no file is selected, a warning message will be displayed to inform
    // the user
    private void createCSVButton() {
        csvButton = new JButton("Choose CSV File");
        csvButton.setFocusable(false);
        csvButton.setToolTipText("Select movie metadata CSV file");
        csvButton.setBounds(610, 65, 230, 36);
        csvButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                csvFileChooser = new JFileChooser();
                csvFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file", "csv");
                csvFileChooser.addChoosableFileFilter(filter);
                csvFileChooser.setFileFilter(filter);
                int response = csvFileChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    csvPath = csvFileChooser.getSelectedFile().getAbsolutePath();
                    csvPathLabel.setText("CSV File: " + csvPath);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No CSV File Selected",
                            "CSV Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    // EFFECTS: creates a JLabel to display the absolute path to the movie
    // directory selected by the user. If no directory selected, displays "No
    // Directory Selected"
    private void createMovieDirectoryPathLabel() {
        movieDirectoryPathLabel = new JLabel("No Directory Selected");
        movieDirectoryPathLabel.setBounds(210, 165, 211, 29);
    }

    // EFFECTS: creteas a JButton that when user clicks on it will open a
    // JFileChooser to selected the directory containing movies. When directory
    // is selected, the text of hte MovieDirectoryPathLabel changes to display
    // the absolute path to the directory
    private void createMovieDirectoryButton() {
        movieDirectoryButton = new JButton("Choose Movie Directory");
        movieDirectoryButton.setFocusable(false);
        movieDirectoryButton.setToolTipText("Select path to movie directory");
        movieDirectoryButton.setBounds(610, 165, 230, 39);
        movieDirectoryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                movieDirectoryChooser = new JFileChooser();
                movieDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int response = movieDirectoryChooser.showOpenDialog(null);
                if (response == JFileChooser.APPROVE_OPTION) {
                    movieDirectoryPath = movieDirectoryChooser.getSelectedFile().getAbsolutePath();
                    movieDirectoryPathLabel.setText("CSV File: " + csvPath);
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No Movie Directory Selected",
                            "Movie Directory Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

        });
    }

    // EFFECTS: creates a JLabel to display the absolute path to the directory
    // containing movie poster images as selected by the user
    private void createImageDirectoryPathLabel() {
        imageDirectoryPathLabel = new JLabel("No Directory Selected");
        imageDirectoryPathLabel.setBounds(210, 265, 197, 35);
    }

    // EFFECTS: creates a JButton that when the user clicks on it will open a
    // JFileChooser to select the directory containing movie poster images.
    // Upon selection, the text of the ImageDirectoryPathLabel changes to the
    // absolute path of the selected directory
    private void createImageDirectoryButton() {
        imageDirectoryButton = new JButton("Choose Image Directory");
        imageDirectoryButton.setFocusable(false);
        imageDirectoryButton.setToolTipText("Select path to image directory");
        imageDirectoryButton.setBounds(610, 265, 230, 64);
    }
}
