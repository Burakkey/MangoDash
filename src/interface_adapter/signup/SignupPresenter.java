package interface_adapter.signup;

import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.ViewManagerModel;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * SignupPresenter takes output data and formats it into an appropriate type that the view can immediately use,
 * and puts it into the signupViewModel.
 */
public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final LoginViewModel loginViewModel;
    private ViewManagerModel viewManagerModel;

    /**
     * Creates a new SignupPresenter object.
     * @param viewManagerModel contains all the different views, changes the active view when needed
     *                               (in response to user)
     * @param signupViewModel data structure that contains the data in the appropriate format for the
     *                        signupView to display
     * @param loginViewModel data structure that contains the data in the appropriate format for the
     *                       loginView to display
     */
    public SignupPresenter(ViewManagerModel viewManagerModel,
                           SignupViewModel signupViewModel,
                           LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.signupViewModel = signupViewModel;
        this.loginViewModel = loginViewModel;
    }

    /**
     * If the input data does not have any errors, then the signup is successful and prepareSuccessView is called prepareSuccessView switches to the login view, with the username text field already filled in with the username the user chose during signup.
     * @param response the output data that is required after the user has signed up
     */
    @Override
    public void prepareSuccessView(SignupOutputData response) {
        // On success, switch to the login view.
        LocalDateTime responseTime = LocalDateTime.parse(response.getCreationTime());
        response.setCreationTime(responseTime.format(DateTimeFormatter.ofPattern("hh:mm:ss")));

        LoginState loginState = loginViewModel.getState();
        loginState.setUsername(response.getUsername());
        this.loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();

        viewManagerModel.setActiveView(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * If the input data does have errors, then the signup is unsuccessful and prepareFailView is called.
     * prepareFailView lets signupState know that there is an error, and provides the correct error message.
     * Then this method lets signupViewModel that a property has changed, so the error window appears on the view.
     * @param error a String containing the error message to be shown to the user
     */
    @Override
    public void prepareFailView(String error) {
        SignupState signupState = signupViewModel.getState();
        signupState.setError(error);
        signupViewModel.firePropertyChanged();
    }
}
