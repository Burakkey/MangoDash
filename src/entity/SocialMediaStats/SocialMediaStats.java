package entity.SocialMediaStats;

import org.json.JSONArray;

import java.util.HashMap;

public interface SocialMediaStats {

    void setFollowers(JSONArray followers);

    JSONArray getFollowers();


    void setPosts(JSONArray posts);

    JSONArray getPosts();

    HashMap getStats();
}
