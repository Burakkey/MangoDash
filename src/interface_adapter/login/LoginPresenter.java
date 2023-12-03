package interface_adapter.login;

import interface_adapter.homepage.HomepagePresenter;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.ViewManagerModel;

import org.json.JSONArray;
import org.json.JSONObject;
import use_case.change_user_data.ChangeDataOutput;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


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
    public void prepareSuccessView(LoginOutputData loginOutputData) {
        // On success, switch to the logged in view.
        HomepageState homepageState = new HomepageState();
        homepageState.setName(loginOutputData.getName());
        homepageState.setUsername(loginOutputData.getUsername());
        homepageState.setBio(loginOutputData.getBio());
        homepageState.setFacebookToken((String) loginOutputData.getFacebookData().get("apiKey"));
        homepageState.setFacebookStatsHashMap(HomepagePresenter.makeFacebookStatsHashmap(loginOutputData));
        homepageState.setInstagramToken((String) loginOutputData.getInstagramData().get("apiKey"));
        homepageState.setInstagramStatsHashMap(HomepagePresenter.makeInstagramStatsHashmap(loginOutputData));
        this.homepageViewModel.setState(homepageState);
        this.viewManagerModel.setActiveView(homepageViewModel.getViewName());
        this.loginViewModel.setState(new LoginState());
        this.loginViewModel.firePropertyChanged();
        this.homepageViewModel.firePropertyChanged();
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setError((error));
        loginViewModel.firePropertyChanged();
    }
}
