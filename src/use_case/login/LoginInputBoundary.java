package use_case.login;

/**
 * LoginInputBoundary is an interface with methods that detail what to do with the input data
 */
public interface LoginInputBoundary {
    /**
     * execute uses the loginInputData to login the user
     * @param loginInputData the data that the user inputs in the LoginView
     */
    void execute(LoginInputData loginInputData);
}
