package use_case.signup;

import java.time.LocalDateTime;
/**
 * SignupInputData contains all the required data for a user to sign up.
 */
public class SignupInputData {

    final private String name;
    final private String username;
    final private String password;
    final private String repeatPassword;

    final private LocalDateTime creationTime;

    /**
     * Creates a new SignupInputData object.
     * @param name the chosen name of the user, the String in the Name text field
     * @param username the chosen username of the user, the String in the Username text field
     * @param password the chosen password of the user, the String in the password text field
     * @param repeatPassword the String in the second password text field
     * @param creationTime the time this new SignupInputData object is created
     */
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
