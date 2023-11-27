package entity.SocialMediaStats;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class FacebookStats implements SocialMediaStats {
    private String apiKey;
    private HashMap<String, JSONArray> stats;

    public FacebookStats() {
        stats = new HashMap<>();
        stats.put("friends", new JSONArray().put(0));
        stats.put("posts_raw", new JSONArray());
        stats.put("posts_cleaned", new JSONArray());
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

    public void getPostData() throws MalformedURLException {
        JSONArray posts = stats.get("posts_raw");
        JSONArray postsdata = new JSONArray();
        for (int i = 0; i < posts.length(); i++) {
            JSONObject post = posts.getJSONObject(i);
            String postID = post.getString("id");
            URL urlGetPostLikes = new URL(
                    "https://graph.facebook.com/v18.0/" + postID + "?fields=reactions.summary(1),comments.summary(1)&access_token="
                            + apiKey);

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetPostLikes.openStream(), "UTF-8"))) {
                for (String line; (line = reader.readLine()) != null;) {
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
                    postsdata.put(id_likes_comments);
                }
            } catch (IOException e) {
                System.out.println("Error with API call to get user posts summary");
            }
        }
        stats.put("posts_cleaned", postsdata); // array with hashmaps for each post
    }

    @Override
    public void updateStats() throws MalformedURLException {
        String userAccountId = null;
        String userAccountName = null;

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


        // Retrieve User's Full Name
        URL urlGetName = new URL(
                "https://graph.facebook.com/v18.0/" + userAccountId + "?fields=name&access_token="
                        + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetName.openStream(), "UTF-8"))) {
            for (String line; (line = reader.readLine()) != null; ) {
                JSONObject object = new JSONObject(line);
                userAccountName = (String) object.get("name");
            }
        } catch (IOException e) {
            System.out.println("Error with API call to get user account name");
        }

        if (userAccountName == null) {
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
                JSONObject userFriends = (JSONObject) object.get("friends");
                JSONObject userFriendsSummary = (JSONObject) userFriends.get("summary");

                JSONArray jsonFriendCount = new JSONArray();
                jsonFriendCount.put(userFriendsSummary.get("total_count"));
                stats.put("friends", (JSONArray) jsonFriendCount);
            }
        } catch (IOException e) {
            System.out.println("Error with API call to getting user friends info");
        }

        // Retrieve User's Posts
        URL urlGetAccountInfo = new URL(
                "https://graph.facebook.com/v18.0/" + userAccountId + "?fields=posts&access_token="
                        + apiKey);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetAccountInfo.openStream(),
                StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null;) {
                JSONObject object = new JSONObject(line);
                JSONObject userPosts = (JSONObject) object.get("posts");
                stats.put("posts_raw", (JSONArray) userPosts.get("data"));
            }
        } catch (IOException e) {
            System.out.println("Error with API call to getting user posts info");
        }
    }
}