package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Movie;
import net.miginfocom.swing.MigLayout;

// Represents an individual movie card in the MainWindow
public class MovieCard {
    private final int PANEL_WIDTH = 301;
    private final int PANEL_HEIGHT = 500;
    private final int POSTER_WIDTH = 300; // 2:3 poster W:H ratio
    private final int POSTER_HEIGHT = 450;
    private final String FONT = "Montserrat";

    private Movie movie;
    private JFrame frame;
    private JPanel panel;
    private ImageIcon icon;
    private JButton viewButton;
    private MigLayout mgl;

    // EFFECTS: creates a movie card to represent the given movie
    public MovieCard(JFrame frame, Movie movie) {
        this.frame = frame;
        this.movie = movie;
        initialize();
    }

    // getter
    public JPanel getPanel() {
        return panel;
    }

    // MODIFIES: this
    // EFFECTS: creates a 200x400 JPanel with vertical box layout and
    // adds to it:
    // - Movie Poster
    // - Movie Title and release date
    // - Movie length (in min)
    // - View button
    private void initialize() {
        panel = new JPanel();
        mgl = new MigLayout("wrap, insets 10",
                "[]",
                "[]5[]5[]10[]");
        panel.setLayout(mgl);
        panel.setSize(PANEL_WIDTH, PANEL_HEIGHT);
        panel.add(createPoster(), "center");
        panel.add(createTitleAndDate(), "left");
        panel.add(createLengthLabel(), "left");
        panel.add(createViewButton(), "center, grow");
    }

    // EFFECTS: creates a JLabel containing scaled movie poster
    private JLabel createPoster() {
        String path = movie.getImagePath();
        icon = new ImageIcon(path);
        JLabel label = new JLabel(scaleImage(icon, POSTER_WIDTH, POSTER_HEIGHT));
        return label;
    }

    // EFFECTS: creates a JPanel with "Movie (Year)" text
    private JLabel createTitleAndDate() {
        String title = movie.getTitle();
        int year = movie.getReleaseYear();
        JLabel label = new JLabel(title + " (" + year + ")");
        label.setMinimumSize(new Dimension(200, 10));
        label.setFont(new Font(FONT, Font.BOLD, 16));
        return label;
    }

    // EFFECTS: creates a JLabel to show the length of the movie in hour-min
    // format
    private JLabel createLengthLabel() {
        int length = movie.getLengthMinutes();
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
        viewButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                new MovieWindow(frame, movie);
            }
        });
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
