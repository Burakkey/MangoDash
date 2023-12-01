package view.homepage;

import interface_adapter.ViewModel;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.login_home.LoginHomeViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.switchview.SwitchViewController;

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
    private final HomePanelComponent homePanelComponent;
    private final RankingPanelComponent rankingPanelComponent;

    private final ExtensionPanelComponents extensionPanelComponents;

    private final SettingsPanelComponent settingsPanelComponent;

    private final HomepageController homepageController;

    private final SwitchViewController switchViewController;



    public HomepageView(HomepageViewModel homepageViewModel, HomePanelComponent homePanelComponent,
                        RankingPanelComponent rankingPanelComponent, ExtensionPanelComponents extensionPanelComponents,
                        SettingsPanelComponent settingsPanelComponent, HomepageController homepageController, SwitchViewController switchViewController) {
        this.homepageController = homepageController;
        this.switchViewController = switchViewController;
        this.setPreferredSize(new Dimension(1200, 600));

        this.homepageViewModel = homepageViewModel;
        this.homepageViewModel.addPropertyChangeListener(this);
        this.homePanelComponent = homePanelComponent;
        this.rankingPanelComponent = rankingPanelComponent;
        this.extensionPanelComponents = extensionPanelComponents;
        this.settingsPanelComponent = settingsPanelComponent;


        JLabel title = new JLabel("Homepage View");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);



        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setFont(homepageViewModel.getComfortaaSmall());
        tabbedPane.setPreferredSize(new Dimension(1200, 600));
        tabbedPane.add(HomepageViewModel.HOME_TAB_LABEL, homePanelComponent.getPanel(homepageViewModel, homepageController, switchViewController));
        tabbedPane.add(HomepageViewModel.RANKING_TAB_LABEL, RankingPanelComponent.getPanel());
        tabbedPane.add(HomepageViewModel.EXTENSION_TAB_LABEL, ExtensionPanelComponents.getPanel(homepageViewModel, homepageController, switchViewController));
        tabbedPane.add(HomepageViewModel.ACCOUNT_TAB_LABEL, SettingsPanelComponent.getPanel(homepageViewModel, homepageController, switchViewController));

        this.add(tabbedPane);

        this.updateView(homepageViewModel.getState());
    }


    public void updateView(HomepageState newHomepageState) {
        // Update the view based on the new state of homepageViewModel
        this.homepageViewModel.setState(newHomepageState);
        this.settingsPanelComponent.updatePanel(newHomepageState);
        this.extensionPanelComponents.updatePanel(newHomepageState);
        this.homePanelComponent.updatePanel(newHomepageState);
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
