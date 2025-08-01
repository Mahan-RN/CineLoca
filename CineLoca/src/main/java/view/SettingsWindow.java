package view;

import java.awt.Color;
import java.awt.Font;
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

import model.MovieCSVReader;
import model.MovieFileReader;
import net.miginfocom.swing.MigLayout;

// Represents the settings menue of the UI
public class SettingsWindow {
    private final int WINDOW_WIDTH = 850;
    private final int WINDOW_HEIGHT = 700;

    private MovieCSVReader csvReader;
    private MovieFileReader movieFileReader;
    private MovieFileReader imageFileReader;
    private String movieCSVPath;
    private String seriesCSVPath;
    private String movieDirectoryPath;
    private String seriesDirectoryPath;
    private String movieImageDirectoryPath;
    private String seriesImageDirectoryPath;
    private JDialog window;
    private MigLayout mgl;
    private JButton movieCSVButton;
    private JButton seriesCSVButton;
    private JButton movieDirectoryButton;
    private JButton seriesDirectoryButton;
    private JButton movieImageDirectoryButton;
    private JButton seriesImageDirectoryButton;
    private JButton loadButton;
    private JLabel movieHeaderLabel;
    private JLabel seriesHeaderLabel;
    private JLabel movieCSVPathLabel;
    private JLabel seriesCSVPathLabel;
    private JLabel movieDirectoryPathLabel;
    private JLabel seriesDirectoryPathLabel;
    private JLabel movieImageDirectoryPathLabel;
    private JLabel seriesImageDirectoryPathLabel;

    // EFFECTS: initializes the settings menue and its associated components
    public SettingsWindow(JFrame frame) {
        initialize(frame);
        window.setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: takes a parent container (JFrame of the MainWindow) and creates
    // a JDialog on top of it. Adds buttons and labels to the JDialog.
    // The JDialog:
    // - Is modal (when opened, user can't click on the main page)
    private void initialize(JFrame frame) {
        window = new JDialog(frame, "Settings", true);
        window.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        window.setLocationRelativeTo(frame);
        mgl = new MigLayout("insets 20, wrap, fillx",
                "[]225[]",
                "[]15[]50[]50[]20[]50[]15[]50[]50[]");
        window.setLayout(mgl);
        window.add(createMovieHeaderLabel(), "span, center");
        window.add(createMovieCSVPathLabel());
        window.add(createMovieCSVButton(), "center");
        window.add(createMovieDirectoryPathLabel());
        window.add(createMovieDirectoryButton(), "center");
        window.add(createMovieImageDirectoryPathLabel());
        window.add(createMovieImageDirectoryButton(), "center");
        window.add(createLoadButton(), "span, center");

        window.add(createSeriesHeaderLabel(), "span, center");
        window.add(createSeriesCSVPathLabel());
        window.add(createSeriesCSVButton(), "center");
        window.add(createSeriesDirectoryPathLabel());
        window.add(createSeriesDirectoryButton(), "center");
        window.add(createSeriesImageDirectoryPathLabel());
        window.add(createSeriesImageDirectoryButton(), "center");
        //window.add(createLoadButton(), "span, center");
    }

    // =====================
    // Private Helper Methods
    // =====================

    // --- JLabels:

    // MODIFIES: this
    // EFFECTS: returns a JLabel with text "Movies" in bold
    private JLabel createMovieHeaderLabel() {
        movieHeaderLabel = new JLabel("Movie Resources");
        movieHeaderLabel.setFont(new Font("Arial", Font.BOLD, 13));
        return movieHeaderLabel;
    }

    // MODIFIES: this
    // EFFECTS: returns a JLabel with text "" in bold
    private JLabel createSeriesHeaderLabel() {
        seriesHeaderLabel = new JLabel("TV Show Resources");
        seriesHeaderLabel.setFont(new Font("Arial", Font.BOLD, 13));
        return seriesHeaderLabel;
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display the path to the CSV file contaning
    // movie metadata as choosen by the user
    private JLabel createMovieCSVPathLabel() {
        movieCSVPathLabel = new JLabel("No CSV file selected for movies");
        return movieCSVPathLabel;
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display the path to the CSV file contaning
    // tv series metadata as choosen by the user
    private JLabel createSeriesCSVPathLabel() {
        seriesCSVPathLabel = new JLabel("No CSV file selected for TV shows");
        return seriesCSVPathLabel;
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display the absolute path to the movie
    // directory selected by the user. If no directory selected, displays "No
    // Directory Selected"
    private JLabel createMovieDirectoryPathLabel() {
        movieDirectoryPathLabel = new JLabel("No directory selected for movies");
        return movieDirectoryPathLabel;
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display the absolute path to the movie
    // directory selected by the user. If no directory selected, displays "No
    // Directory Selected"
    private JLabel createSeriesDirectoryPathLabel() {
        seriesDirectoryPathLabel = new JLabel("No directory selected for TV shows");
        return seriesDirectoryPathLabel;
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display the absolute path to the directory
    // containing movie poster images as selected by the user
    private JLabel createMovieImageDirectoryPathLabel() {
        movieImageDirectoryPathLabel = new JLabel("No directory selected for movie posters");
        return movieImageDirectoryPathLabel;
    }

    // MODIFIES: this
    // EFFECTS: creates a JLabel to display the absolute path to the directory
    // containing TV show poster images as selected by the user
    private JLabel createSeriesImageDirectoryPathLabel() {
        seriesImageDirectoryPathLabel = new JLabel("No directory selected for TV show posters");
        return seriesImageDirectoryPathLabel;
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
    private JButton createMovieCSVButton() {
        movieCSVButton = new JButton("Choose Movie CSV File");
        movieCSVButton.setFocusable(false);
        movieCSVButton.setToolTipText("Select movie metadata CSV file");
        movieCSVButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser csvFileChooser = csvChooser();
                int response = csvFileChooser.showOpenDialog(window);
                if (response == JFileChooser.APPROVE_OPTION) {
                    movieCSVPath = csvFileChooser.getSelectedFile().getAbsolutePath();
                    movieCSVPathLabel.setText("<html>Movie CSV File:<br>"
                            + movieCSVPath
                            + "</html>");
                } else {
                    JOptionPane.showMessageDialog(window,
                            "No Movie CSV File Selected",
                            "Movie CSV Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        return movieCSVButton;
    }

    // MODIFIES: this
    // EFFECTS: creates a button for selection of series CSV data. Only allows
    // selection of CSV files
    // The text of the seriesCSVPathLabel will change to the absolute path of the
    // selected file.
    // If no file is selected, a warning message will be displayed to inform
    // the user
    private JButton createSeriesCSVButton() {
        seriesCSVButton = new JButton("Choose TV Show CSV File");
        seriesCSVButton.setFocusable(false);
        seriesCSVButton.setToolTipText("Select TV show metadata CSV file");
        seriesCSVButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser csvFileChooser = csvChooser();
                int response = csvFileChooser.showOpenDialog(window);
                if (response == JFileChooser.APPROVE_OPTION) {
                    seriesCSVPath = csvFileChooser.getSelectedFile().getAbsolutePath();
                    seriesCSVPathLabel.setText("<html>TV Show CSV File:<br>"
                            + seriesCSVPath
                            + "</html>");
                } else {
                    JOptionPane.showMessageDialog(window,
                            "No TV Show CSV File Selected",
                            "TV Show CSV Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        return seriesCSVButton;
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
                int response = movieDirectoryChooser.showOpenDialog(window);
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

    // MODIFIES: this
    // EFFECTS: creteas a JButton that when user clicks on it will open a
    // JFileChooser to selected the directory containing movies. When directory
    // is selected, the text of hte MovieDirectoryPathLabel changes to display
    // the absolute path to the directory
    private JButton createSeriesDirectoryButton() {
        seriesDirectoryButton = new JButton("Choose TV Show Directory");
        seriesDirectoryButton.setFocusable(false);
        seriesDirectoryButton.setToolTipText("Select path to TV show directory");
        seriesDirectoryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser seriesDirectoryChooser = directoryChooser();
                int response = seriesDirectoryChooser.showOpenDialog(window);
                if (response == JFileChooser.APPROVE_OPTION) {
                    seriesDirectoryPath = seriesDirectoryChooser.getSelectedFile()
                            .getAbsolutePath();
                    seriesDirectoryPathLabel.setText("<html>TV Show Directory:<br>"
                            + seriesDirectoryPath
                            + "</html>");
                } else {
                    warningPopUp("TV Show Directory Warning",
                            "No TV Show Directory Selected");
                }
            }
        });
        return seriesDirectoryButton;
    }

    // MODFIES: this
    // EFFECTS: creates a JButton that when the user clicks on it will open a
    // JFileChooser to select the directory containing movie poster images.
    // Upon selection, the text of the ImageDirectoryPathLabel changes to the
    // absolute path of the selected directory
    private JButton createMovieImageDirectoryButton() {
        movieImageDirectoryButton = new JButton("Choose Movie Poster Directory");
        movieImageDirectoryButton.setFocusable(false);
        movieImageDirectoryButton.setToolTipText("Select path to movie poster directory");
        movieImageDirectoryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imageDirectoryChooser = directoryChooser();
                int response = imageDirectoryChooser.showOpenDialog(window);
                if (response == JFileChooser.APPROVE_OPTION) {
                    movieImageDirectoryPath = imageDirectoryChooser.getSelectedFile()
                            .getAbsolutePath();
                    movieImageDirectoryPathLabel.setText("<html>Movie Poster Directory:<br>"
                            + movieImageDirectoryPath
                            + "</html>");
                } else {
                    warningPopUp("Movie Poster Directory Warning",
                            "No Movie Poster Directory Selected");
                }
            }
        });
        return movieImageDirectoryButton;
    }

    // MODFIES: this
    // EFFECTS: creates a JButton that when the user clicks on it will open a
    // JFileChooser to select the directory containing tv show poster images.
    // Upon selection, the text of the ImageDirectoryPathLabel changes to the
    // absolute path of the selected directory
    private JButton createSeriesImageDirectoryButton() {
        seriesImageDirectoryButton = new JButton("Choose TV Show Poster Directory");
        seriesImageDirectoryButton.setFocusable(false);
        seriesImageDirectoryButton.setToolTipText("Select path to TV show poster directory");
        seriesImageDirectoryButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser imageDirectoryChooser = directoryChooser();
                int response = imageDirectoryChooser.showOpenDialog(window);
                if (response == JFileChooser.APPROVE_OPTION) {
                    seriesImageDirectoryPath = imageDirectoryChooser.getSelectedFile()
                            .getAbsolutePath();
                    seriesImageDirectoryPathLabel.setText("<html>TV Show Poster Directory:<br>"
                            + seriesImageDirectoryPath
                            + "</html>");
                } else {
                    warningPopUp("TV Show Poster Directory Warning",
                            "No TV Show Poster Directory Selected");
                }
            }
        });
        return seriesImageDirectoryButton;
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
        loadButton.setFont(new Font("Arial", Font.BOLD, 13));
        loadButton.setBackground(Color.darkGray);
        loadButton.setToolTipText("Load movies from selected resources");
        loadButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (movieCSVPath == null) {
                    errorPopUp("CSV Error",
                            "No CSV File Selected");
                } else if (movieDirectoryPath == null) {
                    errorPopUp("Movie Directory Error",
                            "No Movie Directory Selected");
                } else if (movieImageDirectoryPath == null) {
                    errorPopUp("Image Directory Error",
                            "No Image Directory Selected");
                } else {
                    try {
                        csvReader = new MovieCSVReader(movieCSVPath);
                        csvReader.loadMediaFromCSV();
                        movieFileReader = new MovieFileReader(movieDirectoryPath);
                        movieFileReader.addPathsToCollection(false);
                        imageFileReader = new MovieFileReader(movieImageDirectoryPath);
                        imageFileReader.addPathsToCollection(true);
                        JOptionPane.showMessageDialog(window,
                                "Data was loaded successfully! "
                                        + "Refresh the main window.",
                                "Success!",
                                JOptionPane.INFORMATION_MESSAGE);
                        window.dispose();
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
        JOptionPane.showMessageDialog(window,
                "An error occurred:\n" + msg,
                title,
                JOptionPane.ERROR_MESSAGE);
    }

    // EFFECTS: shows a pop-up warning message with the given title and error msg
    private void warningPopUp(String title, String msg) {
        JOptionPane.showMessageDialog(window,
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
