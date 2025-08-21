package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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
import view.util.Pagination;
import view.util.WrapLayout;

// Represents the main window of the program's UI
public class MainWindow {

    private final int FRAME_WIDTH = 800;
    private final int FRAME_HEIGHT = 500;
    private final int TOTAL_RESULTS_PER_PAGE = 30;
    private final String LOAD_BUTTON_ICON = "/view/buttonIcons/refreshButton.png";
    private final String SETTINGS_BUTTON_ICON = "/view/buttonIcons/settingsButton.png";
    private final String INFORMATION_BUTTON_ICON = "/view/buttonIcons/informationButton.png";
    private final String SORT_BY_BUTTON_ICON = "/view/buttonIcons/sortButton.png";
    private final String PREVIOUS_PAGE_BUTTON_ICON = "/view/buttonIcons/previousPageButton.png";
    private final String NEXT_PAGE_BUTTON_ICON = "/view/buttonIcons/nextPageButton.png";

    private MediaCollection movieCollection;
    private boolean movieView;
    private int pageNumber;
    private int maxPageNumber;
    private JFrame frame;
    private JPanel topPanel;
    private JPanel centerPanel;
    private JPanel bottomPanel;
    private JScrollPane scrollPane;
    private JButton settingsButton;
    private JButton loadMoviesButton;
    private JButton loadSeriesButton;
    private JButton informationButton;
    private JButton searchButton;
    private JButton firstPage;
    private JButton previousPage;
    private JButton nextPage;
    private JButton lastPage;
    private JLabel windowTitle;
    private JLabel pageCount;
    private JTextField textField;
    private MigLayout mgl;

    // EFFECTS: initializes the main window JFrame
    public MainWindow() {
        movieCollection = MediaCollection.getInstance();
        movieView = true;
        pageNumber = 1;
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
        frame = new JFrame("CineLoca");
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setLocationRelativeTo(null);
        setTopPanel();
        setCenterPanel();
        setBottomPanel();
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
        frame.add(topPanel, BorderLayout.NORTH);
        createSettingsButton();
        createInformationButton();
        createTotalMoviesCounterLabel();
        createLoadMoviesButton();
        createLoadSeriesButton();
        topPanel.add(settingsButton, "split 4, left, gapx 5");
        topPanel.add(loadMoviesButton);
        topPanel.add(loadSeriesButton);
        topPanel.add(createSortButton());
        topPanel.add(windowTitle, "center");
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

    // MODIFIES: this
    // EFFECTS: sets the bottom component of this frame for pagination
    private void setBottomPanel() {
        bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 5, 10));
        // TODO
        frame.add(bottomPanel, BorderLayout.SOUTH);
        bottomPanel.add(createFirstPageButton());
        bottomPanel.add(createPreviousPageButton());
        bottomPanel.add(createPageCountLabel());
        bottomPanel.add(createNextPageButton());
        bottomPanel.add(createLastPageButton());
    }

    // Buttons:

    // EFFECTS: creates the settings button. When clicked, opens the
    // settings window
    private void createSettingsButton() {
        settingsButton = new JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource(SETTINGS_BUTTON_ICON));
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
        ImageIcon icon = new ImageIcon(getClass().getResource(LOAD_BUTTON_ICON));
        loadMoviesButton = new JButton("Refresh movies", icon);
        loadMoviesButton.setFocusable(false);
        loadMoviesButton.setToolTipText("Refresh window");
        loadMoviesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                movieView = true;
                pageNumber = 1;
                updatePageCountLabel();
                if (movieCollection.getAllMediaIDs().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Movie> movies = movieCollection.moviesSortedByTitleAscending();
                    int totalResults = movies.size();
                    int startIndex = Pagination.startIndex(pageNumber, TOTAL_RESULTS_PER_PAGE);
                    int endIndex = Pagination.endIndex(pageNumber, TOTAL_RESULTS_PER_PAGE, totalResults);
                    List<Movie> firstBatch = movies.subList(startIndex, endIndex + 1);
                    for (Movie movie : firstBatch) {
                        MovieCard card = new MovieCard(frame, movie);
                        JPanel cardPanel = card.getPanel();
                        centerPanel.add(cardPanel);
                    }
                    System.out.println(startIndex);
                    System.out.println(endIndex);
                    windowTitle.setText("Movies");
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
        ImageIcon icon = new ImageIcon(getClass().getResource(LOAD_BUTTON_ICON));
        loadSeriesButton = new JButton("Refresh TV shows", icon);
        loadSeriesButton.setFocusable(false);
        loadSeriesButton.setToolTipText("Refresh window");
        loadSeriesButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                movieView = false;
                pageNumber = 1;
                updatePageCountLabel();
                if (movieCollection.getSeries().isEmpty()) {
                    emptyCollectionPopUp();
                } else {
                    centerPanel.removeAll();
                    ArrayList<Series> seriesList = movieCollection.seriesSortedByTitleAscending();
                    int totalResults = seriesList.size();
                    int startIndex = Pagination.startIndex(pageNumber, TOTAL_RESULTS_PER_PAGE);
                    int endIndex = Pagination.endIndex(pageNumber, TOTAL_RESULTS_PER_PAGE, totalResults);
                    List<Series> firstBatch = seriesList.subList(startIndex, endIndex + 1);
                    for (Series series : firstBatch) {
                        SeriesCard card = new SeriesCard(frame, series);
                        JPanel cardPanel = card.getMainPanel();
                        centerPanel.add(cardPanel);
                    }
                    windowTitle.setText("TV shows");
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
        ImageIcon icon = new ImageIcon(getClass().getResource(INFORMATION_BUTTON_ICON));
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
        ImageIcon icon = new ImageIcon(getClass().getResource(SORT_BY_BUTTON_ICON));
        JButton dropDownButton = DropDownButtonFactory.createDropDownButton(icon,
                popupMenu);
        dropDownButton.setToolTipText("Sort movies by:");
        return dropDownButton;
    }

    // EFFECTS: creates a button for going to the first page
    private JButton createFirstPageButton() {
        firstPage = new JButton("First");
        firstPage.setFocusable(false);
        firstPage.setFont(new Font("Arial", Font.PLAIN, 16));
        // TODO: add action listener
        return firstPage;
    }

    // EFFECTS: creates a button for going to the previous page
    // If already on the first page, it will do nothing
    private JButton createPreviousPageButton() {
        ImageIcon icon = new ImageIcon(getClass().getResource(PREVIOUS_PAGE_BUTTON_ICON));
        previousPage = new JButton("Prev", icon);
        previousPage.setIconTextGap(8);
        previousPage.setFont(new Font("Arial", Font.PLAIN, 16));
        previousPage.setFocusable(false);
        // TODO: add action listener
        return previousPage;
    }

    // EFFECTS: creates a button for going to the next page
    // If on the last page, it will do nothing
    private JButton createNextPageButton() {
        ImageIcon icon = new ImageIcon(getClass().getResource(NEXT_PAGE_BUTTON_ICON));
        nextPage = new JButton("Next", icon);
        nextPage.setHorizontalTextPosition(JButton.LEFT);
        nextPage.setIconTextGap(8);
        nextPage.setFocusable(false);
        nextPage.setFont(new Font("Arial", Font.PLAIN, 16));
        // TODO: add action listener
        return nextPage;
    }

    // EFFECTS: creates a button for going to the last page
    private JButton createLastPageButton() {
        lastPage = new JButton("Last");
        lastPage.setFont(new Font("Arial", Font.PLAIN, 16));
        lastPage.setFocusable(false);
        // TODO: add action listener
        return lastPage;
    }

    // Labels:

    // EFFECTS: creates a label that shows total number of movies in the
    // collection
    private void createTotalMoviesCounterLabel() {
        windowTitle = new JLabel("Please load media to begin",
                JLabel.CENTER);
        windowTitle.setFont(new Font("Arial", Font.PLAIN, 18));
    }

    // EFFECTS: creates a label showing the page number "Page X"
    private JLabel createPageCountLabel() {
        pageCount = new JLabel("Page " + pageNumber, JLabel.CENTER);
        pageCount.setFont(new Font("Arial", Font.PLAIN, 16));
        return pageCount; // TODO: fine tune font size
    }

    // Menu Items:

    // EFFECTS: creates a menu item that when clicked, sorts the collection
    // by title. If collection is empty, shows a pop-up error.
    private JMenuItem sortByTitleItemAscending() {
        JMenuItem menuItemSortByTitle = new JMenuItem("Title (0–9, A–Z)");
        menuItemSortByTitle.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (movieView) {
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
                } else {
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
                        centerPanel.revalidate();
                        centerPanel.repaint();
                    }
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
                if (movieView) {
                    if (movieCollection.getMovies().isEmpty()) {
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
                } else {
                    if (movieCollection.getSeries().isEmpty()) {
                        emptyCollectionPopUp();
                    } else {
                        centerPanel.removeAll();
                        ArrayList<Series> seriesList = movieCollection.seriesSortedByTitleDescending();
                        for (Series series : seriesList) {
                            SeriesCard card = new SeriesCard(frame, series);
                            JPanel cardPanel = card.getMainPanel();
                            centerPanel.add(cardPanel);
                        }
                        centerPanel.revalidate();
                        centerPanel.repaint();
                    }
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
                if (movieView) {
                    if (movieCollection.getMovies().isEmpty()) {
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
                } else {
                    if (movieCollection.getSeries().isEmpty()) {
                        emptyCollectionPopUp();
                    } else {
                        centerPanel.removeAll();
                        ArrayList<Series> seriesList = movieCollection.seriesSortedByYearAscending();
                        for (Series series : seriesList) {
                            SeriesCard card = new SeriesCard(frame, series);
                            JPanel cardPanel = card.getMainPanel();
                            centerPanel.add(cardPanel);
                        }
                        centerPanel.revalidate();
                        centerPanel.repaint();
                    }
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
                if (movieView) {
                    if (movieCollection.getMovies().isEmpty()) {
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
                } else {
                    if (movieCollection.getSeries().isEmpty()) {
                        emptyCollectionPopUp();
                    } else {
                        centerPanel.removeAll();
                        ArrayList<Series> seriesList = movieCollection.seriesSortedByYearDescending();
                        for (Series series : seriesList) {
                            SeriesCard card = new SeriesCard(frame, series);
                            JPanel cardPanel = card.getMainPanel();
                            centerPanel.add(cardPanel);
                        }
                        centerPanel.revalidate();
                        centerPanel.repaint();
                    }
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
                if (movieView) {
                    if (movieCollection.getMovies().isEmpty()) {
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
                } else {
                    if (movieCollection.getSeries().isEmpty()) {
                        emptyCollectionPopUp();
                    } else if (input.isBlank()) {
                        emptySearchStringPopUp();
                    } else {
                        centerPanel.removeAll();
                        ArrayList<Series> seriesList = movieCollection.searchSeriesTitle(input);
                        for (Series series : seriesList) {
                            SeriesCard card = new SeriesCard(frame, series);
                            JPanel cardPanel = card.getMainPanel();
                            centerPanel.add(cardPanel);
                        }
                        centerPanel.revalidate();
                        centerPanel.repaint();
                    }
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

    // Helpers for updating labels

    // EFFECTS: updates page count label to the current page number
    private void updatePageCountLabel() {
        pageCount.setText("Page " + pageNumber);
    }
}
