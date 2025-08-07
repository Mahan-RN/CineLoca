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
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Season;
import model.Series;
import net.miginfocom.swing.MigLayout;

// Represents a detailed series window
public class SeriesWindow {

    private final int WINDOW_WIDTH = 450;
    private final int WINDO_HEIGHT = 800;
    private final int POSTER_HEIGHT = 525;
    private final int POSTER_WIDTH = 350;
    private final String PLAY_BUTTON_ICON = "CineLoca\\src\\main\\resources"
            + "\\view\\buttonIcons\\playButton.png";
    private final String FONT = "Montserrat";

    private Series series;
    private JDialog window;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JScrollPane scrollPane;
    private JLabel posterLabel;
    private JLabel directorLabel;
    private JLabel actorsLabel;
    private JLabel lengthLabel;
    private JLabel countaryLabel;
    private JLabel subtitleLabel;
    private ImageIcon icon;

    // EFFECTS: creates a SeriesWindow for the given series
    public SeriesWindow(JFrame frame, Series series) {
        this.series = series;
        initialize(frame);
        window.setVisible(true);
    }

    // EFFECTS: sets up the window
    private void initialize(JFrame frame) {
        window = new JDialog(frame, series.getTitle(), true);
        window.setSize(WINDOW_WIDTH, WINDO_HEIGHT);
        window.setLocationRelativeTo(frame);
        MigLayout mgl = new MigLayout("wrap, insets 10",
                "[]10[]");
        window.setLayout(mgl);
        window.add(createLeftPanel());
        window.add(createRightPanel());
    }

    private JPanel createLeftPanel() {
        leftPanel = new JPanel();
        MigLayout mgl = new MigLayout("wrap, insets 10",
                "[]");
        leftPanel.setLayout(mgl);
        leftPanel.add(createPoster(), "center");
        leftPanel.add(createTitleAndDate(), "left");
        // leftPanel.add(createPlayButton(), "center, grow");
        leftPanel.add(createLengthLabel(), "left");
        leftPanel.add(createCreatorLabel(), "left");
        leftPanel.add(createActorsLabel(), "left, grow");
        leftPanel.add(craeateCountaryLabel(), "left");
        leftPanel.add(createsubLabel(), "left");
        return leftPanel;
    }

    private JScrollPane createRightPanel() {
        rightPanel = new JPanel();
        MigLayout mgl = new MigLayout("wrap, insets 10", "[]");
        rightPanel.setLayout(mgl);
        scrollPane = new JScrollPane(rightPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        addSeriesMediaLinks(rightPanel);
        return scrollPane;
    }

    private void addSeriesMediaLinks(JPanel panel) {
        for (Season season : series.getSeaonsList()) {
            JLabel seasonLabel = new JLabel("Season "
                    + season.getSeasonNumber() + " " + "(" + season.getTotalEpisodes()
                    + " episodes)");
            panel.add(seasonLabel);
            int i = 1;
            for (String episode : season.getEpisodes()) {
                JButton episodeButton = new JButton("Episode " + i);
                episodeButton.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            File file = new File(episode);
                            Desktop.getDesktop().open(file);
                        } catch (IOException exception) {
                            JOptionPane.showMessageDialog(window, "Error:\n" + e,
                                    "TV Show Episode File Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });
                panel.add(episodeButton);
                i++;
            }
        }
    }

    // EFFECTS: creates a JLabel containing scaled movie poster
    private JLabel createPoster() {
        String path = series.getImagePath();
        icon = new ImageIcon(path);
        posterLabel = new JLabel(scaleImage(icon, POSTER_WIDTH, POSTER_HEIGHT)); // 2:3 ratio
        return posterLabel;
    }

    // EFFECTS: creates a JLabel with "Movie (Year)" text
    private JLabel createTitleAndDate() {
        String title = series.getTitle();
        int year = series.getReleaseYear();
        JLabel label = new JLabel(title + " (" + year + ")");
        label.setMinimumSize(new Dimension(200, 10));
        label.setFont(new Font(FONT, Font.BOLD, 16));
        return label;
    }

    // EFFECTS: creates a JLabel to show the length of the movie in hour-min
    // format
    private JLabel createLengthLabel() {
        int length = series.getLengthMinutes();
        int hours = length / 60;
        int minutes = length % 60;
        lengthLabel = new JLabel("Run time: " + hours + " h " + minutes + " min");
        lengthLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return lengthLabel;
    }

    // EFFECTS: creates label for movie director
    private JLabel createCreatorLabel() {
        String director = series.getCreator();
        directorLabel = new JLabel("Directed by: " + director);
        directorLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return directorLabel;
    }

    // EFFECTS: creates label for movie actors
    private JLabel createActorsLabel() {
        actorsLabel = new JLabel("Starring: " + series.actorsToString());
        actorsLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return actorsLabel;
    }

    // EFFECTS: creates label for movie countary
    private JLabel craeateCountaryLabel() {
        countaryLabel = new JLabel("Countary: " + series.getCountary());
        countaryLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return countaryLabel;
    }

    // EFFECTS: creates label for movie subtitle availability
    private JLabel createsubLabel() {
        String answer = "No";
        if (series.hasEnglishSubtitle()) {
            answer = "Yes";
        }
        subtitleLabel = new JLabel("Subtitle availability: " + answer);
        subtitleLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return subtitleLabel;
    }

    // EFFECTS: scales a given ImageIcon to the desired width and height
    private ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        Image image = icon.getImage(); // transform it
        // scale it the smooth way
        Image newimg = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(newimg); // transform it back
    }
}
