package view;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.Image;

import model.Movie;

// Represents an individual movie card in the MainWindow
public class MovieCard {
    private Movie movie;
    private JPanel panel;
    private ImageIcon icon;

    // EFFECTS: creates a movie card to represent the given movie
    public MovieCard(Movie movie) {
        this.movie = movie;
        initialize();
    }

    // MODIFIES: this
    // EFFECTS: creates a 200x400 JPanel with vertical box layout and adds to it:
    // - Movie Poster
    // - Movie Title and release date
    // - Movie length (in min)
    // - View button
    private void initialize() {
        panel = new JPanel(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setSize(200, 400);
        panel.add(createPoster());
    }

    // EFFECTS: creates a JLabel containing scaled movie poster
    private JLabel createPoster() {
        String path = movie.getImagePath();
        icon = new ImageIcon(path);
        JLabel label = new JLabel(scaleImage(icon, 100, 200));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    // EFFECTS: scales a given ImageIcon to the desired width and height
    // while maintaining the aspect ratio
    // Based on User "Unmitigated" response from
    // https://stackoverflow.com/questions/6714045/how-to-resize-jlabel-imageicon
    private ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        int nw = icon.getIconWidth();
        int nh = icon.getIconHeight();

        if (icon.getIconWidth() > w) {
            nw = w;
            nh = (nw * icon.getIconHeight()) / icon.getIconWidth();
        }

        if (nh > h) {
            nh = h;
            nw = (icon.getIconWidth() * nh) / icon.getIconHeight();
        }

        return new ImageIcon(icon.getImage().getScaledInstance(nw, nh, Image.SCALE_SMOOTH));
    }
}
