package interface_adapter.login;

import interface_adapter.ViewModel;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * LoginViewModel is a data structure that contains all the data that the LoginView needs.
 */
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

    /**
     * Creates a new LoginViewModel. There are no parameters, as all the instance variables are predetermined.
     */
    public LoginViewModel() {
        super("log in");
    }

    public void setState(LoginState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    /**
     * the LoginPresenter calls firePropertyChanged to let the LoginViewModel know to alert the LoginView that a property has been changed
     */
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
