package view.Homepage;

import interface_adapter.homepage.HomepageViewModel;

import javax.swing.*;

public class ExtensionPanelComponents {
    public static JPanel getPanel(){
        JPanel extensionPanel = new JPanel();
        extensionPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        return extensionPanel;
    }
}
