package use_case.signup;

import java.time.LocalDateTime;

public class SignupInputData {

    final private String name;
    final private String username;
    final private String password;
    final private String repeatPassword;

    final private LocalDateTime creationTime;

    public SignupInputData(String name, String username, String password, String repeatPassword, LocalDateTime creationTime) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
        this.creationTime = creationTime;
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

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

}
