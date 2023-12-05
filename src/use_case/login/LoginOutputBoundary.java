package use_case.login;

/**
 * LoginOutputBoundary is an interface defining the contract for presenting login-related responses
 * and output to the LoginPresenter after successful logins.
 */
public interface LoginOutputBoundary {
    /**
     * the response when the login is successful (switches the active view)
     * @param user the user output data that is needed for the next active view
     */
    void prepareSuccessView(LoginOutputData user);

    /**
     * the response when the login is not successful (window with error message)
     * @param error a string specifying why the login was not successful
     */
    void prepareFailView(String error);
}