package entity.SocialMediaStats;

import org.json.JSONArray;
import java.util.HashMap;

public class FacebookStats implements SocialMediaStats {

    private HashMap<String, JSONArray> stats;

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

    public JSONArray getName() {
        return this.stats.get("fullname");
    }
}
