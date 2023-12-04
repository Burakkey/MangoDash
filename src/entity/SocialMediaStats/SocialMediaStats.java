package entity.SocialMediaStats;

import org.json.JSONArray;

import java.util.HashMap;

/**
 * Represents statistics related to Social Media data.
 * This entity class encapsulates information about various statistics associated with a Social Media account
 */
public interface SocialMediaStats {

    HashMap<String, JSONArray> getStats();

    void setStats(HashMap<String, JSONArray> stats);
}
