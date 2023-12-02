package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

import java.time.LocalDateTime;

/**
 * Creates a new SignupController
 * */
public class SignupController {

    final SignupInputBoundary userSignupUseCaseInteractor;
    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    /**
     * gives the input data to the signupUseCaseInterator, which decides what to do (how/what to respond)
     * @param name the chosen name in the Name text field
     * @param username the chosen username in the Username text field
     * @param password1 the password in the first Password text field
     * @param password2 the repeat password in the second Password text field
     */
    public void execute(String name, String username, String password1, String password2) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println(currentDateTime);
        SignupInputData signupInputData = new SignupInputData(
                name, username, password1, password2, currentDateTime);
        userSignupUseCaseInteractor.execute(signupInputData);
    }
}
