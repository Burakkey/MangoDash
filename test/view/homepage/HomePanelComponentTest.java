package view.homepage;

import app.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.LoginHomeView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

class HomePanelComponentTest {
    static boolean popUpDiscovered = false;
    public JButton getButton(int n) {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }

        Assertions.assertNotNull(app); // found the window?

        Component root = app.getComponent(0);

        Component cp = ((JRootPane) root).getContentPane();

        JPanel jp = (JPanel) cp;

        JPanel jp2 = (JPanel) jp.getComponent(0);

        HomepageView hv = (HomepageView) jp2.getComponent(2);

        JTabbedPane tabbedPane = (JTabbedPane) hv.getComponent(0); //the tabbed pane

        JPanel HomeTab = (JPanel) tabbedPane.getComponent(0); //the home tab

        JPanel buttons = (JPanel) HomeTab.getComponent(1);

        return (JButton) buttons.getComponent(n); // this should be the clear button
    }

    @Test
    void testInstagramButtonPresent() throws ClassNotFoundException {
        Main.main(null);
        JButton button = getButton(0);
        assert(button.getText().equals("Instagram"));
    }

    @Test
    void testFacebookButtonPresent() throws ClassNotFoundException {
        Main.main(null);
        JButton button = getButton(1);
        assert(button.getText().equals("Facebook"));
    }

    @Test
    void testInstagramButtonPopUpShown() throws ClassNotFoundException {
        Main.main(null);
        JFrame app = null;

        JButton button = getButton(0);

        createCloseTimer().start();

        button.doClick();

        assert(popUpDiscovered);
    }

    @Test
    void testFacebookButtonPopUpShown() throws ClassNotFoundException {
        Main.main(null);
        JFrame app = null;

        JButton button = getButton(1);

        createCloseTimer().start();

        button.doClick();

        assert(popUpDiscovered);
    }

    private Timer createCloseTimer() {
        ActionListener close = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Window[] windows = Window.getWindows();
                for (Window window : windows) {

                    if (window instanceof JDialog) {

                        JDialog dialog = (JDialog)window;

                        // this ignores old dialogs
                        if (dialog.isVisible()) {
//                            String s = ((JOptionPane) ((BorderLayout) dialog.getRootPane()
//                                    .getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER)).getMessage().toString();
//                            System.out.println("message = " + s);

                            // store the information we got from the JDialog
                            //HomePanelComponentTest.message = s;
                            HomePanelComponentTest.popUpDiscovered = true;

                            //System.out.println("disposing of..." + window.getClass());
                            window.dispose();
                        }
                    }
                }
            }

        };

        Timer t = new Timer(1000, close);
        t.setRepeats(false);
        return t;
    }
}