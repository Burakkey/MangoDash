package use_case.login;

import data_access.SQLiteUserDataAccessObject;
import entity.CommonUser;
import entity.CommonUserFactory;
import entity.User;
import org.junit.Before;
import org.junit.Test;
import use_case.change_user_data.InstagramAPIDataAccessInterface;

import static org.junit.Assert.*;

public class LoginInteractorTest {

    private LoginInteractor loginInteractor;
    private LoginUserDataAccessInterface userDataAccessObject;
    private LoginOutputBoundary loginPresenter;

    private InstagramAPIDataAccessInterface instagramAPIDataAccessInterface;


    /**
     * Set up the test environment
     */
    @Before
    public void setUp() {
        userDataAccessObject = new InMemoryUserDataAccessObject();
    }


    @Test
    public void execute_whenUserExists_shouldPrepareSuccessView() {
        LoginInputData input = new LoginInputData("nonexisting_user", "nonexisting_user_password");
        LoginOutputBoundary loginOutputBoundary = new LoginOutputBoundary() {
            @Override // Mock Presenter
            public void prepareSuccessView(LoginOutputData user) {

            }

            @Override
            public void prepareFailView(String error) {

            }
        };

        loginInteractor = new LoginInteractor(userDataAccessObject, loginOutputBoundary, instagramAPIDataAccessInterface);
        assertFalse(userDataAccessObject.existsByName(input.getUsername())); //
        loginInteractor.execute(input);
        assertFalse(userDataAccessObject.existsByName(input.getUsername())); // Check this user does not exist

    }
    /**
     * Test the execution of the login functionality when the user does not exist.
     */
    @Test
    public void execute_whenUserDoesNotExist_shouldPrepareFailView() {
        LoginInputData input = new LoginInputData("nonexisting_user", "nonexisting_user_password");
        LoginOutputBoundary loginOutputBoundary = new LoginOutputBoundary() {
            @Override // Mock Presenter
            public void prepareSuccessView(LoginOutputData user) {

            }

            @Override
            public void prepareFailView(String error) {

            }
        };

        loginInteractor = new LoginInteractor(userDataAccessObject, loginOutputBoundary, instagramAPIDataAccessInterface);
        assertFalse(userDataAccessObject.existsByName(input.getUsername())); //
        loginInteractor.execute(input);
        assertFalse(userDataAccessObject.existsByName(input.getUsername())); // Check this user does not exist

    }
}

