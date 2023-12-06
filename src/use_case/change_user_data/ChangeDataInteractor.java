package use_case.change_user_data;

import data_access.APIDataAccessInterface;
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

    public ChangeDataInteractor(ChangeDataAccessInterface changeDataAccessInterface, ChangeDataOutputBoundary homepagePresenter) {

        this.changeDataAccessInterface = changeDataAccessInterface;
        this.homepagePresenter = homepagePresenter;
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
                } else {
                    changeDataAccessInterface.modifyUser(newName, username, newPassword, bio);
                    homepagePresenter.prepareFailView("Password Changed Successfully!");
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
}
