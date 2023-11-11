package app;

import data_access.FileUserDataAccessObject;
import entity.CommonUserFactory;
import interface_adapter.clear_users.ClearViewModel;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login_home.LoginHomeViewModel;
import interface_adapter.signup.SignupViewModel;
import interface_adapter.ViewManagerModel;
import view.*;
import view.homepage.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Build the main program window, the main panel containing the
        // various cards, and the layout, and stitch them together.

        // The main application window.
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
        ClearViewModel clearViewModel = new ClearViewModel();
        HomepageViewModel homepageViewModel = new HomepageViewModel();
        LoginHomeViewModel loginHomeViewModel = new LoginHomeViewModel();


        FileUserDataAccessObject userDataAccessObject;
        FileUserDataAccessObject userDataAccessObject2;
        try {
            userDataAccessObject = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
            userDataAccessObject2 = new FileUserDataAccessObject("./users.csv", new CommonUserFactory());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, userDataAccessObject, clearViewModel, userDataAccessObject2);
        views.add(signupView, signupView.viewName);

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, homepageViewModel, userDataAccessObject);
        views.add(loginView, loginView.viewName);

        LoggedInView loggedInView = new LoggedInView(loggedInViewModel, viewManagerModel);
        views.add(loggedInView, loggedInView.viewName);

        HomePanelComponent homePanelComponent = new HomePanelComponent();
        RankingPanelComponent rankingPanelComponent = new RankingPanelComponent();
        ExtensionPanelComponents extensionPanelComponents = new ExtensionPanelComponents();
        SettingsPanelComponent settingsPanelComponent = new SettingsPanelComponent();
        HomepageView homepageView = HomepageUseCaseFactory.create(viewManagerModel, loginViewModel, homepageViewModel, homePanelComponent, rankingPanelComponent,
                extensionPanelComponents, settingsPanelComponent, userDataAccessObject);
        views.add(homepageView, homepageView.viewName);


        LoginHomeView loginHomeView = new LoginHomeView(loginHomeViewModel, viewManagerModel); // TODO CHANGE IN PARAMETERS
        views.add(loginHomeView, loginHomeView.viewName);

        viewManagerModel.setActiveView(loginHomeView.viewName);
        viewManagerModel.firePropertyChanged();

        application.pack();
        application.setVisible(true);
    }
}