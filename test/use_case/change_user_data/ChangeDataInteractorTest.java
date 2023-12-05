package use_case.change_user_data;

import data_access.FacebookAPIDataAccessObject;
import data_access.InstagramAPIDataAccessObject;
import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import entity.SocialMediaStats.FacebookStats;
import entity.SocialMediaStats.InstagramStats;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import use_case.login.LoginOutputData;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.*;

public class ChangeDataInteractorTest {
    private APIDataAccessInterface facebookAPI;

    private APIDataAccessInterface instagramAPI;

    private CommonUserFactory userFactory;
    private HashMap<String, String> apiKeys;
    @Before
    public void setUp() {
        userFactory = new CommonUserFactory();
        instagramAPI = new InstagramAPIDataAccessObject("", new InstagramStats());
        facebookAPI = new FacebookAPIDataAccessObject("", new FacebookStats());
    }
    @Test
    public void SuccessfullyTest() throws ClassNotFoundException {
        // First create user so that we can test
        User user = userFactory.create("name", "username", "password", "bio", apiKeys, LocalDateTime.now());
        ChangeDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        ((SQLiteUserDataAccessObject) userRepository).save(user);

        ChangeDataInput inputData = new ChangeDataInput("username", "newName", "newBio");

        ChangeDataOutputBoundary successPresenter = new ChangeDataOutputBoundary() {
            @Override
            public void prepareFailView(String s) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareSuccessView(ChangeDataOutput changeDataOutput) {
                assertEquals("newBio", changeDataOutput.getBio()); // Test for updated bio
                assertEquals("newName", changeDataOutput.getName()); // Test for updated name
                assertEquals("username", changeDataOutput.getUsername()); // Test for username
            }

            @Override
            public void prepareAPIView(ChangeDataOutput changeDataOutput) {
                fail("Use case success is unexpected.");
            }
        };

        ChangeDataInputBoundary interactor = new ChangeDataInteractor(userRepository, successPresenter, instagramAPI, facebookAPI);
        interactor.executeSaveChanges(inputData);
    }

    @Test
    public void testGetInstagramAndFacebookData() {
        // Setup Instagram and Facebook data
        HashMap<String, Object> instagramData = new HashMap<>();
        instagramData.put("followers", 100);
        instagramData.put("posts", 50);

        HashMap<String, Object> facebookData = new HashMap<>();
        facebookData.put("likes", 200);
        facebookData.put("shares", 75);

        // Create ChangeDataOutput object with this data
        ChangeDataOutput changeDataOutput = new ChangeDataOutput(instagramData, facebookData);

        // Assert that the data retrieved matches the data set
        assertEquals("Instagram followers should match", 100, changeDataOutput.getInstagramData().get("followers"));
        assertEquals("Instagram posts should match", 50, changeDataOutput.getInstagramData().get("posts"));
        assertEquals("Facebook likes should match", 200, changeDataOutput.getFacebookData().get("likes"));
        assertEquals("Facebook shares should match", 75, changeDataOutput.getFacebookData().get("shares"));
    }





    @Test
    public void PasswordMismatchTest() throws ClassNotFoundException {
        // First create user so that we can test
        User user = userFactory.create("name", "username", "password", "bio", apiKeys, LocalDateTime.now());
        ChangeDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        ((SQLiteUserDataAccessObject) userRepository).save(user);
        // LOGIN

        ChangeDataInput inputData = new ChangeDataInput("username", "name", "bio", "password", "newPassword", "newWRONGPassword");


        ChangeDataOutputBoundary successPresenter = new ChangeDataOutputBoundary() {
            @Override
            public void prepareFailView(String s) {
                assertEquals("New passwords does not match.", s);
            }

            @Override
            public void prepareSuccessView(ChangeDataOutput changeDataOutput) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareAPIView(ChangeDataOutput changeDataOutput) {
                fail("Use case success is unexpected.");
            }
        };

        ChangeDataInputBoundary interactor = new ChangeDataInteractor(userRepository, successPresenter, instagramAPI, facebookAPI);
        interactor.executeSaveChanges(inputData);

    }


    @Test
    public void WrongCurrentPasswordTest() throws ClassNotFoundException {
        // First create user so that we can test
        User user = userFactory.create("name", "username", "password", "bio", apiKeys, LocalDateTime.now());
        ChangeDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        ((SQLiteUserDataAccessObject) userRepository).save(user);

        ChangeDataInput inputData = new ChangeDataInput("username", "name", "bio", "WRONGPassword", "newPassword", "newPassword");


        ChangeDataOutputBoundary successPresenter = new ChangeDataOutputBoundary() {
            @Override
            public void prepareFailView(String s) {
                assertEquals("Incorrect password for " + user.getUserName() + ".", s);
            }

            @Override
            public void prepareSuccessView(ChangeDataOutput changeDataOutput) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareAPIView(ChangeDataOutput changeDataOutput) {
                fail("Use case success is unexpected.");
            }
        };

        ChangeDataInputBoundary interactor = new ChangeDataInteractor(userRepository, successPresenter, instagramAPI, facebookAPI);
        interactor.executeSaveChanges(inputData);

    }

    @Test
    public void testLoginOutputDataGettersAndFailureFlag() {
        // Define test data
        String expectedName = "Test Name";
        String expectedUsername = "TestUsername";
        String expectedBio = "Test Bio";
        HashMap<String, Object> instagramData = new HashMap<>();
        HashMap<String, Object> facebookData = new HashMap<>();
        boolean useCaseSuccess = true;

        // Create a LoginOutputData object with test data
        LoginOutputData loginOutputData = new LoginOutputData(expectedName, expectedUsername, expectedBio, instagramData, facebookData);

        // Assert that each getter returns the correct value
        assertEquals("Username getter should return the correct username", expectedUsername, loginOutputData.getUsername());
        assertEquals("Name getter should return the correct name", expectedName, loginOutputData.getName());
        assertEquals("Bio getter should return the correct bio", expectedBio, loginOutputData.getBio());
        assertSame("Instagram data getter should return the correct data", instagramData, loginOutputData.getInstagramData());
        assertSame("Facebook data getter should return the correct data", facebookData, loginOutputData.getFacebookData());

        // If needed, add additional logic to test scenarios when useCaseFailed is true
    }


    @Test
    public void executeAPIChangesSuccessfullyTest() throws ClassNotFoundException {
        // First create user so that we can test
        User user = userFactory.create("name", "username", "password", "bio", apiKeys, LocalDateTime.now());
        ChangeDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        ((SQLiteUserDataAccessObject) userRepository).save(user);

        ChangeDataInput inputData = new ChangeDataInput("username", "name",
                "EAAE1wmc7D2IBO7A6ARgobRPzhoPN9LLTx5ZCbzclXCBsMNaAnZADz6ujpfb16WgxJnG4qIRggtbKFfTM2CHGVVUzL21ZBgkZAnlTaGRWnaOw8vApwUrWKSWiCZBwzrCukupaNVb6QQcaYx0RGw4h4J1nJ2pJIRlJEh6wCxcTtTwyVMiDZBY21yO1KLjioqfWRjdtzz6pPOoSZCf7szqhYbiQ47cqx8ZD",
                "EAAMw2YKsBFwBO4pt7KgLmZBeEzVHOYB90bh1HM9xJN1QZAo0qWZBEn8M0A1q5tdXXOjNDE3a8SbZAdxkCqXp4XoJb1QwCKDbXkn0S4jbGNV4UpRa4pqHLhNc1nbBmewt9Ri0l1EmKU29JtY6D3nZCiHeLcvpZB48DR9LpMSVrQvQUMJmtxVLgr8nlc");


        ChangeDataOutputBoundary successPresenter = new ChangeDataOutputBoundary() {
            @Override
            public void prepareFailView(String s) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareSuccessView(ChangeDataOutput changeDataOutput) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareAPIView(ChangeDataOutput changeDataOutput) {
                // TODO WHAT DO YOU GUYS CHANGE IN HERE
                HashMap<String,String> expected = new HashMap<>();
                expected.put("Facebook", "EAAE1wmc7D2IBO7A6ARgobRPzhoPN9LLTx5ZCbzclXCBsMNaAnZADz6ujpfb16WgxJnG4qIRggtbKFfTM2CHGVVUzL21ZBgkZAnlTaGRWnaOw8vApwUrWKSWiCZBwzrCukupaNVb6QQcaYx0RGw4h4J1nJ2pJIRlJEh6wCxcTtTwyVMiDZBY21yO1KLjioqfWRjdtzz6pPOoSZCf7szqhYbiQ47cqx8ZD");
                expected.put("Instagram",  "EAAMw2YKsBFwBO4pt7KgLmZBeEzVHOYB90bh1HM9xJN1QZAo0qWZBEn8M0A1q5tdXXOjNDE3a8SbZAdxkCqXp4XoJb1QwCKDbXkn0S4jbGNV4UpRa4pqHLhNc1nbBmewt9Ri0l1EmKU29JtY6D3nZCiHeLcvpZB48DR9LpMSVrQvQUMJmtxVLgr8nlc");
                assertEquals(expected, user.getApiKeys());
            }
        };

        ChangeDataInputBoundary interactor = new ChangeDataInteractor(userRepository, successPresenter, instagramAPI, facebookAPI);
        interactor.executeAPIChanges(inputData);

    }

}


