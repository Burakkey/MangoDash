package entity;

import java.time.LocalDateTime;
import java.util.List;

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

    List<String> getApiKey();


}
