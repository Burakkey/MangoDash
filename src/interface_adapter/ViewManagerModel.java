package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * This class is responsible for managing the views within the application
 */
public class ViewManagerModel {

    private String activeViewName;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public String getActiveView() {
        return activeViewName;
    }

    /**
     * Set the provided view corresponding to the provided String as the view that the user sees
     * @param activeView the desired view's name
     */
    public void setActiveView(String activeView) {
        this.activeViewName = activeView;
    }

    /**
     * Presenters call this method to let the ViewModel know to alert the View that a property has been changed
     */
    public void firePropertyChanged() {
        support.firePropertyChange("view", null, this.activeViewName);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }
}
