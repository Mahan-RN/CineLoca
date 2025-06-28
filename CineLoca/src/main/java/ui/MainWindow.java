package ui;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.MovieCollection;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;

// Represents the main window of the program's UI
public class MainWindow {

    private MovieCollection movieCollection;
    private JFrame frame;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JScrollPane scrollPane;
    private JButton settingsButton;
    private JButton informationButton;
    private JLabel totalMoviesCounter;

    // EFFECTS: initializes the main window JFrame
    public MainWindow() {
        movieCollection = MovieCollection.getInstance();
        initializeMainFrame();
    }

    // MODIFIES: this
    // EFFECTS: private set up method for the JFrame of the main window
    // - Sets "CineLoca" as the name displayed on the main window
    // - Makes the "X" button on top left to close the application
    // - Sets the window size to full screen; then sets size for when the screen
    // is minimized
    // - Sets the relative location of the window at the center of the device
    // screen
    private void initializeMainFrame() {
        this.frame = new JFrame("CineLoca");
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setSize(800, 500);
        this.frame.setLocationRelativeTo(null);
        setTopPanel();
        setCenterPanel();
    }

    // MODIFIES: this
    // EFFECTS: creates a JPanel with borderlayout at the NORTH region of
    // the main frame. Adds a settings button at the right of the JPanel
    private void setTopPanel() {
        this.topPanel = new JPanel();
        this.topPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 15));
        this.frame.add(topPanel, BorderLayout.NORTH);
        createSettingsButton();
        createInformationButton();
        createTotalMoviesCounterLabel();
        topPanel.add(settingsButton);
        topPanel.add(informationButton);
        topPanel.add(totalMoviesCounter);
    }

    // MODIFIES: this
    // EFFECTS: creates a vertically scrollable JPanel with grid layout at
    // the center of the main frame.
    // - Grid layout has unlimied rows (0), 5 columns, 10 pixel hgap, and
    // 15 pixel vgap
    private void setCenterPanel() {
        centerPanel = new JPanel();
        scrollPane = new JScrollPane(centerPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        centerPanel.setLayout(new GridLayout(0, 5, 10, 15));
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    // EFFECTS: creates a JButton with label "Settings"
    private void createSettingsButton() {
        settingsButton = new JButton("Settings");
    }

    // EFFECTS: creates a JButton with label "Session Information"
    private void createInformationButton() {
        informationButton = new JButton("Session Information");
    }

    // EFFECTS: creates a JLabel that shows total number of movies in collection
    private void createTotalMoviesCounterLabel() {
        int moviesNum = movieCollection.getAllMovieIDs().size();
        totalMoviesCounter = new JLabel("Total Movies in Collection: "
                + moviesNum, JLabel.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: makes the main window visible; used to start the application
    // from Main
    public void show() {
        this.frame.setVisible(true);
    }

}
