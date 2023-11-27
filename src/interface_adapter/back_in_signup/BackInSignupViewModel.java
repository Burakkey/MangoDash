package interface_adapter.back_in_signup;

import interface_adapter.ViewModel;

import javax.swing.text.View;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class BackInSignupViewModel extends ViewModel {
    public final String TITLE_LABEL = "Log In";
    private BackInSignupState state = new BackInSignupState();
    public BackInSignupViewModel(){
        super("Log In");
    }
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
