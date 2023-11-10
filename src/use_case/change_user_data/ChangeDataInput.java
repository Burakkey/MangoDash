package use_case.change_user_data;

public class ChangeDataInput {
    final private String username;
    final private String name;

    final private String bio;
    private String oldPassword;

    private String newPassword;

    private String repeateNewPassword;

    public ChangeDataInput(String username, String name, String bio) {
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
}
