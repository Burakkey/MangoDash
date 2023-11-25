package entity;

import entity.SocialMediaStats.InstagramStats;
import entity.SocialMediaStats.SocialMediaStats;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;

class CommonUser implements User {

    private String bio;
    private String name;

    private final String username;
    private String password;
    private final LocalDateTime creationTime;
    private HashMap<String, SocialMediaStats> socialMedias;

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

        this.socialMedias = new HashMap<>();
        socialMedias.put("Instagram", new InstagramStats());
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
    public void setApiKey(String socialMedia, String apiKey) {
        if (socialMedias.containsKey(socialMedia)) {
            socialMedias.get(socialMedia).setApiKey(apiKey);
        }
    }

    public static void main(String[] args) {
//        CommonUser commonUser = new CommonUser("Richard", "RickShaltz", "111", "empty", null);
//        commonUser.setApiKey("Instagram","EAAMw2YKsBFwBO8e6FdON200XZBSg6PH6pq4ZCmxsGHkkZCZBCckZCnaSmJQczJxPCh0q0aNZBwATZAsmddRQrYmC0q8rZC62vCl4M53oxKOjfrbRrpGKSN4WdWajZCQmiIlDcYTJxA4pajiFxiWGPdFzfHMRu3LJDsBom9lboVDQJMexHf3TQuZAYWkPsc4oEaMDTkPJp1LfFSmKiSmTrZB2ZCXdkPIZD");
    }
}
