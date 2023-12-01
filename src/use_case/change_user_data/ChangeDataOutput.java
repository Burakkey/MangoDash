package use_case.change_user_data;

import org.json.JSONArray;

public class ChangeDataOutput {
    private String username;

    private String name;

    private String bio;

    private JSONArray instagramFollowers;

    private JSONArray instagramPosts;

    private String instagramAPIKey;

    private String facebookAPIKey;

    private Boolean facebookKeyError;

    private Boolean instagramKeyError;

    public ChangeDataOutput(String username, String name, String bio) {
        this.username = username;
        this.name = name;
        this.bio = bio;
        this.instagramKeyError = false;
        this.facebookKeyError = false;
    }

    public ChangeDataOutput(JSONArray instagramFollowers, JSONArray instagramPosts, String instagramAPIKey, String facebookAPIKey, Boolean facebookKeyError, Boolean instagramKeyError){
        this.instagramFollowers = instagramFollowers;
        this.instagramPosts = instagramPosts;
        this.instagramAPIKey = instagramAPIKey;
        this.facebookAPIKey = facebookAPIKey;
        this.facebookKeyError = facebookKeyError;
        this.instagramKeyError = instagramKeyError;
    }

    public JSONArray getInstagramPosts() {
        return instagramPosts;
    }

    public Boolean getFacebookKeyError() {
        return facebookKeyError;
    }

    public Boolean getInstagramKeyError() {
        return instagramKeyError;
    }

    public String getInstagramAPIKey() {
        return instagramAPIKey;
    }

    public String getFacebookAPIKey() {
        return facebookAPIKey;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public JSONArray getInstagramFollowers() {
        return instagramFollowers;
    }

    public JSONArray getPosts() {
        return instagramPosts;
    }

    public String getBio() {
        return bio;
    }
}
