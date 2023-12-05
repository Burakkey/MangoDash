package view;

import app.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

class LoginHomeViewTest {

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

        LoginHomeView lhv = (LoginHomeView) jp2.getComponent(3);

        JPanel buttons = (JPanel) lhv.getComponent(1);

        return (JButton) buttons.getComponent(n); // this should be the clear button
    }

    /**
     * Test that the Signup button is present and where it is expected to be
     */
    @Test
    void testSignupButtonPresent() throws ClassNotFoundException {
        Main.main(null);
        JButton button = getButton(0);
        assert(button.getText().equals("Sign Up"));
    }

    /**
     * Tests tha the Login button is present and where it is expected to be
     */
    @Test
    void testLoginButtonPresent() throws ClassNotFoundException {
        Main.main(null);
        JButton button = getButton(1);
        assert(button.getText().equals("Log In"));
    }



}