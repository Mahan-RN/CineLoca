package view;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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
        window.setSize(450, 800);
        window.setLocationRelativeTo(frame);
        mgl = new MigLayout("wrap, insets 10",
                "[]");
        window.setLayout(mgl);
        window.add(createPoster(), "center");
        window.add(createTitleAndDate(), "left");
        window.add(createPlayButton(), "center, grow");
        window.add(createLengthLabel(), "left");
        window.add(createDirectorLabel(), "left");
        window.add(createActorsLabel(), "left, grow");
        window.add(craeateCountaryLabel(), "left");
        window.add(createsubLabel(), "left");
    }

    // EFFECTS: creates a JLabel containing scaled movie poster
    private JLabel createPoster() {
        String path = movie.getImagePath();
        icon = new ImageIcon(path);
        posterLabel = new JLabel(scaleImage(icon, 350, 525)); // 2:3 ratio
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
        lengthLabel = new JLabel("Run time: " + hours + " h " + minutes + " min");
        lengthLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        return lengthLabel;
    }

    // EFFECTS: creates label for movie director
    private JLabel createDirectorLabel() {
        String director = movie.getDirector();
        directorLabel = new JLabel("Directed by: " + director);
        directorLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        return directorLabel;
    }

    // EFFECTS: creates label for movie actors
    private JLabel createActorsLabel() {
        actorsLabel = new JLabel("Starring: " + movie.actorsToString());
        actorsLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        return actorsLabel;
    }

    // EFFECTS: creates label for movie countary
    private JLabel craeateCountaryLabel() {
        countaryLabel = new JLabel("Countary: " + movie.getCountary());
        countaryLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        return countaryLabel;
    }

    // EFFECTS: creates label for movie subtitle availability
    private JLabel createsubLabel() {
        String answer = "No";
        if (movie.hasEnglishSubtitle()) {
            answer = "Yes";
        }
        subtitleLabel = new JLabel("Subtitle availability: " + answer);
        subtitleLabel.setFont(new Font("Montserrat", Font.PLAIN, 14));
        return subtitleLabel;
    }

    // EFFECTS: creates a play button that when clicked will open the movie
    // file using OS default app. Throws IO Exception if the file cannot be
    // opened
    private JButton createPlayButton() {
        ImageIcon icon = new ImageIcon("CineLoca\\src\\main\\resources\\view\\buttonIcons\\playButton.png");
        playButton = new JButton("Play", icon);
        playButton.setIconTextGap(10);
        playButton.setFont(new Font("Montserrat", Font.BOLD, 12));
        playButton.setFocusable(false);
        playButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    File file = new File(movie.getFilePath());
                    Desktop.getDesktop().open(file);
                } catch (IOException exception) {
                    JOptionPane.showMessageDialog(window, "Error:\n" + e,
                            "Movie File Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        return playButton;
    }

    // EFFECTS: scales a given ImageIcon to the desired width and height
    private ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        Image image = icon.getImage(); // transform it
        // scale it the smooth way
        Image newimg = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(newimg); // transform it back
    }
}
