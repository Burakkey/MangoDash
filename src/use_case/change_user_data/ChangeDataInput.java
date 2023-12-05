package use_case.change_user_data;

/**
 * ChangeInputData contains the required data for a user to change their information
 */
public class ChangeDataInput {
    final private String username;
    final private String name;

    private String bio;
    private String oldPassword;

    private String newPassword;

    private String repeateNewPassword;

    private String facebookAPIToken;

    private String instagramAPIToken;

    /**
     * Creates a new ChangeInputData object used for changing a user's name and/or bio
     * @param username the user's username, from the Username text field
     * @param name
     * @param bio
     */
    public ChangeDataInput(String username, String name, String bio) {
        this.username = username;
        this.name = name;
        this.bio = bio;
    }

    /**
     * Creates a new ChangeInputData object used for changing a user's password
     * @param username
     * @param name
     * @param bio
     * @param oldPassword
     * @param newPassword
     * @param repeatNewPassword
     */
    public ChangeDataInput(String username, String name, String bio, String oldPassword, String newPassword, String repeatNewPassword) {
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repeateNewPassword = repeatNewPassword;

    }

    /**
     * Creates a new ChangeDataInput used for changing a user's Facebook and/or Instagram API token
     * @param username
     * @param name
     * @param facebookAPIToken
     * @param instagramAPIToken
     */
    public ChangeDataInput(String username, String name, String facebookAPIToken, String instagramAPIToken){
        this.username = username;
        this.name = name;
        this.facebookAPIToken = facebookAPIToken;
        this.instagramAPIToken = instagramAPIToken;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public String getRepeateNewPassword() {
        return repeateNewPassword;
    }

    public String getBio() {
        return bio;
    }

    public String getFacebookAPIToken() {
        return facebookAPIToken;
    }

    public String getInstagramAPIToken() {
        return instagramAPIToken;
    }
}
