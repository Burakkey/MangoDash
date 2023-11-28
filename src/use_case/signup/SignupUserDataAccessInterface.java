package use_case.signup;

import entity.User;

/**
 * SignupUserDataAccessInterface is an interface that details all the methods that require access to data objects
 * during the signup process.
 */
public interface SignupUserDataAccessInterface {
    /**
     * existsByName takes the given String and checks the database to see if there is another user that is already using that username.
     * @param identifier the username the user has chosen when signing up
     * @return true if the username is already taken, false if the username is not already taken
     */
    boolean existsByName(String identifier);

    /**
     * save occurs when the sign up is successful; this method puts the User and their info into the database
     * @param user represents the user and their info
     */
    void save(User user);

    /**
     * validName takes the given String and checks if it is a valid name (if it contains only letters).
     * @param name the name that the user inputs in the Name text field
     * @return true if the name is valid, false if the name is invalid.
     */
    boolean validName(String name);

}
