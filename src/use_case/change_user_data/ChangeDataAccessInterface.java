package use_case.change_user_data;

import entity.User;

public interface ChangeDataAccessInterface {
    boolean existsByName(String identifier);

    void modifyUser(String name, String username, String password);

    void modifyUser(String name, String username);

    User get(String username);
}
