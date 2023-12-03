package entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class CommonUserTest {

    @Test
    public void testGettersAndSetters() {
        LocalDateTime now = LocalDateTime.now();
        CommonUser user = new CommonUser("Burak", "burakkey", "12345", "I love CS", null, now);

        assertEquals("Burak", user.getName());
        assertEquals("burakkey", user.getUserName());
        assertEquals("12345", user.getPassword());
        assertEquals("I love CS", user.getBio());
        assertEquals(now, user.getCreationTime());
        assertEquals(null, user.getApiKeys());  // apiKeys is initially set to null

        HashMap<String, String> apiKeys = new HashMap<>();
        apiKeys.put("Instagram", "sampleInstagramKey");
        apiKeys.put("Facebook", "sampleFacebookKey");


        user.setApiKeys("Instagram", "sampleInstagramKey");
        user.setApiKeys("Facebook", "sampleFacebookKey");


        assertEquals(apiKeys, user.getApiKeys());
    }

    @Test
    public void testSetPassword() {
        CommonUser user = new CommonUser("Burak", "burakkey", "12345", "I love CS", null, LocalDateTime.now());
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testSetName() {

        CommonUser user = new CommonUser("Burak", "burakkey", "12345", "I love CS", null, LocalDateTime.now());
        user.setName("NewName");
        assertEquals("NewName", user.getName());
    }
}