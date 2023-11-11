package view.Homepage;

import interface_adapter.homepage.HomepageViewModel;

import javax.swing.*;

public class HomePanelComponent {
    public static JPanel getPanel(){
        JPanel homePanel = new JPanel();
        homePanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        return homePanel;
    }

}
