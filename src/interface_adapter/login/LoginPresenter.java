package interface_adapter.login;

import interface_adapter.homepage.HomepagePresenter;
import interface_adapter.homepage.HomepageState;
import interface_adapter.homepage.HomepageViewModel;
import interface_adapter.ViewManagerModel;

import use_case.DataGetter;
import use_case.change_api_data.instagram.ChangeInstagramDataOutput;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;
/**
 * LoginPresenter takes output data and formats it into an appropriate type that the view can immediately use,
 * and puts it into the LoginViewModel.
 */
public class LoginPresenter implements LoginOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final HomepageViewModel homepageViewModel;
    private ViewManagerModel viewManagerModel;

    /**
     * Creates a new LoginPresenter
     * @param viewManagerModel contains all the different views, changes the active view when needed (in response to user)
     * @param homepageViewModel data structure that contains the data in the appropriate format for the HomepageView to display
     * @param loginViewModel data structure that contains the data in the appropriate format for the LoginView to display
     */
    public LoginPresenter(ViewManagerModel viewManagerModel,
                          HomepageViewModel homepageViewModel,
                          LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.homepageViewModel = homepageViewModel;
        this.loginViewModel = loginViewModel;
    }

    /**
     * If the input data does not have any errors, then the login is successful and prepareSuccessView is called.
     * prepareSuccessView switches to the homepage view, with the all the user's data retained and filled in where needed.
     * @param loginOutputData the output data that is needed for the homepage
     */
    @Override
    public void prepareSuccessView(LoginOutputData loginOutputData) {
        // On success, switch to the logged in view.
        HomepageState homepageState = new HomepageState();
        homepageState.setName(loginOutputData.getName());
        homepageState.setUsername(loginOutputData.getUsername());
        homepageState.setBio(loginOutputData.getBio());
        homepageState.setFacebookToken((String) loginOutputData.getFacebookData().get("apiKey"));

        DataGetter instagramGetter = new ChangeInstagramDataOutput(loginOutputData.getInstagramData());
        DataGetter facebookGetter = new ChangeInstagramDataOutput(loginOutputData.getFacebookData());

        homepageState.setFacebookToken((String) loginOutputData.getFacebookData().get("apiKey"));
        homepageState.setFacebookStatsHashMap(HomepagePresenter.makeFacebookStatsHashmap(facebookGetter));

        homepageState.setInstagramToken((String) loginOutputData.getInstagramData().get("apiKey"));
        homepageState.setInstagramStatsHashMap(HomepagePresenter.makeInstagramStatsHashmap(instagramGetter));
        this.homepageViewModel.setState(homepageState);
        this.viewManagerModel.setActiveView(homepageViewModel.getViewName());
        this.loginViewModel.setState(new LoginState());
        this.loginViewModel.firePropertyChanged();
        this.homepageViewModel.firePropertyChanged();
        this.viewManagerModel.firePropertyChanged();
    }

    /**
     * If the input data has errors, then the login is unsuccessful and prepareFailView is called.
     * prepareFailView lets loginState know that there is an error, and provides the correct error message.
     * Then this method lets loginViewModel know that a property has changed, so the error window appears on the view.
     * @param error a String containing the case-specific error message to be shown to the user
     */
    @Override
    public void prepareFailView(String error) {
        LoginState loginState = loginViewModel.getState();
        loginState.setError((error));
        loginViewModel.firePropertyChanged();
    }
}
