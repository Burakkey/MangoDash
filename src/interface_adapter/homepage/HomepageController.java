package interface_adapter.homepage;

import use_case.change_api_data.ChangeAPIDataInput;
import use_case.change_api_data.ChangeAPIDataInputBoundary;
import use_case.change_user_data.ChangeDataInput;
import use_case.change_user_data.ChangeDataInputBoundary;

import java.util.HashMap;
import java.util.Map;

/**
 * HomepageController is responsible for handling requests related to the homepage.
 * This controller manages incoming requests and processes data.
 */
public class HomepageController {

    private final ChangeDataInputBoundary changeDataInputBoundary;
    private HashMap<String, ChangeAPIDataInputBoundary> apiDataInput;

    public HomepageController(ChangeDataInputBoundary changeDataInputBoundary, HashMap<String, ChangeAPIDataInputBoundary> apiDataInput) {

        this.changeDataInputBoundary = changeDataInputBoundary;
        this.apiDataInput = apiDataInput;
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

    public void executeAPIChanges(String username, String name, HashMap<String, String> apiTokens){

        for (Map.Entry<String, ChangeAPIDataInputBoundary> entry : apiDataInput.entrySet()) {
            String key = entry.getKey();
            ChangeAPIDataInputBoundary apiInteractor = entry.getValue();
            String apiKey = apiTokens.get(key);
            System.out.println(key + apiKey);
            ChangeAPIDataInput apiDataInput = new ChangeAPIDataInput(apiKey, username, name);
            apiInteractor.executeAPIChanges(apiDataInput);
        }

    }

}
