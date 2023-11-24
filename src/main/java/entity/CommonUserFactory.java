package entity;

import java.time.LocalDateTime;

public class CommonUserFactory implements UserFactory {
    /**
     * Requires: password is valid.
     * @param name
     * @param password
     * @return
     */

    @Override
    public User create(String name, String username, String password, String bio, LocalDateTime ltd) {
        return new CommonUser(name, username, password, bio, ltd);
    }
}
