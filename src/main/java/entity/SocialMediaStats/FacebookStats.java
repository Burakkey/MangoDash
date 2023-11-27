package entity.SocialMediaStats;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
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

    public static void main(String[] args) throws MalformedURLException {
        FacebookStats facebook = new FacebookStats();
        String key = "EAAE1wmc7D2IBOzhnp8Y4GlGZACmqvPmXaCI6WzLwctdNYpwp0uYE3XmgGFKVe3rhwBa0DNDZCwxJOcZCJOqg9h53L4MwRO0UcmxFEZBxw3YqOmfL4kLxFWO3ZBO6ltLClQrLtDkCuMjWZB8weZAAnorf0axciB3YXaZCgsdtD4ROSDU6IKhCzEd11iaZCUZCtROwSJ09f55Wv0U7TErW0gzLaXGMbNsXgtEfV7dPnXNitQor0LCi3awhmHN2WyPK5HzooZD";
        facebook.setApiKey(key);
        System.out.println(facebook.stats);
    }

    @Override
    public HashMap<String, JSONArray> getStats() {
        return stats;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;

        try {
            updateStats();
        } catch (MalformedURLException exception) {
            System.out.println("API call failed, possible expired token");
        }
    }

    @Override
    public void updateStats() throws MalformedURLException {
        String userAccountId = null;

        URL urlGetId = new URL(
                "https://graph.facebook.com/v18.0/me?fields=id%2Cname%2Cemail&access_token=" + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetId.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null; ) {
                JSONObject object = new JSONObject(line);
                userAccountId = (String) object.get("id");
            }
        } catch (IOException e) {
            System.out.println("Error with API call to get user account ID");
        }

        if (userAccountId == null) {
            return;
        }
    }
}