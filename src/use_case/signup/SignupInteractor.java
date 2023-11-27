package use_case.signup;

import entity.User;
import entity.UserFactory;

import java.util.HashMap;

public class SignupInteractor implements SignupInputBoundary {
    final SignupUserDataAccessInterface userDataAccessObject;
    final SignupOutputBoundary userPresenter;
    final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("Username already exists.");
        } else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        } else {
            HashMap<String, String> emptyApiKeys = new HashMap<>();
            User user = userFactory.create(signupInputData.getName() ,signupInputData.getUsername(), signupInputData.getPassword(), "", emptyApiKeys, signupInputData.getCreationTime());
            userDataAccessObject.save(user);

            SignupOutputData signupOutputData = new SignupOutputData(user.getName(), signupInputData.getCreationTime().toString(), false);
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }
}