package group47.cs2212.petgame;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Player class represents a player in the virtual pet game,
 * including personal details, scores, settings, inventory, and pet interactions.
 */
public class Player implements Serializable {
    private static final long serialVersionUID = 1L;

    private String playerID;
    private String username;
    private String email;
    private int highScore;
    private int currentScore;
    private boolean isLoggedIn;
    private Inventory inventory;
    private Pet pet;

    /**
     * Constructor for creating a new Player.
     *
     * @param playerID Unique identifier for the player.
     * @param username Player's username.
     * @param email    Player's email.
     */
    public Player(String playerID, String username, String email) {
        this.playerID = playerID;
        this.username = username;
        this.email = email;
        this.highScore = 0;
        this.currentScore = 0;
        this.isLoggedIn = false;
        this.inventory = new Inventory();
    }
    // Getters and Setters
    /**
     * Gets the unique player ID.
     *
     * @return The player ID.
     */
    public String getPlayerID() {
        return playerID;
    }

    /**
     * Gets the player's username.
     *
     * @return The player's username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets a new username for the player.
     *
     * @param username The new username for the player.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Gets the player's email.
     *
     * @return The player's email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets a new email for the player.
     *
     * @param email The new email for the player.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets the player's high score.
     *
     * @return The player's high score.
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Gets the player's current score.
     *
     * @return The player's current score.
     */
    public int getCurrentScore() {
        return currentScore;
    }

    /**
     * Checks if the player is logged in.
     *
     * @return True if the player is logged in, false otherwise.
     */
    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    /**
     * Logs the player in, setting the login status to true.
     */
    public void login() {
        this.isLoggedIn = true;
    }

    /**
     * Logs the player out, setting the login status to false.
     */
    public void logout() {
        this.isLoggedIn = false;
    }

    /**
     * Gets the player's inventory.
     *
     * @return The player's inventory.
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Gets the pet assigned to the player.
     *
     * @return The player's pet.
     */
    public Pet getPet() {
        return pet;
    }

    /**
     * Sets the pet for the player.
     *
     * @param pet The pet to assign to the player.
     */
    public void setPet(Pet pet) {
        this.pet = pet;
    }

    /**
     * Updates the player's current score and high score if applicable.
     *
     * @param scoreDelta The change in score.
     */
    public void updateScore(int scoreDelta) {
        if (this.currentScore + scoreDelta < 0) {
            this.currentScore = 0;
        } else {
            this.currentScore += scoreDelta;
        }
        if (this.currentScore > this.highScore) {
            this.highScore = this.currentScore;
        }
    }

    /**
     * Save the player's progress (stub for integration with file handling).
     */
    public void saveProgress() {
        System.out.println("Player progress saved for " + username);
    }

    /**
     * Load the player's progress (stub for integration with file handling).
     */
    public void loadProgress() {
        System.out.println("Player progress loaded for " + username);
    }
    @Override
    public String toString() {
        return "Player{" +
                "playerID='" + playerID + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", highScore=" + highScore +
                ", currentScore=" + currentScore +
                ", isLoggedIn=" + isLoggedIn +
                ", pet=" + (pet != null ? pet.getName() : "None") +
                '}';
    }
}
