package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;



import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

/**
 * The SignupView displays information to the user, is responsible for the signup page UI, and observes/reacts to events that are triggered by the user.
 */
public class SignupView extends JPanel implements ActionListener, PropertyChangeListener {



    public final String viewName = "sign up";

    private final SignupViewModel signupViewModel;

    final JTextField nameInputField = new JTextField(15);

    final JTextField usernameInputField = new JTextField(15);
    final JPasswordField passwordInputField = new JPasswordField(15);
    final JPasswordField repeatPasswordInputField = new JPasswordField(15);


    private final SignupController signupController;

    final JButton signUp;
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

        Font medFont = signupViewModel.getComfortaaMedium();
        Font smallFont = signupViewModel.getComfortaaSmall();
        this.setPreferredSize(new Dimension(1200, 600)); // set window size
        this.setBackground(SignupViewModel.BACKGROUND_COLOR); //set colour

        this.signupController = controller;
        this.signupViewModel = signupViewModel;
        this.viewManagerModel = viewManagerModel;
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        try {
            BufferedImage titlePicture = ImageIO.read(new File("src/assets/signup_view/SignUpViewTitle.png"));
            JLabel picLabel = new JLabel(new ImageIcon(titlePicture));
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            add(picLabel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        signupViewModel.addPropertyChangeListener(this);

        JLabel nameLabel = new JLabel(SignupViewModel.NAME_LABEL);
        JLabel usernameLabel = new JLabel(SignupViewModel.USERNAME_LABEL);
        JLabel passwordLabel = new JLabel(SignupViewModel.PASSWORD_LABEL);
        JLabel repeatPasswordLabel = new JLabel(SignupViewModel.REPEAT_PASSWORD_LABEL);

        Border border = BorderFactory.createEmptyBorder(0, 0, 0, 10);
        nameLabel.setBorder(border);
        usernameLabel.setBorder(border);
        passwordLabel.setBorder(border);
        repeatPasswordLabel.setBorder(border);

        nameLabel.setFont(smallFont);
        usernameLabel.setFont(smallFont);
        passwordLabel.setFont(smallFont);
        repeatPasswordLabel.setFont(smallFont);

        nameInputField.setFont(smallFont);
        usernameInputField.setFont(smallFont);
        passwordInputField.setFont(smallFont);
        repeatPasswordInputField.setFont(smallFont);

        LabelTextPanel nameInfo = new LabelTextPanel(
                nameLabel, nameInputField);
        LabelTextPanel usernameInfo = new LabelTextPanel(
                usernameLabel, usernameInputField);
        LabelTextPanel passwordInfo = new LabelTextPanel(
                passwordLabel, passwordInputField);
        LabelTextPanel repeatPasswordInfo = new LabelTextPanel(
                repeatPasswordLabel, repeatPasswordInputField);
        usernameInfo.setBackground(SignupViewModel.BACKGROUND_COLOR);
        passwordInfo.setBackground(SignupViewModel.BACKGROUND_COLOR);
        nameInfo.setBackground(SignupViewModel.BACKGROUND_COLOR);
        repeatPasswordInfo.setBackground(SignupViewModel.BACKGROUND_COLOR);

//        Border border = BorderFactory.createEmptyBorder(0, 0, 0, 0);
//        usernameInfo.setBorder(border);
//        passwordInfo.setBorder(border);
//        nameInfo.setBorder(border);
//        repeatPasswordInfo.setBorder(border);



        JPanel buttons = new JPanel();
        signUp = new JButton(SignupViewModel.SIGNUP_BUTTON_LABEL);
        signUp.setBackground(SignupViewModel.BUTTON_ORANGE);
        cancel = new JButton(SignupViewModel.CANCEL_BUTTON_LABEL);
        cancel.setBackground(SignupViewModel.BUTTON_ORANGE);

        signUp.setFont(medFont);
        cancel.setFont(medFont);

        buttons.add(signUp);
        buttons.add(cancel);
        buttons.setBackground(SignupViewModel.BACKGROUND_COLOR);


        signUp.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(signUp)) {
                            SignupState currentState = signupViewModel.getState();

                            currentState.setUsername(usernameInputField.getText());
                            currentState.setPassword(passwordInputField.getText());
                            currentState.setName(nameInputField.getText());
                            currentState.setRepeatPassword(repeatPasswordInputField.getText());

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