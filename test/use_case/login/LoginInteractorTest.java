package use_case.login;

import data_access.APIDataAccessInterface;
import data_access.FacebookAPIDataAccessObject;
import data_access.InstagramAPIDataAccessObject;
import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import entity.SocialMediaStats.FacebookStats;
import entity.SocialMediaStats.InstagramStats;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.*;

public class LoginInteractorTest {
    private APIDataAccessInterface facebookAPI;
    private APIDataAccessInterface instagramAPI;
    private CommonUserFactory userFactory;
    private HashMap<String, String> apiKeys;
    @Before
    public void setUp() {
        userFactory = new CommonUserFactory();
        facebookAPI = new FacebookAPIDataAccessObject("", new FacebookStats());
        instagramAPI = new InstagramAPIDataAccessObject("", new InstagramStats());
    }

    @Test
    public void successTest() throws ClassNotFoundException, RuntimeException {

        User user = userFactory.create("name", "username", "password", "bio", apiKeys, LocalDateTime.now());
        LoginInputData inputData = new LoginInputData("username", "password");
        LoginUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        userRepository.save(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {

            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertTrue(userRepository.existsByName(user.getUsername()));
                assertEquals("bio", user.getBio());
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter, instagramAPI, facebookAPI);
        interactor.execute(inputData);

    }
    @Test
    public void WrongPasswordForExistedUser() throws ClassNotFoundException{
        User user = userFactory.create("name", "User", "password", "bio", apiKeys, LocalDateTime.now());
        LoginInputData inputData = new LoginInputData("User", "WronngPassword");
        LoginUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        userRepository.save(user);

        LoginOutputBoundary failPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Incorrect password for " + "User" + ".", error);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failPresenter, instagramAPI, facebookAPI);
        interactor.execute(inputData);

    }

    @Test
    public void UserDoesNotExist() throws ClassNotFoundException{
        LoginUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        LoginInputData inputData = new LoginInputData("NotAUser", "NotAUser");

        LoginOutputBoundary failPresenter = new LoginOutputBoundary() {
            @Override
            public void prepareSuccessView(LoginOutputData user) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("NotAUser" + ": Account does not exist.", error);
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failPresenter, instagramAPI, facebookAPI);
        interactor.execute(inputData);

    }

}