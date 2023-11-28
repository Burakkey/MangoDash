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

/**
 * The SignupView displays information to the user, is responsible for the signup page UI, and observes/reacts to events that are triggered by the user.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {



    public final String viewName = "sign up";

    private final SignupViewModel signupViewModel;

    private final JTextField nameInputField = new JTextField(15);

    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);


    private final SignupController signupController;

    private final JButton signUp;
    private final JButton cancel;

    private final ViewManagerModel viewManagerModel;


    /**
     * Constructs a new SignupView.
     * @param controller the signup controller, receives the info that the user inputs
     * @param signupViewModel data structure that contains the info for the view to display
     * @param viewManagerModel contains all the different views, changes the active view when needed
     *                         (in response to user)
     */
    public SignupView(SignupController controller, SignupViewModel signupViewModel, ViewManagerModel viewManagerModel) {

        final Color LIGHT_ORANGE = signupViewModel.BACKGROUND_COLOR;

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

        LabelTextPanel nameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.NAME_LABEL), nameInputField);
        LabelTextPanel usernameInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.USERNAME_LABEL), usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.PASSWORD_LABEL), passwordInputField);
        LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL), repeatPasswordInputField);
        usernameInfo.setBackground(LIGHT_ORANGE);
        passwordInfo.setBackground(LIGHT_ORANGE);
        nameInfo.setBackground(LIGHT_ORANGE);
        repeatPasswordInfo.setBackground(LIGHT_ORANGE);

        JPanel buttons = new JPanel();
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        signUp.setBackground(SignupViewModel.BUTTON_ORANGE);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        cancel.setBackground(SignupViewModel.BUTTON_ORANGE);

        signUp.setFont(medFont);
        cancel.setFont(medFont);

        buttons.add(signUp);
        buttons.add(cancel);
        buttons.setBackground(LIGHT_ORANGE);


        signUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            SignupState currentState = signupViewModel.getState();

                            signupController.execute(
                                    currentState.getName(),
                                    currentState.getUsername(),
                                    currentState.getPassword(),
                                    currentState.getRepeatPassword()
                            );
                        }
                    }
                }
        );

        cancel.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(cancel)) {
                            viewManagerModel.setActiveView("Home");
                            viewManagerModel.firePropertyChanged();
                        }
                    }
                }
        );

        // This makes a new KeyListener implementing class, instantiates it, and
        // makes it listen to keystrokes in the usernameInputField.
        //
        // Notice how it has access to instance variables in the enclosing class!
        nameInputField.addKeyListener(
                new KeyListener() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        SignupState currentState = signupViewModel.getState();
                        String text = nameInputField.getText() + e.getKeyChar();
                        currentState.setName(text);
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


//        this.add(title);
        this.add(nameInfo);
        this.add(usernameInfo);
        this.add(passwordInfo);
        this.add(repeatPasswordInfo);
        this.add(buttons);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out. println("Click " + evt.getActionCommand());
    }

    /**
     * If the evt property's new value is related to the SignUpState, then an error panel will pop up if there is an error with the values the user has inputted into the text fields.
     * @param evt A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getNewValue() instanceof SignupState) {
            SignupState state = (SignupState) evt.getNewValue();
            if (state.getError() != null) {
                JOptionPane.showMessageDialog(this, state.getError());
            }
        }
    }
}