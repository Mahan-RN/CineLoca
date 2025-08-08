package view;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
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

import model.Media;
import model.Movie;
import net.miginfocom.swing.MigLayout;

// Represents a detailed movie window
public class MovieWindow extends AbstractMediaWindow {
    private final int WINDOW_WIDTH = 450;
    private final String PLAY_BUTTON_ICON = "CineLoca\\src\\main\\resources"
            + "\\view\\buttonIcons\\playButton.png";

    private Movie movie;
    private JDialog window;
    private MigLayout mgl;
    private JButton playButton;
    private JLabel directorLabel;
    private JLabel lengthLabel;

    // EFFECTS: creates a detailed movie window
    public MovieWindow(JFrame frame, Media media) {
        super(frame, media);
        window.setVisible(true);
    }

    // EFFECTS: creates a modal JDialog on top of the frame
    @Override
    protected void initialize(JFrame frame) {
        this.movie = (Movie) media;
        window = new JDialog(frame, movie.getTitle(), true);
        window.setSize(WINDOW_WIDTH, WINDO_HEIGHT);
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

    // EFFECTS: creates a JLabel with "Movie (Year)" text
    @Override
    protected JLabel createTitleAndDate() {
        String title = movie.getTitle();
        int year = movie.getReleaseYear();
        titleDateLabel = new JLabel(title + " (" + year + ")");
        titleDateLabel.setMinimumSize(new Dimension(200, 10));
        titleDateLabel.setFont(new Font(FONT, Font.BOLD, 16));
        return titleDateLabel;
    }

    // EFFECTS: creates a JLabel to show the length of the movie in hour-min
    // format
    @Override
    protected JLabel createLengthLabel() {
        int length = movie.getLengthMinutes();
        int hours = length / 60;
        int minutes = length % 60;
        lengthLabel = new JLabel("Run time: " + hours + " h " + minutes + " min");
        lengthLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return lengthLabel;
    }

    // EFFECTS: creates label for movie director
    private JLabel createDirectorLabel() {
        String director = movie.getDirector();
        directorLabel = new JLabel("Directed by: " + director);
        directorLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return directorLabel;
    }

    // EFFECTS: creates a play button that when clicked will open the movie
    // file using OS default app. Throws IO Exception if the file cannot be
    // opened
    private JButton createPlayButton() {
        ImageIcon icon = new ImageIcon(PLAY_BUTTON_ICON);
        playButton = new JButton("Play", icon);
        playButton.setIconTextGap(10);
        playButton.setFont(new Font(FONT, Font.BOLD, 12));
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
}
