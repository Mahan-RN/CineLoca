package ui;

import javax.swing.JFrame;

// Represents the main window of the program's UI
public class MainWindow {

    private JFrame window;

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
        this.window = new JFrame("CineLoca");
        this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.window.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.window.setLocationRelativeTo(null);
    }

    // MODIFIES: this
    // EFFECTS: makes the main window visible; used to start the application
    // from Main
    public void show() {
        this.window.setVisible(true);
    }

}
