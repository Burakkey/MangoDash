package use_case.login;

/**
 * LoginOutputBoundary is an interface with methods that detail how the program will respond to input data
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