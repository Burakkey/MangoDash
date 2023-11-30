package use_case.signup;

/**
 * SignupOutputBoundary is an interface with methods that detail how the program will respond to the input data
 */
public interface SignupOutputBoundary {
    void prepareSuccessView(SignupOutputData user);

    void prepareFailView(String error);
}