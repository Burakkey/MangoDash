package interface_adapter.homepage;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginViewModel;
import use_case.change_user_data.ChangeDataOutput;
import use_case.change_user_data.ChangeDataOutputBoundary;

public class HomepagePresenter implements ChangeDataOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final HomepageViewModel homepageViewModel;
    private ViewManagerModel viewManagerModel;

    public HomepagePresenter(LoginViewModel loginViewModel, HomepageViewModel homepageViewModel,
                             ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.homepageViewModel = homepageViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareFailView(String s) {
        // On success, switch to the logged in view.
    }

    @Override
    public void prepareSuccessView(ChangeDataOutput changeDataOutput) {
        // On success, switch to the logged in view.
        HomepageState homepageState = new HomepageState();
        homepageState.setName(changeDataOutput.getName());
        homepageState.setUsername(changeDataOutput.getUsername());
        this.homepageViewModel.setState(homepageState);
        this.viewManagerModel.setActiveView(homepageViewModel.getViewName());
        this.homepageViewModel.firePropertyChanged();
        this.viewManagerModel.firePropertyChanged();
    }
}
