package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.openide.awt.DropDownButtonFactory;

import model.MediaCollection;
import model.Movie;
import model.Series;
import net.miginfocom.swing.MigLayout;
import view.util.WrapLayout;

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

    private MediaCollection movieCollection;
    private boolean movieView;
    private JFrame frame;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JScrollPane scrollPane;
    private JButton settingsButton;
    private JButton loadMoviesButton;
    private JButton loadSeriesButton;
    private JButton informationButton;
    private JButton searchButton;
    private JLabel totalMoviesCounter;
    private JTextField textField;
    private MigLayout mgl;

    // EFFECTS: initializes the main window JFrame
    public MainWindow() {
        movieCollection = MediaCollection.getInstance();
        movieView = true;
        initializeMainFrame();
    }

    // MODIFIES: this
    // EFFECTS: makes the main frame visible
    public void show() {
        this.frame.setVisible(true);
    }

    // =====================
    // Private Helper Methods
    // =====================

    // Main Frame and its container components:

    // MODIFIES: this
    // EFFECTS: private set up method for main JFrame
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
    // the main frame. Adds its components.
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
        createLoadSeriesButton();
        topPanel.add(settingsButton, "split 4, left, gapx 5");
        topPanel.add(loadMoviesButton);
        topPanel.add(loadSeriesButton);
        topPanel.add(createSortButton());
        topPanel.add(totalMoviesCounter, "center");
        topPanel.add(createSearchBar(), "split 2, right, gapx 10");
        topPanel.add(informationButton);
    }

    // MODIFIES: this
    // EFFECTS: creates a vertically scrollable JPanel at the center of the
    // main frame. Adds its components
    private void setCenterPanel() {
        centerPanel = new JPanel();
        WrapLayout wl = new WrapLayout(WrapLayout.CENTER, 10, 10);
        centerPanel.setLayout(wl);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10,
                10, 10));
        scrollPane = new JScrollPane(centerPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        frame.add(scrollPane, BorderLayout.CENTER);
    }

    // Buttons:

    // EFFECTS: creates the settings button. When clicked, opens the
    // settings window
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

    // EFFECTS: creates a load movies button. When clicked, loads movies from
    // the collection, sorted by title.
    // If collection is empty, shows a pop-up error message
    public void createLoadMoviesButton() {
        ImageIcon icon = new ImageIcon(LOAD_BUTTON_ICON);
        loadMoviesButton = new JButton("Refresh movies", icon);
        loadMoviesButton.setFocusable(false);
        loadMoviesButton.setToolTipText("Refresh window");
        loadMoviesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                movieView = true;
                if (movieCollection.getAllMediaIDs().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Movie> movies = movieCollection.moviesSortedByTitleAscending();
                    for (Movie movie : movies) {
                        MovieCard card = new MovieCard(frame, movie);
                        JPanel cardPanel = card.getPanel();
                        centerPanel.add(cardPanel);
                    }
                    totalMoviesCounter.setText("Total Movies in Collection: "
                            + movieCollection.getAllMediaIDs().size());
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            }
        });
    }

    // EFFECTS: creates a load series button. When clicked, loads series from
    // the collection, sorted by title.
    // If collection is empty, shows a pop-up error message
    public void createLoadSeriesButton() {
        ImageIcon icon = new ImageIcon(LOAD_BUTTON_ICON);
        loadSeriesButton = new JButton("Refresh TV shows", icon);
        loadSeriesButton.setFocusable(false);
        loadSeriesButton.setToolTipText("Refresh window");
        loadSeriesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                movieView = false;
                if (movieCollection.getSeries().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Series> seriesList = movieCollection.seriesSortedByTitleAscending();
                    for (Series series : seriesList) {
                        SeriesCard card = new SeriesCard(frame, series);
                        JPanel cardPanel = card.getMainPanel();
                        centerPanel.add(cardPanel);
                    }
                    totalMoviesCounter.setText("Total TV shows in collection: "
                            + movieCollection.getSeries().size());
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            }
        });
    }

    // EFFECTS: creates the information button. When clicked, opens information
    // window
    private void createInformationButton() {
        informationButton = new JButton();
        ImageIcon icon = new ImageIcon(INFORMATION_BUTTON_ICON);
        informationButton.setIcon(icon);
        informationButton.setFocusable(false);
        String text = "Information: current session information and about this app";
        informationButton.setToolTipText(text);
        informationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new InformationWindow(frame);
            }
        });
    }

    // EFFECTS: creates a sort button. When clicked, opens a drop-down menu
    // letting user choose how to sort their collection
    private JButton createSortButton() {
        JPopupMenu popupMenu = new JPopupMenu("Sort by:");
        popupMenu.add(sortByTitleItemAscending());
        popupMenu.add(sortByTitleItemDescending());
        popupMenu.add(sortByYearItemAscending());
        popupMenu.add(sortByYearItemDescending());
        ImageIcon icon = new ImageIcon(SORT_BY_BUTTON_ICON);
        JButton dropDownButton = DropDownButtonFactory.createDropDownButton(icon,
                popupMenu);
        dropDownButton.setToolTipText("Sort movies by:");
        return dropDownButton;
    }

    // Labels:

    // EFFECTS: creates a label that shows total number of movies in the
    // collection
    private void createTotalMoviesCounterLabel() {
        int moviesNum = movieCollection.getAllMediaIDs().size();
        totalMoviesCounter = new JLabel("Total Movies in Collection: "
                + moviesNum, JLabel.CENTER);
        totalMoviesCounter.setFont(new Font("Arial", Font.PLAIN, 16));
    }

    // Menu Items:

    // EFFECTS: creates a menu item that when clicked, sorts the collection
    // by title. If collection is empty, shows a pop-up error.
    private JMenuItem sortByTitleItemAscending() {
        JMenuItem menuItemSortByTitle = new JMenuItem("Title (0–9, A–Z)");
        menuItemSortByTitle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (movieCollection.getAllMediaIDs().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Movie> movies = movieCollection.moviesSortedByTitleAscending();
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

    // EFFECTS: creates a menu item that when clicked, sorts the collection
    // by title. If collection is empty, shows a pop-up error.
    private JMenuItem sortByTitleItemDescending() {
        JMenuItem menuItemSortByTitle = new JMenuItem("Title (Z-A, 9–0)");
        menuItemSortByTitle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (movieCollection.getAllMediaIDs().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Movie> movies = movieCollection.moviesSortedByTitleDescending();
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
    private JMenuItem sortByYearItemAscending() {
        JMenuItem menuItemSortByYear = new JMenuItem("Old to New");
        menuItemSortByYear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (movieCollection.getAllMediaIDs().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Movie> movies = movieCollection.moviesSortedByYearAscending();
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

    // EFFECTS: creates a menu item that when clicked, sort the collection by
    // release year. Shows a pop-up error if collection is empty
    private JMenuItem sortByYearItemDescending() {
        JMenuItem menuItemSortByYear = new JMenuItem("New to Old");
        menuItemSortByYear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (movieCollection.getAllMediaIDs().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Movie> movies = movieCollection.moviesSortedByYearDescending();
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

    // Search Bar:

    // EFFECTS: creates a panel with a search textfield and button side by side
    // When enter is pressed or search button is clicked, shows movies with
    // matching title
    private JPanel createSearchBar() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        textField = createSearchField();
        searchButton = createSearchButton();
        ActionListener listener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String input = textField.getText();
                if (movieCollection.getAllMediaIDs().isEmpty()) {
                    emptyCollectionPopUp();
                } else if (input.isBlank()) {
                    emptySearchStringPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Movie> movies = movieCollection.searchMovieTitle(input);
                    for (Movie movie : movies) {
                        MovieCard card = new MovieCard(frame, movie);
                        JPanel cardPanel = card.getPanel();
                        centerPanel.add(cardPanel);
                    }
                    centerPanel.revalidate();
                    centerPanel.repaint();
                }
            }

        };
        textField.addActionListener(listener);
        searchButton.addActionListener(listener);
        panel.add(textField);
        panel.add(searchButton);
        return panel;
    }

    // EFFECTS: creates a text field for searching movie titles
    private JTextField createSearchField() {
        JTextField textField = new JTextField(15);
        textField.setFont(new Font("Arial", Font.PLAIN, 13));
        return textField;
    }

    // EFFECTS: creates a search button
    private JButton createSearchButton() {
        JButton searchButton = new JButton("Search");
        searchButton.setFocusable(false);
        String text = "Search movies by title. Type and then press enter";
        searchButton.setToolTipText(text);
        return searchButton;
    }

    // Pop-ups:

    // EFFECTS: pop-up error message to inform user that collection is empty
    private void emptyCollectionPopUp() {
        JOptionPane.showMessageDialog(frame,
                "Your collection is empty. Load movies first.",
                "Empty Collection",
                JOptionPane.ERROR_MESSAGE);
    }

    private void emptySearchStringPopUp() {
        JOptionPane.showMessageDialog(frame,
                "Invalid search string!",
                "Invalid Search",
                JOptionPane.ERROR_MESSAGE);
    }

}
