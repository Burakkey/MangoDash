package view.homepage;

import app.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.*;

class ExtensionPanelComponentsTest {
    static String facebookMessage = "";
    static String instaMessage = "";
    static boolean popUpDiscovered = false;

    public JTextField getTextfield(int x, int y) {
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
        JPanel ExtensionsTab = (JPanel) tabbedPane.getComponent(1); //the extensions tab

        JPanel p = (JPanel) ExtensionsTab.getComponent(0); //fields panel
        JTextField textField = null;
        Component[] components = p.getComponents();
        for (Component component : components) {
            GridBagConstraints gbc = ((GridBagLayout) p.getLayout()).getConstraints(component);
            if (gbc != null && gbc.gridx == x && gbc.gridy == y) {
                textField = (JTextField) component;
                break;
            }
        }
        return textField;
    }
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

        JPanel ExtensionsTab = (JPanel) tabbedPane.getComponent(1); //the extensions tab

        JPanel buttons = (JPanel) ExtensionsTab.getComponent(1);

        return (JButton) buttons.getComponent(n); // this should be the clear button
    }

    @Test
    void testSaveChangesButtonPresent() throws ClassNotFoundException {
        Main.main(null);
        JButton button = getButton(0);
        assert(button.getText().equals("Save Changes"));
    }

    // fails because createCloseTimer is not able to detect the second JDialog that pops up
    @Test
    void testSaveChangesInvalidFacebookInstagramKeys() throws ClassNotFoundException {
        Main.main(null);
        JButton button = getButton(0);
        JTextField instagramText = getTextfield(1,4);
        JTextField facebookText = getTextfield(1, 3);

        instagramText.setText("a"); //invalid Instagram API key
        facebookText.setText("a"); //invalid Facebook API key

        createCloseTimer().start();

        button.doClick();

        assert(popUpDiscovered);
        assert(instaMessage.equals("Invalid Instagram API key"));
        //assert(facebookMessage.equals("Invalid Facebook API key"));

    }

    // fails because createCloseTimer is not able to detect the second JDialog that pops up
    @Test
    void testSaveChangesValidFacebookInstagramKeys() throws ClassNotFoundException {
        Main.main(null);
        JButton button = getButton(0);
        JTextField instagramText = getTextfield(1,4);
        JTextField facebookText = getTextfield(1, 3);

        instagramText.setText("EAAMw2YKsBFwBO4pt7KgLmZBeEzVHOYB90bh1HM9xJN1QZAo0qWZBEn8M0A1q5tdXXOjNDE3a8SbZAdxkCqXp4XoJb1QwCKDbXkn0S4jbGNV4UpRa4pqHLhNc1nbBmewt9Ri0l1EmKU29JtY6D3nZCiHeLcvpZB48DR9LpMSVrQvQUMJmtxVLgr8nlc"); //valid Instagram API key
        facebookText.setText("a"); //invalid Facebook API key

        createCloseTimer().start();

        button.doClick();

        assert(popUpDiscovered);
        assert(instaMessage.equals("Successfully Validated Instagram API key"));
        //assert(facebookMessage.equals("Invalid Instagram API key"));
    }

    private Timer createCloseTimer() {
        ActionListener close = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    System.out.println(window.getName()); //we see that
                    if (window instanceof JDialog) {


                        JDialog dialog = (JDialog)window;

                        // this ignores old dialogs
                        if (dialog.isVisible()) {
                            String s = ((JOptionPane) ((BorderLayout) dialog.getRootPane()
                                    .getContentPane().getLayout()).getLayoutComponent(BorderLayout.CENTER)).getMessage().toString();

                            // store the information we got from the JDialog
                            ExtensionPanelComponentsTest.instaMessage = s;
                            ExtensionPanelComponentsTest.popUpDiscovered = true;
                            System.out.println(s);

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