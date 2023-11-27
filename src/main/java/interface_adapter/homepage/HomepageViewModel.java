package interface_adapter.homepage;

import interface_adapter.ViewModel;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class HomepageViewModel extends ViewModel {

    public static final String HOME_TAB_LABEL = "Home";

    public static final String RANKING_TAB_LABEL = "Rankings";

    public static final String EXTENSION_TAB_LABEL = "Extensions";

    public static final String ACCOUNT_TAB_LABEL = "Settings";

    public static final String LOG_OUT_BUTTON_LABEL = "Logout";

    public static final String CHANGE_PASSWORD_BUTTON_LABEL = "Change Password";

    public static final String SAVE_CHANGES_BUTTON_LABEL = "Save Changes";

    public static final String NAME_JLABEL = "Name";

    public static final String USERNAME_JLABEL = "Username";

    public static final String BIO_JLABEL = "Bio";

    public static final Color BACKGROUND_COLOR = Color.ORANGE;

    private static HomepageState homepageState = new HomepageState();

    public HomepageViewModel() {
        super("homepage");
    }

    public void setState(HomepageState state) {
        homepageState = state;
    }

    public HomepageState getState(){
        return homepageState;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    // This is what the Signup Presenter will call to let the ViewModel know
    // to alert the View
    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.homepageState);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}