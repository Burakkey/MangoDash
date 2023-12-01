package use_case.login;

import org.json.JSONArray;
import use_case.InstagramDataGetter;

import java.util.HashMap;

public class LoginOutputData implements InstagramDataGetter {

    private final String username;

    private final String name;

    private final String bio;
    private boolean useCaseFailed;

    private HashMap <String, Object> instagramData;

    private HashMap <String, Object> facebookData;



    public LoginOutputData(String name, String username, String bio, HashMap <String, Object> instagramData, HashMap <String, Object> facebookData, boolean useCaseFailed) {
        this.name = name;
        this.username = username;
        this.bio = bio;
        this.instagramData = instagramData;
        this.facebookData = facebookData;
        this.useCaseFailed = useCaseFailed;
    }

    public HashMap<String,Object> getInstagramData() {
        return instagramData;
    }

    public HashMap<String,Object> getFacebookData() {
        return facebookData;
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
}
