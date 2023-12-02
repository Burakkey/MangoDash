package interface_adapter.login;

import use_case.login.LoginInputData;
import use_case.login.LoginInputBoundary;

/**
 * LoginController passes the data from the LoginView that the user inputted to the loginUseCaseInteractor, which decides what to do with the data
 */
public class LoginController {

    final LoginInputBoundary loginUseCaseInteractor;

    /**
     * Creates a new LoginController
     * @param loginUseCaseInteractor decides what to do with the user's input data from the LoginView
     */
    public LoginController(LoginInputBoundary loginUseCaseInteractor) {
        this.loginUseCaseInteractor = loginUseCaseInteractor;
    }

    /**
     * gives the input data to the loginUseCaseInterator, which decides what to do (how/what to respond)
     * @param username the username in the Username text field
     * @param password the password in the Password text field
     */
    public void execute(String username, String password) {
        LoginInputData loginInputData = new LoginInputData(
                username, password);

        loginUseCaseInteractor.execute(loginInputData);
    }
}
