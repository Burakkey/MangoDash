package use_case.login;

import entity.User;
import org.json.JSONArray;
import use_case.change_user_data.APIDataAccessInterface;

import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * LoginInteractor takes the login info data and uses it to log the user in. If there is an error with the
 * input data, the user will not be logged in.
 */
public class LoginInteractor implements LoginInputBoundary {
    final LoginUserDataAccessInterface userDataAccessObject;
    final LoginOutputBoundary loginPresenter;

    final APIDataAccessInterface instagramAPIDataAccessInterface;

    final APIDataAccessInterface facebookAPIDataAccessInterface;

    /**
     * Creates a new LoginInteractor.
     * @param userDataAccessInterface interface that defines methods of data access objects related to the action of logging in
     * @param loginOutputBoundary interface that defines methods that describe how output data is transferred to outer
     *                           layers (presenter), related to the action of logging up
     */
    public LoginInteractor(LoginUserDataAccessInterface userDataAccessInterface,
                           LoginOutputBoundary loginOutputBoundary,
                           APIDataAccessInterface instagramAPIDataAccessInterface,
                           APIDataAccessInterface facebookAPIDataAccessInterface) {
        this.userDataAccessObject = userDataAccessInterface;
        this.loginPresenter = loginOutputBoundary;
        this.instagramAPIDataAccessInterface = instagramAPIDataAccessInterface;
        this.facebookAPIDataAccessInterface = facebookAPIDataAccessInterface;
    }

    /**
     * Given the login input data, decides whether to log the user in.
     * The user will be able to successfully log up if the password corresponds to the username
     * Otherwise, a window will appear showing the corresponding error.
     * @param loginInputData data structure that contains the data needed to log a user in
     */
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

                facebookAPIDataAccessInterface.setAPI(facebookApiKey);
                instagramAPIDataAccessInterface.setAPI(instagramApiKey);


                try {
                    instagramAPIDataAccessInterface.fetchData();
                    facebookAPIDataAccessInterface.fetchData();
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }

                HashMap<String, JSONArray> instagramStats = instagramAPIDataAccessInterface.getStats().getStats();
                HashMap<String, JSONArray> facebookStats = facebookAPIDataAccessInterface.getStats().getStats();

                // Creating new HashMaps
                HashMap<String, Object> instagramData = new HashMap<>();
                HashMap<String, Object> facebookData = new HashMap<>();

                // Iterating over Instagram data
                for (String key : instagramStats.keySet()) {
                    JSONArray value = instagramStats.get(key);
                    instagramData.put(key, value);
                }

                // Adding additional Instagram data
                instagramData.put("apiKey", instagramApiKey);

                // Iterating over Facebook data
                for (String key : facebookStats.keySet()) {
                    JSONArray value = facebookStats.get(key);
                    facebookData.put(key, value);
                }

                // Adding additional Facebook data
                facebookData.put("apiKey", facebookApiKey);

                LoginOutputData loginOutputData = new LoginOutputData(user.getName(), user.getUserName(), user.getBio(), instagramData, facebookData, false);
                loginPresenter.prepareSuccessView(loginOutputData);
            }
        }
    }
}