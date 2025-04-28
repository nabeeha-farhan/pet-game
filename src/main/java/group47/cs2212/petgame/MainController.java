package group47.cs2212.petgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller for the main page of the Pet Game application.
 * <br><br>
 * This controller is responsible for managing the main game UI and handling user interactions
 * such as navigating to different menus, loading images, and closing the application.
 * <br><br>
 * The following actions are handled by this controller:
 * <ul>
 *   <li>Opening the New Game menu</li>
 *   <li>Opening the Tutorial</li>
 *   <li>Opening the Parental Controls menu</li>
 *   <li>Opening the Load Game menu</li>
 *   <li>Opening the Settings menu</li>
 *   <li>Exiting the game</li>
 * </ul>
 * <br><br>
 * <b>Example usage:</b>
 * <pre>
 * {@code
 * MainController controller = new MainController();
 * controller.openNewGame(event);
 * }
 * </pre>
 *
 * @version 1.0
 */
public class MainController {

    /** UILoader object for loading UI */
    private UILoader uiLoader;

    /**
     * Initializes the Controller
     * <p>
     * Initializes the controller with the UILoader to navigate the game.
     * </p>
     */
    public void initialize() {
        uiLoader = new UILoader();
    }

    /**
     * Opens the New Game menu.
     *
     * @param event detects the button clicked, used for the UILoader
     * @throws LoadUIException Throws a LoadUIException signifying a failed UI Load operation
     */
    @FXML
    private void openNewGame(ActionEvent event) throws LoadUIException {
        try{
            uiLoader.changeUI(event, "NewGame.fxml", null);
        } catch (Exception e) {
            System.out.println("Error loading new game: " + e.getMessage());

            throw new LoadUIException("Failed loading new game", e);
        }
    }

    /**
     * Opens the Tutorial.
     *
     * @param event detects the button clicked, used for the UILoader
     * @throws LoadUIException Throws a LoadUIException signifying a failed UI Load operation
     */
    @FXML
    private void openTutorial(ActionEvent event) throws LoadUIException {
        try{
            uiLoader.changeUI(event, "Tutorial.fxml", null);
        } catch (Exception e) {
            System.out.println("Error loading new game: " + e.getMessage());

            throw new LoadUIException("Failed loading new game", e);
        }
    }

    /**
     * Opens the Parental Controls menu.
     *
     * @param event detects the button clicked, used for the UILoader
     * @throws LoadUIException Throws a LoadUIException signifying a failed UI Load operation
     */
    @FXML
    private void openParentalControls(ActionEvent event) throws LoadUIException {
        try{
            uiLoader.changeUI(event, "ParentalControls.fxml", null);
        } catch (Exception e) {
            System.out.println("Error loading new game: " + e.getMessage());

            throw new LoadUIException("Failed loading new game", e);
        }
    }

    /**
     * Opens the Load Game menu.
     *
     * @param event detects the button clicked, used for the UILoader
     * @throws LoadUIException Throws a LoadUIException signifying a failed UI Load operation
     */
    @FXML
    private void openLoadGame(ActionEvent event) throws LoadUIException {
        try{
            uiLoader.changeUI(event, "LoadGame.fxml", null);
        } catch (Exception e) {
            System.out.println("Error loading new game: " + e.getMessage());

            throw new LoadUIException("Failed loading new game", e);
        }
    }

    /**
     * Opens the Settings.
     *
     * @param event detects the button clicked, used for the UILoader
     * @throws LoadUIException Throws a LoadUIException signifying a failed UI Load operation
     */
    @FXML
    private void openSettings(ActionEvent event) throws LoadUIException {
        try{
            uiLoader.changeUI(event, "Settings.fxml", null);
        } catch (Exception e) {
            System.out.println("Error loading new game: " + e.getMessage());

            throw new LoadUIException("Failed loading new game", e);
        }
    }

    /**
     * Closes the game
     *
     * @param event detects the button clicked, used for closing the application
     */
    @FXML
    private void exit(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
