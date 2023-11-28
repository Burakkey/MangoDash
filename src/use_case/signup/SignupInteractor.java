package use_case.signup;

import entity.User;
import entity.UserFactory;

import java.util.HashMap;

/**
 * SignupInteractor takes the signup info data and uses it to sign a new user up. If there is an error with the input data, the user will not be signed up.
 */
public class SignupInteractor implements SignupInputBoundary {
    final SignupUserDataAccessInterface userDataAccessObject;
    final SignupOutputBoundary userPresenter;
    final UserFactory userFactory;

    /**
     * Creates a new SignupInteractor.
     * @param signupDataAccessInterface interface that defines methods of data access objects related to the action of signing up
     * @param signupOutputBoundary interface that defines methods that describe how output data is transferred to outer layers, related to the action of signing up
     * @param userFactory interface that defines methods of how new users are created when signing up
     */
    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    /**
     * Given the signup input data, decides whether to allow the user to sign up with this data or not:
     * The user will be able to successfully sign up if their chosen Name only contains letters, their chosen Username does not already exist, and the chosen Password in both password fields matches.
     * Otherwise, a window will appear showing the corresponding error.
     * @param signupInputData data structure that contains the data needed to sign a user up
     */
    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.validName(signupInputData.getName())) {
            userPresenter.prepareFailView("Name can only contain letters.");
        } else if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
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