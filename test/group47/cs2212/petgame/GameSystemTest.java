package group47.cs2212.petgame;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.List;

/**
 * JUnit 5 test class for the GameSystem class.
 */
class GameSystemTest {

    private static final String TEST_SAVE_DIR = "test_saves";
    private GameSystem gameSystem;

    @BeforeEach
    void setUp() {
        // Initialize GameSystem with a test save directory
        gameSystem = new GameSystem(TEST_SAVE_DIR);
    }

    @AfterEach
    void tearDown() {
        // Clean up the test save directory after each test
        File dir = new File(TEST_SAVE_DIR);
        if (dir.exists()) {
            for (File file : dir.listFiles()) {
                file.delete();
            }
            dir.delete();
        }
    }

    @Test
    @DisplayName("Test saveGame() - Successful save")
    void testSaveGame_Success() {
        GameState state = createTestGameState("Buddy", "TestPlayer");
        boolean result = gameSystem.saveGame(state);

        assertTrue(result, "Game should be saved successfully.");
        File saveFile = new File(TEST_SAVE_DIR + File.separator + "Buddy.sav");
        assertTrue(saveFile.exists(), "Save file should exist after saving.");
    }

    @Test
    @DisplayName("Test loadGame() - Successful load")
    void testLoadGame_Success() {
        GameState state = createTestGameState("Buddy", "TestPlayer");
        gameSystem.saveGame(state);

        GameState loadedState = gameSystem.loadGame("Buddy.sav");

        assertNotNull(loadedState, "Loaded game state should not be null.");
        assertEquals(state.getPlayer().getUsername(), loadedState.getPlayer().getUsername(),
                "Loaded player's username should match the saved player's username.");
        assertEquals(state.getPlayer().getPet().getName(), loadedState.getPlayer().getPet().getName(),
                "Loaded pet's name should match the saved pet's name.");
    }

    @Test
    @DisplayName("Test loadGame() - File not found")
    void testLoadGame_FileNotFound() {
        GameState loadedState = gameSystem.loadGame("NonExistent.sav");

        assertNull(loadedState, "Loaded game state should be null if the file does not exist.");
    }

    @Test
    @DisplayName("Test deleteSave() - Successful delete")
    void testDeleteSave_Success() {
        GameState state = createTestGameState("Buddy", "TestPlayer");
        gameSystem.saveGame(state);

        boolean result = gameSystem.deleteSave("Buddy.sav");

        assertTrue(result, "Save file should be deleted successfully.");
        File saveFile = new File(TEST_SAVE_DIR + File.separator + "Buddy.sav");
        assertFalse(saveFile.exists(), "Save file should no longer exist after deletion.");
    }

    @Test
    @DisplayName("Test deleteSave() - File not found")
    void testDeleteSave_FileNotFound() {
        boolean result = gameSystem.deleteSave("NonExistent.sav");

        assertFalse(result, "Delete operation should return false if the file does not exist.");
    }

    @Test
    @DisplayName("Test listSaveFiles() - Multiple saves")
    void testListSaveFiles() {
        gameSystem.saveGame(createTestGameState("Buddy", "TestPlayer1"));
        gameSystem.saveGame(createTestGameState("Max", "TestPlayer2"));

        List<String> saveFiles = gameSystem.listSaveFiles();

        assertNotNull(saveFiles, "Save file list should not be null.");
        assertTrue(saveFiles.contains("Buddy.sav"), "Save file list should include Buddy.sav.");
        assertTrue(saveFiles.contains("Max.sav"), "Save file list should include Max.sav.");
        assertEquals(2, saveFiles.size(), "Save file list should contain exactly 2 files.");
    }

    @Test
    @DisplayName("Test saveGame() - Directory creation")
    void testSaveGame_DirectoryCreation() {
        File dir = new File(TEST_SAVE_DIR);
        if (dir.exists()) {
            dir.delete(); // Ensure directory doesn't exist
        }

        GameState state = createTestGameState("Buddy", "TestPlayer");
        boolean result = gameSystem.saveGame(state);

        assertTrue(result, "Game should be saved successfully.");
        assertTrue(dir.exists(), "Save directory should be created if it does not exist.");
    }

    /**
     * Helper method to create a test GameState object.
     *
     * @param petName    Name of the player's pet.
     * @param playerName Name of the player.
     * @return A GameState object.
     */
    private GameState createTestGameState(String petName, String playerName) {
        Pet pet = new Pet(petName, "Dog");
        Player player = new Player("P001", playerName, playerName + "@example.com");
        player.setPet(pet);
        return new GameState(player);
    }
}

