package use_case.login;

import entity.User;

/**
 * LoginUserDataAccessInterface is an interface that details all the methods that require access to data objects
 * during the login process.
 */
public interface LoginUserDataAccessInterface {
    /**
     * existsByName takes the given String and checks the database to see if that String exists in the database as an existing Username
     * @param identifier the username the user has inputted into login Username text field
     * @return true if the username is in the database, false otherwise
     */
    boolean existsByName(String identifier);

    User get(String username);
}
