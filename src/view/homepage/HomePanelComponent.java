package view.homepage;
import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;

import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * The HomePanelComponent contains methods that display information to the user from the Home tab on the HomepageView.
 * It is responsible for the UI for displaying the Facebook and Instagram data, and observes/reacts to events triggered by the user on the Home tab.
 */
public class HomePanelComponent {

    private InstagramDataSet instagramDataSet;
    private FacebookDataSet facebookDataSet;

    /**
     * Creates a new HomePanelComponent with the default instagram and facebook data sets
     */
    public HomePanelComponent() {
        instagramDataSet = new InstagramDataSet();
        facebookDataSet = new FacebookDataSet();
    }

    public void updatePanel(HomepageState newState) {
        instagramDataSet.updateStats(newState.getInstagramStatsHashMap());
        facebookDataSet.updateStats(newState.getFacebookStatsHashMap());
    }

    /**
     * This method builds the Home Panel UI and reacts to the buttons clicked
     * @param homepageViewModel
     * @param homepageController
     * @param viewManagerModel
     * @param application
     * @return the Home Panel
     */
    public static JPanel getPanel(HomepageViewModel homepageViewModel, HomepageController homepageController, ViewManagerModel viewManagerModel, JFrame application) {
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
                    JDialog instagramPage = new JDialog(application, "InstagramPage", true);
                    instagramPage.setSize(1200,650);
                    JPanel instagramStats = InstagramPanel.getPanel(homepageViewModel, homepageController);
                    instagramPage.add(instagramStats);
                    instagramPage.setLocationRelativeTo(null);
                    instagramPage.setVisible(true);
                }
            }
        });

        facebookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(facebookButton)) {
                    JDialog facebookPage = new JDialog(application, "FacebookPage", true);
                    facebookPage.setSize(1200,650);
                    JPanel facebookStats = FacebookPanel.getPanel(homepageViewModel, homepageController);
                    facebookPage.add(facebookStats);
                    facebookPage.setLocationRelativeTo(null);
                    facebookPage.setVisible(true);
                }
            }
        });

        return homePanel;
    }

}
