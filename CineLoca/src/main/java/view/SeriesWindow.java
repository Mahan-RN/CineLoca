package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;

import model.Season;
import model.Series;
import net.miginfocom.swing.MigLayout;

// Represents a detailed series window
public class SeriesWindow {

    private final int WINDOW_WIDTH = 770;
    private final int WINDO_HEIGHT = 800;
    private final int POSTER_HEIGHT = 525;
    private final int POSTER_WIDTH = 350;
    private final String PLAY_BUTTON_ICON = "CineLoca\\src\\main\\resources"
            + "\\view\\buttonIcons\\episodePlayButton.png";
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
        window.add(createLeftPanel(), "grow");
        window.add(createRightPanel(), "grow, span");
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

    // MODIFIES: this
    // EFFECTS: creates a scrollable panel with season labels and buttons linked
    // to each episode
    private JScrollPane createRightPanel() {
        rightPanel = new JPanel();
        MigLayout mgl = new MigLayout("wrap, insets 10",
                "[300]");
        rightPanel.setLayout(mgl);
        scrollPane = new JScrollPane(rightPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        addSeriesMediaLinks(rightPanel);
        return scrollPane;
    }

    // MODIFIES: this
    // EFFECTS: adds a label and buttons for each season in the series
    // to the panel
    private void addSeriesMediaLinks(JPanel panel) {
        for (Season season : series.getSeaonsList()) {
            JLabel seasonLabel = new JLabel("Season "
                    + season.getSeasonNumber() + " "
                    + "(" + season.getTotalEpisodes()
                    + " episodes)");
            seasonLabel.setFont(new Font(FONT, Font.BOLD, 14));
            seasonLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
            Border blackline = BorderFactory.createLineBorder(Color.black);
            seasonLabel.setBorder(blackline);
            seasonLabel.setBackground(Color.LIGHT_GRAY);
            panel.add(seasonLabel, "grow");
            loadEpisodes(panel, season.getEpisodes());
        }
    }

    // MODIFIES: this
    // EFFECTS: adds a play button for each episode in the series to the panel
    private void loadEpisodes(JPanel panel, ArrayList<String> episodes) {
        int i = 1;
        ImageIcon icon = new ImageIcon(PLAY_BUTTON_ICON);
        for (String episode : episodes) {
            JButton episodeButton = new JButton("Episode " + i, icon);
            episodeButton.setFocusable(false);
            episodeButton.setFont(new Font(FONT, Font.PLAIN, 13));
            episodeButton.setAlignmentX(JButton.LEFT_ALIGNMENT);
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
            panel.add(episodeButton, "grow");
            i++;
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
        String prefix = "Episode length: ≈ ";
        lengthLabel = new JLabel();
        lengthLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        if (hours != 0) {
            lengthLabel.setText(prefix + hours + " h " + minutes + " min");
        } else {
            lengthLabel.setText(prefix + minutes + " min");
        }
        return lengthLabel;
    }

    // EFFECTS: creates label for movie director
    private JLabel createCreatorLabel() {
        String director = series.getCreator();
        directorLabel = new JLabel("Created by: " + director);
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
