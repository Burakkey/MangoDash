package use_case.change_user_data;

import entity.SocialMediaStats.FacebookStats;
import entity.SocialMediaStats.InstagramStats;
import org.json.JSONArray;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * ChangeDataInteractor takes the change data input data and uses it to change the user's data. If there is an error with the
 * input data, the data will not change
 */
public class ChangeDataInteractor implements ChangeDataInputBoundary{

    final ChangeDataAccessInterface changeDataAccessInterface;

    final ChangeDataOutputBoundary homepagePresenter;

    final APIDataAccessInterface instagramAPIDataAccessInterface;
    final APIDataAccessInterface facebookAPIDataAccessInterface;

    /**
     * Creates a new ChangeDataInteractor
     * @param changeDataAccessInterface
     * @param homepagePresenter
     * @param instagramAPIDataAccessInterface
     * @param facebookAPIDataAccessInterface
     */
    public ChangeDataInteractor(ChangeDataAccessInterface changeDataAccessInterface, ChangeDataOutputBoundary homepagePresenter,
                                APIDataAccessInterface instagramAPIDataAccessInterface,
                                APIDataAccessInterface facebookAPIDataAccessInterface) {
        this.changeDataAccessInterface = changeDataAccessInterface;
        this.homepagePresenter = homepagePresenter;
        this.instagramAPIDataAccessInterface = instagramAPIDataAccessInterface;
        this.facebookAPIDataAccessInterface = facebookAPIDataAccessInterface;
    }

    /**
     * Given the changeDataInput, decide whether to successfully change the user's info, and which info to change.
     * This includes changing password, or changing name and/or bio.
     * @param changeDataInput
     */
    @Override
    public void executeSaveChanges(ChangeDataInput changeDataInput) {
        String username = changeDataInput.getUsername();
        String newName = changeDataInput.getName();
        String oldPassword = changeDataInput.getOldPassword();
        String newPassword = changeDataInput.getNewPassword();
        String repeatNewPassword = changeDataInput.getRepeateNewPassword();
        String bio = changeDataInput.getBio();
        if (changeDataInput.getNewPassword() != null &&
                changeDataInput.getOldPassword() != null &&
                changeDataInput.getRepeateNewPassword() != null) {
            String pwd = changeDataAccessInterface.get(username).getPassword();
            if (!oldPassword.equals(pwd)){
                homepagePresenter.prepareFailView("Incorrect password for " + username + ".");
            }else {
                if (!(repeatNewPassword.equals(newPassword))){
                    homepagePresenter.prepareFailView("New passwords does not match.");
                }
            }


        } else{

            // Make changes to user's name and bio
            changeDataAccessInterface.modifyUser(newName,username, bio);
            ChangeDataOutput changeDataOutput = new ChangeDataOutput(username, newName, bio);
            homepagePresenter.prepareSuccessView(changeDataOutput);
        }
    }


    /**
     * Given the changeDataInput, decide whether to successfully change the user's info.
     * This includes the user's Facebook and/or Instagram API key(s). Then, change the corresponding Social Media data to
     * reflect the change in the API key(s).
     * @param changeDataInput
     */
    @Override
    public void executeAPIChanges(ChangeDataInput changeDataInput) {
        String username = changeDataInput.getUsername();
        String facebookAPIToken = changeDataInput.getFacebookAPIToken();
        String instagramAPIToken = changeDataInput.getInstagramAPIToken();
        changeDataAccessInterface.modifyUserAPI(username, facebookAPIToken, instagramAPIToken);
        instagramAPIDataAccessInterface.setAPI(instagramAPIToken);
        facebookAPIDataAccessInterface.setAPI(facebookAPIToken);
        boolean instagramKeyError = false;
        boolean facebookKeyError = false;
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
        instagramData.put("apiKey", instagramAPIToken);
        instagramData.put("keyError", instagramAPIDataAccessInterface.isApiError());

        // Iterating over Facebook data
        for (String key : facebookStats.keySet()) {
            JSONArray value = facebookStats.get(key);
            facebookData.put(key, value);
        }

        // Adding additional Facebook data
        facebookData.put("apiKey", facebookAPIToken);
        facebookData.put("keyError", facebookAPIDataAccessInterface.isApiError());

        ChangeDataOutput changeDataOutput = new ChangeDataOutput(instagramData, facebookData);
        homepagePresenter.prepareAPIView(changeDataOutput);
    }
}
