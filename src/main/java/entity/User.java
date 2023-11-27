package entity;

import org.json.JSONArray;

import java.time.LocalDateTime;

public interface User {


    String getName();
    String getUserName();

    String getPassword();

    LocalDateTime getCreationTime();

    void setPassword(String password);

    void setName(String name);

    void setBio(String bio);

    String getBio();

    void setApiKey(String socialMedia, String apiKey);

}