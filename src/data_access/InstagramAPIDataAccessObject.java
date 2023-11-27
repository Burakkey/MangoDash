package data_access;

import entity.SocialMediaStats.InstagramStats;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.change_user_data.InstagramAPIIDataAccessInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class InstagramAPIDataAccessObject implements InstagramAPIIDataAccessInterface {
    private String apiKey;
    private InstagramStats instagramStats;

    public InstagramAPIDataAccessObject(String apiKey, InstagramStats instagramStats) {
        this.apiKey = apiKey;
        this.instagramStats = instagramStats;
    }

    @Override
    public void fetchData() throws MalformedURLException {
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
            }
        } catch (IOException e) {
            System.out.println("Error with API call to get user account Username");
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
                instagramStats.setFollowers(followers);

                JSONArray posts = businessDiscovery.getJSONObject("media").getJSONArray("data");
                instagramStats.setPosts(posts);
            }
        } catch (IOException e) {
            System.out.println("Error with API call to getting user info");
        }
        System.out.println(instagramStats.getFollowers());
        System.out.println(instagramStats.getPosts());
    }

    @Override
    public void setAPI(String apiUrl) {
        this.apiKey = apiUrl;
    }

    @Override
    public InstagramStats getInstagramStats() {
        return instagramStats;
    }

}
