package view;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Media;
import model.Season;
import model.Series;
import net.miginfocom.swing.MigLayout;

// Represents a detailed series window
public class SeriesWindow extends AbstractMediaWindow {

    private final int WINDOW_WIDTH = 785;
    private final String PLAY_BUTTON_ICON = "CineLoca\\src\\main\\resources"
            + "\\view\\buttonIcons\\episodePlayButton.png";

    private Series series;
    private JDialog window;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JScrollPane scrollPane;
    private JLabel totalSeasonsLabel;
    private JLabel totalSeasonsOnDiskLabel;
    private JLabel creatorLabel;
    private JLabel lengthLabel;

    // EFFECTS: creates a SeriesWindow for the given series
    public SeriesWindow(JFrame frame, Media media) {
        super(frame, media);
        window.setVisible(true);
    }

    // EFFECTS: sets up the window
    @Override
    protected void initialize(JFrame frame) {
        this.series = (Series) media;
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
        leftPanel.add(createTotalSeasonsLabel(), "left");
        leftPanel.add(createTotalSeasonsOnDiskLabel(), "left");
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
            seasonLabel.setBackground(Color.LIGHT_GRAY);
            panel.add(seasonLabel, "grow");
            loadEpisodes(panel, season.getSortedEpisodes());
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

    // EFFECTS: creates a JLabel with "Movie (Year)" text
    @Override
    protected JLabel createTitleAndDate() {
        String title = series.getTitle();
        int releaseYear = series.getReleaseYear();
        int endYear = series.getEndYear();
        if (endYear == 0) {
            titleDateLabel = new JLabel(title + " (" + releaseYear + "- Present)");
        } else {
            titleDateLabel = new JLabel(title + " (" + releaseYear + "-" + endYear + ")");
        }
        titleDateLabel.setMinimumSize(new Dimension(200, 10));
        titleDateLabel.setFont(new Font(FONT, Font.BOLD, 16));
        return titleDateLabel;
    }

    // EFFECTS: returns a label displaying the total number of seasons in this
    // series based on IMDb metadata
    private JLabel createTotalSeasonsLabel() {
        int num = series.getTotalSeasonsIMDb();
        totalSeasonsLabel = new JLabel("Total Seasons (IMDb): " + num);
        totalSeasonsLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return totalSeasonsLabel;
    }

    // EFFECTS: returns a label displaying the total number of seasons in this
    // series based on number of seasons availabel on user's disk
    private JLabel createTotalSeasonsOnDiskLabel() {
        int num = series.getAvailableSeasons();
        totalSeasonsOnDiskLabel = new JLabel("Total Seasons (your collection): "
                + num);
        totalSeasonsOnDiskLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return totalSeasonsOnDiskLabel;
    }

    // EFFECTS: creates a JLabel to show the length of the movie in hour-min
    // format
    @Override
    protected JLabel createLengthLabel() {
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
        creatorLabel = new JLabel("Created by: " + director);
        creatorLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return creatorLabel;
    }
}
