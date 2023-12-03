package use_case.change_user_data;

import org.json.JSONArray;
import use_case.FacebookDataGetter;
import use_case.InstagramDataGetter;

import java.util.HashMap;

public class ChangeDataOutput implements InstagramDataGetter, FacebookDataGetter {
    private String username;

    private String name;

    private String bio;

    private HashMap <String, Object> instagramData;

    private HashMap <String, Object> facebookData;


    public ChangeDataOutput(String username, String name, String bio) {
        this.username = username;
        this.name = name;
        this.bio = bio;
    }

    public ChangeDataOutput(HashMap <String, Object> instagramData, HashMap <String, Object> facebookData){
        this.instagramData = instagramData;
        this.facebookData = facebookData;
    }

    public HashMap<String, Object> getInstagramData() {
        return instagramData;
    }

    public HashMap<String, Object> getFacebookData() {
        return facebookData;
    }

    public String getUsername() {
        return username;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }
}
