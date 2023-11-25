package use_case.change_user_data;

public class ChangeDataInput {
    final private String username;
    final private String name;

    private String bio;
    private String oldPassword;

    private String newPassword;

    private String repeateNewPassword;

    private String facebookAPIToken;

    private String instagramAPIToken;

    public ChangeDataInput(String username, String name, String bio, String instagramAPI) {
        this.username = username;
        this.name = name;
        this.bio = bio;
    }

    public ChangeDataInput(String username, String name, String bio, String oldPassword, String newPassword, String repeatNewPassword) {
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repeateNewPassword = repeatNewPassword;

    }

    public ChangeDataInput(String username, String name, String bio, String facebookAPIToken, String instagramAPIToken){
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
