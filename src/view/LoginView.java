package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "log in";
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    // TODO
    public final Color LIGHT_ORANGE = new Color(255, 200, 100);

    final JTextField usernameInputField = new JTextField(15);

    private final JLabel usernameErrorField = new JLabel();

    final JPasswordField passwordInputField = new JPasswordField(15);
    private final JLabel passwordErrorField = new JLabel();

    final JButton logIn;
    final JButton cancel;
    final JButton back;
    private final LoginController loginController;

    public LoginView(LoginViewModel loginViewModel, LoginController controller, ViewManagerModel viewManagerModel) {
        Font font = new Font("Times New Roman", Font.PLAIN, 20); // change font later

        this.setPreferredSize(new Dimension(1200, 600)); // set window size
        this.setBackground(Color.ORANGE); //set colour


        this.loginController = controller;
        this.loginViewModel = loginViewModel;
        this.loginViewModel.addPropertyChangeListener(this);
        this.viewManagerModel = viewManagerModel;

        JLabel title = new JLabel("Login");
        title.setFont(font);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel j = new JLabel("Username   ");
        j.setFont(font);
        LabelTextPanel usernameInfo = new LabelTextPanel(j, usernameInputField);
        usernameInfo.setBackground(Color.ORANGE); //set colour

        JLabel k = new JLabel("Password   ");
        k.setFont(font);
        LabelTextPanel passwordInfo = new LabelTextPanel(k, passwordInputField);
        passwordInfo.setBackground(Color.ORANGE); //set colour

        JPanel buttons = new JPanel();
        buttons.setBackground(Color.ORANGE); //set colour

        logIn = new JButton(loginViewModel.LOGIN_BUTTON_LABEL);
        logIn.setFont(font);
        buttons.add(logIn);

        cancel = new JButton(loginViewModel.CANCEL_BUTTON_LABEL);
        cancel.setFont(font);
        buttons.add(cancel);

        back = new JButton(loginViewModel.BACK_BUTTON_LABEL);
        back.setFont(font);
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

        cancel.addActionListener(this);
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

        this.add(title);
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
        if (evt.getActionCommand() == cancel.getActionCommand()){
            JOptionPane.showConfirmDialog(this, "Cancel not implemented yet.");
        }
        else if (evt.getActionCommand() == back.getActionCommand()){
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
        LoginState state = (LoginState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(LoginState state) {
        usernameInputField.setText(state.getUsername());
        passwordInputField.setText(state.getPassword()); //Added
    }

}