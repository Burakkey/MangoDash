package view.Homepage;

import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HomepageView extends JPanel implements ActionListener, PropertyChangeListener {
    public final String viewName = "homepage";
    private final HomepageViewModel homepageViewModel;
    private final JPanel homePanel;
    private final JPanel rankingPanel;

    private final JPanel extensionPanel;

    private final JPanel settingsPanel;


    public HomepageView(HomepageViewModel homepageViewModel) {
        this.setPreferredSize(new Dimension(1200, 600));

        this.homepageViewModel = homepageViewModel;
        this.homepageViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Homepage View");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        homePanel = HomePanelComponent.getPanel();
        rankingPanel = RankingPanelComponent.getPanel();
        extensionPanel = ExtensionPanelComponents.getPanel();
        settingsPanel = SettingsPanelComponent.getPanel(homepageViewModel);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(1200, 600));
        tabbedPane.add(HomepageViewModel.HOME_TAB_LABEL, homePanel);
        tabbedPane.add(HomepageViewModel.RANKING_TAB_LABEL, rankingPanel);
        tabbedPane.add(HomepageViewModel.EXTENSION_TAB_LABEL, extensionPanel);
        tabbedPane.add(HomepageViewModel.ACCOUNT_TAB_LABEL, settingsPanel);

        this.add(tabbedPane);

        this.updateView(homepageViewModel.getState());
    }


    public void updateView(HomepageState newHomepageState) {
        // Update the view based on the new state of homepageViewModel
        this.homepageViewModel.setState(newHomepageState);
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
