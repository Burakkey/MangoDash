package interface_adapter.login_home;

import interface_adapter.ViewModel;
import interface_adapter.login.LoginState;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoginHomeViewModel extends ViewModel {
    public static final String LOGIN_BUTTON_LABEL = "Log In";
    public static final String SIGNUP_BUTTON_LABEL = "Sign Up";
    public static final Color LIGHT_ORANGE = new Color(255,215,181);
    public static final Color BUTTON_ORANGE = new Color(255,179,138);
    private LoginHomeState state = new LoginHomeState();
    public LoginHomeViewModel() {
        super("login home menu");
    }

    public void setState(LoginHomeState state) {
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

    public LoginHomeState getState() {
        return state;
    }

}
