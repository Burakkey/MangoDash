package view.homepage;

import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * The ExtensionPanelComponent contains methods that display information to the user from the Extensions tab on the HomepageView.
 * It is responsible for the UI for changing/inputting the user's Facebook and/or Instagram API key(s), and observes/reacts
 * to events triggered by the user on the Extension tab.
 */
public class ExtensionPanelComponents {
    public static JTextField facebookAPIaccesstoken = new JTextField(15);
    public static JTextField instagramAPIAccessToken = new JTextField(15);

    /**
     * After the state of the Homepage changes, this method updates the Facebook and/or Instagram API key shown in the text fields
     * @param newState the new state
     */
    public void updatePanel(HomepageState newState){

        // these are updated without re-creating a new instance, add component that can change here!
        facebookAPIaccesstoken.setText(newState.getFacebookToken());
        instagramAPIAccessToken.setText(newState.getInstagramToken());

    }

    /**
     * This method builds the Extension Panel UI and reacts to the user inputs
     * @param homepageViewModel
     * @param homepageController
     * @return the Extension Panel
     */
    public static JPanel getPanel(HomepageViewModel homepageViewModel, HomepageController homepageController) {

        // Below are how components are place (visuals) feel free to do whatever here
        JPanel settingsPanel = new JPanel();
        settingsPanel.setName(HomepageViewModel.EXTENSION_TAB_LABEL);
        settingsPanel.setLayout(new GridBagLayout());
        settingsPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        // Create a panel for the text fields and labels
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);
        settingsPanel.setPreferredSize(new Dimension(200, 30));



        // Create and configure JLabels with right alignment
        Border border =  BorderFactory.createEmptyBorder(0, 50, 0, 10);
        JLabel facebookAPILabel = new JLabel("Facebook Access Token:");
        facebookAPILabel.setHorizontalAlignment(SwingConstants.RIGHT);
        facebookAPILabel.setFont(homepageViewModel.getComfortaaSmall());
        facebookAPILabel.setBorder(border);
        JLabel instagramAPILabel = new JLabel("Instagram Access Token:");
        instagramAPILabel.setHorizontalAlignment(SwingConstants.RIGHT);
        instagramAPILabel.setFont(homepageViewModel.getComfortaaSmall());
        instagramAPILabel.setBorder(border);

        // Create a GridBagConstraints object for layout control
        GridBagConstraints gbc = new GridBagConstraints();

        try {
            gbc.gridx = 0; // Left column
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.NORTH;
            gbc.gridheight = 3;
            gbc.gridwidth = 3;
            gbc.insets = new Insets(5, 5, 0, 0);
            BufferedImage settingsPicture=
                    ImageIO.read(new File("src/assets/homepage/ApiKey.png"));
            JLabel picLabel = new JLabel(new ImageIcon(settingsPicture));
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            Border emptyBorder = BorderFactory.createEmptyBorder(0, 50, 50, 50);
            picLabel.setBorder(emptyBorder);
            fieldsPanel.add(picLabel, gbc);

        } catch (IOException ex) {
            System.out.println("Image not found!");
        }
        // Add JLabel and JTextField pairs to the fieldsPanel
        gbc.gridx = 0; // Left column
        gbc.gridy = 3;
        gbc.gridheight = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 5, 5);
        fieldsPanel.add(facebookAPILabel, gbc);

        gbc.gridx = 1; // Right column
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        facebookAPIaccesstoken.setFont(homepageViewModel.getComfortaaSmall());
        fieldsPanel.add(facebookAPIaccesstoken, gbc);

        // Update gridy to position the next component below
        gbc.gridy = 4;

        gbc.gridx = 0; // Left column
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 5, 5);
        fieldsPanel.add(instagramAPILabel, gbc);

        gbc.gridx = 1; // Right column
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        instagramAPIAccessToken.setFont(homepageViewModel.getComfortaaSmall());
        fieldsPanel.add(instagramAPIAccessToken, gbc);

        // Update gridy to position the next component below
        gbc.gridy = 2;

        // Create a nested panel for buttons using FlowLayout (horizontal layout)
        JPanel buttonsPanel = new JPanel(new FlowLayout());
        buttonsPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        // Create buttons using constants

        JButton saveChangesButton = new JButton(HomepageViewModel.SAVE_CHANGES_BUTTON_LABEL);
        saveChangesButton.setBackground(HomepageViewModel.BUTTON_ORANGE);
        saveChangesButton.setFont(homepageViewModel.getComfortaaSmall());

        // Add buttons to the buttonsPanel
        buttonsPanel.add(saveChangesButton);

        // Create a top-level GridBagConstraints object for layout control
        gbc.gridx = 0; // Centered
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER; // Center fieldsPanel horizontally
        gbc.insets = new Insets(0, 0, 10, 0); // 10px space at the bottom

        // Add fieldsPanel to settingsPanel
        settingsPanel.add(fieldsPanel, gbc);

        // Update y-coordinate to position buttonsPanel below fieldsPanel
        gbc.gridy = 1;
        settingsPanel.add(buttonsPanel, gbc);




        saveChangesButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(saveChangesButton)) {
                            HomepageState currentState = homepageViewModel.getState();
                            currentState.setInstagramToken(instagramAPIAccessToken.getText());

                            currentState.setFacebookToken(facebookAPIaccesstoken.getText());
                            HashMap<String, String> apiTokenMap = new HashMap<>();
                            apiTokenMap.put("Facebook", currentState.getFacebookToken());
                            apiTokenMap.put("Instagram", currentState.getInstagramToken());
                            homepageController.executeAPIChanges(currentState.getUsername(), currentState.getName(),
                                    apiTokenMap);

                            if (instagramAPIAccessToken.getText().isEmpty()) {
                                // Do nothing here because API key is blank
                            } else if (currentState.getInstagramKeyError()) {
                                JOptionPane.showMessageDialog(null, "Invalid Instagram API key", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Successfully Validated Instagram API key", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }

                            if (facebookAPIaccesstoken.getText().isEmpty()) {
                                // Do nothing here because API key is blank
                            } else if (currentState.getFacebookKeyError()) {
                                JOptionPane.showMessageDialog(null, "Invalid Facebook API key", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                            else{
                                JOptionPane.showMessageDialog(null, "Successfully Validated Facebook API key", "Success", JOptionPane.INFORMATION_MESSAGE);
                            }
                        }
                    }

                }
        );
        return settingsPanel;
    }
}
