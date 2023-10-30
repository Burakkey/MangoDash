package view.Homepage;

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

    public HomepageView(HomepageViewModel homepageViewModel) {
        // This is the code that modifies the color
        this.setPreferredSize(new Dimension(1200,600));

        this.homepageViewModel = homepageViewModel;
        this.homepageViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Homepage View");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel homePanel = new JPanel();
        homePanel.setBackground(Color.ORANGE);

        JPanel rankingPanel = new JPanel();
        rankingPanel.setBackground(Color.ORANGE);

        JPanel extensionPanel = new JPanel();
        extensionPanel.setBackground(Color.ORANGE);

        JPanel settingsPanel = settingsPanelComponent.getPanel();

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(1200,600));
        tabbedPane.add(HomepageViewModel.HOME_TAB_LABEL, homePanel);
        tabbedPane.add(HomepageViewModel.RANKING_TAB_LABEL, rankingPanel);
        tabbedPane.add(HomepageViewModel.EXTENSION_TAB_LABEL, extensionPanel);
        tabbedPane.add(HomepageViewModel.ACCOUNT_TAB_LABEL, settingsPanel);

        this.add(tabbedPane);
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
//      HomepageState state = (HomepageState) evt.getNewValue();
//      username.setText(state.getUsername());
    }
}
