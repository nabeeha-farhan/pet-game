package group47.cs2212.petgame;

import java.util.List;

/**
 * Manages the overall game flow, including starting, pausing, resuming, saving, and loading games.
 * It also handles interactions with the current player and their pet, including updating pet attributes.
 */
public class GameManagement {
    private Player currentPlayer;
    private boolean gameRunning;
    private GameSystem gameSystem;
    private GameState currentState;

    /**
     * Constructor for initializing the game management system.
     * It sets up the game system with the given file path for saving/loading game progress.
     *
     * @param saveFilePath The directory or path where game saves are stored.
     */
    public GameManagement(String saveFilePath) {
        gameSystem = new GameSystem(saveFilePath);
        currentPlayer = null; // Initially, no pet is created
        gameRunning = false;
        currentState = null;
    }

    /**
     * Starts a new game with the specified player.
     * A new pet is created for the player, and the game state is initialized.
     *
     * @param newPlayer The new player to start the game with.
     */
    public void startNewGame(Player newPlayer) {
        currentPlayer = newPlayer; // Create a new Pet object
        currentState = new GameState(currentPlayer); // Save the pet into the game state
        gameRunning = true;
        System.out.println("New game started with pet: " + currentPlayer.getPet().getName());
    }

    /**
     * Pauses the current game.
     * The game will be stopped, and no further updates can occur until resumed.
     */
    public void pauseGame() {
        gameRunning = false;
    }

    /**
     * Resumes the game from its paused state.
     * The game continues as before from where it was paused.
     */
    public void resumeGame() {
        gameRunning = true;
    }

    /**
     * Ends the current game and saves the player's progress.
     * The game will be paused, and the current game state will be saved.
     */
    public void endGame() {
        gameRunning = false;
        saveGame();
        System.out.println("Game ended.");
    }

    /**
     * Saves the current game state to a file.
     * The game state will only be saved if it is valid (i.e., the state is not null).
     *
     * @return {@code true} if the game state was saved successfully, {@code false} otherwise.
     */
    public boolean saveGame() {
        if (currentState != null) {
            return gameSystem.saveGame(currentState);
        }
        System.err.println("No game state to save.");
        return false;
    }

    /**
     * Loads a previously saved game from a file.
     * The specified save file will be loaded, and the current state and player will be updated accordingly.
     *
     * @param saveFileName The name of the save file to load.
     * @return {@code true} if the game was loaded successfully, {@code false} otherwise.
     */
    public boolean loadGame(String saveFileName) {
        GameState loadedState = gameSystem.loadGame(saveFileName);
        if (loadedState != null) {
            currentState = loadedState;
            currentPlayer = currentState.getPlayer();
            gameRunning = true;
            System.out.println("Game loaded with pet: " + currentPlayer.getPet().getName());
            return true;
        }
        return false;
    }

    /**
     * Deletes a saved game file.
     * The specified save file will be deleted from the system.
     *
     * @param saveFileName The name of the save file to delete.
     * @return {@code true} if the save file was deleted successfully, {@code false} otherwise.
     */
    public boolean deleteSave(String saveFileName) {
        return gameSystem.deleteSave(saveFileName);
    }

    /**
     * Lists all available saved games from the system.
     *
     * @return A list of saved game file names.
     */
    public List<String> listSavedGames() {
        return gameSystem.listSaveFiles();
    }

    /**
     * Retrieves the pet associated with the current player.
     *
     * @return The pet of the current player.
     */
    public Pet getCurrentPet() {
        return currentPlayer.getPet();
    }

    /**
     * Retrieves the current player.
     *
     * @return The current player of the game.
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Checks whether the game is currently running.
     *
     * @return {@code true} if the game is running, {@code false} otherwise.
     */
    public boolean isGameRunning() {
        return gameRunning;
    }

    /**
     * Updates the pet's hunger attribute by a specified amount.
     *
     * @param change The amount to increase or decrease the pet's hunger.
     */
    public void updatePetHunger(int change) {
        currentPlayer.getPet().updateHunger(change);
    }

    /**
     * Updates the pet's happiness attribute by a specified amount.
     *
     * @param change The amount to increase or decrease the pet's happiness.
     */

    public void updatePetHappiness(int change) {
        currentPlayer.getPet().updateHappiness(change);
    }

    /**
     * Updates the pet's energy attribute by a specified amount.
     *
     * @param change The amount to increase or decrease the pet's energy.
     */
    public void updatePetEnergy(int change) {
        currentPlayer.getPet().updateEnergy(change);
    }

    /**
     * Checks if the pet is happy based on its current happiness attribute.
     *
     * @return {@code true} if the pet is happy, {@code false} otherwise.
     */
    public boolean checkPetHappiness() {
        return currentPlayer.getPet().isHappy();
    }

    /**
     * Checks if the pet is healthy based on its current health status.
     *
     * @return {@code true} if the pet is healthy, {@code false} otherwise.
     */
    public boolean checkPetHealth() {
        return currentPlayer.getPet().isHealthy();
    }

    /**
     * Puts the pet to sleep, if needed, based on the pet's current attributes.
     */
    public void putPetToSleep() {
        currentPlayer.getPet().putToSleep();
    }

    /**
     * Retrieves the pet's attributes (e.g., hunger, happiness, energy) as an array.
     *
     * @return An array of integers representing the pet's attributes.
     */
    public Integer[] getPetAttributes() {
        return currentPlayer.getPet().getAttributes();
    }
}
