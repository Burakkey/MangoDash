package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class LoginViewTest {

    @Mock
    private LoginViewModel loginViewModel;
    @Mock
    private LoginController loginController;
    @Mock
    private ViewManagerModel viewManagerModel;

    private LoginView loginView;
    private AutoCloseable mocks;

    @Before
    public void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
        when(loginViewModel.getComfortaaMedium()).thenReturn(new Font("SansSerif", Font.PLAIN, 12)); // Example font

        // Ensure that loginViewModel returns a non-null LoginState
        when(loginViewModel.getState()).thenReturn(new LoginState());

        loginView = new LoginView(loginViewModel, loginController, viewManagerModel);
    }

    @Test
    public void testLoginProcess() {
        // Simulate user input
        loginView.usernameInputField.setText("testUser");
        loginView.passwordInputField.setText("testPass");

        // Simulate login button click
        loginView.logIn.doClick();
        LoginState currentState = loginViewModel.getState();

        // Verify that the controller's execute method is called with correct parameters
        verify(loginController, times(1)).execute("testUser", "testPass");
        assertEquals("testUser", currentState.getUsername());
        assertEquals("testPass", currentState.getPassword());
    }
}