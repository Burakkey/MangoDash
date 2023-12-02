package use_case.signup;

/**
 * SignupInputBoundary is an interface with methods that detail what to do with the input data
 */
public interface SignupInputBoundary {
    /**
     * execute uses the signupInputData to signup
     * @param signupInputData the data that the user inputs in the SignupView
     */
    void execute(SignupInputData signupInputData);
}
