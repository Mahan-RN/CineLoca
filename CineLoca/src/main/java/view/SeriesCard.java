package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Series;
import net.miginfocom.swing.MigLayout;

// Represents an individual series card in the MainWindow
public class SeriesCard {

    private final int PANEL_WIDTH = 301;
    private final int PANEL_HEIGHT = 500;
    private final int POSTER_WIDTH = 300; // 2:3 poster W:H ratio
    private final int POSTER_HEIGHT = 450;
    private final String FONT = "Montserrat";

    private Series series;
    private JFrame frame;
    private JPanel mainPanel;
    private ImageIcon icon;
    private JButton viewButton;

    // EFFECTS: creates a series card to represents the given series
    public SeriesCard(JFrame frame, Series series) {
        this.frame = frame;
        this.series = series;
        initialize();
    }

    // getter
    public JPanel getMainPanel() {
        return mainPanel;
    }

    // MODIFIES: this
    // EFFECTS: sets up the main JPanel, containing a left panel with seires
    // poster and metadata and a right panel with a scrollable list of
    // this series episodes
    private void initialize() {
        mainPanel = new JPanel();
        MigLayout mgl = new MigLayout("wrap, insets 10",
                "[]",
                "[]5[]5[]5[]10[]");
        mainPanel.setLayout(mgl);
        mainPanel.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        mainPanel.add(createPoster(), "center");
        mainPanel.add(createTitleAndDate(), "left");
        mainPanel.add(createSeasonsNumberLabel());
        mainPanel.add(createLengthLabel(), "left");
        mainPanel.add(createViewButton(), "center, grow");
    }

    // // EFFECTS: creates a panel with series poster and metadata
    // private JPanel createLeftPanel() {
    // // leftPanel = new JPanel();
    // // MigLayout mgl = new MigLayout("wrap, insets 10",
    // // "[]",
    // // "[]5[]5[]10[]");
    // // leftPanel.setLayout(mgl);
    // // mainPanel.setSize(LEFT_PANEL_WIDTH, LEFT_PANEL_HEIGHT);
    // mainPanel.add(createPoster(), "center");
    // mainPanel.add(createTitleAndDate(), "left");
    // mainPanel.add(createLengthLabel(), "left");
    // // mainPanel.add(createViewButton(), "center, grow");
    // return leftPanel;
    // }

    // EFFECTS: creates a JLabel containing scaled series poster
    private JLabel createPoster() {
        String path = series.getImagePath();
        icon = new ImageIcon(path);
        JLabel label = new JLabel(scaleImage(icon, POSTER_WIDTH, POSTER_HEIGHT));
        return label;
    }

    // EFFECTS: creates a JPanel with "Series (releaseYear-endYear)" text
    private JLabel createTitleAndDate() {
        String title = series.getTitle();
        int releaseYear = series.getReleaseYear();
        int endYear = series.getEndYear();
        JLabel label;
        if (endYear == 0) {
            label = new JLabel(title + " (" + releaseYear + "- Present)");
        } else {
            label = new JLabel(title + " (" + releaseYear + "-" + endYear + ")");
        }
        label.setMinimumSize(new Dimension(200, 10));
        label.setFont(new Font(FONT, Font.BOLD, 16));
        return label;
    }

    // EFFECTS: returns a JLabel displaying number of seasons this series has
    private JLabel createSeasonsNumberLabel() {
        int numSeasons = series.getTotalSeasonsIMDb();
        JLabel seasonsLabel = new JLabel(numSeasons + " Seasons");
        seasonsLabel.setFont(new Font(FONT, Font.BOLD, 12));
        return seasonsLabel;
    }

    // EFFECTS: creates a JLabel to show the length of the series in hour-min
    // format
    private JLabel createLengthLabel() {
        int length = series.getLengthMinutes();
        int hours = length / 60;
        int minutes = length % 60;
        JLabel label = new JLabel(hours + " h " + minutes + " min");
        label.setFont(new Font(FONT, Font.PLAIN, 12));
        return label;
    }

    // EFFECTS: creates a button that can be clicked to open dedicated page to
    // to the movie
    private JButton createViewButton() {
        viewButton = new JButton("View");
        viewButton.setFocusable(false);
        viewButton.setToolTipText("View detailed movie page");
        viewButton.setFont(new Font(FONT, Font.BOLD, 12));
        // viewButton.addActionListener(new ActionListener() {

        // @Override
        // public void actionPerformed(ActionEvent e) {
        // new MovieWindow(frame, movie);
        // }
        // }); //TODO: add action listener after finishing SeriesInformationWindow
        return viewButton;
    }

    // EFFECTS: scales a given ImageIcon to the desired width and height
    private ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        Image image = icon.getImage(); // transform it
        // scale it the smooth way
        Image newimg = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(newimg); // transform it back
    }

}
