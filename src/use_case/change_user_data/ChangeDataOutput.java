package use_case.change_user_data;

public class ChangeDataOutput {
    private final String username;

    private final String name;

    public ChangeDataOutput(String username, String name) {
        this.username = username;
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }
}
