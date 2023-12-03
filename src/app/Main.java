package app;
import com.formdev.flatlaf.*;
import data_access.InstagramAPIDataAccessObject;
import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login_home.LoginHomeViewModel;
import interface_adapter.signup.SignupViewModel;
import view.*;
import view.homepage.*;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
        FlatLightLaf.setup();
        JFrame application = new JFrame("Login Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        CardLayout cardLayout = new CardLayout();

        // The various View objects. Only one view is visible at a time.
        JPanel views = new JPanel(cardLayout);
        application.add(views);

        // This keeps track of and manages which view is currently showing.
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        new ViewManager(views, cardLayout, viewManagerModel);

        // The data for the views, such as username and password, are in the ViewModels.
        // This information will be changed by a presenter object that is reporting the
        // results from the use case. The ViewModels are observable, and will
        // be observed by the Views.
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();
        HomepageViewModel homepageViewModel = new HomepageViewModel();
        LoginHomeViewModel loginHomeViewModel = new LoginHomeViewModel();

        SQLiteUserDataAccessObject userDataAccessObject;
        SQLiteUserDataAccessObject userDataAccessObject2;
        userDataAccessObject = new SQLiteUserDataAccessObject("users.db", new CommonUserFactory());
        userDataAccessObject2 = new SQLiteUserDataAccessObject("users.db", new CommonUserFactory());

        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, userDataAccessObject);
        views.add(signupView, signupView.viewName);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, homepageViewModel, userDataAccessObject);
        views.add(loginView, loginView.viewName);

        InstagramPanel instagramPanel = new InstagramPanel(application);
        FacebookPanel facebookPanel = new FacebookPanel(application);
        RankingPanelComponent rankingPanelComponent = new RankingPanelComponent();
        ExtensionPanelComponents extensionPanelComponents = new ExtensionPanelComponents();
        SettingsPanelComponent settingsPanelComponent = new SettingsPanelComponent();
        HomepageView homepageView = HomepageUseCaseFactory.create(viewManagerModel, loginViewModel, homepageViewModel, instagramPanel, facebookPanel, rankingPanelComponent,
                extensionPanelComponents, settingsPanelComponent, userDataAccessObject, application);
        views.add(homepageView, homepageView.viewName);

        //


        LoginHomeView loginHomeView = new LoginHomeView(loginHomeViewModel, viewManagerModel);
        views.add(loginHomeView, loginHomeView.viewName);

        viewManagerModel.setActiveView(loginHomeView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setSize(800,650);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}