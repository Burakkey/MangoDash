package use_case.signup;

import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class SignupInteractorTest {
    private CommonUserFactory userFactory;
    @Before
    public void setUp() {
        userFactory = new CommonUserFactory();
    }
//TODO PROBLEM OCCURS AFTER RUNNING 1ST TIME SINCE IT SAVES TO THE DB
    @Test
    public void successTest() throws ClassNotFoundException {
        SignupInputData inputData = new SignupInputData("er", "er", "password", "password", LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);

        // This creates a successPresenter that tests whether the test case is as we expect.
        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                assertEquals("er", user.getUsername());
                assertNotNull(user.getCreationTime()); // any creation time is fine.
                assertTrue(userRepository.existsByName("er"));
            }

            @Override
            public void prepareFailView(String error) {
//                assertEquals("Passwords don't match.", error);
                fail("Use case failure is unexpected.");
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter, userFactory);
        interactor.execute(inputData);
//        userRepository.
    }
    @Test
    public void failurePasswordMismatchTest() throws ClassNotFoundException {
        SignupInputData inputData = new SignupInputData("name", "username", "password", "wrongPassword", LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);

        // This creates a presenter that tests whether the test case is as we expect.
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Passwords don't match.", error);
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, userFactory);
        interactor.execute(inputData);
        File file = new File("testusers.db");
        file.delete();
    }

    @Test
    public void failureUserExistsTest() throws ClassNotFoundException {
        // Add Paul to the repo so that when we check later they already exist
        User user = userFactory.create("name", "username", "password", "bio", null, LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        userRepository.save(user);

        SignupInputData inputData = new SignupInputData("name", "username", "password", "password", LocalDateTime.now());


        // This creates a presenter that tests whether the test case is as we expect.
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Username already exists.", error);
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, userFactory);
        interactor.execute(inputData);
    }

    @Test
    public void failureNameCanOnlyContainLetters() throws ClassNotFoundException {
        SignupInputData inputData = new SignupInputData("Burak123", "Burakkey", "password", "Password", LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Name can only contain letters.", error);
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, userFactory);
        interactor.execute(inputData);
    }
}
dd