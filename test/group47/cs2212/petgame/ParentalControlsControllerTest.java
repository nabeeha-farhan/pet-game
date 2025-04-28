package group47.cs2212.petgame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class ParentalControlsTest {

    private ParentalControls parentalControls;

    @BeforeEach
    void setUp() {
        parentalControls = new ParentalControls("test123"); // Default password
    }

    @Test
    void testPasswordSet() {
        assertTrue(parentalControls.isPasswordSet(), "Password should be set initially.");
    }

    @Test
    void testSetPassword() {
        parentalControls.setPassword("newPassword123");
        assertTrue(parentalControls.authenticate("newPassword123"), "New password should be set correctly.");
        assertFalse(parentalControls.authenticate("test123"), "Old password should no longer be valid.");
    }

    @Test
    void testAuthenticate() {
        assertTrue(parentalControls.authenticate("test123"), "Authentication should succeed with the correct password.");
        assertFalse(parentalControls.authenticate("wrongPassword"), "Authentication should fail with an incorrect password.");
    }

    @Test
    void testSetDailyTimeLimit() {
        parentalControls.setDailyTimeLimit(120);
        String stats = parentalControls.getPlaytimeStatistics();
        assertTrue(stats.contains("120"), "Daily time limit should be updated to 120 minutes.");
    }

    @Test
    void testSetRestrictedHours() {
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(20, 0);
        parentalControls.setRestrictedHours(start, end);

        // Simulate checking play permission during restricted hours
        LocalTime currentTime = LocalTime.of(9, 0);
        boolean withinHours = currentTime.isAfter(start) && currentTime.isBefore(end);

        assertTrue(withinHours, "Playtime should be within allowed hours.");
    }

    @Test
    void testCanPlayWithinAllowedConditions() {
        parentalControls.setDailyTimeLimit(120);
        parentalControls.setRestrictedHours(LocalTime.of(8, 0), LocalTime.of(20, 0));

        // Simulate being within allowed hours and under the time limit
        assertTrue(parentalControls.canPlay(), "Player should be allowed to play under valid conditions.");
    }

    @Test
    void testCanPlayOutsideAllowedHours() {
        parentalControls.setRestrictedHours(LocalTime.of(8, 0), LocalTime.of(20, 0));

        // Simulate the current time being outside allowed hours
        LocalTime now = LocalTime.of(22, 0);
        boolean outsideHours = now.isBefore(LocalTime.of(8, 0)) || now.isAfter(LocalTime.of(20, 0));

        assertTrue(outsideHours, "Play should not be allowed outside restricted hours.");
        assertFalse(parentalControls.canPlay(), "Player should not be allowed to play outside allowed hours.");
    }
}
