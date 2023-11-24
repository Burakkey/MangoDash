package entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

class CommonUser implements User {

    private String bio;
    private String name;

    private final String username;
    private String password;
    private final LocalDateTime creationTime;
    private String apiKey;

    /**
     * Requires: password is valid.
     * @param name
     * @param password
     */
    CommonUser(String name, String username, String password, String bio, LocalDateTime creationTime) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.bio = bio;
        this.creationTime = creationTime;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getUserName() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String getBio() {
        return bio;
    }

    @Override
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
        updateAccountInformation();
    }

    @Override
    public String getApiKey() {
        return this.apiKey;
    }

    private void updateAccountInformation() throws MalformedURLException {
        String accessToken = "EAAMw2YKsBFwBO7AZBktfgplOmteTtfc9UZAIQwJZBKJwrEpW81xlZAMoZBA8Tc0s0lwpXibOpBoRJ0pcAf3nCOXpJ54EV54970pjkQtVDGosB745jXCIMjmQISIZCv0I5vE1npXK0UvIdwqkvQd2K596ZC5SAAbZBz83bN2BZBbzNsppFrEBWMZAwyWp7MrlVGG4o4hCAOSEEafKn7bZCQapvZBjEqMZD";
        String userAccountId = null;
        String userAccountUsername = null;

        URL urlGetId = new URL(
                "https://graph.facebook.com/v18.0/me?fields=id%2Cname%2Caccounts%7Binstagram_business_account%7D&access_token=" + accessToken);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetId.openStream(), "UTF-8"))) {

            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);
                JSONObject object = new JSONObject(line);
                JSONArray tracker = (JSONArray) ((JSONObject) object.get("accounts")).get("data");
                JSONObject businessAccount = (JSONObject) ((JSONObject)tracker.get(0)).get("instagram_business_account");
                System.out.println(businessAccount.get("id"));
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
                        + accessToken);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetUsername.openStream(), "UTF-8"))) {

            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);
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
                        userAccountUsername +
                        "){followers_count,media_count,media{comments_count,like_count}}&access_token="
                        + accessToken);


        int followerCount;
        JSONArray posts;


        try (BufferedReader reader = new BufferedReader(new InputStreamReader(urlGetAccountInfo.openStream(),
                StandardCharsets.UTF_8))) {

            for (String line; (line = reader.readLine()) != null;) {
                System.out.println(line);
                JSONObject object = new JSONObject(line);
                JSONObject businessDiscovery = (JSONObject) object.get("business_discovery");
                followerCount = (int) businessDiscovery.get("followers_count");

                JSONObject media = (JSONObject) businessDiscovery.get("media");
                posts = (JSONArray) media.get("data");
                System.out.println(posts);
                System.out.println(followerCount);
            }

        } catch (IOException e) {
            System.out.println("Error with API call to getting user info");
        }
    }
}
