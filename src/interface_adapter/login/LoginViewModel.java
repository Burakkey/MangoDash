package interface_adapter.login;

import interface_adapter.ViewModel;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginViewModel extends ViewModel {

    public final String TITLE_LABEL = "Log In View";
    public final String USERNAME_LABEL = "Enter Username:";
    public final String PASSWORD_LABEL = "Enter Password:";
    public static final Color BACKGROUND_COLOR = new Color(255,215,181);
    public static final Color BUTTON_ORANGE = new Color(255,179,138);
    public static final String LOGIN_BUTTON_LABEL = "Log in";
    public static final String CANCEL_BUTTON_LABEL = "Cancel";
    public static final String BACK_BUTTON_LABEL = "Back";

    private LoginState state = new LoginState();

    public LoginViewModel() {
        super("log in");
    }

    public void setState(LoginState state) {
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

    public LoginState getState() {
        return state;
    }
}
