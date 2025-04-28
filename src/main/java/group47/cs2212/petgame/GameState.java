package group47.cs2212.petgame;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents the state of the game, including the pet and its attributes.
 */
public class GameState implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private final Player player;
    private final long timestamp;
    private boolean gameRunning;
    private ParentalControls parentalControls;

    public GameState(Player player) {
        this.player = player;
        this.timestamp = System.currentTimeMillis();
        this.gameRunning = false;
        // Provide a default password when initializing parental controls
        this.parentalControls = new ParentalControls("1234"); // Use a String for the password
    }

    public Player getPlayer() {
        return player;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public void setGameRunning(boolean gameRunning) {
        this.gameRunning = gameRunning;
    }

    public ParentalControls getParentalControls() {
        return parentalControls;
    }

    public void setParentalControls(ParentalControls parentalControls) {
        this.parentalControls = parentalControls;
    }

    public void startGame() {
        if (parentalControls.canPlay()) {
            gameRunning = true;
            System.out.println("Game started.");
        } else {
            System.out.println("Time limit reached or outside allowed hours. Gameplay blocked.");
        }
    }

    @Override
    public String toString() {
        return "GameState{" +
                "player=" + player +
                ", timestamp=" + timestamp +
                ", gameRunning=" + gameRunning +
                '}';
    }
}
