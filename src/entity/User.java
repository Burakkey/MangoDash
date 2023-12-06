package entity;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * Represents a user in this application.
 * This interface defines the common methods that a user entity has
 */
public interface User {


    String getName();
    String getUserName();

    String getPassword();

    LocalDateTime getCreationTime();

    void setPassword(String password);

    void setName(String name);

    void setBio(String bio);

    String getBio();

    void setApiKeys(String socialMedia, String apiKey);

    HashMap<String,String> getApiKeys();


}
