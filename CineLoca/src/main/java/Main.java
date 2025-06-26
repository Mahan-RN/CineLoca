import javax.swing.SwingUtilities;
import com.formdev.flatlaf.FlatDarkLaf;

import ui.MainWindow;

public class Main {
    public static void main(String[] args) throws Exception {
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainWindow main = new MainWindow();
                main.show();
            }
        });
    }
}
