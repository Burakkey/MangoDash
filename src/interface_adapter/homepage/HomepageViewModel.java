package interface_adapter.homepage;

import interface_adapter.ViewModel;
import interface_adapter.homepage.HomepageState;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HomepageViewModel extends ViewModel {

    public static final String HOME_TAB_LABEL = "Home";

    public static final String RANKING_TAB_LABEL = "Rankings";

    public static final String EXTENSION_TAB_LABEL = "Extensions";

    public static final String ACCOUNT_TAB_LABEL = "Settings";



    public static final Color BACKGROUND_COLOR = Color.ORANGE;

    private HomepageState state = new HomepageState();

    public HomepageViewModel() {
        super("homepage menu");
    }

    public void setState(HomepageState state) {
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

    public HomepageState getState() {
        return state;
    }
}
