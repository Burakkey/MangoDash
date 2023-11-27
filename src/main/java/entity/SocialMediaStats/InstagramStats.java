package entity.SocialMediaStats;

import org.json.JSONArray;
import java.util.HashMap;

public class InstagramStats implements SocialMediaStats {

    private HashMap<String, JSONArray> stats;

    public InstagramStats() {
        stats = new HashMap<>();
        stats.put("followers", new JSONArray());
        stats.put("posts", new JSONArray());
    }

    @Override
    public void setFollowers(JSONArray followers) {
        stats.put("followers", followers);
    }

    @Override
    public JSONArray getFollowers() {
        return stats.getOrDefault("followers", new JSONArray());
    }

    @Override
    public void setPosts(JSONArray posts) {
        stats.put("posts", posts);
    }

    @Override
    public JSONArray getPosts() {
        return stats.getOrDefault("posts", new JSONArray());
    }
}