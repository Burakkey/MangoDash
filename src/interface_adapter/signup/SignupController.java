package interface_adapter.signup;

import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;

import java.time.LocalDateTime;

public class SignupController {

    final SignupInputBoundary userSignupUseCaseInteractor;
    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    public void execute(String name, String username, String password1, String password2) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println(currentDateTime); //TODO: Remove this later @Hisham
        SignupInputData signupInputData = new SignupInputData(
                name, username, password1, password2, currentDateTime);
        userSignupUseCaseInteractor.execute(signupInputData);
    }
}
