package entity;

import org.junit.Test;

import static org.junit.Assert.*;

public class PasswordValidatorServiceTest {

    @Test
    public void testPasswordIsValid() {
        PasswordValidatorService validatorService = new PasswordValidatorService();

        // Test with a valid password
        assertTrue(validatorService.passwordIsValid("strongPassword"));

        // Test with a null password
        assertFalse(validatorService.passwordIsValid(null));

        // Test with a short password
        assertFalse(validatorService.passwordIsValid("weak"));
    }
}
