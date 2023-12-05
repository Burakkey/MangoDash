package use_case.login;

import org.json.JSONArray;
import use_case.FacebookDataGetter;
import use_case.InstagramDataGetter;

import java.util.HashMap;

/**
 * LoginOutputData contains the required data after the user has logged in
 */
public class LoginOutputData implements InstagramDataGetter, FacebookDataGetter {

    private final String username;

    private final String name;

    private final String bio;

    private HashMap <String, Object> instagramData;

    private HashMap <String, Object> facebookData;


    /**
     * Creates a new LoginOutputData object.
     * @param name the name the user specified during sign up
     * @param username the user's username
     * @param bio the user's chosen biography, if any
     * @param facebookData the user's facebook data
     * @param instagramData the user's instagram data
     */
    public LoginOutputData(String name, String username, String bio, HashMap <String, Object> instagramData, HashMap <String, Object> facebookData) {
        this.name = name;
        this.username = username;
        this.bio = bio;
        this.instagramData = instagramData;
        this.facebookData = facebookData;
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
