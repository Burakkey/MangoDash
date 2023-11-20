package interface_adapter.login;

import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.ViewManagerModel;

import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;


public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final HomepageViewModel homepageViewModel;
    private ViewManagerModel viewManagerModel;

    public LoginPresenter(ViewManagerModel viewManagerModel,
                          HomepageViewModel homepageViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homepageViewModel = homepageViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(LoginOutputData response) {
        // On success, switch to the logged in view.
        HomepageState homepageState = new HomepageState();
        homepageState.setName(response.getName());
        homepageState.setUsername(response.getUsername());
        homepageState.setBio(response.getBio());
        this.homepageViewModel.setState(homepageState);
        this.viewManagerModel.setActiveView(homepageViewModel.getViewName());
        this.homepageViewModel.firePropertyChanged();
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setError((error));
//        loginState.setUsernameError(error);
        loginViewModel.firePropertyChanged();
    }
}
