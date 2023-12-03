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

public class HomepageView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "homepage";
    private final HomepageViewModel homepageViewModel;
    private final InstagramPanel instagramPanel;
    private final RankingPanelComponent rankingPanelComponent;

    private final ExtensionPanelComponents extensionPanelComponents;

    private final SettingsPanelComponent settingsPanelComponent;

    private final HomepageController homepageController;

    private final JFrame application;



    public HomepageView(HomepageViewModel homepageViewModel, InstagramPanel instagramPanel,
                        RankingPanelComponent rankingPanelComponent, ExtensionPanelComponents extensionPanelComponents,
                        SettingsPanelComponent settingsPanelComponent, HomepageController homepageController, ViewManagerModel viewManagerModel,
                        JFrame application) {
        this.homepageController = homepageController;
        this.setPreferredSize(new Dimension(1200, 600));

        this.homepageViewModel = homepageViewModel;
        this.homepageViewModel.addPropertyChangeListener(this);
        this.instagramPanel = instagramPanel;
        this.rankingPanelComponent = rankingPanelComponent;
        this.extensionPanelComponents = extensionPanelComponents;
        this.settingsPanelComponent = settingsPanelComponent;
        this.application = application;


        JLabel title = new JLabel("Homepage View");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);



        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(homepageViewModel.getComfortaaSmall());
        tabbedPane.setPreferredSize(new Dimension(1200, 600));
        tabbedPane.add(HomepageViewModel.HOME_TAB_LABEL, HomePanelComponent.getPanel(homepageViewModel, homepageController, viewManagerModel, application));
        tabbedPane.add(HomepageViewModel.RANKING_TAB_LABEL, RankingPanelComponent.getPanel());
        tabbedPane.add(HomepageViewModel.EXTENSION_TAB_LABEL, ExtensionPanelComponents.getPanel(homepageViewModel, homepageController));
        tabbedPane.add(HomepageViewModel.ACCOUNT_TAB_LABEL, SettingsPanelComponent.getPanel(homepageViewModel, homepageController, viewManagerModel));

        this.add(tabbedPane);

        this.updateView(homepageViewModel.getState());
    }


    public void updateView(HomepageState newHomepageState) {
        // Update the view based on the new state of homepageViewModel
        this.homepageViewModel.setState(newHomepageState);
        this.settingsPanelComponent.updatePanel(newHomepageState);
        this.extensionPanelComponents.updatePanel(newHomepageState);
        this.instagramPanel.updatePanel(newHomepageState);
//        this.rankingPanelComponent.updatePanel(newHomepageState);
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
