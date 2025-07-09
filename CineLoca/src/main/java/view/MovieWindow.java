package view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Movie;
import net.miginfocom.swing.MigLayout;

// Represents the detailed movie window
public class MovieWindow {
    private Movie movie;
    private JDialog window;
    private MigLayout mgl;
    private JButton playButton;
    private JLabel posterLabel;
    private JLabel directorLabel;
    private JLabel actorsLabel;
    private JLabel lengthLabel;
    private JLabel countaryLabel;
    private JLabel subtitleLabel;
    private ImageIcon icon;

    // EFFECTS: creates a detailed movie window
    public MovieWindow(JFrame frame, Movie movie) {
        this.movie = movie;
        initialize(frame);
        window.setVisible(true);
    }

    // EFFECTS: creates a modal JDialog on top of the frame
    private void initialize(JFrame frame) {
        window = new JDialog(frame, movie.getTitle(), true);
        window.setSize(600, 900);
        window.setLocationRelativeTo(frame);
        mgl = new MigLayout("wrap, insets 10", "[]");
        window.setLayout(mgl);

    }

    // EFFECTS: creates a JLabel containing scaled movie poster
    private JLabel createPoster() {
        String path = movie.getImagePath();
        icon = new ImageIcon(path);
        posterLabel = new JLabel(scaleImage(icon, 300, 450)); // 2:3 ratio
        return posterLabel;
    }

    // EFFECTS: creates a JPanel with "Movie (Year)" text
    private JLabel createTitleAndDate() {
        String title = movie.getTitle();
        int year = movie.getReleaseYear();
        JLabel label = new JLabel(title + " (" + year + ")");
        label.setMinimumSize(new Dimension(200, 10));
        label.setFont(new Font("Montserrat", Font.BOLD, 16));
        return label;
    }

    // EFFECTS: creates a JLabel to show the length of the movie in hour-min
    // format
    private JLabel createLengthLabel() {
        int length = movie.getLengthMinutes();
        int hours = length / 60;
        int minutes = length % 60;
        lengthLabel = new JLabel(hours + " h " + minutes + " min");
        lengthLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
        return lengthLabel;
    }

    private JLabel createDirectorLabel() {
        String director = movie.getDirector();
        directorLabel = new JLabel("Directed by: " + director);
        directorLabel.setFont(new Font("Montserrat", Font.PLAIN, 12));
        return directorLabel;
    }

    private JLabel createActorsLabel() {
        String text = "";
        if (movie.getActors().isEmpty()) {
            text = "";
        } else {
            for (String actor : movie.getActors()) {
                text = text + actor + ", ";
            }
        }
        actorsLabel = new JLabel(text);
        return actorsLabel;

    }

    // EFFECTS: scales a given ImageIcon to the desired width and height
    private ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        Image image = icon.getImage(); // transform it
        // scale it the smooth way
        Image newimg = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(newimg); // transform it back
    }
}
