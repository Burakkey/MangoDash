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
                changeDataAccessInterface.modifyUser(newName,username,newPassword);
                ChangeDataOutput changeDataOutput = new ChangeDataOutput(newName,username, bio);
                homepagePresenter.prepareSuccessView(changeDataOutput);
            }


        } else{
            // Make changes to user's name and bio
            changeDataAccessInterface.modifyUser(newName,username);
            ChangeDataOutput changeDataOutput = new ChangeDataOutput(newName, username, bio);
            homepagePresenter.prepareSuccessView(changeDataOutput);
        }
    }

    @Override
    public void executeLogout(ChangeDataInput changeDataInput) {

    }
}
