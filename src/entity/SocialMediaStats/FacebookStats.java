package entity.SocialMediaStats;

import org.json.JSONArray;
import java.util.HashMap;

/**
 * Represents statistics related to Facebook data.
 * This entity encapsulates information about various statistics associated with a Facebook account
 */
public class FacebookStats implements SocialMediaStats {

    private HashMap<String, JSONArray> stats;

    /**
     * Constructs a FacebookStats object with data about the followers and posts of a Facebook account
     */
    public FacebookStats() {
        stats = new HashMap<>();
        stats.put("followers", new JSONArray());
        stats.put("posts", new JSONArray());
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
