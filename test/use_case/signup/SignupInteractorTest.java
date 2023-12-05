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

    @Test
    public void successTest() throws ClassNotFoundException {
        LocalDateTime testTime = LocalDateTime.now().minusDays(1); // A different time for testing
        String testTimeString = testTime.toString();

        SignupInputData inputData = new SignupInputData("er", "er", "password", "password", LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);

        // This creates a successPresenter that tests whether the test case is as we expect.
        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // Set a new creation time and check if it's updated correctly
                user.setCreationTime(testTimeString);
                assertEquals("The creation time should be updated to the test time", testTimeString, user.getCreationTime());

                assertEquals("er", user.getUsername());
                assertTrue("User should exist in the repository", userRepository.existsByName("er"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter, userFactory);
        interactor.execute(inputData);

        File file = new File("testusers.db");
        file.delete();
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

        File file = new File("testusers.db");
        file.delete();
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

        File file = new File("testusers.db");
        file.delete();
    }

    @Test
    public void failureUsernameExistsTest() throws ClassNotFoundException {
        // First, create a user with a specific username
        User existingUser = userFactory.create("ExistingName", "ExistingUsername", "password", "", null, LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        userRepository.save(existingUser);

        // Now, try to sign up with the same username
        SignupInputData inputData = new SignupInputData("NewName", "ExistingUsername", "password", "password", LocalDateTime.now());

        SignupOutputBoundary failurePresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
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
    public void testSignupOutputDataOnSuccess() throws ClassNotFoundException {
        SignupInputData inputData = new SignupInputData("NewName", "NewUsername", "password", "password", LocalDateTime.now());
        SignupUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);


        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                assertEquals("NewUsername", user.getUsername());
                assertNotNull(user.getCreationTime()); // Ensure creation time is set
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter, userFactory);
        interactor.execute(inputData);
    }


}
