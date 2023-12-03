package use_case.login;

import entity.CommonUserFactory;
import entity.SocialMediaStats.InstagramStats;
import entity.User;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mock;
import use_case.change_user_data.InstagramAPIDataAccessInterface;
import use_case.login.*;

import java.net.MalformedURLException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LoginInteractorTest {

    @Mock
    private LoginUserDataAccessInterface userDataAccessObject;

    @Mock
    private LoginOutputBoundary loginPresenter;

    @Mock
    private InstagramAPIDataAccessInterface instagramAPIDataAccessInterface;

    private LoginInteractor loginInteractor;

    @Before
    public void setUp() {
        userDataAccessObject = mock(LoginUserDataAccessInterface.class);
        loginPresenter = mock(LoginOutputBoundary.class);
        instagramAPIDataAccessInterface = mock(InstagramAPIDataAccessInterface.class);

        loginInteractor = new LoginInteractor(userDataAccessObject, loginPresenter, instagramAPIDataAccessInterface);
    }

    @Test
    public void execute_whenUserDoesNotExist_shouldPrepareFailView() {
        // Arrange
        LoginInputData input = new LoginInputData("nonexistent_user", "password");
        when(userDataAccessObject.existsByName("nonexistent_user")).thenReturn(false);

        // Act
        loginInteractor.execute(input);

        // Assert
        verify(loginPresenter).prepareFailView("nonexistent_user: Account does not exist.");
    }
}

