package use_case.login;

/**
 * LoginInputData contains the required data for a user to sign up.
 */
public class LoginInputData {

    final private String username;
    final private String password;

    /**
     * Creates a new LoginInputData object.
     * @param username the user's username, from the Username text field
     * @param password the user's corresponding password, from the Password text field
     */
    public LoginInputData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    String getUsername() {
        return username;
    }

    String getPassword() {
        return password;
    }

}
