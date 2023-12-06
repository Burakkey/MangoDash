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
}
