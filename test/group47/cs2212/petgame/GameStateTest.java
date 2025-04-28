package group47.cs2212.petgame;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for the GameState class.
 */
class GameStateTest {

    private Player player;

    @BeforeEach
    void setUp() {
        // Initialize a new Player object before each test
        player = new Player("P001", "TestPlayer", "testplayer@example.com");
        Pet pet = new Pet("Buddy", "Dog");
        player.setPet(pet);
    }

    @Test
    @DisplayName("Test GameState Initialization")
    void testGameStateInitialization() {
        GameState gameState = new GameState(player);

        assertNotNull(gameState.getPlayer(), "GameState should have a non-null player.");
        assertEquals(player, gameState.getPlayer(), "Player in GameState should match the provided player.");
        assertTrue(gameState.getTimestamp() > 0, "Timestamp should be greater than zero.");
    }

    @Test
    @DisplayName("Test Timestamp Accuracy")
    void testTimestampAccuracy() {
        long beforeCreation = System.currentTimeMillis();
        GameState gameState = new GameState(player);
        long afterCreation = System.currentTimeMillis();

        assertTrue(gameState.getTimestamp() >= beforeCreation,
                "Timestamp should be greater than or equal to the time before creation.");
        assertTrue(gameState.getTimestamp() <= afterCreation,
                "Timestamp should be less than or equal to the time after creation.");
    }

    @Test
    @DisplayName("Test toString Method")
    void testToStringMethod() {
        GameState gameState = new GameState(player);
        String expected = "GameState{pet=" + player.getPet() + ", timestamp=" + gameState.getTimestamp() + "}";

        assertEquals(expected, gameState.toString(), "toString() should return the expected string representation.");
    }
}
