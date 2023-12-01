package interface_adapter.signup;

import interface_adapter.ViewModel;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SignupViewModel extends ViewModel {
    public static final Color BACKGROUND_COLOR = new Color(255,215,181);
    public static final Color BUTTON_ORANGE = new Color(255,179,138);
    public static final String TITLE_LABEL = "Sign Up View";
    public static final String USERNAME_LABEL = "Choose username:";
    public static final String PASSWORD_LABEL = "Choose password:";
    public static final String REPEAT_PASSWORD_LABEL = "Re-enter password:";

    public static final String NAME_LABEL = "Name:";

    public static final String SIGNUP_BUTTON_LABEL = " Sign up ";
    public static final String CANCEL_BUTTON_LABEL = " Cancel ";
    public static final String BACK_BUTTON_LABEL = " Back ";


    private SignupState state = new SignupState();

    public SignupViewModel() {
        super("sign up");
    }

    public void setState(SignupState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public SignupState getState() {
        return state;
    }
}
