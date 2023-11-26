package entity.SocialMediaStats;

import org.json.JSONArray;

public interface SocialMediaStats {

    void setFollowers(JSONArray followers);

    JSONArray getFollowers();

    void setPosts(JSONArray posts);



    JSONArray getPosts();
}
