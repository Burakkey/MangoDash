package use_case.login;

import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import use_case.change_user_data.InstagramAPIDataAccessInterface;
import use_case.signup.SignupInputData;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupOutputData;
import use_case.signup.SignupUserDataAccessInterface;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class LoginInteractorTest {
    private InstagramAPIDataAccessInterface instagramAPI;
    private CommonUserFactory userFactory;
    @Before
    public void setUp(){
        userFactory = new CommonUserFactory();
    }
// TODO - ??
    @Test
    public void successTest() throws ClassNotFoundException {

        User user = userFactory.create("name", "username", "password", "bio", null, LocalDateTime.now());
        LoginInputData inputData = new LoginInputData("username", "password");
        LoginUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        userRepository.save(user);

        // This creates a successPresenter that tests whether the test case is as we expect.
        LoginOutputBoundary successPresenter = new LoginOutputBoundary() {

            @Override
            public void prepareSuccessView(LoginOutputData user) {
                assertFalse(userRepository.existsByName("username"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        LoginInputBoundary interactor = new LoginInteractor(userRepository, successPresenter, instagramAPI);
        interactor.execute(inputData);
    }
    @Test
    public void WrongPasswordForExistedUser() throws ClassNotFoundException{
        User user = userFactory.create("name", "User", "password", "bio", null, LocalDateTime.now());
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

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failPresenter, instagramAPI);
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

        LoginInputBoundary interactor = new LoginInteractor(userRepository, failPresenter, instagramAPI);
        interactor.execute(inputData);


    }

}