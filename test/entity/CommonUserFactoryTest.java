package entity;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CommonUserFactoryTest {

    @Test
    public void testCreateUser() {
        // Data
        CommonUserFactory userFactory = new CommonUserFactory();
        String name = "Burak Unat";
        String username = "burak.unat";
        String password = "Password";
        String bio = "I love programming!";
        HashMap<String, String> apiKeys = new HashMap<>();
        LocalDateTime creationTime = LocalDateTime.now();

        // Initialize
        User user = userFactory.create(name, username, password, bio, apiKeys, creationTime);

        //Test
        assertNotNull(user);
        assertEquals(name, user.getName());
        assertEquals(username, user.getUserName());
        assertEquals(password, user.getPassword());
        assertEquals(bio, user.getBio());
        assertEquals(apiKeys, user.getApiKeys());
        assertEquals(creationTime, user.getCreationTime());
    }
}
