package interface_adapter;

import java.awt.*;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

/**
 * Abstract class representing a view model in the application.
 * This abstract class defines the common behaviour and methods of the view model classes used in this application.
 */
public abstract class ViewModel {

    private String viewName;
    private Font comfortaaBig;
    private Font comfortaaMedium;
    private Font comfortaaSmall;

    /**
     * Creates a new ViewModel object with a name
     * @param viewName the name of this ViewModel
     */
    public ViewModel(String viewName) {
        this.viewName = viewName;

        try {
            comfortaaBig = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/assets/fonts/Comfortaa.ttf")).deriveFont(50f);
            comfortaaMedium = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/assets/fonts/Comfortaa.ttf")).deriveFont(30f);
            comfortaaSmall = Font.createFont(Font.TRUETYPE_FONT,
                    new File("src/assets/fonts/Comfortaa.ttf")).deriveFont(20f);

        } catch (IOException | FontFormatException e) {
            //Handle exception
            System.out.println("Comfortaa not loaded into the system.");
        }
    }
    public String getViewName() {
        return this.viewName;
    }

    public Font getComfortaaBig() {
        return comfortaaBig;
    }
    public Font getComfortaaMedium() {
        return comfortaaMedium;
    }
    public Font getComfortaaSmall() {
        return comfortaaSmall;
    }

    public abstract void firePropertyChanged();
    public abstract void addPropertyChangeListener(PropertyChangeListener listener);


}
