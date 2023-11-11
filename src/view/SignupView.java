package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.back_in_signup.BackInSignupController;
import interface_adapter.back_in_signup.BackInSignupState;
import interface_adapter.back_in_signup.BackInSignupViewModel;
import interface_adapter.clear_users.ClearState;
import interface_adapter.clear_users.ClearViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;


import interface_adapter.clear_users.ClearController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final ClearViewModel clearViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private final SignupController signupController;
    private final ClearController clearController;

    private final JButton signUp;
    private final JButton cancel;

    private final JButton clear;
    // TODO
    private final JButton back;
    private final ViewManagerModel viewManagerModel;



    public SignupView(SignupController controller, SignupViewModel signupViewModel, ClearController controller2, ClearViewModel clearViewModel, ViewManagerModel viewManagerModel) {

        this.signupController = controller;
        this.signupViewModel = signupViewModel;
        this.clearController = controller2;
        this.clearViewModel = clearViewModel;
        this.viewManagerModel = viewManagerModel;

        signupViewModel.addPropertyChangeListener(this);
        clearViewModel.addPropertyChangeListener(this);


        JLabel title = new JLabel(SignupViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);
        LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);

        JPanel buttons = new JPanel();
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        buttons.add(signUp);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        buttons.add(cancel);
        clear = new JButton(SignupViewModel.CLEAR_BUTTON_LABEL);
        buttons.add(clear);

        // TODO
        back = new JButton((SignupViewModel.BACK_BUTTON_LABEL));
        buttons.add(back);


        signUp.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            SignupState currentState = signupViewModel.getState();

                            signupController.execute(
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );

        // TODO Add the body to the actionPerformed method of the action listener below
        //      for the "clear" button. You'll need to write the controller before
        //      you can complete this.
        clear.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(clear)) {
                            clearController.execute();
                        }
                    }
                }
        );


        cancel.addActionListener(this);
        back.addActionListener(this);

        // This makes a new KeyListener implementing class, instantiates it, and
        // makes it listen to keystrokes in the usernameInputField.
        //
        // Notice how it has access to instance variables in the enclosing class!
        usernameInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        String text = usernameInputField.getText() + e.getKeyChar();
                        currentState.setUsername(text);
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {
                    }

                    @Override
                    public void keyReleased(KeyEvent e) {
                    }
                });

        passwordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        currentState.setPassword(passwordInputField.getText() + e.getKeyChar());
                        signupViewModel.setState(currentState);
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        repeatPasswordInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        currentState.setRepeatPassword(repeatPasswordInputField.getText() + e.getKeyChar());
                        signupViewModel.setState(currentState); // Hmm, is this necessary?
                    }

                    @Override
                    public void keyPressed(KeyEvent e) {

                    }

                    @Override
                    public void keyReleased(KeyEvent e) {

                    }
                }
        );

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
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
        System.out. println("Click " + evt.getActionCommand());
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof SignupState) {
            SignupState state = (SignupState) evt.getNewValue();
            if (state.getUsernameError() != null) {
                JOptionPane.showMessageDialog(this, state.getUsernameError());
            }
        } else if (evt.getNewValue() instanceof ClearState) {
            ClearState state = (ClearState) evt.getNewValue();
            JOptionPane.showConfirmDialog(this, String.join("\n", state.getUsernames()));
        }
    }
}