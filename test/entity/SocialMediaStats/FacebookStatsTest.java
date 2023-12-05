package entity.SocialMediaStats;

import org.junit.Test;
import org.json.JSONArray;

import java.util.HashMap;

import static org.junit.Assert.*;

public class FacebookStatsTest {
    @Test
    public void testInitialization() {
        FacebookStats facebookStats = new FacebookStats();

        assertNotNull(facebookStats.getStats());
        assertTrue(facebookStats.getStats().containsKey("posts"));
        assertTrue(facebookStats.getStats().containsKey("followers"));

        assertTrue(facebookStats.getPosts().isEmpty());
        assertTrue(facebookStats.getFollowers().isEmpty());
    }

    @Test
    public void testSetAndGetStats() {
        FacebookStats facebookStats = new FacebookStats();

        HashMap<String, JSONArray> newStats = new HashMap<>();
        JSONArray newPosts = new JSONArray();
        newPosts.put("New Post 1");
        JSONArray newFollowers = new JSONArray();
        newFollowers.put("Follower 1");

        newStats.put("posts", newPosts);
        newStats.put("followers", newFollowers);

        facebookStats.setStats(newStats);

        assertEquals(newStats, facebookStats.getStats());
        assertEquals(newPosts, facebookStats.getPosts());
        assertEquals(newFollowers, facebookStats.getFollowers());
    }

    @Test
    public void testIndividualStats() {
        FacebookStats facebookStats = new FacebookStats();

        JSONArray posts = new JSONArray();
        posts.put("Post 1");
        posts.put("Post 2");
        JSONArray followers = new JSONArray();
        followers.put("Follower 1");
        followers.put("Follower 2");

        facebookStats.getStats().put("posts", posts);
        facebookStats.getStats().put("followers", followers);

        assertEquals(posts, facebookStats.getPosts());
        assertEquals(followers, facebookStats.getFollowers());
    }
}
