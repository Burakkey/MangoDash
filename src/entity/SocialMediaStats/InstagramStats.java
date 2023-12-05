package entity.SocialMediaStats;

import org.json.JSONArray;

import java.net.MalformedURLException;
import java.util.HashMap;

/**
 * Represents statistics related to Instagram data.
 * This entity encapsulates information about various statistics associated with an Instagram account
 */
public class InstagramStats implements SocialMediaStats {

    private HashMap<String, JSONArray> stats;

    /**
     * Constructs an InstagramStats object with data about the followers, posts, and username of an Instagram account
     */
    public InstagramStats() {
        stats = new HashMap<>();
        stats.put("followers", new JSONArray());
        stats.put("posts", new JSONArray());
        stats.put("username", new JSONArray());
    }
    @Override
    public HashMap<String, JSONArray> getStats() {
        return stats;
    }

    @Override
    public void setStats(HashMap<String, JSONArray> stats) {
        this.stats = stats;
    }

    public JSONArray getPosts() {
        return this.stats.get("posts");
    }

    public JSONArray getFollowers() {
        return this.stats.get("followers");
    }

    public JSONArray getUsername() {
        return this.stats.get("username");
    }
}
