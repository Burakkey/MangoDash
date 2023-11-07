package view;

import interface_adapter.login_home.LoginHomeViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

public class LoginHomeView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "login home";
    private final LoginHomeViewModel loginHomeViewModel;
    final JButton signupButton;
    final JButton loginButton;
    public LoginHomeView(LoginHomeViewModel loginHomeViewModel) {
        // This is the code that modifies the color
        this.setBackground(Color.ORANGE);
        setPreferredSize(new Dimension(1200,600));


        this.loginHomeViewModel = loginHomeViewModel;
        this.loginHomeViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Login Home View");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);


//        BufferedImage myPicture = ImageIO.read(new File("path-to-file"));
//        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
//        add(picLabel);

        JPanel buttons = new JPanel();
        buttons.setBackground(Color.ORANGE);
        signupButton = new JButton(loginHomeViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signupButton);
        loginButton = new JButton(loginHomeViewModel.LOGIN_BUTTON_LABEL);
        buttons.add(loginButton);
//
        signupButton.addActionListener(this);
        loginButton.addActionListener(this);
//
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//
        this.add(title);
        this.add(buttons);
    }

    /**
     //     * React to a button click that results in evt.
     //     */
    public void actionPerformed(ActionEvent evt) {
//        if (evt.getActionCommand() == signupButton.getActionCommand()):
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//        LoggedInState state = (LoggedInState) evt.getNewValue();
//        username.setText(state.getUsername());
    }

}