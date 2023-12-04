package data_access;

import entity.SocialMediaStats.InstagramStats;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.change_user_data.InstagramAPIDataAccessInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * A DataAccessObject that interacts with the Instagram Graph API. This class contains methods that allow the application
 * to access data on the Instagram platform from the user's Instagram account
 */
public class InstagramAPIDataAccessObject implements InstagramAPIDataAccessInterface {
    private String apiKey;
    private InstagramStats instagramStats;
    private boolean apiError;

    /**
     * given a valid API key, creates a InstagramAPIDataAccessObject that is able to access data related to the user's Instagram account
     * @param apiKey the valid API key
     * @param instagramStats the relevant info from the user's Instagram account
     */
    public InstagramAPIDataAccessObject(String apiKey, InstagramStats instagramStats) {
        this.apiKey = apiKey;
        this.instagramStats = instagramStats;
        this.apiError = false;
    }

    /**
     * retrieves the relevant data from the user's Instagram account
     * @throws MalformedURLException
     */
    @Override
    public void fetchData() throws MalformedURLException {
        apiError = false;
        HashMap<String, JSONArray> stats = new HashMap<>();
        stats.put("followers", new JSONArray());
        stats.put("posts", new JSONArray());
        stats.put("username", new JSONArray());

        String userAccountId = null;
        String userAccountUsername = null;

        URL urlGetId = new URL(
                "https://graph.facebook.com/v18.0/me?fields=id%2Cname%2Caccounts%7Binstagram_business_account%7D&access_token=" + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetId.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                JSONObject object = new JSONObject(line);
                JSONArray tracker = object.getJSONObject("accounts").getJSONArray("data");
                JSONObject businessAccount = tracker.getJSONObject(0).getJSONObject("instagram_business_account");
                userAccountId = businessAccount.getString("id");
            }
        } catch (IOException e) {
            System.out.println("Error with API call to get user account ID");
            apiError = true;
            return;
        }

        if (userAccountId == null) {
            return;
        }

        URL urlGetUsername = new URL(
                "https://graph.facebook.com/v18.0/" + userAccountId + "?fields=username&access_token=" + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetUsername.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null;) {
                JSONObject object = new JSONObject(line);
                userAccountUsername = object.getString("username");

                stats.get("username").put(userAccountUsername);
            }
        } catch (IOException e) {
            System.out.println("Error with API call to get user account Username");
            apiError = true;
            return;
        }

        if (userAccountUsername == null) {
            return;
        }

        URL urlGetAccountInfo = new URL(
                "https://graph.facebook.com/v18.0/" + userAccountId + "?fields=business_discovery.username(" +
                        userAccountUsername + // Use the actual username
                        "){followers_count,media_count,media{comments_count,like_count}}&access_token=" + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetAccountInfo.openStream(), StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                JSONObject object = new JSONObject(line);
                JSONObject businessDiscovery = object.getJSONObject("business_discovery");

                JSONArray followers = new JSONArray().put(businessDiscovery.getInt("followers_count"));
                stats.put("followers", followers);

                JSONArray posts = businessDiscovery.getJSONObject("media").getJSONArray("data");
                stats.put("posts", posts);
            }
        } catch (IOException e) {
            System.out.println("Error with API call to getting user info");
            apiError = true;
            return;
        }
        System.out.println(stats.get("posts"));
        System.out.println(stats.get("followers"));

        instagramStats.setStats(stats);
    }

    @Override
    public void setAPI(String apiUrl) {
        this.apiKey = apiUrl;
    }

    @Override
    public InstagramStats getInstagramStats() {
        return instagramStats;
    }

    @Override
    public boolean isApiError() {
        return apiError;
    }
}
