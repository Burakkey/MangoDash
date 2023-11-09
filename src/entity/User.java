package entity;

import java.time.LocalDateTime;

public interface User {


    String getName();
    String getUserName();

    String getPassword();

    LocalDateTime getCreationTime();
}
