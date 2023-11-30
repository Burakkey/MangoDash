package use_case.login;

import org.json.JSONArray;

public class LoginOutputData {

    private final String username;

    private final String name;

    private final String bio;

    private final String facebookAPI;

    private final String instagramAPI;
    private boolean useCaseFailed;

    private JSONArray instagramPosts;

    private JSONArray instagramFollowers;

    public LoginOutputData(String name, String username, String bio, String facebookAPI, String instagramAPI, JSONArray instagramPosts, JSONArray instagramFollowers, boolean useCaseFailed) {
        this.name = name;
        this.username = username;
        this.bio = bio;
        this.facebookAPI = facebookAPI;
        this.useCaseFailed = useCaseFailed;
        this.instagramAPI = instagramAPI;
        this.instagramPosts = instagramPosts;
        this.instagramFollowers = instagramFollowers;
    }

    public JSONArray getInstagramPosts() {
        return instagramPosts;
    }

    public JSONArray getInstagramFollowers() {
        return instagramFollowers;
    }

    public String getUsername() {
        return username;
    }

    public String getName(){
        return name;
    }

    public String getBio(){
        return bio;
    }

    public String getFacebookAPI() {
        return facebookAPI;
    }

    public String getInstagramAPI() {
        return instagramAPI;
    }
}
