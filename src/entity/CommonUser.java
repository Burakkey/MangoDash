package entity;

import entity.SocialMediaStats.InstagramStats;
import entity.SocialMediaStats.SocialMediaStats;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * CommonUser represents a common user entity in this application, and has variables representing the information a common user would have
 */
class CommonUser implements User {

    private String bio;
    private String name;

    private final String username;
    private String password;
    private final LocalDateTime creationTime;
    private HashMap<String, String> apiKeys;

    /**
     * Constructs a CommonUser object with the provided information:
     * @param name the user's name
     * @param username the user's username
     * @param password the user's password
     * @param bio the user's bio
     * @param apiKeys the user's API keys
     * @param creationTime the time this new user was created
     */
    CommonUser(String name, String username, String password, String bio, HashMap<String, String> apiKeys, LocalDateTime creationTime) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.creationTime = creationTime;
        this.apiKeys = apiKeys;
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

    @Override
    public void setApiKeys(String socialMedia, String apiKey) {
        if (apiKeys == null) {
            apiKeys = new HashMap<>();
        }
        apiKeys.put(socialMedia, apiKey);
    }

    @Override
    public HashMap<String,String> getApiKeys() {
        return apiKeys;
    }



    public static void main(String[] args) {
//        CommonUser commonUser = new CommonUser("Richard", "RickShaltz", "111", "empty", null);
//        commonUser.setApiKey("Instagram","EAAMw2YKsBFwBO8e6FdON200XZBSg6PH6pq4ZCmxsGHkkZCZBCckZCnaSmJQczJxPCh0q0aNZBwATZAsmddRQrYmC0q8rZC62vCl4M53oxKOjfrbRrpGKSN4WdWajZCQmiIlDcYTJxA4pajiFxiWGPdFzfHMRu3LJDsBom9lboVDQJMexHf3TQuZAYWkPsc4oEaMDTkPJp1LfFSmKiSmTrZB2ZCXdkPIZD");
    }
}
