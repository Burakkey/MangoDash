package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.signup.SignupController;
import interface_adapter.signup.SignupState;
import interface_adapter.signup.SignupViewModel;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;
import java.lang.reflect.Field;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class SignupViewTest {

    @Mock
    private SignupViewModel signupViewModel;
    @Mock
    private SignupController signupController;
    @Mock
    private ViewManagerModel viewManagerModel;

    private SignupView signupView;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        // Assuming that the SignupViewModel provides access to the current SignupState
        SignupState initialState = new SignupState();
        when(signupViewModel.getState()).thenReturn(initialState);

        signupView = new SignupView(signupController, signupViewModel, viewManagerModel);
    }

    @Test
    public void testSignupStateChange() {
        // Simulate user input
        signupView.nameInputField.setText("testName");
        signupView.usernameInputField.setText("testUser");
        signupView.passwordInputField.setText("testPass");
        signupView.repeatPasswordInputField.setText("testPass");

        // Simulate signup button click
        signupView.signUp.doClick();

        // Get the current state from the mock ViewModel
        SignupState currentState = signupViewModel.getState();

        // Verify that the state has been updated
        assertEquals("testName", currentState.getName());
        assertEquals("testUser", currentState.getUsername());
        assertEquals("testPass", currentState.getPassword());
        assertEquals("testPass", currentState.getRepeatPassword());

        // Verify that the controller's execute method is called with correct parameters
        verify(signupController, times(1)).execute("testName", "testUser", "testPass", "testPass");
    }

    @Test
    public void testCancelActionChangesView() throws NoSuchFieldException, IllegalAccessException {
        // Access the private 'cancel' button field using reflection
        Field cancelButtonField = SignupView.class.getDeclaredField("cancel");
        cancelButtonField.setAccessible(true); // Make the field accessible
        JButton cancelButton = (JButton) cancelButtonField.get(signupView); // Get the 'cancel' button instance

        // Simulate the cancel button click
        cancelButton.doClick();

        // Verify that the viewManagerModel's setActiveView method is called with "Home"
        verify(viewManagerModel, times(1)).setActiveView("Home");

        // Verify that the viewManagerModel's firePropertyChanged method is called
        verify(viewManagerModel, times(1)).firePropertyChanged();
    }



}
