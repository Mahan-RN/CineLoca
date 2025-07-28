package view;

import java.awt.Font;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import model.MediaCollection;
import net.miginfocom.swing.MigLayout;

// Represents the information window
public class InformationWindow {
    private final String FONT = "Montserrat";
    
    private JDialog window;
    private MigLayout mgl;
    private JLabel duplicatesLabel;
    private JLabel creditsLabel;
    private MediaCollection collection;

    // EFFECTS: initializes information window
    public InformationWindow(JFrame frame) {
        this.collection = MediaCollection.getInstance();
        initialize(frame);
        window.setVisible(true);
    }

    // EFFECTS: setsup information window and its components
    private void initialize(JFrame frame) {
        window = new JDialog(frame, "Information", true);
        mgl = new MigLayout("wrap, insets 10",
                "[]",
                "[]5[]");
        window.setLayout(mgl);
        window.setSize(800, 300);
        window.setLocationRelativeTo(frame);
        window.add(createDuplicatesLabel(), "left");
        window.add(createCreditsLabel(), "left, span");
    }

    // EFFECTS: creates a label for duplicate movie IDs detected when loading
    // movies from the CSV file
    private JLabel createDuplicatesLabel() {
        duplicatesLabel = new JLabel("Duplicate IDs: "
                + collection.getDuplicateIDs().toString());
        duplicatesLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return duplicatesLabel;
    }

    // EFFECTS: creates a label for software credits and link to Github
    private JLabel createCreditsLabel() {
        String str = "<html>CineLoca is developed and maintained by MahanRN.<br>"
                + "For more information visit https://github.com/Mahan-RN/CineLoca</html>";
        creditsLabel = new JLabel(str);
        creditsLabel.setFont(new Font(FONT, Font.PLAIN, 14));
        return creditsLabel;
    }
}
