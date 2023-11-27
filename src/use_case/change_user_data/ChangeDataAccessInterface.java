package use_case.change_user_data;

import entity.User;

public interface ChangeDataAccessInterface {
    boolean existsByName(String identifier);

    void modifyUser(String name, String username, String password, String bio);

    void modifyUser(String name, String username, String bio);

    void modifyUserAPI(String username, String facebookAPI, String instagramAPI);

    User get(String username);
}
