package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Movie;
import model.MovieCollection;
import net.miginfocom.swing.MigLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Represents the main window of the program's UI
public class MainWindow {

    private MovieCollection movieCollection;
    private JFrame frame;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JScrollPane scrollPane;
    private JButton settingsButton;
    private JButton loadMoviesButton;
    private JButton informationButton;
    private JLabel totalMoviesCounter;
    private MigLayout mgl;

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
        mgl = new MigLayout("insets 10, fillx",
                "",
                "[][grow][]");
        this.topPanel.setLayout(mgl);
        this.frame.add(topPanel, BorderLayout.NORTH);
        createSettingsButton();
        createInformationButton();
        createTotalMoviesCounterLabel();
        createLoadMoviesButton();
        topPanel.add(settingsButton, "split 2, left, gapx 5");
        topPanel.add(loadMoviesButton);
        topPanel.add(totalMoviesCounter, "center");
        topPanel.add(informationButton, "right");
    }

    // MODIFIES: this
    // EFFECTS: creates a vertically scrollable JPanel with grid layout at
    // the center of the main frame.
    // - Grid layout has unlimied rows (0), 5 columns, 10 pixel hgap, and
    // 15 pixel vgap
    private void setCenterPanel() {
        centerPanel = new JPanel();
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane = new JScrollPane(centerPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        centerPanel.setLayout(new GridLayout(0, 4, 10, 10));
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    // EFFECTS: creates a JButton with label "Settings"
    private void createSettingsButton() {
        settingsButton = new JButton();
        ImageIcon icon = new ImageIcon("CineLoca\\src\\main\\resources\\view\\buttonIcons\\settingsButton.png");
        settingsButton.setIcon(icon);
        settingsButton.setFocusable(false);
        settingsButton.setToolTipText("Settings: set path to application resources");
        settingsButton.setBackground(Color.GRAY);
        settingsButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new SettingsWindow(frame);
            }

        });
    }

    // EFFECTS: creates a JButton with label "Load Movies"
    public void createLoadMoviesButton() {
        loadMoviesButton = new JButton();
        ImageIcon icon = new ImageIcon("CineLoca\\src\\main\\resources\\view\\buttonIcons\\refreshButton.png");
        loadMoviesButton.setIcon(icon);
        loadMoviesButton.setFocusable(false);
        loadMoviesButton.setToolTipText("Refresh window");
        loadMoviesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                for (Movie movie : movieCollection.getMovieMap().values()) {
                    MovieCard card = new MovieCard(movie);
                    JPanel cardPanel = card.getPanel();
                    centerPanel.add(cardPanel);
                }
                totalMoviesCounter.setText("Total Movies in Collection: "
                        + movieCollection.getAllMovieIDs().size());
                centerPanel.revalidate();
                centerPanel.repaint();
            }
        });
    }

    // EFFECTS: creates a JButton with label "Session Information"
    private void createInformationButton() {
        informationButton = new JButton();
        ImageIcon icon = new ImageIcon("CineLoca\\src\\main\\resources\\view\\buttonIcons\\informationButton.png");
        informationButton.setIcon(icon);
        informationButton.setFocusable(false);
        informationButton.setToolTipText("Information: current session information and about this app");
    }

    // EFFECTS: creates a JLabel that shows total number of movies in collection
    private void createTotalMoviesCounterLabel() {
        int moviesNum = movieCollection.getAllMovieIDs().size();
        totalMoviesCounter = new JLabel("Total Movies in Collection: "
                + moviesNum, JLabel.CENTER);
        totalMoviesCounter.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    // MODIFIES: this
    // EFFECTS: makes the main window visible; used to start the application
    // from Main
    public void show() {
        this.frame.setVisible(true);
    }

}
