package group47.cs2212.petgame;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class UserManagementTest {

    private UserManagement userManagement;

    @BeforeEach
    void setUp() {
        userManagement = new UserManagement();
    }

    @Test
    void testRegisterUser() {
        assertTrue(userManagement.register("test@example.com", "testuser", "password123"),
                "User should be registered successfully.");
        assertFalse(userManagement.register("test@example.com", "testuser", "password123"),
                "Registering the same user should fail.");
    }

    @Test
    void testLoginSuccess() {
        userManagement.register("test@example.com", "testuser", "password123");
        assertTrue(userManagement.login("testuser", "password123"),
                "Login should succeed with correct credentials.");
    }

    @Test
    void testLoginFailure() {
        assertFalse(userManagement.login("wronguser", "wrongpassword"),
                "Login should fail with incorrect credentials.");
    }

    @Test
    void testResetPassword() {
        userManagement.register("test@example.com", "testuser", "password123");
        assertTrue(userManagement.resetPassword("test@example.com", "newpassword"),
                "Password should be reset successfully.");
        assertTrue(userManagement.login("testuser", "newpassword"),
                "Login should succeed with the new password.");
    }
}
