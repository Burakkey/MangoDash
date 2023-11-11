package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.back_in_signup.BackInSignupController;
import interface_adapter.back_in_signup.BackInSignupState;
import interface_adapter.back_in_signup.BackInSignupViewModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;



import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {

    public final Color LIGHT_ORANGE = new Color(255, 200, 100);

    public final String viewName = "sign up";

    private final SignupViewModel signupViewModel;
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);
    private final SignupController signupController;

    private final JButton signUp;
    private final JButton cancel;


    private final JButton back;
    private final ViewManagerModel viewManagerModel;



    public SignupView(SignupController controller, SignupViewModel signupViewModel, ViewManagerModel viewManagerModel) {

        Font medFont = signupViewModel.getComfortaaMedium();
        this.setPreferredSize(new Dimension(1200, 600)); // set window size
        this.setBackground(LIGHT_ORANGE); //set colour

        this.signupController = controller;
        this.signupViewModel = signupViewModel;
        this.viewManagerModel = viewManagerModel;

        try {
            BufferedImage titlePicture = ImageIO.read(new File("src/assets/signup_view/SignUpViewTitle.png"));
            JLabel picLabel = new JLabel(new ImageIcon(titlePicture));
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(picLabel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        signupViewModel.addPropertyChangeListener(this);

        JLabel usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        JLabel repeatPasswordLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);

        usernameLabel.setFont(medFont);
        passwordLabel.setFont(medFont);
        repeatPasswordLabel.setFont(medFont);

        LabelTextPanel usernameInfo = new LabelTextPanel(usernameLabel, usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(passwordLabel, passwordInputField);
        LabelTextPanel repeatPasswordInfo = new LabelTextPanel(repeatPasswordLabel, repeatPasswordInputField);

        usernameInfo.setBackground(LIGHT_ORANGE);
        passwordInfo.setBackground(LIGHT_ORANGE);
        repeatPasswordInfo.setBackground(LIGHT_ORANGE);

        JPanel buttons = new JPanel();
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        back = new JButton((SignupViewModel.BACK_BUTTON_LABEL));

        signUp.setFont(medFont);
        cancel.setFont(medFont);
        back.setFont(medFont);

        buttons.add(signUp);
        buttons.add(cancel);
        buttons.add(back);
        buttons.setBackground(LIGHT_ORANGE);


        signUp.addActionListener(
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

        cancel.addActionListener(this);
        back.addActionListener(this);

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
        }
    }
}