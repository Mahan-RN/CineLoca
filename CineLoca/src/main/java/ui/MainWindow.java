package ui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

// Represents the main window of the program's UI
public class MainWindow {

    private JFrame frame;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JButton settingsButton;

    // EFFECTS: initializes the main window JFrame
    public MainWindow() {
        initialize();
    }

    // MODIFIES: this
    // EFFECTS: private set up method for the JFrame of the main window
    // - Sets "CineLoca" as the name displayed on the main window
    // - Makes the "X" button on top left to close the application
    // - Sets the window size to full screen
    // - Sets the relative location of the window at the center of the device
    // screen
    private void initialize() {
        this.frame = new JFrame("CineLoca");
        this.frame.setLayout(new BorderLayout());
        this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.frame.setLocationRelativeTo(null);
        setTopPanel();
        setCenterPanel();
    }

    // MODIFIES: this
    // EFFECTS: creates a JPanel with borderlayout at the NORTH region of
    // the main frame. Adds a settings button at the right of the JPanel
    private void setTopPanel() {
        this.topPanel = new JPanel();
        this.topPanel.setLayout(new BorderLayout());
        this.frame.add(topPanel, BorderLayout.NORTH);
        settingsButton = new JButton("Settings");
        topPanel.add(settingsButton, BorderLayout.EAST);
    }

    // MODIFIES: this
    // EFFECTS: creates a JPanel with flowlayout at the center of the main frame
    private void setCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 5));
        frame.add(centerPanel, BorderLayout.CENTER);
    }

    // MODIFIES: this
    // EFFECTS: makes the main window visible; used to start the application
    // from Main
    public void show() {
        this.frame.setVisible(true);
    }

}
