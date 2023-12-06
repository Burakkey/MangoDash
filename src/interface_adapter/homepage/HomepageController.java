package interface_adapter.homepage;

import use_case.change_api_data.ChangeAPIDataInput;
import use_case.change_api_data.ChangeAPIDataInputBoundary;
import use_case.change_user_data.ChangeDataInput;
import use_case.change_user_data.ChangeDataInputBoundary;

import java.util.HashMap;
import java.util.Map;

/**
 * HomepageController passes the data from the 
 * LoginView that the user inputted to the loginUseCaseInteractor, which decides what to do with the data
 */
public class HomepageController {

    private final ChangeDataInputBoundary changeDataInputBoundary;
    private HashMap<String, ChangeAPIDataInputBoundary> apiDataInput;

    public HomepageController(ChangeDataInputBoundary changeDataInputBoundary, HashMap<String, ChangeAPIDataInputBoundary> apiDataInput) {
        this.changeDataInputBoundary = changeDataInputBoundary;
        this.apiDataInput = apiDataInput;
    }

    public void executeSaveChanges(String username, String name, String bio, String oldPassword, String newPassword, String repeatNewPassword) {
        ChangeDataInput changeDataInput = new ChangeDataInput(username, name, bio, oldPassword, newPassword, repeatNewPassword);
        changeDataInputBoundary.executeSaveChanges(changeDataInput);
    }

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
//    public void executeInstagrmGraphing(String username, String name, String facebookAPI, String instagramAPI){
//        ChangeDataInput changeDataInput = new ChangeDataInput(username, name, facebookAPI, instagramAPI);
//        changeDataInputBoundary.executeAPIChanges(changeDataInput);
//
//    }

}
