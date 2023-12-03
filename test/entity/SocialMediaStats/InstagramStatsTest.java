package entity.SocialMediaStats;

import org.json.JSONArray;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class InstagramStatsTest {
    @Test
    public void testInitialization() {
        InstagramStats instagramStats = new InstagramStats();

        // Check if the stats are initialized with empty JSONArrays
        assertNotNull(instagramStats.getStats());
        assertTrue(instagramStats.getStats().containsKey("posts"));
        assertTrue(instagramStats.getStats().containsKey("followers"));
        assertTrue(instagramStats.getStats().containsKey("username"));

        assertTrue(instagramStats.getPosts().isEmpty());
        assertTrue(instagramStats.getFollowers().isEmpty());
        assertTrue(instagramStats.getUsername().isEmpty());
    }

    @Test
    public void testSetAndGetStats() {
        InstagramStats instagramStats = new InstagramStats();

        // Create new stats
        HashMap<String, JSONArray> newStats = new HashMap<>();
        JSONArray newPosts = new JSONArray();
        newPosts.put("New Post 1");
        JSONArray newFollowers = new JSONArray();
        newFollowers.put("Follower 1");
        JSONArray newUsername = new JSONArray();
        newUsername.put("NewUsername");

        newStats.put("posts", newPosts);
        newStats.put("followers", newFollowers);
        newStats.put("username", newUsername);

        // Set new stats
        instagramStats.setStats(newStats);

        // Check if the stats are updated
        assertEquals(newStats, instagramStats.getStats());
        assertEquals(newPosts, instagramStats.getPosts());
        assertEquals(newFollowers, instagramStats.getFollowers());
        assertEquals(newUsername, instagramStats.getUsername());
    }

    @Test
    public void testIndividualStats() {
        InstagramStats instagramStats = new InstagramStats();

        // Add data to individual stats
        JSONArray posts = new JSONArray();
        posts.put("Post 1");
        posts.put("Post 2");
        JSONArray followers = new JSONArray();
        followers.put("Follower 1");
        followers.put("Follower 2");
        JSONArray username = new JSONArray();
        username.put("Username1");

        instagramStats.getStats().put("posts", posts);
        instagramStats.getStats().put("followers", followers);
        instagramStats.getStats().put("username", username);

        // Check if individual stats are retrieved correctly
        assertEquals(posts, instagramStats.getPosts());
        assertEquals(followers, instagramStats.getFollowers());
        assertEquals(username, instagramStats.getUsername());
    }
}
