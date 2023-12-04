package interface_adapter.homepage;

import use_case.change_user_data.ChangeDataInput;
import use_case.change_user_data.ChangeDataInputBoundary;

/**
 * HomepageController is responsible for handling requests related to the homepage.
 * This controller manages incoming requests and processes data.
 */
public class HomepageController {

    private final ChangeDataInputBoundary changeDataInputBoundary;

    /**
     * Creates a HomepageController object with the input information from the HomepageView / panels on the Homepage
     * @param changeDataInputBoundary
     */
    public HomepageController(ChangeDataInputBoundary changeDataInputBoundary) {
        this.changeDataInputBoundary = changeDataInputBoundary;
    }

    /**
     * Execute changes to the user's password
     * @param username the user's username
     * @param name the user's name
     * @param bio the user's bio
     * @param oldPassword the user's old password
     * @param newPassword the user's new password (the password they want to change to)
     * @param repeatNewPassword the password in the second password text field
     */
    public void executeSaveChanges(String username, String name, String bio, String oldPassword, String newPassword, String repeatNewPassword) {
        ChangeDataInput changeDataInput = new ChangeDataInput(username, name, bio, oldPassword, newPassword, repeatNewPassword);
        changeDataInputBoundary.executeSaveChanges(changeDataInput);
    }

    /**
     * Executes changes to the user's name and/or bio
     * @param username the user's username
     * @param name the user's name
     * @param bio the user's bio
     */
    public void executeSaveChanges(String username, String name, String bio){
        ChangeDataInput changeDataInput = new ChangeDataInput(username, name, bio);
        changeDataInputBoundary.executeSaveChanges(changeDataInput);

    }

    /**
     * Executes changes to the user's API keys
     * @param username the user's username
     * @param name the user's name
     * @param facebookAPI the user's Facebook API in the Facebook API text field
     * @param instagramAPI the user's Instagram API in the Instagram API text field
     */
    public void executeAPIChanges(String username, String name, String facebookAPI, String instagramAPI){
        ChangeDataInput changeDataInput = new ChangeDataInput(username, name, facebookAPI, instagramAPI);
        changeDataInputBoundary.executeAPIChanges(changeDataInput);

    }

}
