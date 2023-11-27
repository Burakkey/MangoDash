package interface_adapter.homepage;

import use_case.change_user_data.ChangeDataInput;
import use_case.change_user_data.ChangeDataInputBoundary;

public class HomepageController {

    private final ChangeDataInputBoundary changeDataInputBoundary;

    public HomepageController(ChangeDataInputBoundary changeDataInputBoundary) {
        this.changeDataInputBoundary = changeDataInputBoundary;
    }

    public void executeSaveChanges(String username, String name, String bio, String oldPassword, String newPassword, String repeatNewPassword) {
        ChangeDataInput changeDataInput = new ChangeDataInput(username, name, bio, oldPassword, newPassword, repeatNewPassword);
        changeDataInputBoundary.executeSaveChanges(changeDataInput);
    }

    public void executeSaveChanges(String username, String name, String bio){
        ChangeDataInput changeDataInput = new ChangeDataInput(username, name, bio);
        changeDataInputBoundary.executeSaveChanges(changeDataInput);

    }

    public void executeAPIChanges(String username, String name, String facebookAPI, String instagramAPI){
        ChangeDataInput changeDataInput = new ChangeDataInput(username, name, facebookAPI, instagramAPI);
        changeDataInputBoundary.executeAPIChanges(changeDataInput);

    }
}