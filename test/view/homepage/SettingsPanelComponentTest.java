package view.homepage;

import app.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
                        JDialog dialog = (JDialog) window;
                        String message = dialog.getTitle();
                        processDialogMessage(message);
                        dialog.dispose();
                    }
                }
            }
        };
        return new Timer(1000, close);
    }


    private void processDialogMessage(String message) {
        // Process the dialog message and set appropriate flags or variables
        if (message.contains("Change Password")) {
            popUpDiscovered = true;
        }
    }

    @Test
    void testChangePasswordButtonAction() throws ClassNotFoundException {
        // Initialize the main application
        Main.main(null);

        // Get the changePasswordButton using your method
        changePasswordButton = getButton(1);

        // Create and start the timer to close the dialog
        Timer timer = createCloseTimer();
        timer.start();

        // Simulate the button click
        SwingUtilities.invokeLater(() -> changePasswordButton.doClick());

        // Allow some time for the dialog to appear and be processed
        try {
            Thread.sleep(2000); // Wait 2 seconds for the dialog to appear
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }

        // Assertions
        assertTrue(popUpDiscovered, "Popup did not appear");
    }

    private JDialog findAndProcessDialog() {
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JDialog && window.isVisible()) {
                return (JDialog) window;
            }
        }
        return null;
    }

    @Test
    void testChangePasswordDialogCancellation() throws Exception {

        Main.main(null);
        // Simulate the button click to open the dialog
        changePasswordButton = getButton(1);
        SwingUtilities.invokeLater(() -> changePasswordButton.doClick());

        // Wait a little for the dialog to appear
        Thread.sleep(500); // Adjust this delay as needed

        // Find the open dialog
        JDialog dialog = findAndProcessDialog();
        Assertions.assertNotNull(dialog);

        // Simulate clicking the "Cancel" button
        JDialog finalDialog = dialog;
        SwingUtilities.invokeLater(() -> {
            // This simulates clicking the "Cancel" button in the dialog
            JOptionPane optionPane = (JOptionPane) finalDialog.getContentPane().getComponent(0);
            optionPane.setValue(JOptionPane.CANCEL_OPTION);
        });

        // Wait a little for the dialog to process the click
        Thread.sleep(500); // Adjust this delay as needed

        // Re-find the dialog to check if it is still visible
        dialog = findAndProcessDialog();
        assertNull(dialog, "Dialog should be closed after clicking Cancel");
    }
}
