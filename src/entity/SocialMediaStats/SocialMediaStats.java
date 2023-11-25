package entity.SocialMediaStats;

import org.json.JSONArray;

import java.net.MalformedURLException;
import java.util.HashMap;

public interface SocialMediaStats {
    HashMap<String, JSONArray> getStats();

    void setApiKey(String apiKey);

    void updateStats() throws MalformedURLException;

    String getApiKey();
}
