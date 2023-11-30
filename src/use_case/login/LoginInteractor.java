package use_case.login;

import entity.User;
import org.json.JSONArray;
import use_case.change_user_data.InstagramAPIDataAccessInterface;

import java.net.MalformedURLException;
import java.util.HashMap;

public class LoginInteractor implements LoginInputBoundary {
    final LoginUserDataAccessInterface userDataAccessObject;
    final LoginOutputBoundary loginPresenter;

    final InstagramAPIDataAccessInterface instagramAPIDataAccessInterface;

//    final InstagramAPIDataAccessInterface facebookAPIDataAccessInterface;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary, InstagramAPIDataAccessInterface instagramAPIDataAccessInterface) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
        this.instagramAPIDataAccessInterface = instagramAPIDataAccessInterface;
//        this.facebookAPIDataAccessInterface = facebookAPIDataAccessInterface;
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
                String facebookApiKey = (apiKeys != null && apiKeys.containsKey("Facebook")) ? apiKeys.get("Facebook") : "";
                String instagramApiKey = (apiKeys != null && apiKeys.containsKey("Instagram")) ? apiKeys.get("Instagram") : "";

                instagramAPIDataAccessInterface.setAPI(instagramApiKey);
                try {
                    instagramAPIDataAccessInterface.fetchData();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

                JSONArray instagramFollowers = instagramAPIDataAccessInterface.getInstagramStats().getFollowers();
                JSONArray instagramPosts = instagramAPIDataAccessInterface.getInstagramStats().getPosts();

                LoginOutputData loginOutputData = new LoginOutputData(user.getName(), user.getUserName(), user.getBio(), facebookApiKey, instagramApiKey, instagramPosts, instagramFollowers, false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}