package interface_adapter.clear_users;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

// TODO Complete me

public class ClearViewModel extends ViewModel{
    public final String TITLE_LABEL = "Clear View";

    public static final String CLEAR_BUTTON_LABEL = "Delete all users";

    private ClearState state = new ClearState();

    public ClearViewModel() {
        super("Clear");
    }

    public void setState(ClearState state) {
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

    public ClearState getState() {
        return state;
    }
}
