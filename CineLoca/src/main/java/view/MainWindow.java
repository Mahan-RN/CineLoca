package view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;

import org.openide.awt.DropDownButtonFactory;

import model.Movie;
import model.MovieCollection;
import net.miginfocom.swing.MigLayout;
import view.util.WrapLayout;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

// Represents the main window of the program's UI
public class MainWindow {

    private final int FRAME_WIDTH = 800;
    private final int FRAME_HEIGHT = 500;
    private final String LOAD_BUTTON_ICON = "CineLoca\\src\\main\\resources"
            + "\\view\\buttonIcons\\refreshButton.png";
    private final String SETTINGS_BUTTON_ICON = "CineLoca\\src\\main\\resources"
            + "\\view\\buttonIcons\\settingsButton.png";
    private final String INFORMATION_BUTTON_ICON = "CineLoca\\src\\main"
            + "\\resources\\view\\buttonIcons\\informationButton.png";
    private final String SORT_BY_BUTTON_ICON = "CineLoca\\src\\main\\resources"
            + "\\view\\buttonIcons\\sortButton.png";

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
        this.frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
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
        topPanel.add(settingsButton, "split 3, left, gapx 5");
        topPanel.add(loadMoviesButton);
        topPanel.add(createSortButton());
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
        WrapLayout wl = new WrapLayout(WrapLayout.CENTER, 10, 10);
        centerPanel.setLayout(wl);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane = new JScrollPane(centerPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    // EFFECTS: creates a JButton with label "Settings"
    private void createSettingsButton() {
        settingsButton = new JButton();
        ImageIcon icon = new ImageIcon(SETTINGS_BUTTON_ICON);
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
        ImageIcon icon = new ImageIcon(LOAD_BUTTON_ICON);
        loadMoviesButton.setIcon(icon);
        loadMoviesButton.setFocusable(false);
        loadMoviesButton.setToolTipText("Refresh window");
        loadMoviesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (movieCollection.getAllMovieIDs().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Movie> movies = movieCollection.moviesSortedByTitle();
                    for (Movie movie : movies) {
                        MovieCard card = new MovieCard(frame, movie);
                        JPanel cardPanel = card.getPanel();
                        centerPanel.add(cardPanel);
                    }
                    totalMoviesCounter.setText("Total Movies in Collection: "
                            + movieCollection.getAllMovieIDs().size());
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            }
        });
    }

    // EFFECTS: creates a JButton with label "Session Information"
    private void createInformationButton() {
        informationButton = new JButton();
        ImageIcon icon = new ImageIcon(INFORMATION_BUTTON_ICON);
        informationButton.setIcon(icon);
        informationButton.setFocusable(false);
        informationButton.setToolTipText("Information: current session information and about this app");
        informationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InformationWindow(frame);
            }
        });
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

    private JButton createSortButton() {
        JPopupMenu popupMenu = new JPopupMenu("Sort by:");
        popupMenu.add(sortByTitleItem());
        popupMenu.add(sortByYearItem());
        ImageIcon icon = new ImageIcon(SORT_BY_BUTTON_ICON);
        JButton dropDownButton = DropDownButtonFactory.createDropDownButton(icon, popupMenu);
        return dropDownButton;
    }

    // EFFECTS: creates a menu item that when clicked, sorts the collection
    // by title. If collection is empty, shows a pop-up error.
    private JMenuItem sortByTitleItem() {
        JMenuItem menuItemSortByTitle = new JMenuItem("Title");
        menuItemSortByTitle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (movieCollection.getAllMovieIDs().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Movie> movies = movieCollection.moviesSortedByTitle();
                    for (Movie movie : movies) {
                        MovieCard card = new MovieCard(frame, movie);
                        JPanel cardPanel = card.getPanel();
                        centerPanel.add(cardPanel);
                    }
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            }
        });
        return menuItemSortByTitle;
    }

    // EFFECTS: creates a menu item that when clicked, sort the collection by
    // release year. Shows a pop-up error if collection is empty
    private JMenuItem sortByYearItem() {
        JMenuItem menuItemSortByYear = new JMenuItem("Release Year");
        menuItemSortByYear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (movieCollection.getAllMovieIDs().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Movie> movies = movieCollection.moviesSortedByYear();
                    for (Movie movie : movies) {
                        MovieCard card = new MovieCard(frame, movie);
                        JPanel cardPanel = card.getPanel();
                        centerPanel.add(cardPanel);
                    }
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            }
        });
        return menuItemSortByYear;
    }

    // EFFECTS: pop-up error message to inform user that collection is empty
    private void emptyCollectionPopUp() {
        JOptionPane.showMessageDialog(frame,
                "Your collection is empty. Load movies first.",
                "Empty Collection",
                JOptionPane.ERROR_MESSAGE);
    }

}
