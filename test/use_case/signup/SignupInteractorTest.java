package use_case.signup;

import data_access.InMemoryUserDataAccessObject;
import data_access.SQLiteUserDataAccessObject;
import entity.CommonUserFactory;
import entity.User;
import entity.UserFactory;
import org.junit.Before;
import org.junit.Test;
import use_case.login.LoginInputData;
import use_case.login.LoginInteractor;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

import static org.junit.Assert.*;

public class SignupInteractorTest {


    private SignupUserDataAccessInterface userDataAccessObject;



    private UserFactory userFactory;

    private SignupInteractor signupInteractor;

    @Before
    public void setUp() {

        userDataAccessObject = new InMemoryUserDataAccessObject();
        userFactory = new CommonUserFactory();
    }

    @Test
    public void prepareSuccessViewTest() {
        SignupInputData input = new SignupInputData("John Doe", "john.doe", "password", "wrongPassword", null);
        SignupOutputBoundary userPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {

            }

            @Override
            public void prepareFailView(String error) {

            }
        };
        signupInteractor = new SignupInteractor(userDataAccessObject, userPresenter, userFactory);


        assertFalse(userDataAccessObject.existsByName(input.getUsername())); //
        signupInteractor.execute(input);
        assertTrue(userDataAccessObject.existsByName(input.getUsername())); // Check this user exists



    }

    @Test
    void successTest() {
        SignupInputData inputData = new SignupInputData("John Doe", "john.doe", "password", "wrongPassword", null);
        SignupUserDataAccessInterface userRepository = new InMemoryUserDataAccessObject();

        // This creates a successPresenter that tests whether the test case is as we expect.
        SignupOutputBoundary successPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {
                // 2 things to check: the output data is correct, and the user has been created in the DAO.
                assertEquals("Paul", user.getUsername());
                assertNotNull(user.getCreationTime()); // any creation time is fine.
                assertTrue(userRepository.existsByName("Paul"));
            }

            @Override
            public void prepareFailView(String error) {
                fail("Use case failure is unexpected.");
            }
        };

        SignupInputBoundary interactor = new SignupInteractor(userRepository, successPresenter, new CommonUserFactory());
        interactor.execute(inputData);
    }

    @Test
    public void execute_passwordsDoNotMatch_shouldPrepareFailView() {
        SignupInputData input = new SignupInputData("John Doe", "john.doe", "password", "wrongPassword", null);
        SignupOutputBoundary userPresenter = new SignupOutputBoundary() {
            @Override
            public void prepareSuccessView(SignupOutputData user) {

            }

            @Override
            public void prepareFailView(String error) {

            }
        };
        signupInteractor = new SignupInteractor(userDataAccessObject, userPresenter, userFactory);



        // assertNull(); // userPresenter.prepareFailView("Passwords don't match.");
        // assertEquals(userPresenter.prepareFailView("Passwords don't match."), signupInteractor.execute(input));
    }
}