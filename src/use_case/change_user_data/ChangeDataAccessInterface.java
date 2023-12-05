package use_case.change_user_data;

import entity.User;

/**
 * ChangeDataAccessInterface is an interface that provides the methods needed to change a user's info
 */
public interface ChangeDataAccessInterface {
    boolean existsByName(String identifier);

    /**
     * changes the user's password, after inputting the same password twice into the prompt for changing passwords
     * @param name
     * @param username
     * @param password
     * @param bio
     */
    void modifyUser(String name, String username, String password, String bio);

    /**
     * changes the user's name and/or bio
     * @param name
     * @param username
     * @param bio
     */
    void modifyUser(String name, String username, String bio);

    /**
     * changes the user's facebookAPI token and/or instagramAPI token
     * @param username
     * @param facebookAPI
     * @param instagramAPI
     */
    void modifyUserAPI(String username, String facebookAPI, String instagramAPI);

    User get(String username);
}
