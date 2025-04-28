package group47.cs2212.petgame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The GameSystem class handles saving, loading, and managing game states.
 */
public class GameSystem {
    private String saveDirectory;

    /**
     * Constructor for GameSystem.
     *
     * @param saveDirectory The directory where save files are stored.
     */
    public GameSystem(String saveDirectory) {
        this.saveDirectory = saveDirectory;
        File dir = new File(saveDirectory);
        if (!dir.exists()) {
            dir.mkdirs(); // Create directory if it doesn't exist
        }
    }

    /**
     * Saves the game state to a file.
     *
     * @param state The GameState object to save.
     * @return True if the save was successful, false otherwise.
     */
    public boolean saveGame(GameState state) {
        String fileName = saveDirectory + File.separator + state.getPlayer().getPet().getName() + ".sav";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(state);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Loads a game state from a file.
     *
     * @param fileName The name of the save file to load.
     * @return The loaded GameState object, or null if the load failed.
     */
    public GameState loadGame(String fileName) {
        String filePath = saveDirectory + File.separator + fileName;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (GameState) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Deletes a save file.
     *
     * @param fileName The name of the save file to delete.
     * @return True if the file was successfully deleted, false otherwise.
     */
    public boolean deleteSave(String fileName) {
        File file = new File(saveDirectory + File.separator + fileName);
        return file.delete();
    }

    /**
     * Lists all saved game files in the directory.
     *
     * @return A list of save file names.
     */
    public List<String> listSaveFiles() {
        File dir = new File(saveDirectory);
        String[] files = dir.list((d, name) -> name.endsWith(".sav"));
        if (files != null) {
            return new ArrayList<>(List.of(files));
        }
        return new ArrayList<>();
    }
}
