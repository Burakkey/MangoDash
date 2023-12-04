package use_case.switchView;

import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import use_case.login.LoginUserDataAccessInterface;

import java.time.LocalDateTime;

import static org.junit.Assert.*;

public class SwitchViewInteractorTest {
    private CommonUserFactory userFactory;
    @Before
    public void setUp(){
        userFactory = new CommonUserFactory();
    }


    @Test
    public void SuccessfulLogout() throws ClassNotFoundException {
        User user = userFactory.create("name", "username", "password", "bio", null, LocalDateTime.now());
        LoginUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        userRepository.save(user);
        // TRY LOGIN

        SwitchViewInputData inputData = new SwitchViewInputData("Logout");

        SwitchViewOutputBoundary successPresenter = new SwitchViewOutputBoundary() {
            @Override
            public void prepareLogoutView() {
                assertEquals("", user.getUserName());
                assertEquals("", user.getPassword());
                assertNotNull(user.getCreationTime()); // any creation time is fine.
            }
        };

        SwitchViewInputBoundary interactor = new SwitchViewInteractor(successPresenter);
        interactor.execute(inputData);
    }

    @Test
    public void FailLogout() throws ClassNotFoundException {
        User user = userFactory.create("name", "username", "password", "bio", null, LocalDateTime.now());
        LoginUserDataAccessInterface userRepository = new SQLiteUserDataAccessObject("testusers.db", userFactory);
        userRepository.save(user);

        SwitchViewInputData inputData = new SwitchViewInputData("Logout");

        SwitchViewOutputBoundary successPresenter = new SwitchViewOutputBoundary() {
            @Override
            public void prepareLogoutView() {
                assertEquals("username", user.getUserName());
                assertEquals("password", user.getPassword());
                assertNotNull(user.getCreationTime()); // any creation time is fine.
            }
        };

        SwitchViewInputBoundary interactor = new SwitchViewInteractor(successPresenter);
        interactor.execute(inputData);
    }

}