package use_case.login;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import use_case.change_user_data.InstagramAPIDataAccessInterface;

import static org.mockito.Mockito.*;

class LoginInteractorTest {

    @Test
    void testExecuteWhenUserDoesNotExist() {
        // Arrange
        LoginUserDataAccessInterface userDataAccessObject = mock(LoginUserDataAccessInterface.class);
        LoginOutputBoundary loginPresenter = mock(LoginOutputBoundary.class);
        InstagramAPIDataAccessInterface instagramAPIDataAccessInterface = mock(InstagramAPIDataAccessInterface.class);

        LoginInteractor loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter, instagramAPIDataAccessInterface);

        LoginInputData loginInputData = new LoginInputData("nonexistentUser", "password");

        // Stubbing: Return false when checking if the user exists
        when(userDataAccessObject.existsByName("nonexistentUser")).thenReturn(false);

        // Act
        loginInteractor.execute(loginInputData);

        // Assert
        // Verify that the fail view is prepared with the correct message
        verify(loginPresenter).prepareFailView("nonexistentUser: Account does not exist.");
    }

    // Add more tests for other branches in the execute method...
}
