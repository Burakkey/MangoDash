package use_case.change_user_data;

import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ChangeDataInteractorTest {

    private CommonUserFactory userFactory;
    private HashMap<String, String> apiKeys;
    @Before
    public void setUp() {
        userFactory = new CommonUserFactory();
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
        };

        ChangeDataInputBoundary interactor = new ChangeDataInteractor(userRepository, successPresenter);
        interactor.executeSaveChanges(inputData);
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
        };

        ChangeDataInputBoundary interactor = new ChangeDataInteractor(userRepository, successPresenter);
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
        };

        ChangeDataInputBoundary interactor = new ChangeDataInteractor(userRepository, successPresenter);
        interactor.executeSaveChanges(inputData);

    }

    @Test
    public void passwordDoNotMatchTest() throws ClassNotFoundException {
        // Arrange
        String username = "username";
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        String repeatNewPassword = "differentNewPassword";
        String bio = "bio";

        ChangeDataAccessInterface mockRepository = mock(ChangeDataAccessInterface.class);
        ChangeDataOutputBoundary mockPresenter = mock(ChangeDataOutputBoundary.class);

        // Create user with correct old password
        User mockUser = userFactory.create("name", "username", "password", "bio", apiKeys, LocalDateTime.now());
        when(mockRepository.get(username)).thenReturn(mockUser);

        ChangeDataInput inputData = new ChangeDataInput(username, null, oldPassword, newPassword, repeatNewPassword, bio);
        ChangeDataInputBoundary interactor = new ChangeDataInteractor(mockRepository, mockPresenter);

        // Execute
        interactor.executeSaveChanges(inputData);

        // Assert
        verify(mockPresenter).prepareFailView("New passwords does not match.");
    }

}


