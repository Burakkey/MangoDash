package interface_adapter.back_in_signup;

import interface_adapter.ViewManagerModel;
import use_case.Back.BackOutputBoundary;
import use_case.Back.BackOutputData;

public class BackInSignupPresenter implements BackOutputBoundary {
    private ViewManagerModel viewManagerModel;
    public BackInSignupViewModel backInSignupViewModel;
    public BackInSignupPresenter(ViewManagerModel viewManagerModel, BackInSignupViewModel backInSignupViewModel){
        this.viewManagerModel = viewManagerModel;
        this.backInSignupViewModel = backInSignupViewModel;
    }


    @Override
    public void prepareSuccessView(BackOutputData response) {
        // On success, switch to the HomeLogin in view.
        viewManagerModel.setActiveView(backInSignupViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
