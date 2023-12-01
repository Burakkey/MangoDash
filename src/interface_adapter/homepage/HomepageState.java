package interface_adapter.homepage;

import java.util.HashMap;

public class HomepageState {
    private String name; // Add name field

    private String username;
    private String bio; // Add bio field

    private String instagramToken;

    private String facebookToken;

    private HashMap<String, Object> instagramStatsHashMap;

    private Boolean instagramKeyError;

    private Boolean facebookKeyError;

    public HomepageState(HomepageState copy) {
        name = copy.name;
        username = copy.username;
        bio = copy.bio;
        instagramToken = copy.instagramToken;
        facebookToken = copy.facebookToken;
        instagramStatsHashMap = copy.instagramStatsHashMap;
        instagramKeyError = copy.instagramKeyError;
        facebookKeyError = copy.facebookKeyError;
    }


    public HomepageState(){}

    public String getUsername() {
        return username;
    }

    public Boolean getInstagramKeyError() {
        return instagramKeyError;
    }

    public void setInstagramKeyError(Boolean instagramKeyError) {
        this.instagramKeyError = instagramKeyError;
    }

    public Boolean getFacebookKeyError() {
        return facebookKeyError;
    }

    public void setFacebookKeyError(Boolean facebookKeyError) {
        this.facebookKeyError = facebookKeyError;
    }

    public void setInstagramStatsHashMap(HashMap<String, Object> instagramStatsHashMap) {
        this.instagramStatsHashMap = instagramStatsHashMap;
    }

    public HashMap<String, Object> getInstagramStatsHashMap() {
        return instagramStatsHashMap;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return this.bio;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setInstagramToken(String instagramToken){
        this.instagramToken = instagramToken;
    }

    public void setFacebookToken(String facebookToken){
        this.facebookToken = facebookToken;
    }

    public String getInstagramToken(){
        return this.instagramToken;
    }

    public String getFacebookToken(){
        return this.facebookToken;
    }

    @Override
    public String toString() {
        return "HomepageState{" +
                "username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
