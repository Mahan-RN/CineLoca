package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.opencsv.exceptions.CsvValidationException;

import model.FileReader;
import model.MovieCSVReader;
import net.miginfocom.swing.MigLayout;

// Represents the settings menue of the UI
public class SettingsWindow {
    private MovieCSVReader csvReader;
    private FileReader movieFileReader;
    private FileReader imageFileReader;
    private String csvPath;
    private String movieDirectoryPath;
    private String imageDirectoryPath;
    private JDialog settingsDialog;
    private MigLayout mgl;
    private JButton csvButton;
    private JButton movieDirectoryButton;
    private JButton imageDirectoryButton;
    private JButton loadButton;
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
        settingsDialog.setSize(900, 600);
        settingsDialog.setLocationRelativeTo(frame);
        mgl = new MigLayout("insets 20, wrap, fillx",
                "[]100[]",
                "[]100[]100[]200[]");
        settingsDialog.setLayout(mgl);
        settingsDialog.add(createCSVPathLabel());
        settingsDialog.add(createCSVButton(), "center");
        settingsDialog.add(createMovieDirectoryPathLabel());
        settingsDialog.add(createMovieDirectoryButton(), "center");
        settingsDialog.add(createImageDirectoryPathLabel());
        settingsDialog.add(createImageDirectoryButton(), "center");
        settingsDialog.add(createLoadButton(), "span, grow");
    }

    // =====================
    // Private Helper Methods
    // =====================

    // --- JLabels:

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display the path to the CSV file contaning
    // movie metadata as choosen by the user. Displays "No CSV file selected"
    // when user hasn't chosen anything yet
    private JLabel createCSVPathLabel() {
        csvPathLabel = new JLabel("No CSV file selected");
        return csvPathLabel;
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display the absolute path to the movie
    // directory selected by the user. If no directory selected, displays "No
    // Directory Selected"
    private JLabel createMovieDirectoryPathLabel() {
        movieDirectoryPathLabel = new JLabel("No Directory Selected");
        return movieDirectoryPathLabel;
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display the absolute path to the directory
    // containing movie poster images as selected by the user
    private JLabel createImageDirectoryPathLabel() {
        imageDirectoryPathLabel = new JLabel("No Directory Selected");
        return imageDirectoryPathLabel;
    }

    // --- JButtons:

    // MODIFIES: this
    // EFFECTS: creates a CSV button that upon being clicked on will open
    // a JFileChooser for the user to select the desired movie metadata CSV
    // file. JFileChooser only allows selection of CSV files
    // The text of the CSVPathLabel will change to the absolute path of the
    // selected file.
    // If no file is selected, a warning message will be displayed to inform
    // the user
    private JButton createCSVButton() {
        csvButton = new JButton("Choose CSV File");
        csvButton.setFocusable(false);
        csvButton.setToolTipText("Select movie metadata CSV file");
        csvButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser csvFileChooser = csvChooser();
                int response = csvFileChooser.showOpenDialog(settingsDialog);
                if (response == JFileChooser.APPROVE_OPTION) {
                    csvPath = csvFileChooser.getSelectedFile().getAbsolutePath();
                    csvPathLabel.setText("<html>CSV File:<br>"
                            + csvPath
                            + "</html>");
                } else {
                    JOptionPane.showMessageDialog(null,
                            "No CSV File Selected",
                            "CSV Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        return csvButton;
    }

    // MODIFIES: this
    // EFFECTS: creteas a JButton that when user clicks on it will open a
    // JFileChooser to selected the directory containing movies. When directory
    // is selected, the text of hte MovieDirectoryPathLabel changes to display
    // the absolute path to the directory
    private JButton createMovieDirectoryButton() {
        movieDirectoryButton = new JButton("Choose Movie Directory");
        movieDirectoryButton.setFocusable(false);
        movieDirectoryButton.setToolTipText("Select path to movie directory");
        movieDirectoryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser movieDirectoryChooser = directoryChooser();
                int response = movieDirectoryChooser.showOpenDialog(settingsDialog);
                if (response == JFileChooser.APPROVE_OPTION) {
                    movieDirectoryPath = movieDirectoryChooser.getSelectedFile()
                            .getAbsolutePath();
                    movieDirectoryPathLabel.setText("<html>Movie Directory:<br>"
                            + movieDirectoryPath
                            + "</html>");
                } else {
                    warningPopUp("Movie Directory Warning",
                            "No Movie Directory Selected");
                }
            }
        });
        return movieDirectoryButton;
    }

    // MODFIES: this
    // EFFECTS: creates a JButton that when the user clicks on it will open a
    // JFileChooser to select the directory containing movie poster images.
    // Upon selection, the text of the ImageDirectoryPathLabel changes to the
    // absolute path of the selected directory
    private JButton createImageDirectoryButton() {
        imageDirectoryButton = new JButton("Choose Image Directory");
        imageDirectoryButton.setFocusable(false);
        imageDirectoryButton.setToolTipText("Select path to image directory");
        imageDirectoryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imageDirectoryChooser = directoryChooser();
                int response = imageDirectoryChooser.showOpenDialog(settingsDialog);
                if (response == JFileChooser.APPROVE_OPTION) {
                    imageDirectoryPath = imageDirectoryChooser.getSelectedFile()
                            .getAbsolutePath();
                    imageDirectoryPathLabel.setText("<html>Image Directory:<br>"
                            + imageDirectoryPath
                            + "</html>");
                } else {
                    warningPopUp("Image Directory Warning",
                            "No Image Directory Selected");
                }
            }
        });
        return imageDirectoryButton;
    }

    // MODIFIES: this, MovieCollection, Movie(s)
    // EFFECTS: creates a JButton to laod movies from CSV metadata file, movie
    // directory, and image directory selected by the user.
    // - Produces a pop-up error message if user has not selected the required
    // resources
    // - Produces pop-up error messages corresponding to exceptions that might
    // be thrown
    private JButton createLoadButton() {
        loadButton = new JButton("Load Movies to Collection");
        loadButton.setFocusable(false);
        loadButton.setToolTipText("Load movies from selected resources");
        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (csvPath == null) {
                    errorPopUp("CSV Error",
                            "No CSV File Selected");
                } else if (movieDirectoryPath == null) {
                    errorPopUp("Movie Directory Error",
                            "No Movie Directory Selected");
                } else if (imageDirectoryPath == null) {
                    errorPopUp("Image Directory Error",
                            "No Image Directory Selected");
                } else {
                    try {
                        csvReader = new MovieCSVReader(csvPath);
                        csvReader.loadMoviesFromCSV();
                        movieFileReader = new FileReader(movieDirectoryPath);
                        movieFileReader.addPathsToCollection(false);
                        imageFileReader = new FileReader(imageDirectoryPath);
                        imageFileReader.addPathsToCollection(true);
                    } catch (FileNotFoundException ex) {
                        errorPopUp("FileNotFound Exception",
                                ex.getMessage());
                    } catch (IOException ex) {
                        errorPopUp("IO Exception",
                                ex.getMessage());
                    } catch (CsvValidationException ex) {
                        errorPopUp("CsvValidation Exception",
                                ex.getMessage());
                    }
                }
            }
        });
        return loadButton;
    }

    // --- JOptionPane pop-up window helpers:

    // EFFECTS: shows a pop-up error message with the given title and error msg
    private void errorPopUp(String title, String msg) {
        JOptionPane.showMessageDialog(null,
                "An error occurred:\n" + msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: shows a pop-up warning message with the given title and error msg
    private void warningPopUp(String title, String msg) {
        JOptionPane.showMessageDialog(null,
                "Warning:\n" + msg,
                title,
                JOptionPane.WARNING_MESSAGE);
    }

    // --- JFileChooser helpers:

    // MODIFIES: this
    // EFFECTS: returns a JFileChooser that filters to only display csv files
    public JFileChooser csvChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("CSV file",
                "csv");
        chooser.addChoosableFileFilter(filter);
        chooser.setFileFilter(filter);
        return chooser;
    }

    // MODIFIES: this
    // EFFECTS: returns a JFileChooser that filters to only display directories
    public JFileChooser directoryChooser() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        return chooser;
    }
}
