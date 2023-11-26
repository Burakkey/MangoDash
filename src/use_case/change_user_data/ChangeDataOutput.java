package use_case.change_user_data;

import org.json.JSONArray;

public class ChangeDataOutput {
    private String username;

    private String name;

    private String bio;

    private JSONArray instagramFollowers;

    private JSONArray posts;

    public ChangeDataOutput(String username, String name, String bio) {
        this.username = username;
        this.name = name;
        this.bio = bio;
    }

    public ChangeDataOutput(JSONArray instagramFollowers, JSONArray posts){
        this.instagramFollowers = instagramFollowers;
        this.posts = posts;
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
