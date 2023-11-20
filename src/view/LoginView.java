package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupState;

import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "log in";
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    final JTextField usernameInputField = new JTextField(15);

    private final JLabel usernameErrorField = new JLabel();

    final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    final JButton logIn;
    final JButton back;
    private final LoginController loginController;

    public LoginView(LoginViewModel loginViewModel, LoginController controller, ViewManagerModel viewManagerModel) {
        Font medFont = loginViewModel.getComfortaaMedium();
        final Color LIGHT_ORANGE = loginViewModel.BACKGROUND_COLOR;

        this.setPreferredSize(new Dimension(1200, 600));
        this.setBackground(LIGHT_ORANGE);

        this.loginController = controller;
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        try {
            BufferedImage titlePicture = ImageIO.read(new File("src/assets/login_view/LoginViewTitle.png"));
            JLabel picLabel = new JLabel(new ImageIcon(titlePicture));
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(picLabel);
        } catch (IOException ignored) {
        }

        JLabel j = new JLabel("Username   ");
        j.setFont(medFont);
        LabelTextPanel usernameInfo = new LabelTextPanel(j, usernameInputField);
        usernameInfo.setBackground(LIGHT_ORANGE);

        JLabel k = new JLabel("Password   ");
        k.setFont(medFont);
        LabelTextPanel passwordInfo = new LabelTextPanel(k, passwordInputField);
        passwordInfo.setBackground(LIGHT_ORANGE);

        JPanel buttons = new JPanel();
        buttons.setBackground(LIGHT_ORANGE);

        logIn = new JButton(loginViewModel.LOGIN_BUTTON_LABEL);
        logIn.setFont(medFont);
        buttons.add(logIn);

        back = new JButton(loginViewModel.BACK_BUTTON_LABEL);
        back.setFont(medFont);
        buttons.add(back);


        logIn.addActionListener(                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(logIn)) {
                            LoginState currentState = loginViewModel.getState();

                            loginController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword()
                            );
                        }
                    }
                }
        );

        back.addActionListener(this);

        usernameInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText() + e.getKeyChar());
                loginViewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
            }
        });
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        passwordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        LoginState currentState = loginViewModel.getState();
                        currentState.setPassword(passwordInputField.getText() + e.getKeyChar());
                        loginViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });

        //this.add(title);
        this.add(usernameInfo);
        this.add(usernameErrorField);
        this.add(passwordInfo);
        this.add(passwordErrorField);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        if (evt.getActionCommand() == back.getActionCommand()){
            viewManagerModel.setActiveView("Home");
            viewManagerModel.firePropertyChanged();
        }
        else if (evt.getActionCommand() == logIn.getActionCommand()){
            viewManagerModel.setActiveView("logged in");
            viewManagerModel.firePropertyChanged();
        }

        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof LoginState) {
            LoginState state = (LoginState) evt.getNewValue();
            setFields(state);
            if (state.getUsernameError() != null) {
                JOptionPane.showMessageDialog(this, state.getUsernameError());
            } else if (state.getPasswordError() != null) {
                JOptionPane.showMessageDialog(this,state.getPasswordError());
            }
        }
//        LoginState state = (LoginState) evt.getNewValue();
//        setFields(state);
//        if (evt.getNewValue() instanceof SignupState) {
//            LoginState state = (LoginState) evt.getNewValue();
//            if (state.getUsernameError() != null) {
//                JOptionPane.showMessageDialog(this, state.getUsernameError());
//            }
//        }
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword()); //Added
    }

}