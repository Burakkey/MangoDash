package app;

import data_access.FacebookAPIDataAccessObject;
import data_access.InstagramAPIDataAccessObject;
import entity.CommonUserFactory;
import entity.SocialMediaStats.InstagramStats;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import use_case.change_user_data.InstagramAPIDataAccessInterface;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginUserDataAccessInterface;
import view.LoginView;

import javax.swing.*;
import java.io.IOException;

public class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {}

    public static LoginView create(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            HomepageViewModel homepageViewModel,
            LoginUserDataAccessInterface userDataAccessObject) {

        try {
            LoginController loginController = createLoginUseCase(viewManagerModel, loginViewModel, homepageViewModel, userDataAccessObject);
            return new LoginView(loginViewModel, loginController, viewManagerModel);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Could not open user data file.");
        }

        return null;
    }

    private static LoginController createLoginUseCase(
            ViewManagerModel viewManagerModel,
            LoginViewModel loginViewModel,
            HomepageViewModel homepageViewModel,
            LoginUserDataAccessInterface userDataAccessObject) throws IOException {

        // Notice how we pass this method's parameters to the Presenter.
        LoginOutputBoundary loginOutputBoundary = new LoginPresenter(viewManagerModel, homepageViewModel, loginViewModel);

        UserFactory userFactory = new CommonUserFactory();

        InstagramStats instagramStats = new InstagramStats();
        InstagramAPIDataAccessInterface instagramAPIDataAccessInterface = new InstagramAPIDataAccessObject("", instagramStats);;
        LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary, instagramAPIDataAccessInterface);

        return new LoginController(loginInteractor);
    }
}
