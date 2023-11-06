package view.Homepage;

import interface_adapter.homepage.HomepageViewModel;

import javax.swing.*;

public class RankingPanelComponent {
    public static JPanel getPanel(){
        JPanel rankingPanel = new JPanel();
        rankingPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);


        return rankingPanel;
    }
}
