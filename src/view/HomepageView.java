package view;

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
    final JButton homeButton;
    final JButton rankingsButton;
    final JButton extensionsButton;
    final JButton settingsButton;

    public HomepageView(HomepageViewModel homepageViewModel) {
        // This is the code that modifies the color
        this.setBackground(Color.ORANGE);
        setSize(new Dimension(400,400));

        this.homepageViewModel = homepageViewModel;
        this.homepageViewModel.addPropertyChangeListener(this);

        JLabel title = new JLabel("Homepage View");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.X_AXIS));

        homeButton = new JButton(homepageViewModel.HOME_BUTTON_LABEL);
        buttons.add(homeButton);

        rankingsButton = new JButton(homepageViewModel.RANKINGS_BUTTON_LABEL);
        buttons.add(rankingsButton);

        extensionsButton = new JButton(homepageViewModel.EXTENSIONS_BUTTON_LABEL);
        buttons.add(extensionsButton);

        settingsButton = new JButton(homepageViewModel.SETTINGS_BUTTON_LABEL);
        buttons.add(settingsButton);

        homeButton.addActionListener(this);
        rankingsButton.addActionListener(this);
        extensionsButton.addActionListener(this);
        settingsButton.addActionListener(this);

        this.setLayout(new BorderLayout());

        this.add(title, BorderLayout.NORTH);
        this.add(buttons, BorderLayout.NORTH);
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
