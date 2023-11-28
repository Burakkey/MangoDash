package entity.SocialMediaStats;

import org.json.JSONArray;

import java.util.HashMap;

public interface SocialMediaStats {

    HashMap<String, JSONArray> getStats();

    void setStats(HashMap<String, JSONArray> stats);
}
