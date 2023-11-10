package use_case.login;

public class LoginOutputData {

    private final String username;

    private final String name;
    private boolean useCaseFailed;

    public LoginOutputData(String name, String username, boolean useCaseFailed) {
        this.name = name;
        this.username = username;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getName(){
        return name;
    }

}
