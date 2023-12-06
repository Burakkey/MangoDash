package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;
import interface_adapter.login_home.LoginHomeViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
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

        // Verify that loginViewModel returns a non-null LoginState
        when(loginViewModel.getState()).thenReturn(new LoginState());

        loginView = new LoginView(loginViewModel, loginController, viewManagerModel);
    }

    private JButton getButton(String buttonName) throws NoSuchFieldException, IllegalAccessException {
        Field field = LoginView.class.getDeclaredField(buttonName);
        field.setAccessible(true);
        return (JButton) field.get(loginView);
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

    @Test
    public void testBackButtonAction() throws NoSuchFieldException, IllegalAccessException {
        JButton backButton = getButton("back");

        // Simulate the back button click
        backButton.doClick();

        // Verify that the view changes to "Home"
        verify(viewManagerModel, times(1)).setActiveView("Home");
        verify(viewManagerModel, times(1)).firePropertyChanged();
    }

    @Test
    public void testPropertyChangeUpdatesFields() {
        LoginState newState = new LoginState();
        newState.setUsername("newUser");
        newState.setPassword("newPass");

        // Simulate property change event
        PropertyChangeEvent evt = new PropertyChangeEvent(this, "state", null, newState);
        loginView.propertyChange(evt);

        // Verify that the fields are updated correctly
        assertEquals("newUser", loginView.usernameInputField.getText());
        assertEquals("newPass", new String(loginView.passwordInputField.getPassword()));
    }

}