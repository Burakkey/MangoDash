package use_case.signup;

public class SignupInputData {

    final private String name;
    final private String username;
    final private String password;
    final private String repeatPassword;

    public SignupInputData(String name, String username, String password, String repeatPassword) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }

    String getName(){
        return name;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
}
