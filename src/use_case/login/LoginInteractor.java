package use_case.login;

import entity.User;

import java.util.HashMap;

public class LoginInteractor implements LoginInputBoundary {
    final LoginUserDataAccessInterface userDataAccessObject;
    final LoginOutputBoundary loginPresenter;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        String username = loginInputData.getUsername();
        String password = loginInputData.getPassword();
        if (!userDataAccessObject.existsByName(username)) {
            loginPresenter.prepareFailView(username + ": Account does not exist.");
        } else {
            String pwd = userDataAccessObject.get(username).getPassword();
            if (!password.equals(pwd)) {
                loginPresenter.prepareFailView("Incorrect password for " + username + ".");
            } else {

                User user = userDataAccessObject.get(loginInputData.getUsername());
                HashMap<String, String> apiKeys = user.getApiKeys();
                String facebookApiKey = (apiKeys != null && apiKeys.containsKey("Facebook")) ? apiKeys.get("Facebook") : " ";
                String instagramApiKey = (apiKeys != null && apiKeys.containsKey("Instagram")) ? apiKeys.get("Instagram") : " ";



                LoginOutputData loginOutputData = new LoginOutputData(user.getName(), user.getUserName(), user.getBio(), facebookApiKey, instagramApiKey,false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}