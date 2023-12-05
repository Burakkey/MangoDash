package view.homepage;


import interface_adapter.ViewModel;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * HomepageView displays information on the Homepage to the user, is responsible for the home page UI, and observes/reacts
 * to events that are triggered by the user.
 */
public class HomepageView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "homepage";
    private final HomepageViewModel homepageViewModel;
    private final InstagramPanel instagramPanel;
    private  final FacebookPanel facebookPanel;
    private final HomePanelComponent homePanelComponent;
    private final ExtensionPanelComponents extensionPanelComponents;

    private final SettingsPanelComponent settingsPanelComponent;

    private final HomepageController homepageController;

    private final JFrame application;

    /**
     * Constructs a new HomepageView object, using the parameters given:
     * @param homepageViewModel
     * @param instagramPanel the instagram panel that is displayed when the instagram button on the Home Panel is clicked
     * @param facebookPanel the facebook panel that is displayed when the facebook button on the Home Panel is clicked
     * @param homePanelComponent the home panel that is displayed when the home tab is clicked
     * @param extensionPanelComponents the extension panel that is displayed when the extension tab is clicked
     * @param settingsPanelComponent the settings panel that is displayed when the settings tab is clicked
     * @param homepageController
     * @param viewManagerModel
     * @param application
     */
    public HomepageView(HomepageViewModel homepageViewModel, InstagramPanel instagramPanel, FacebookPanel facebookPanel,
                        HomePanelComponent homePanelComponent, ExtensionPanelComponents extensionPanelComponents,
                        SettingsPanelComponent settingsPanelComponent, HomepageController homepageController, ViewManagerModel viewManagerModel,
                        JFrame application) {
        this.homepageController = homepageController;
        this.setPreferredSize(new Dimension(1200, 600));

        this.homepageViewModel = homepageViewModel;
        this.homepageViewModel.addPropertyChangeListener(this);
        this.instagramPanel = instagramPanel;
        this.facebookPanel = facebookPanel;
        this.homePanelComponent = homePanelComponent;
        this.extensionPanelComponents = extensionPanelComponents;
        this.settingsPanelComponent = settingsPanelComponent;
        this.application = application;


        JLabel title = new JLabel("Homepage View");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);



        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(homepageViewModel.getComfortaaSmall());
        tabbedPane.setPreferredSize(new Dimension(1200, 600));
        tabbedPane.add(HomepageViewModel.HOME_TAB_LABEL, HomePanelComponent.getPanel(homepageViewModel, homepageController, viewManagerModel, application));
        tabbedPane.add(HomepageViewModel.EXTENSION_TAB_LABEL, ExtensionPanelComponents.getPanel(homepageViewModel, homepageController));
        tabbedPane.add(HomepageViewModel.ACCOUNT_TAB_LABEL, SettingsPanelComponent.getPanel(homepageViewModel, homepageController, viewManagerModel));

        this.add(tabbedPane);

        this.updateView(homepageViewModel.getState());
    }

    /**
     * Updates the HomepageView when the HomepageState changes (thus updating the other components of the Homepage)
     * @param newHomepageState
     */
    public void updateView(HomepageState newHomepageState) {
        // Update the view based on the new state of homepageViewModel
        this.homepageViewModel.setState(newHomepageState);
        this.settingsPanelComponent.updatePanel(newHomepageState);
        this.extensionPanelComponents.updatePanel(newHomepageState);
        this.instagramPanel.updatePanel(newHomepageState);
        this.facebookPanel.updatePanel(newHomepageState);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        HomepageState newState = (HomepageState) evt.getNewValue();
        this.updateView(newState);  // Update the view after the state change
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }
}
