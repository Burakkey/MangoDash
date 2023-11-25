package use_case.change_user_data;

public class ChangeDataInteractor implements ChangeDataInputBoundary{

    final ChangeDataAccessInterface changeDataAccessInterface;

    final ChangeDataOutputBoundary homepagePresenter;

    public ChangeDataInteractor(ChangeDataAccessInterface changeDataAccessInterface, ChangeDataOutputBoundary homepagePresenter) {
        this.changeDataAccessInterface = changeDataAccessInterface;
        this.homepagePresenter = homepagePresenter;
    }

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

    @Override
    public void executeAPIChanges(ChangeDataInput changeDataInput) {
        String username = changeDataInput.getUsername();
        String facebookAPIToken = changeDataInput.getFacebookAPIToken();
        String instagramAPIToken = changeDataInput.getInstagramAPIToken();
        changeDataAccessInterface.modifyUserAPI(username, facebookAPIToken, instagramAPIToken);
    }
}
