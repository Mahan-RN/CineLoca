package view;

import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.Media;

public abstract class AbstractMediaWindow {

    protected final int WINDO_HEIGHT = 820;
    protected final int POSTER_HEIGHT = 525;
    protected final int POSTER_WIDTH = 350;
    protected final String FONT = "Montserrat";
    protected Media media;
    protected JLabel posterLabel;
    protected JLabel titleDateLabel;
    protected JLabel actorsLabel;
    protected JLabel countaryLabel;
    protected JLabel subtitleLabel;
    protected ImageIcon icon;

    // EFFECTS: creates a window for the given media. The provided frame is the
    // parent frame
    public AbstractMediaWindow(JFrame frame, Media media) {
        this.media = media;
        initialize(frame);
    }

    // MODIFIES: this
    // EFFECTS: sets up this window
    protected abstract void initialize(JFrame frame);

    // EFFECTS: creates a label with media title and date
    protected abstract JLabel createTitleAndDate();

    // EFFECTS: creates a label to display the length of the media
    protected abstract JLabel createLengthLabel();

    // EFFECTS: creates a JLabel containing scaled media poster
    protected JLabel createPoster() {
        String path = media.getImagePath();
        icon = new ImageIcon(path);
        posterLabel = new JLabel(scaleImage(icon, POSTER_WIDTH, POSTER_HEIGHT)); // 2:3 ratio
        return posterLabel;
    }

    // EFFECTS: creates label for media actors
    protected JLabel createActorsLabel() {
        actorsLabel = new JLabel("Starring: " + media.actorsToString());
        actorsLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return actorsLabel;
    }

    // EFFECTS: creates label for media countary
    protected JLabel craeateCountaryLabel() {
        countaryLabel = new JLabel("Countary: " + media.getCountary());
        countaryLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return countaryLabel;
    }

    // EFFECTS: creates label for media subtitle availability
    protected JLabel createsubLabel() {
        String answer = "No";
        if (media.hasEnglishSubtitle()) {
            answer = "Yes";
        }
        subtitleLabel = new JLabel("Subtitle availability: " + answer);
        subtitleLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return subtitleLabel;
    }

    // EFFECTS: scales a given ImageIcon to the desired width and height
    protected ImageIcon scaleImage(ImageIcon icon, int w, int h) {
        Image image = icon.getImage(); // transform it
        // scale it the smooth way
        Image newimg = image.getScaledInstance(w, h, Image.SCALE_SMOOTH);
        return new ImageIcon(newimg); // transform it back
    }

}
