package view.homepage;

import app.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SettingsPanelComponentTest {
    private SettingsPanelComponent settingsPanelComponent;
    private JButton changePasswordButton;

    private boolean popUpDiscovered = false;

    @BeforeEach
    void setUp() {
        // Initialize your SettingsPanelComponent and other necessary components here
        settingsPanelComponent = new SettingsPanelComponent();
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

        JPanel settingsTab = (JPanel) tabbedPane.getComponent(2); //the settings tab

        JPanel buttons = (JPanel) settingsTab.getComponent(1);

        return (JButton) buttons.getComponent(n);
    }

    private Timer createCloseTimer() {
        ActionListener close = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Window[] windows = Window.getWindows();
                for (Window window : windows) {
                    if (window instanceof JDialog && window.isVisible()) {
                        JDialog dialog = (JDialog)window;
                        String message = extractMessageFromDialog(dialog);
                        processDialogMessage(message);
                        dialog.dispose();
                    }
                }
            }
        };

        return new Timer(1000, close);
    }

    private String extractMessageFromDialog(JDialog dialog) {
        // Extract and return the message from the dialog
        JOptionPane optionPane = (JOptionPane) dialog.getContentPane().getComponent(0);
        return (String) optionPane.getMessage();
    }

    private void processDialogMessage(String message) {
        // Process the dialog message and set appropriate flags or variables
        if (message.contains("Change Password")) {
            popUpDiscovered = true;
        }
    }

    @Test
    void testChangePasswordButtonAction() throws ClassNotFoundException {
        // Get the changePasswordButton using your method
        Main.main(null);
        changePasswordButton = getButton(2);

        createCloseTimer().start();

        // Simulate the button click
        changePasswordButton.doClick();

        // Assertions
        assertTrue(popUpDiscovered, "Popup did appear");
        // Add more specific assertions about the popup message if needed
        // For example: assertEquals("Expected message", popupMessage);
    }
}
