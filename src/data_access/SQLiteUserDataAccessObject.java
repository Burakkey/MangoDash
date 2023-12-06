package data_access;

import entity.User;
import entity.UserFactory;
import org.json.JSONObject;
import use_case.change_user_data.ChangeDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * SQLiteUserDataAccessObject provides methods relating to user information for the application to interact with the database
 */
public class SQLiteUserDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface,
        ChangeDataAccessInterface {

    private final String dbUrl;
    private UserFactory userFactory;

    private final Map<String, User> accounts = new HashMap<>();

    /**
     * creates a SQLiteUserDataAccessObject that takes user information and puts it into a SQL file.
     * @param dbPath the String of the file name of the SQL database
     * @param userFactory encapsulates the data of the user
     * @throws ClassNotFoundException
     */
    public SQLiteUserDataAccessObject(String dbPath, UserFactory userFactory) throws ClassNotFoundException {
        this.userFactory = userFactory;
        this.dbUrl = "jdbc:sqlite:" + dbPath;

        try (Connection conn = DriverManager.getConnection(dbUrl)) {
            if (conn != null) {
                Class.forName("org.sqlite.JDBC");
                // Create tables if they don't exist
                try (Statement stmt = conn.createStatement()) {
                    String sql = "CREATE TABLE IF NOT EXISTS users " +
                            "(name TEXT, " +
                            "username TEXT PRIMARY KEY, " +
                            "password TEXT, " +
                            "bio TEXT, " +
                            "api_keys TEXT, "+
                            "creation_time TEXT)";
                    stmt.execute(sql);
                }

                // Load existing users from the database
                String query = "SELECT * FROM users";
                try (PreparedStatement statement = conn.prepareStatement(query);
                     ResultSet result = statement.executeQuery()) {
                    while (result.next()) {
                        String name = result.getString("name");
                        String username = result.getString("username");
                        String password = result.getString("password");
                        String bio = result.getString("bio");
                        String apiKeysJson = result.getString("api_keys");
                        LocalDateTime creationTime = LocalDateTime.parse(result.getString("creation_time"));

                        Map<String, Object> apiKeysObjectMap = new JSONObject(apiKeysJson).toMap();
                        HashMap<String, String> apiKeys = new HashMap<>();

                        for (Map.Entry<String, Object> entry : apiKeysObjectMap.entrySet()) {
                            apiKeys.put(entry.getKey(), entry.getValue().toString());
                        }

                        User user = userFactory.create(name, username, password, bio, apiKeys, creationTime);
                        accounts.put(username, user);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * existsByName takes the given String and checks the database to see if there is another user that is already using that username.
     * @param identifier the username the user has chosen when signing up
     * @return true if the username is already taken, false if the username is not already taken
     */
    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    /**
     * validName takes the given String and checks if it is a valid name (if it contains only letters).
     * @param name the name that the user inputs in the Name text field
     * @return true if the name is valid, false if the name is invalid.
     */
    @Override
    public boolean validName(String name) {
        for (int i = 0; i < name.length(); i++) {
            if (!Character.isLetter(name.charAt(i)) && name.charAt(i) != ' ') {
                return false;
            }
        }
        return true;
    }

    /**
     * occurs when the sign up is successful; this method puts the User and their info into the database
     * @param user represents the user and their info
     */
    @Override
    public void save(User user) {
        accounts.put(user.getUserName(), user);
        this.save();
    }

    /**
     * occurs when the user has already signed up, and they want to change their info. This method updates/changes the database
     * reflect these changes
     */
    public void save() {
        String sql = "INSERT OR REPLACE INTO users(name, username, password, bio, api_keys, creation_time) VALUES(?,?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            for (User user : accounts.values()) {
                statement.setString(1, user.getName());
                statement.setString(2, user.getUserName());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getBio());

                // Serialize HashMap to JSON String
                JSONObject apiKeysJson = new JSONObject(user.getApiKeys()); // Assuming getApiKeys returns a HashMap
                statement.setString(5, apiKeysJson.toString()); // Serialized API keys

                statement.setString(6, user.getCreationTime().toString());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * changes the user's password, after inputting the same password twice into the prompt for changing passwords
     * @param name the user's name
     * @param username the user's username (unique identifier)
     * @param password the user's new password
     * @param bio the user's bio
     */
    @Override
    public void modifyUser(String name, String username, String password, String bio) {
        User user = accounts.get(username);
        if (user != null) {
            user.setPassword(password);
            user.setName(name);
            user.setBio(bio);
            this.save();
        }
    }

    /**
     * changes the user's name and/or bio
     * @param name the user's (new) name
     * @param username the user's username (unique identifier)
     * @param bio the user's (new) bio
     */
    @Override
    public void modifyUser(String name, String username, String bio) {
        User user = accounts.get(username);
        if (user != null) {
            user.setName(name);
            user.setBio(bio);
            this.save();
        }
    }

    /**
     * changes the user's facebookAPI token and/or instagramAPI token
     * @param username the user's username (unique identifier)
     * @param facebookAPI the user's FacebookAPI token
     * @param instagramAPI the user's InstagramAPI token
     */
    @Override
    public void modifyUserAPI(String username, String facebookAPI, String instagramAPI) {
        User user = accounts.get(username);
        if (user != null) {
            HashMap<String, String> apiKeys = user.getApiKeys();
            if (apiKeys == null) {
                apiKeys = new HashMap<>();
                if (facebookAPI != null) {
                    apiKeys.put("Facebook", facebookAPI);
                }
                if (instagramAPI != null) {
                    apiKeys.put("Instagram", instagramAPI);
                }

            }

            // Update the Facebook and Instagram API keys
            if (facebookAPI != null) {
                user.setApiKeys("Facebook", facebookAPI);
            }
            if (instagramAPI != null) {
                user.setApiKeys("Instagram", instagramAPI);
            }

            this.save();
        }
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

}
