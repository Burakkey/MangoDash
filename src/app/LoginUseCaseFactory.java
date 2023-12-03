package app;

import data_access.FacebookAPIDataAccessObject;
import data_access.InstagramAPIDataAccessObject;
import entity.CommonUserFactory;
import entity.SocialMediaStats.FacebookStats;
import entity.SocialMediaStats.InstagramStats;
import entity.UserFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import use_case.change_user_data.FacebookAPIDataAccessInterface;
import use_case.change_user_data.InstagramAPIDataAccessInterface;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginInteractor;
import use_case.login.LoginUserDataAccessInterface;
import view.LoginView;

import javax.swing.*;
import java.io.IOException;

/**
 * LoginUseCaseFactory is only called in main, it creates a new LoginView
 */
public class LoginUseCaseFactory {

    /** Prevent instantiation. */
    private LoginUseCaseFactory() {}

    /**
     * create creates a LoginView and connects the different parameters to the LoginView, so that the LoginView can
     * communicate with the other classes necessary for logging a user in.
     * @param viewManagerModel
     * @param loginViewModel
     * @param homepageViewModel
     * @param userDataAccessObject
     * @return a new LoginView that can communicate with the necessary classes to log a user in
     */
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
        FacebookStats facebookStats = new FacebookStats();
        InstagramAPIDataAccessInterface instagramAPIDataAccessInterface = new InstagramAPIDataAccessObject("", instagramStats);
        FacebookAPIDataAccessInterface facebookAPIDataAccessInterface = new FacebookAPIDataAccessObject("", facebookStats);
        LoginInputBoundary loginInteractor = new LoginInteractor(
                userDataAccessObject, loginOutputBoundary, instagramAPIDataAccessInterface, facebookAPIDataAccessInterface);

        return new LoginController(loginInteractor);
    }
}
