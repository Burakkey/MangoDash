package data_access;

import entity.SocialMediaStats.SocialMediaStats;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class APIUserDataAccessObject {
    private final SocialMediaStats socialMediaStats;
    private final String apiKey;

    public APIUserDataAccessObject(SocialMediaStats socialMediaStats, String apiKey) {
        this.socialMediaStats = socialMediaStats;
        this.apiKey = apiKey;
    }

    public SocialMediaStats getSocialMediaStats() {
        return socialMediaStats;
    }

    public void fetchData() throws MalformedURLException {
        String userAccountId = null;
        String userAccountUsername = null;

        URL urlGetId = new URL(
                "https://graph.facebook.com/v18.0/me?fields=id%2Cname%2Caccounts%7Binstagram_business_account%7D&access_token=" + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetId.openStream(), "UTF-8"))) {

            for (String line; (line = reader.readLine()) != null;) {
                JSONObject object = new JSONObject(line);
                JSONArray tracker = (JSONArray) ((JSONObject) object.get("accounts")).get("data");
                JSONObject businessAccount = (JSONObject) ((JSONObject)tracker.get(0)).get("instagram_business_account");
                userAccountId = (String) businessAccount.get("id");

            }

        } catch (IOException e) {
            System.out.println("Error with API call to get user account ID");
        }

        if (userAccountId == null) {
            return;
        }

        // Call to get the username now
        URL urlGetUsername = new URL(
                "https://graph.facebook.com/v18.0/" + userAccountId + "?fields=username&access_token="
                        + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetUsername.openStream(), "UTF-8"))) {

            for (String line; (line = reader.readLine()) != null;) {
                JSONObject object = new JSONObject(line);
                userAccountUsername = (String) object.get("username");
            }

        } catch (IOException e) {
            System.out.println("Error with API call to get user account Username");
        }

        if (userAccountUsername == null) {
            return;
        }
//
        URL urlGetAccountInfo = new URL(
                "https://graph.facebook.com/v18.0/" + userAccountId + "?fields=business_discovery.username(" +
//                        userAccountUsername +
                        "bluebottle" +
                        "){followers_count,media_count,media{comments_count,like_count}}&access_token="
                        + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetAccountInfo.openStream(), StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                JSONObject object = new JSONObject(line);
                JSONObject businessDiscovery = object.getJSONObject("business_discovery");

                // Set followers
                JSONArray followers = new JSONArray().put(businessDiscovery.getInt("followers_count"));
                socialMediaStats.setFollowers(followers);

                // Set posts
                JSONArray posts = businessDiscovery.getJSONObject("media").getJSONArray("data");
                socialMediaStats.setPosts(posts);

                // Print stats for debugging (optional)
                System.out.println(socialMediaStats.getStats());
            }
        } catch (IOException e) {
            System.out.println("Error with API call to getting user info");
        }
    }

}
