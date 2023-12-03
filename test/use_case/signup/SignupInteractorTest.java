package use_case.signup;

import data_access.InMemoryUserDataAccessObject;
import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class SignupInteractorTest {
    @Test
    void successTest() throws ClassNotFoundException {
        SignupInputData inputData = new SignupInputData("name", "username", "password", "password", LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", new CommonUserFactory());

        // This creates a successPresenter that tests whether the test case is as we expect.
        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                assertEquals("name", user.getUsername());
                assertNotNull(user.getCreationTime()); // any creation time is fine.
                assertTrue(userRepository.existsByName("Paul"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };
    }

    @Test
    void failurePasswordMismatchTest() throws ClassNotFoundException {
        SignupInputData inputData = new SignupInputData("name", "username", "password", "password", LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", new CommonUserFactory());

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

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureUserExistsTest() throws ClassNotFoundException {
        SignupInputData inputData = new SignupInputData("name", "username", "password", "password", LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", new CommonUserFactory());

        // Add Paul to the repo so that when we check later they already exist
        UserFactory factory = new CommonUserFactory();
        User user = factory.create("name", "username", "password", "", null, LocalDateTime.now());
        userRepository.save(user);

        // This creates a presenter that tests whether the test case is as we expect.
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("User already exists.", error);
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    @Test
    void failureNameIsInvalid() throws ClassNotFoundException {
        SignupInputData inputData = new SignupInputData("name", "username", "password", "password", LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", new CommonUserFactory());

        // This creates a presenter that tests whether the test case is as we expect.
        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                fail("Use case success is unexpected.");
            }

            @Override
            public void prepareFailView(String error) {
                assertEquals("Name can only contain letters.", error);
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, failurePresenter, new CommonUserFactory());
        interactor.execute(inputData);
    }

}