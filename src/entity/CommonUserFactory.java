package entity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class CommonUserFactory implements UserFactory {
    /**
     * Requires: password is valid.
     * @param name
     * @param password
     * @return
     */

    @Override
    public User create(String name, String username, String password, String bio, HashMap<String,String> apiKeys, LocalDateTime ltd) {
        return new CommonUser(name, username, password, bio, apiKeys, ltd);
    }
}
