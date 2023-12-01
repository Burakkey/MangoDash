package data_access;

import entity.SocialMediaStats.FacebookStats;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.change_user_data.FacebookAPIDataAccessInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.nio.charset.StandardCharsets;

public class FacebookAPIDataAccessObject implements FacebookAPIDataAccessInterface {
    private String apiKey;
    private FacebookStats facebookStats;

    public FacebookAPIDataAccessObject(String apiKey, FacebookStats facebookStats) {
        this.apiKey = apiKey;
        this.facebookStats = facebookStats;
    }

    public static void main(String[] args) throws MalformedURLException{
        String apiKey = "EAAE1wmc7D2IBOZCR3TwaAvnKB5SeinplkpJuxQL9ay6ZATdHbe6y3UWPEmXxbyJPoWaCltJZAEf94iDKc8pGGwb2yEsP37rGBxtt0NZAlqCufphM9YYg7Dpn4jVAmCNwG1a7NvXUCmJCObTp8btoRBuBTaqAYTgZAzg6fZCJjelnAQXEeGZBZA6gIrp2";
        FacebookAPIDataAccessObject accessObject = new FacebookAPIDataAccessObject(apiKey, new FacebookStats());
        accessObject.fetchData();
    }

    @Override
    public void fetchData() throws MalformedURLException {
        // Reset data
        HashMap<String, JSONArray> stats = new HashMap<>();
        stats.put("followers", new JSONArray());
        stats.put("posts", new JSONArray());

        String userAccountId = null;

        // Retrieve User's ID
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

        // Retrieve User Friend Count
        URL urlGetFriendsInfo = new URL(
                "https://graph.facebook.com/v18.0/" + userAccountId + "?fields=friends&access_token="
                        + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetFriendsInfo.openStream(),
                StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null; ) {
                JSONObject object = new JSONObject(line);
                JSONObject userFriends = (JSONObject) object.get("friends"); // Pull friend data
                JSONObject userFriendsSummary = (JSONObject) userFriends.get("summary");

                JSONArray jsonFriendCount = new JSONArray();
                jsonFriendCount.put(userFriendsSummary.get("total_count"));
                stats.put("followers", (JSONArray) jsonFriendCount); // Save to stats as followers not friends
            }
        } catch (IOException e) {
            System.out.println("Error with API call to getting user friends info");
        }

        // Retrieve User's Posts
        JSONArray posts_raw = new JSONArray();
        URL urlGetAccountInfo = new URL(
                "https://graph.facebook.com/v18.0/" + userAccountId + "?fields=posts&access_token="
                        + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetAccountInfo.openStream(),
                StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                JSONObject object = new JSONObject(line);
                JSONObject userPosts = (JSONObject) object.get("posts");
                posts_raw = (JSONArray) userPosts.get("data");  // Save in JSONArray for data processing
            }
        } catch (IOException e) {
            System.out.println("Error with API call to getting user posts info");
        }

        JSONArray posts_cleaned = new JSONArray();
        for (int i = 0; i < posts_raw.length(); i++) {  // Loop through each post
            JSONObject post = posts_raw.getJSONObject(i);
            String postID = post.getString("id");
            URL urlGetPostLikes = new URL(
                    "https://graph.facebook.com/v18.0/" + postID + "?fields=reactions.summary(1),comments.summary(1)&access_token="
                            + apiKey);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetPostLikes.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {  // Read data for the current post
                    JSONObject object = new JSONObject(line);
                    // Parse post reactions (like count)
                    JSONObject postReactions = (JSONObject) object.get("reactions");
                    JSONObject postSummaryReactions = (JSONObject) postReactions.get("summary");

                    // Parse post comments (comment count)
                    JSONObject postComments = (JSONObject) object.get("comments");
                    JSONObject postSummaryComments = (JSONObject) postComments.get("summary");

                    // Create hashmap of id, like count, and comment count
                    HashMap<String, Integer> id_likes_comments = new HashMap<>();
                    id_likes_comments.put("postnumber", i);  // Post number not to be confused with post id
                    id_likes_comments.put("likes", (Integer) postSummaryReactions.get("total_count"));
                    id_likes_comments.put("comments", (Integer) postSummaryComments.get("total_count"));

                    // add hashmap to array
                    posts_cleaned.put(id_likes_comments);
                }
            } catch (IOException e) {
                System.out.println("Error with data processing to retrieve user posts summary");
            }
        }
        if (posts_cleaned.isEmpty()) {
            System.out.println("No post data was found");
            return;
        } else {
            stats.put("posts", posts_cleaned);
        }

        System.out.println(stats.get("posts"));
        System.out.println(stats.get("followers"));
        facebookStats.setStats(stats);
    }

    @Override
    public void setApi(String apiKey) {
        this.apiKey = apiKey;
    }

    @Override
    public FacebookStats getFacebookStats() {
        return facebookStats;
    }
}
