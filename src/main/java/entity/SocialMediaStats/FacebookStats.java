package entity.SocialMediaStats;

import org.json.JSONArray;
import java.util.HashMap;

public class FacebookStats implements SocialMediaStats {
    private String apiKey;
    private HashMap<String, JSONArray> stats;

    public FacebookStats() {
        stats = new HashMap<>();
        stats.put("friends", new JSONArray().put(0));
        stats.put("posts", new JSONArray());
        stats.put("posts_data", new JSONArray());
    }
}