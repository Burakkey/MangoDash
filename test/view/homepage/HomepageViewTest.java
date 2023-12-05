package view.homepage;

import app.Main;
import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import view.homepage.*;

import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
import static view.homepage.ExtensionPanelComponents.facebookAPIaccesstoken;
import static view.homepage.ExtensionPanelComponents.instagramAPIAccessToken;
import static view.homepage.SettingsPanelComponent.*;

public class HomepageViewTest {

    private HomepageViewModel homepageViewModel;
    private HomepageView homepageView;
    private InstagramPanel instagramPanel;
    private FacebookPanel facebookPanel;
    private ExtensionPanelComponents extensionPanelComponents;
    private SettingsPanelComponent settingsPanelComponent;
    private ViewManagerModel viewManagerModel;
    private JFrame application;

    @BeforeEach
    public void setUp() {
        homepageViewModel = new HomepageViewModel();
        instagramPanel = new InstagramPanel(new JFrame()); // Assuming these can be initialized without a JFrame
        facebookPanel = new FacebookPanel(new JFrame());
        extensionPanelComponents = new ExtensionPanelComponents();
        settingsPanelComponent = new SettingsPanelComponent();
        viewManagerModel = new ViewManagerModel();
        application = new JFrame();

        // Initialize HomepageView without a real controller
        homepageView = new HomepageView(homepageViewModel, instagramPanel, facebookPanel,
                extensionPanelComponents, settingsPanelComponent,
                null, viewManagerModel, application);
    }

    @Test
    public void testHomepageStateChanges() {
        HomepageState newState = new HomepageState();
        newState.setName("New Name");
        newState.setUsername("New Username");
        newState.setBio("New Bio");
        newState.setInstagramToken("New Instagram Token");
        newState.setFacebookToken("New Facebook Token");

        homepageView.updateView(newState);

        assertEquals("New Name", nameInputField.getText());
        assertEquals("New Username", usernameInputField.getText());
        assertEquals("New Bio", bioInputField.getText());
        assertEquals("New Instagram Token", instagramAPIAccessToken.getText());
        assertEquals("New Facebook Token", facebookAPIaccesstoken.getText());
    }

    public JPanel getTab(int n) {
        JFrame app = null;
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window instanceof JFrame) {
                app = (JFrame) window;
            }
        }

        Assertions.assertNotNull(app); // found the window?

        Component root = app.getComponent(0);

        Component cp = ((JRootPane) root).getContentPane();

        JPanel jp = (JPanel) cp;

        JPanel jp2 = (JPanel) jp.getComponent(0);

        HomepageView hv = (HomepageView) jp2.getComponent(2);

        JTabbedPane tabbedPane = (JTabbedPane) hv.getComponent(0);

        return (JPanel) tabbedPane.getComponentAt(n); // this should be the clear button
    }



    @Test
    void testHomeTabPresent() throws ClassNotFoundException {
        Main.main(null);
        JPanel tab = getTab(0);
        assert(tab.getName().equals("Home"));
    }

    @Test
    void testExtenstionTabPresent() throws ClassNotFoundException {
        Main.main(null);
        JPanel tab = getTab(1);
        assert(tab.getName().equals("Extensions"));
    }

    @Test
    void testSettingsTabPresent() throws ClassNotFoundException {
        Main.main(null);
        JPanel tab = getTab(2);
        assert(tab.getName().equals("Settings"));
    }


}