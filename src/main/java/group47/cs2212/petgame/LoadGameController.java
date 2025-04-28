package group47.cs2212.petgame;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * Controller for the "Load Game" functionality in the Pet Game application.
 * This class manages the user interface for loading, deleting, and navigating through saved games.
 * It interacts with {@link GameManagement} to perform file operations and game actions.
 */
public class LoadGameController {

    @FXML
    private ListView<String> savedGamesList;

    @FXML
    private Button loadButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button backButton;

    @FXML
    private ImageView backgroundImageView;

    private UILoader uiLoader;
    private GameManagement gameManagement;

    /**
     * Initializes the controller by setting up the UI components and loading saved games.
     */
    public void initialize() {
        uiLoader = new UILoader();
        gameManagement = new GameManagement("saveDirectory");
        populateSavedGamesList();
        setBackground();
    }

    /**
     * Populates the list of saved games from the {@link GameManagement} instance.
     * The list is displayed in the {@link ListView} for the user to choose from.
     */
    private void populateSavedGamesList() {
        ObservableList<String> savedGames = FXCollections.observableArrayList(gameManagement.listSavedGames());
        savedGamesList.setItems(savedGames);
    }

    /**
     * Sets the background image of the scene.
     * The background is loaded from the file system, and an error is logged if the image is not found.
     */
    private void setBackground() {
        String imagePath = "/group47/cs2212/petgame/background_pictures/default_event_bg.png";
        try {
            Image backgroundImage = new Image(getClass().getResourceAsStream(imagePath));
            backgroundImageView.setImage(backgroundImage);
        } catch (Exception e) {
            System.err.println("Error loading background image: " + imagePath);
            e.printStackTrace();
        }
    }

    /**
     * Handles the "Load Game" button click event.
     * If a saved game is selected, it attempts to load the game. A success or error message is displayed.
     *
     * @param event The action event triggered by the "Load Game" button.
     */
    @FXML
    private void handleLoadGame(ActionEvent event) {
        String selectedGame = savedGamesList.getSelectionModel().getSelectedItem();
        if (selectedGame == null) {
            showAlert("No Game Selected", "Please select a game to load.", AlertType.WARNING);
            return;
        }

        if (gameManagement.loadGame(selectedGame)) {
            showAlert("Game Loaded", "The game has been loaded successfully.", AlertType.INFORMATION);
            try {
                //uiLoader.changeUI(event, "main.fxml", gameManagement);
                UILoader petuiLoader = new UILoader() {
                    @Override
                    protected void applyControllerLogic(Object controller, Object controllerData) {
                        if (controller instanceof PetController petController) {
                            petController.setUser((Player) controllerData);
                        }
                    }
                };

                petuiLoader.changeUI(event, "Pet.fxml", gameManagement.getCurrentPlayer());
            } catch (Exception e) {
                showAlert("Navigation Error", "Failed to navigate to the main menu.", AlertType.ERROR);
            }
        } else {
            showAlert("Load Failed", "Failed to load the selected game.", AlertType.ERROR);
        }
    }

    /**
     * Handles the "Delete Game" button click event.
     * If a saved game is selected, it attempts to delete the game. A success or error message is displayed.
     *
     * @param event The action event triggered by the "Delete Game" button.
     */
    @FXML
    private void handleDeleteGame(ActionEvent event) {
        String selectedGame = savedGamesList.getSelectionModel().getSelectedItem();
        if (selectedGame == null) {
            showAlert("No Game Selected", "Please select a game to delete.", AlertType.WARNING);
            return;
        }

        if (gameManagement.deleteSave(selectedGame)) {
            showAlert("Game Deleted", "The selected game has been deleted successfully.", AlertType.INFORMATION);
            populateSavedGamesList();
        } else {
            showAlert("Delete Failed", "The selected game could not be deleted.", AlertType.ERROR);
        }
    }

    /**
     * Handles the "Back to Menu" button click event.
     * Navigates the user back to the main menu of the game.
     *
     * @param event The action event triggered by the "Back to Menu" button.
     */
    @FXML
    private void handleBackToMenu(ActionEvent event) {
        try {
            uiLoader.changeUI(event, "main.fxml", null);
        } catch (Exception e) {
            showAlert("Navigation Error", "Failed to navigate to the main menu.", AlertType.ERROR);
        }
    }

    /**
     * Displays an alert dialog with a specified title, message, and alert type.
     *
     * @param title   The title of the alert dialog.
     * @param message The message to be displayed in the alert dialog.
     * @param type    The type of alert (e.g., WARNING, ERROR, INFORMATION).
     */
    private void showAlert(String title, String message, AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
