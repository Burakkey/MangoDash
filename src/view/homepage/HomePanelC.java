package view.homepage;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class HomePanelC {
    private InstagramDataSet instagramDataSet;

    public HomePanelC() {
        instagramDataSet = new InstagramDataSet();
        // Initialize other components
    }

    public void updatePanel(HomepageState newState) {
        instagramDataSet.updateStats(newState.getInstagramStatsHashMap());
    }

    public static JPanel getPanel(HomepageViewModel homepageViewModel, HomepageController homepageController, ViewManagerModel viewManagerModel) {
        JPanel homePanel = new JPanel();

        homePanel.setLayout(new GridBagLayout());
        homePanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        homePanel.setPreferredSize(new Dimension(200, 30));

        try {
             BufferedImage picture = ImageIO.read(new File("src/assets/homepage/Home.png"));
            JLabel picLabel = new JLabel(new ImageIcon(picture));
            Border border = BorderFactory.createEmptyBorder(0, 0, 50, 0);
            picLabel.setBorder(border);
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            homePanel.add(picLabel);
        } catch (IOException ignored){}

        JPanel buttons = new JPanel();
        buttons.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        JButton instagramButton = new JButton(HomepageViewModel.INSTAGRAM);
        instagramButton.setBackground(HomepageViewModel.BUTTON_ORANGE);
        instagramButton.setFont(homepageViewModel.getComfortaaSmall());
        buttons.add(instagramButton);

        JButton facebookButton = new JButton(HomepageViewModel.FACEBOOK);
        facebookButton.setBackground(HomepageViewModel.BUTTON_ORANGE);
        facebookButton.setFont(homepageViewModel.getComfortaaSmall());
        buttons.add(facebookButton);

        homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS));
        homePanel.add(buttons);

        instagramButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(instagramButton)) {
                    viewManagerModel.setActiveView("Instapage");
                    viewManagerModel.firePropertyChanged();
                }
            }
        });
        return homePanel;
    }


}
