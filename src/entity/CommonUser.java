package entity;

import java.time.LocalDateTime;

class CommonUser implements User {

    private String bio;
    private String name;

    private final String username;
    private String password;
    private final LocalDateTime creationTime;

    /**
     * Requires: password is valid.
     * @param name
     * @param password
     */
    CommonUser(String name, String username, String password, String bio, LocalDateTime creationTime) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.creationTime = creationTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String getBio() {
        return bio;
    }
}
