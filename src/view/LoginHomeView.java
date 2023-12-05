package view;

import interface_adapter.ViewManagerModel;
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
import java.io.IOException;

public class LoginHomeView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "Home";
    private final LoginHomeViewModel loginHomeViewModel;
    final JButton signupButton;
    final JButton loginButton;
    private final ViewManagerModel viewManagerModel;
    public LoginHomeView(LoginHomeViewModel loginHomeViewModel, ViewManagerModel viewManagerModel) {
        // This is the code that modifies the color
        this.setBackground(LoginHomeViewModel.LIGHT_ORANGE);
        setPreferredSize(new Dimension(1200,600));


        this.loginHomeViewModel = loginHomeViewModel;
        this.loginHomeViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

//        JLabel title = new JLabel("Login Home View");
//        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        try {
            BufferedImage titlePicture =
                    ImageIO.read(new File("src/assets/login_home_view/MangoDashLoginHomeTitle.png"));
            JLabel picLabel = new JLabel(new ImageIcon(titlePicture));
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(picLabel);
        } catch (IOException ex) {
            System.out.println("Image not found!");
        }


        JPanel buttons = new JPanel();
        buttons.setBackground(LoginHomeViewModel.LIGHT_ORANGE);
        signupButton = new JButton(loginHomeViewModel.SIGNUP_BUTTON_LABEL);
        signupButton.setPreferredSize(new Dimension(200, 50));
        signupButton.setFont(loginHomeViewModel.getComfortaaMedium());
        signupButton.setBackground(LoginHomeViewModel.BUTTON_ORANGE);
        buttons.add(signupButton);
        loginButton = new JButton(loginHomeViewModel.LOGIN_BUTTON_LABEL);
        loginButton.setPreferredSize(new Dimension(200, 50));
        loginButton.setFont(loginHomeViewModel.getComfortaaMedium());
        loginButton.setBackground(LoginHomeViewModel.BUTTON_ORANGE);

        buttons.add(loginButton);
//
        signupButton.addActionListener(this);
        loginButton.addActionListener(this);
//
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//
//        this.add(title);
        this.add(buttons);
    }

    /**
     //     * React to a button click that results in evt.
     //     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand() == signupButton.getActionCommand()){
            viewManagerModel.setActiveView("sign up");
            viewManagerModel.firePropertyChanged();
        }
        else if (evt.getActionCommand().equals(loginButton.getActionCommand())){
            viewManagerModel.setActiveView("log in");
            viewManagerModel.firePropertyChanged();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

}