package entity.SocialMediaStats;

public class FacebookStats implements SocialMediaStats {

    private HashMap<String, JSONArray> stats;

    public InstagramStats() {
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
}
