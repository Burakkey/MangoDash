package use_case.login;

public class LoginOutputData {

    private final String username;

    private final String name;

    private final String bio;
    private boolean useCaseFailed;

    public LoginOutputData(String name, String username, String bio, boolean useCaseFailed) {
        this.name = name;
        this.username = username;
        this.bio = bio;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getName(){
        return name;
    }

    public String getBio(){
        return bio;
    }

}
