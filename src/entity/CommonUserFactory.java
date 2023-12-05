package entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Factory class responsible for creating instances of CommonUser.
 */
public class CommonUserFactory implements UserFactory {

    /**
     * Creates a new CommonUser with the provided information
     * Requires: password is valid.
     * @param name the user's name
     * @param username the user's username
     * @param password the user's password
     * @param bio the user's bio
     * @param apiKeys the user's API keys
     * @param ltd the time this new user was created
     */
    @Override
    public User create(String name, String username, String password, String bio, HashMap<String,String> apiKeys, LocalDateTime ltd) {
        return new CommonUser(name, username, password, bio, apiKeys, ltd);
    }
}
