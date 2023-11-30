package use_case.change_user_data;

import org.json.JSONArray;

public class ChangeDataOutput {
    private String username;

    private String name;

    private String bio;

    private JSONArray instagramFollowers;

    private JSONArray posts;

    private String instagramAPIKey;

    private String facebookAPIKey;

    public ChangeDataOutput(String username, String name, String bio) {
        this.username = username;
        this.name = name;
        this.bio = bio;
    }

    public ChangeDataOutput(JSONArray instagramFollowers, JSONArray posts, String instagramAPIKey, String facebookAPIKey){
        this.instagramFollowers = instagramFollowers;
        this.posts = posts;
        this.instagramAPIKey = instagramAPIKey;
        this.facebookAPIKey = facebookAPIKey;
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
        return posts;
    }

    public String getBio() {
        return bio;
    }
}
