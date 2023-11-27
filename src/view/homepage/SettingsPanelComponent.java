package view.homepage;
import interface_adapter.homepage.HomepageController;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.switchview.SwitchViewController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SettingsPanelComponent {

    public SettingsPanelComponent() {
    }

    public static JTextField nameInputField = new JTextField(15);
    public static JTextField bioInputField = new JTextField(15);
    public static JTextField usernameInputField = new JTextField(15);


    public void updatePanel(HomepageState newState){

        // these are updated without re-creating a new instance, add component that can change here!
        nameInputField.setText(newState.getName());
        usernameInputField.setText(newState.getUsername());
        bioInputField.setText(newState.getBio());
    }

    public static JPanel getPanel(HomepageViewModel homepageViewModel, HomepageController homepageController, SwitchViewController switchViewController) {

        // Below are how components are place (visuals) feel free to do whatever here

        JPanel settingsPanel = new JPanel();

        settingsPanel.setLayout(new GridBagLayout());
        settingsPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        // Create a panel for the text fields and labels
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        settingsPanel.setPreferredSize(new Dimension(200, 30));

        // Create and configure JLabels with right alignment
        JLabel usernameLabel = new JLabel(HomepageViewModel.USERNAME_JLABEL);
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel nameLabel = new JLabel(HomepageViewModel.NAME_JLABEL);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel bioLabel = new JLabel(HomepageViewModel.BIO_JLABEL);
        bioLabel.setHorizontalAlignment(SwingConstants.RIGHT);

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
                    ImageIO.read(new File("src/assets/homepage/Settings.png"));
            JLabel picLabel = new JLabel(new ImageIcon(settingsPicture));
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
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
        fieldsPanel.add(usernameLabel, gbc);

        gbc.gridx = 1; // Right column
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        fieldsPanel.add(usernameInputField, gbc);

        // Update gridy to position the next component below
        gbc.gridy = 4;

        gbc.gridx = 0; // Left column
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 5, 5);
        fieldsPanel.add(nameLabel, gbc);

        gbc.gridx = 1; // Right column
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        fieldsPanel.add(nameInputField, gbc);

        // Update gridy to position the next component below
        gbc.gridy = 5;

        gbc.gridx = 0; // Left column
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 0, 5);
        fieldsPanel.add(bioLabel, gbc);

        gbc.gridx = 1; // Right column
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 0, 0);
        fieldsPanel.add(bioInputField, gbc);

        // Create a nested panel for buttons using FlowLayout (horizontal layout)
        JPanel buttonsPanel = new JPanel(new FlowLayout());

        // Create buttons using constants
        JButton logoutButton = new JButton(HomepageViewModel.LOG_OUT_BUTTON_LABEL);
        JButton changePasswordButton = new JButton(HomepageViewModel.CHANGE_PASSWORD_BUTTON_LABEL);
        JButton saveChangesButton = new JButton(HomepageViewModel.SAVE_CHANGES_BUTTON_LABEL);

        logoutButton.setBackground(HomepageViewModel.BUTTON_ORANGE);
        changePasswordButton.setBackground(HomepageViewModel.BUTTON_ORANGE);
        saveChangesButton.setBackground(HomepageViewModel.BUTTON_ORANGE);

        // Add buttons to the buttonsPanel
        buttonsPanel.add(logoutButton);
        buttonsPanel.add(changePasswordButton);
        buttonsPanel.add(saveChangesButton);
        buttonsPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);

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

        nameInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                HomepageState currentState = homepageViewModel.getState();
                String text = nameInputField.getText() + e.getKeyChar();
                currentState.setName(text);
                HomepageViewModel viewModel = new HomepageViewModel();
                viewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Handle key pressed event if needed
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Handle key released event if needed
            }
        });

        usernameInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                HomepageState currentState = homepageViewModel.getState();
                String text = usernameInputField.getText() + e.getKeyChar();
                currentState.setUsername(text);
                HomepageViewModel viewModel = new HomepageViewModel();
                viewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

        bioInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                HomepageState currentState = homepageViewModel.getState();
                String text = bioInputField.getText() + e.getKeyChar();
                currentState.setBio(text);
                HomepageViewModel viewModel = new HomepageViewModel();
                viewModel.setState(currentState);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // Handle key pressed event if needed
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // Handle key released event if needed
            }
        });

        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource().equals(changePasswordButton)) {
                    // Create a custom JDialog as the parent of the OptionDialog
                    JDialog customDialog = new JDialog();
                    customDialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                    // Create an array of JComponents (text fields and labels)
                    JTextField currentPasswordField = new JPasswordField();
                    JTextField newPasswordField = new JPasswordField();
                    JTextField reenterNewPasswordField = new JPasswordField();

                    Object[] message = {
                            "Current Password:", currentPasswordField,
                            "New Password:", newPasswordField,
                            "Re-enter New Password:", reenterNewPasswordField
                    };

                    // Show the option dialog with the custom dialog as the parent
                    int option = JOptionPane.showOptionDialog(
                            customDialog,
                            message,
                            "Change Password",
                            JOptionPane.OK_CANCEL_OPTION,
                            JOptionPane.PLAIN_MESSAGE,
                            null,
                            new Object[]{"Okay", "Cancel"},
                            "default");

                    // Check the user's choice
                    if (option == JOptionPane.OK_OPTION) {
                        // User clicked "Okay", handle the input
                        String currentPassword = currentPasswordField.getText();
                        String newPassword = newPasswordField.getText();
                        String reenterNewPassword = reenterNewPasswordField.getText();

                        if (!reenterNewPassword.equals(newPassword)) {
                            JOptionPane.showMessageDialog(
                                    customDialog,
                                    "New passwords do not match!",
                                    "Error",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        } else {
                            HomepageState currentState = homepageViewModel.getState();
                            homepageController.executeSaveChanges(
                                    currentState.getUsername(), currentState.getName(), currentState.getBio(),
                                    currentPassword, newPassword, reenterNewPassword);

                            // Close the custom dialog, which will also close the OptionDialog
                            customDialog.dispose();
                        }
                    } else {
                        // User clicked "Cancel" or closed the dialog
                        // Close the custom dialog, which will also close the OptionDialog
                        customDialog.dispose();
                    }
                }
            }
        });



        logoutButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(logoutButton)) {
                            switchViewController.execute(logoutButton.getText());
                        }
                    }
                }
        );

        saveChangesButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(saveChangesButton)) {
                            HomepageState currentState = homepageViewModel.getState();
                            homepageController.executeSaveChanges(currentState.getUsername(), currentState.getName(), currentState.getBio());

                        }
                    }
                }
        );
        return settingsPanel;
    }
}