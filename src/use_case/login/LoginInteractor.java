package use_case.login;

import entity.User;
import org.json.JSONArray;
import use_case.change_user_data.FacebookAPIDataAccessInterface;
import use_case.change_user_data.InstagramAPIDataAccessInterface;

import java.net.MalformedURLException;
import java.util.HashMap;

public class LoginInteractor implements LoginInputBoundary {
    final LoginUserDataAccessInterface userDataAccessObject;
    final LoginOutputBoundary loginPresenter;

    final InstagramAPIDataAccessInterface instagramAPIDataAccessInterface;

    final FacebookAPIDataAccessInterface facebookAPIDataAccessInterface;

    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary,
                           InstagramAPIDataAccessInterface instagramAPIDataAccessInterface,
                           FacebookAPIDataAccessInterface facebookAPIDataAccessInterface) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
        this.instagramAPIDataAccessInterface = instagramAPIDataAccessInterface;
        this.facebookAPIDataAccessInterface = facebookAPIDataAccessInterface;
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
                facebookAPIDataAccessInterface.setApi(facebookApiKey);
                try {
                    instagramAPIDataAccessInterface.fetchData();
                    facebookAPIDataAccessInterface.fetchData();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

                JSONArray instagramFollowers = instagramAPIDataAccessInterface.getInstagramStats().getFollowers();
                JSONArray instagramPosts = instagramAPIDataAccessInterface.getInstagramStats().getPosts();
                JSONArray instagramUsername = instagramAPIDataAccessInterface.getInstagramStats().getUsername();

                JSONArray facebookFollowers = facebookAPIDataAccessInterface.getFacebookStats().getFollowers();
                JSONArray facebookPosts = facebookAPIDataAccessInterface.getFacebookStats().getPosts();
                JSONArray facebookUsername = facebookAPIDataAccessInterface.getFacebookStats().getUsername();

                // Creating a new HashMap
                HashMap<String, Object> instagramData = new HashMap<>();
                HashMap<String, Object> facebookData = new HashMap<>();

                // Adding the JSONArray objects to the HashMap
                instagramData.put("followers", instagramFollowers);
                instagramData.put("posts", instagramPosts);
                instagramData.put("username", instagramUsername);
                instagramData.put("apiKey", instagramApiKey);

                facebookData.put("followers", facebookFollowers);
                facebookData.put("posts", facebookPosts);
                facebookData.put("username", facebookUsername);
                facebookData.put("apiKey", facebookApiKey);

                LoginOutputData loginOutputData = new LoginOutputData(user.getName(), user.getUserName(), user.getBio(), instagramData, facebookData, false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}