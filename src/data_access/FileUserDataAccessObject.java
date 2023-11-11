package data_access;

import entity.User;
import entity.UserFactory;
import use_case.change_user_data.ChangeDataAccessInterface;
import use_case.clear_users.ClearUserDataAccessInterface;
import use_case.login.LoginUserDataAccessInterface;
import use_case.signup.SignupUserDataAccessInterface;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class FileUserDataAccessObject implements SignupUserDataAccessInterface, LoginUserDataAccessInterface,
        ClearUserDataAccessInterface, ChangeDataAccessInterface {

    private final File csvFile;

    private final Map<String, Integer> headers = new LinkedHashMap<>();

    private final Map<String, User> accounts = new HashMap<>();

    private UserFactory userFactory;

    public FileUserDataAccessObject(String csvPath, UserFactory userFactory) throws IOException {
        this.userFactory = userFactory;

        csvFile = new File(csvPath);
        headers.put("name", 0);
        headers.put("username", 1);
        headers.put("password", 2);
        headers.put("bio", 3);
        headers.put("creation_time", 4);

        if (csvFile.length() == 0) {
            save();
        } else {

            try (BufferedReader reader = new BufferedReader(new FileReader(csvFile))) {
                String header = reader.readLine();

                // For later: clean this up by creating a new Exception subclass and handling it in the UI.
                assert header.equals("name,username,password,bio,creation_time");

                String row;
                while ((row = reader.readLine()) != null) {
                    String[] col = row.split(",");
                    String name = String.valueOf(col[headers.get("name")]);
                    String username = String.valueOf(col[headers.get("username")]);
                    String password = String.valueOf(col[headers.get("password")]);
                    String bio = String.valueOf(col[headers.get("bio")]);
                    String creationTimeText = String.valueOf(col[headers.get("creation_time")]);
                    LocalDateTime ldt = LocalDateTime.parse(creationTimeText);
                    User user = userFactory.create(name, username, password, bio, ldt);
                    accounts.put(username, user);
                }
            }
        }
    }

    @Override
    public void save(User user) {
        accounts.put(user.getUserName(), user);
        this.save();
    }

    @Override
    public User get(String username) {
        return accounts.get(username);
    }

    private void save() {
        BufferedWriter writer;
        try {
            writer = new BufferedWriter(new FileWriter(csvFile));
            writer.write(String.join(",", headers.keySet()));
            writer.newLine();

            for (User user : accounts.values()) {
                String creationTime = user.getCreationTime().toString(); // Format LocalDateTime as a string
                String line = String.format("%s,%s,%s,%s,%s",
                        user.getName(), user.getUserName(), user.getPassword(), user.getBio(), creationTime);
                writer.write(line);
                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // TODO by me CHECK HERE BURAK ????????!!!!!@@@@
    public List clear(){
        Set<String> keySet = accounts.keySet();
        ArrayList<String> usernames = new ArrayList<>(keySet);
        accounts.clear();
        this.save();
        return usernames;
    }

    /**
     * Return whether a user exists with username identifier.
     * @param identifier the username to check.
     * @return whether a user exists with username identifier
     */
    @Override
    public boolean existsByName(String identifier) {
        return accounts.containsKey(identifier);
    }

    @Override
    public void modifyUser(String username, String name, String password, String bio) {
        User user = accounts.get(username);
        if (user != null) {
            user.setPassword(password);
            user.setName(name);
            user.setBio(bio);
            this.save(); // Save the updated user information to the CSV file
        }
    }

    @Override
    public void modifyUser(String username, String name, String bio) {
        User user = accounts.get(username);
        if (user != null) {
            user.setName(name);
            user.setBio(bio);
            System.out.println("Success till here"); //TODO: Remove this when checked @Hisham
            this.save(); // Save the updated user information to the CSV file
        }
    }


}
