package view.homepage;

import app.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import view.LoginHomeView;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class HomepageViewTest {

    public JPanel getTab(int n) {
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

        JTabbedPane tabbedPane = (JTabbedPane) hv.getComponent(0);

        return (JPanel) tabbedPane.getComponentAt(n); // this should be the clear button
    }

    @Test
    void testHomeTabPresent() throws ClassNotFoundException {
        Main.main(null);
        JPanel tab = getTab(0);
        assert(tab.getName().equals("Home"));
    }

    @Test
    void testExtenstionTabPresent() throws ClassNotFoundException {
        Main.main(null);
        JPanel tab = getTab(1);
        assert(tab.getName().equals("Extensions"));
    }

    @Test
    void testSettingsTabPresent() throws ClassNotFoundException {
        Main.main(null);
        JPanel tab = getTab(2);
        assert(tab.getName().equals("Settings"));
    }
}