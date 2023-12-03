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

    public static final String INSTAGRAM = "Instagram";

    public static final String FACEBOOK = "Facebook";

    public static final String SHOW_INSTAGRAM_GRAPH_LABEL = "Show Instagram Graph";

    public static final String LOG_OUT_BUTTON_LABEL = "Logout";

    public static final String CHANGE_PASSWORD_BUTTON_LABEL = "Change Password";

    public static final String SAVE_CHANGES_BUTTON_LABEL = "Save Changes";

    public static final String NAME_JLABEL = "Name";

    public static final String USERNAME_JLABEL = "Username";

    public static final String BIO_JLABEL = "Bio";

    public static final Color BACKGROUND_COLOR = new Color(255,245,220);
    public static final Color BUTTON_ORANGE = new Color(255,179,138);
    public static final Color OFF_WHITE = new Color(225,225,225);
    public static final Color GRAPH_ORANGE = new Color(255, 207, 150);


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
