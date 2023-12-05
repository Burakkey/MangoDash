package entity;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Interface representing a factory for creating Users
 */
public interface UserFactory {
    /** Requires: password is valid. */
    User create(String name, String username, String password, String bio, HashMap<String, String> apiKeys, LocalDateTime ltd);
}
