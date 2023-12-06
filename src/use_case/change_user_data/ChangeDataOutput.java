package use_case.change_user_data;

public class ChangeDataOutput {
    private String username;

    private String name;

    private String bio;


    public ChangeDataOutput(String username, String name, String bio) {
        this.username = username;
        this.name = name;
        this.bio = bio;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }
}
