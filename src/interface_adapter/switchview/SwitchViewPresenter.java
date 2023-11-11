package interface_adapter.switchview;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import use_case.switchView.SwitchViewOutputBoundary;

public class SwitchViewPresenter implements SwitchViewOutputBoundary {

    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public SwitchViewPresenter(LoginViewModel loginViewModel, ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareLogoutView() {
        System.out.println("Got here");
        viewManagerModel.setActiveView(loginViewModel.getViewName());
        LoginState currentState = loginViewModel.getState();
        currentState.setUsername("");  //removes username from being filled
        currentState.setPassword("");  //removes password from being filled
        loginViewModel.setState(currentState);
        this.loginViewModel.firePropertyChanged();
        this.viewManagerModel.firePropertyChanged();

    }

}



