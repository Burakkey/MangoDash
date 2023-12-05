package use_case.signup;

/**
 * SignupOutputBoundary is an interface defining the contract for presenting signup-related responses
 * and output to the SignupPresenter after successful signups.
 */
public interface SignupOutputBoundary {
    /**
     * the response when the signup is successful (switches the active view)
     * @param user the user output data that is needed for the next active view
     */
    void prepareSuccessView(SignupOutputData user);

    /**
     * the response when the signup is not successful (window with error message)
     * @param error a string specifying why the signup was not successful
     */
    void prepareFailView(String error);
}