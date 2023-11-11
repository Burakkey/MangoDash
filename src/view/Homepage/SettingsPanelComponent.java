package view.Homepage;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SettingsPanelComponent {
    public static JPanel getPanel() {

        final JTextField nameInputField = new JTextField(15);

        final JTextField bioInputField = new JTextField(15);

        JPanel settingsPanel = new JPanel();
        settingsPanel.setLayout(new GridBagLayout());
        settingsPanel.setBackground(HomepageViewModel.BACKGROUND_COLOR);

        // Create a panel for the text fields and labels
        JPanel fieldsPanel = new JPanel(new GridBagLayout());

        // Create text fields
        JTextField usernameTextField = new JTextField("Username");
        usernameTextField.setPreferredSize(new Dimension(200, 30));

        // Create and configure JLabels with right alignment
        JLabel usernameLabel = new JLabel(HomepageViewModel.USERNAME_JLABEL);
        usernameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel nameLabel = new JLabel(HomepageViewModel.NAME_JLABEL);
        nameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        JLabel bioLabel = new JLabel(HomepageViewModel.BIO_JLABEL);
        bioLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        // Create a GridBagConstraints object for layout control
        GridBagConstraints gbc = new GridBagConstraints();

        // Add JLabel and JTextField pairs to the fieldsPanel
        gbc.gridx = 0; // Left column
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(0, 0, 5, 5);
        fieldsPanel.add(usernameLabel, gbc);

        gbc.gridx = 1; // Right column
        gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 0);
        fieldsPanel.add(usernameTextField, gbc);

        // Update gridy to position the next component below
        gbc.gridy = 1;

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
        gbc.gridy = 2;

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

        // Add buttons to the buttonsPanel
        buttonsPanel.add(logoutButton);
        buttonsPanel.add(changePasswordButton);
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

        nameInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
//                HomepageState currentState = HomepageViewModel.getState();
//                String text = nameInputField.getText() + e.getKeyChar();
//                currentState.setName(text);
//                // Update the state in the view model
//                HomepageViewModel viewModel = new HomepageViewModel();
//                viewModel.setState(currentState);
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

        bioInputField.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
//                HomepageState currentState = HomepageViewModel.getState();
//                String text = bioInputField.getText() + e.getKeyChar();
//                currentState.setBio(text);
//                // Update the state in the view model
//                HomepageViewModel viewModel = new HomepageViewModel();
//                viewModel.setState(currentState);
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

        changePasswordButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(changePasswordButton)) {
                            // do something
                        }
                    }
                }
        );

        logoutButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(changePasswordButton)) {
                            // do something
                        }
                    }
                }
        );

        saveChangesButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource().equals(changePasswordButton)) {
                            // do something
                        }
                    }
                }
        );
        return settingsPanel;
    }
}