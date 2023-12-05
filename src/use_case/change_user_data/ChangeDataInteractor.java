package use_case.change_user_data;

import org.json.JSONArray;

import java.net.MalformedURLException;
import java.util.HashMap;
/**
 * ChangeDataInteractor takes the change data input data and uses it to change the user's data. If there is an error with the
 * input data, the data will not change
 */
public class ChangeDataInteractor implements ChangeDataInputBoundary{

    final ChangeDataAccessInterface changeDataAccessInterface;

    final ChangeDataOutputBoundary homepagePresenter;

    final InstagramAPIDataAccessInterface instagramAPIDataAccessInterface;
    final FacebookAPIDataAccessInterface facebookAPIDataAccessInterface;

    /**
     * Creates a new ChangeDataInteractor
     * @param changeDataAccessInterface
     * @param homepagePresenter
     * @param instagramAPIDataAccessInterface
     * @param facebookAPIDataAccessInterface
     */
    public ChangeDataInteractor(ChangeDataAccessInterface changeDataAccessInterface, ChangeDataOutputBoundary homepagePresenter,
                                InstagramAPIDataAccessInterface instagramAPIDataAccessInterface,
                                FacebookAPIDataAccessInterface facebookAPIDataAccessInterface) {
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
                changeDataAccessInterface.modifyUser(username, newName, newPassword, bio);
                ChangeDataOutput changeDataOutput = new ChangeDataOutput(username, newName,bio);
                homepagePresenter.prepareSuccessView(changeDataOutput);
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
        JSONArray instagramFollowers = instagramAPIDataAccessInterface.getInstagramStats().getFollowers();
        JSONArray instagramPosts = instagramAPIDataAccessInterface.getInstagramStats().getPosts();
        JSONArray instagramUsername = instagramAPIDataAccessInterface.getInstagramStats().getUsername();

        JSONArray facebookFollowers = facebookAPIDataAccessInterface.getFacebookStats().getFollowers();
        JSONArray facebookPosts = facebookAPIDataAccessInterface.getFacebookStats().getPosts();
        JSONArray facebookUserName = facebookAPIDataAccessInterface.getFacebookStats().getUsername();

        // Creating a new HashMap
        HashMap<String, Object> instagramData = new HashMap<>();
        HashMap<String, Object> facebookData = new HashMap<>();

        // Adding the JSONArray objects to the HashMap
        instagramData.put("followers", instagramFollowers);
        instagramData.put("posts", instagramPosts);
        instagramData.put("username", instagramUsername);
        instagramData.put("apiKey", instagramAPIToken);
        instagramData.put("keyError", instagramKeyError);

        facebookData.put("followers", facebookFollowers);
        facebookData.put("posts", facebookPosts);
        facebookData.put("username", facebookUserName);
        facebookData.put("apiKey", facebookAPIToken);
        facebookData.put("keyError", facebookKeyError);

        ChangeDataOutput changeDataOutput = new ChangeDataOutput(instagramData, facebookData);
        homepagePresenter.prepareAPIView(changeDataOutput);
    }
}
