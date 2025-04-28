package group47.cs2212.petgame;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 test class for the Player class.
 */
class PlayerTest {

    private Player player;

    @BeforeEach
    void setUp() {
        // Initialize a new Player object before each test
        player = new Player("P001", "TestPlayer", "testplayer@example.com");
    }

    @Test
    @DisplayName("Test Player Initialization")
    void testPlayerInitialization() {
        assertEquals("P001", player.getPlayerID(), "Player ID should match the initialized value.");
        assertEquals("TestPlayer", player.getUsername(), "Username should match the initialized value.");
        assertEquals("testplayer@example.com", player.getEmail(), "Email should match the initialized value.");
        assertEquals(0, player.getHighScore(), "Initial high score should be zero.");
        assertEquals(0, player.getCurrentScore(), "Initial current score should be zero.");
        assertFalse(player.isLoggedIn(), "Player should not be logged in initially.");
        assertNotNull(player.getInventory(), "Player should have an initialized inventory.");
        assertNull(player.getPet(), "Player should not have a pet assigned initially.");
    }

    @Test
    @DisplayName("Test Login and Logout")
    void testLoginAndLogout() {
        player.login();
        assertTrue(player.isLoggedIn(), "Player should be logged in after calling login().");

        player.logout();
        assertFalse(player.isLoggedIn(), "Player should be logged out after calling logout().");
    }

    @Test
    @DisplayName("Test Score Updates")
    void testScoreUpdates() {
        player.updateScore(50);
        assertEquals(50, player.getCurrentScore(), "Current score should reflect the score update.");
        assertEquals(50, player.getHighScore(), "High score should update to the new highest score.");

        player.updateScore(-20);
        assertEquals(30, player.getCurrentScore(), "Current score should decrease by 20.");

        player.updateScore(-50);
        assertEquals(0, player.getCurrentScore(), "Current score should not go below zero.");
    }

    @Test
    @DisplayName("Test High Score Maintenance")
    void testHighScoreMaintenance() {
        player.updateScore(100);
        assertEquals(100, player.getHighScore(), "High score should update to the highest score achieved.");

        player.updateScore(-50);
        assertEquals(100, player.getHighScore(), "High score should remain unchanged when current score decreases.");
    }

    @Test
    @DisplayName("Test Inventory Management")
    void testInventoryManagement() {
        Inventory inventory = player.getInventory();
        Item testItem = new Item("Apple", 5, "Food");

        inventory.addItem(testItem);
        assertTrue(inventory.hasItem("Apple"), "Inventory should contain the added item.");
        assertTrue(inventory.removeItem("Apple"), "Item should be successfully removed from inventory.");
        assertFalse(inventory.hasItem("Apple"), "Inventory should no longer contain the removed item.");
    }

    @Test
    @DisplayName("Test Pet Assignment")
    void testPetAssignment() {
        Pet pet = new Pet("Buddy", "Dog");
        player.setPet(pet);

        assertNotNull(player.getPet(), "Player should have an assigned pet.");
        assertEquals("Buddy", player.getPet().getName(), "Assigned pet's name should match.");
        assertEquals("Dog", player.getPet().getType(), "Assigned pet's type should match.");
    }

    @Test
    @DisplayName("Test Save Progress Stub")
    void testSaveProgress() {
        assertDoesNotThrow(player::saveProgress, "saveProgress() should not throw any exceptions.");
    }

    @Test
    @DisplayName("Test Load Progress Stub")
    void testLoadProgress() {
        assertDoesNotThrow(player::loadProgress, "loadProgress() should not throw any exceptions.");
    }

    @Test
    @DisplayName("Test toString Method")
    void testToStringMethod() {
        Pet pet = new Pet("Buddy", "Dog");
        player.setPet(pet);

        String expectedString = "Player{" +
                "playerID='P001', " +
                "username='TestPlayer', " +
                "email='testplayer@example.com', " +
                "highScore=0, " +
                "currentScore=0, " +
                "isLoggedIn=false, " +
                "pet=Buddy" +
                '}';
        assertEquals(expectedString, player.toString(), "toString() should return the expected string representation.");
    }
}
