package use_case.change_user_data;

import entity.SocialMediaStats.FacebookStats;
import entity.SocialMediaStats.InstagramStats;
import org.json.JSONArray;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Iterator;

public class ChangeDataInteractor implements ChangeDataInputBoundary{

    final ChangeDataAccessInterface changeDataAccessInterface;

    final ChangeDataOutputBoundary homepagePresenter;

    final InstagramAPIDataAccessInterface instagramAPIDataAccessInterface;
    final FacebookAPIDataAccessInterface facebookAPIDataAccessInterface;

    public ChangeDataInteractor(ChangeDataAccessInterface changeDataAccessInterface, ChangeDataOutputBoundary homepagePresenter,
                                InstagramAPIDataAccessInterface instagramAPIDataAccessInterface,
                                FacebookAPIDataAccessInterface facebookAPIDataAccessInterface) {
        this.changeDataAccessInterface = changeDataAccessInterface;
        this.homepagePresenter = homepagePresenter;
        this.instagramAPIDataAccessInterface = instagramAPIDataAccessInterface;
        this.facebookAPIDataAccessInterface = facebookAPIDataAccessInterface;
    }

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
            // Assumes that repeatPassword and newPassword are same!
            // Validates oldPassword is user's password
            // Changes oldPassword to newPassword
            String pwd = changeDataAccessInterface.get(username).getPassword();
            if (!oldPassword.equals(pwd)){
                homepagePresenter.prepareFailView("Incorrect password for " + username + ".");
            }else {
                if (repeatNewPassword.equals(newPassword)){
                    changeDataAccessInterface.modifyUser(username, newName, newPassword, bio);
                    ChangeDataOutput changeDataOutput = new ChangeDataOutput(username, newName,bio);
                    homepagePresenter.prepareSuccessView(changeDataOutput);
                }
                else{
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

    @Override
    public void executeLogout(ChangeDataInput changeDataInput) {

    }

    @Override
    public void executeAPIChanges(ChangeDataInput changeDataInput) {
        String username = changeDataInput.getUsername();
        String facebookAPIToken = changeDataInput.getFacebookAPIToken();
        String instagramAPIToken = changeDataInput.getInstagramAPIToken();
        changeDataAccessInterface.modifyUserAPI(username, facebookAPIToken, instagramAPIToken);
        instagramAPIDataAccessInterface.setAPI(instagramAPIToken);
        facebookAPIDataAccessInterface.setApi(facebookAPIToken);
        boolean instagramKeyError = false;
        boolean facebookKeyError = false;
        try {
            instagramAPIDataAccessInterface.fetchData();
            facebookAPIDataAccessInterface.fetchData();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        if (instagramAPIDataAccessInterface.isApiError()){
            instagramKeyError = true;
        }
        if (facebookAPIDataAccessInterface.isApiError()){
            facebookKeyError = true;
        }


        HashMap<String, JSONArray> instagramStats = instagramAPIDataAccessInterface.getInstagramStats().getStats();
        HashMap<String, JSONArray> facebookStats = facebookAPIDataAccessInterface.getFacebookStats().getStats();

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
        instagramData.put("keyError", instagramKeyError);

        // Iterating over Facebook data
        for (String key : facebookStats.keySet()) {
            JSONArray value = facebookStats.get(key);
            facebookData.put(key, value);
        }

        // Adding additional Facebook data
        facebookData.put("apiKey", facebookAPIToken);
        facebookData.put("keyError", facebookKeyError);

        ChangeDataOutput changeDataOutput = new ChangeDataOutput(instagramData, facebookData);
        homepagePresenter.prepareAPIView(changeDataOutput);
    }
}
