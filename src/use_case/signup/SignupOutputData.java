package use_case.signup;

/**
 * SignupOutputData contains the required data after the user has signed up
 */
public class SignupOutputData {

    private final String username;
    private String creationTime;

    private boolean useCaseFailed;

    /**
     * Creates a new SignupOutputData object.
     * @param username the username that the user has chosen, used to display on the login screen after
     *                 successful signup.
     * @param creationTime the time this SignupOutputData was created
     * @param useCaseFailed whether the signup was successful or not
     */
    public SignupOutputData(String username, String creationTime, boolean useCaseFailed) {
        this.username = username;
        this.creationTime = creationTime;
        this.useCaseFailed = useCaseFailed;
    }

    public String getUsername() {
        return username;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

}
