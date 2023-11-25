package data_access;

import entity.User;
import entity.UserFactory;
import use_case.change_user_data.ChangeDataAccessInterface;
import use_case.clear_users.ClearUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;

public class SQLiteUserDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface,
        ClearUserDataAccessInterface, ChangeDataAccessInterface {

    private final String dbUrl;
    private UserFactory userFactory;

    private final Map<String, User> accounts = new HashMap<>();

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
                            "facebookAPI TEXT, " +
                            "instagramAPI TEXT, " +
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
                        String facebookAPI = result.getString("facebookAPI");
                        String instagramAPI = result.getString("instagramAPI");
                        LocalDateTime creationTime = LocalDateTime.parse(result.getString("creation_time"));
                        User user = userFactory.create(name, username, password, bio, facebookAPI, instagramAPI,creationTime);
                        accounts.put(username, user);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }



    @Override
    public void save(User user) {
        accounts.put(user.getUserName(), user);
        this.save();
    }
    public void save() {
        String sql = "INSERT OR REPLACE INTO users(name, username, password, bio, creation_time) VALUES(?,?,?,?,?)";
        try (Connection conn = DriverManager.getConnection(dbUrl);
             PreparedStatement statement = conn.prepareStatement(sql)) {
            for (User user: accounts.values()){
                statement.setString(1, user.getName());
                statement.setString(2, user.getUserName());
                statement.setString(3, user.getPassword());
                statement.setString(4, user.getBio());
                statement.setString(5, user.getApiKey().get(0));
                statement.setString(6, user.getApiKey().get(1));
                statement.setString(7, user.getCreationTime().toString());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void modifyUser(String name, String username, String password, String bio) {
        User user = accounts.get(username);
        if (user != null) {
            user.setPassword(password);
            user.setName(name);
            user.setBio(bio);
            this.save(); // Save the updated user information to the CSV file
        }
    }

    @Override
    public void modifyUser(String name, String username, String bio) {
        User user = accounts.get(username);
        if (user != null) {
            user.setName(name);
            user.setBio(bio);
            this.save();
        }
    }

    @Override
    public void modifyUserAPI(String username, String facebookAPI, String instagramAPI) {
        User user = accounts.get(username);
        if (user != null) {
            user.setApiKey("Facebook", facebookAPI);
            user.setApiKey("Instagram", instagramAPI);
            this.save();
        }
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    @Override
    public List clear() {
        return null;
    }

    // Other methods like clear, existsByName, modifyUser to be implemented similarly
}
