package group47.cs2212.petgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * A utility class responsible for loading FXML files and managing UI transitions.
 * This class facilitates dynamic data passing, scene switching, and ensuring
 * consistent UI management across the application.
 */
public class UILoader {

    /**
     * Applies controller logic after loading an FXML file.
     * This method can be overridden to inject data into the controller.
     *
     * @param controller     The controller associated with the loaded FXML.
     * @param controllerData The data to pass to the controller.
     */
    protected void applyControllerLogic(Object controller, Object controllerData) {
        // To be overridden by specific implementations if needed.
    }

    /**
     * Changes the UI by loading a new FXML file and updating the current scene.
     *
     * @param event           The ActionEvent triggering the UI change.
     * @param fxmlFile        The FXML file to load.
     * @param controllerData  Data to pass to the controller.
     * @throws Exception If the FXML file cannot be loaded.
     */
    public void changeUI(ActionEvent event, String fxmlFile, Object controllerData) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        Parent root = loader.load();
        Object controller = loader.getController();

        if (controller != null) {
            applyControllerLogic(controller, controllerData);
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }

    /**
     * Displays a notification dialog to the user.
     *
     * @param title   The title of the notification.
     * @param message The message of the notification.
     * @param type    The type of notification (e.g., "info", "warning", "error").
     */
    public void displayNotification(String title, String message, String type) {
        Alert alert;
        switch (type.toLowerCase()) {
            case "warning":
                alert = new Alert(AlertType.WARNING);
                break;
            case "error":
                alert = new Alert(AlertType.ERROR);
                break;
            case "info":
            default:
                alert = new Alert(AlertType.INFORMATION);
                break;
        }
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Closes the current scene.
     *
     * @param currentNode The node triggering the close operation.
     */
    public void closeScene(Node currentNode) {
        Stage stage = (Stage) currentNode.getScene().getWindow();
        stage.close();
    }

    /**
     * Loads a specific FXML file without switching scenes, useful for pop-ups or partial views.
     *
     * @param fxmlFile The FXML file to load.
     * @return The loaded Parent node.
     * @throws Exception If the FXML file cannot be loaded.
     */
    public Parent loadFXML(String fxmlFile) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        return loader.load();
    }

    /**
     * Updates the progress dynamically in a UI controller.
     *
     * @param controller The controller handling the progress bar.
     * @param progress   The progress value to set (0.0 to 1.0).
     */
    public void updateProgressBar(Object controller, double progress) {
        if (controller instanceof EventController) {
            ((EventController) controller).updateProgress(progress);
        } else {
            System.out.println("Controller does not support progress updates.");
        }
    }

    /**
     * Loads and sets an image for an ImageView dynamically.
     *
     * @param imageView The ImageView to set the image for.
     * @param imagePath The relative path of the image to load.
     */
    public void setImage(ImageView imageView, String imagePath) {
        try {
            Image image = new Image(getClass().getResourceAsStream(imagePath));
            imageView.setImage(image);
        } catch (Exception e) {
            System.out.println("Error loading image: " + imagePath);
            e.printStackTrace();
        }
    }

    /**
     * Displays a notification dialog with an optional image.
     *
     * @param title       The title of the notification.
     * @param message     The message of the notification.
     * @param type        The type of notification (e.g., "info", "warning", "error").
     * @param imagePath   The relative path of the image to display in the notification (optional).
     */
    public void displayNotification(String title, String message, String type, String imagePath) {
        Alert alert;
        switch (type.toLowerCase()) {
            case "warning":
                alert = new Alert(AlertType.WARNING);
                break;
            case "error":
                alert = new Alert(AlertType.ERROR);
                break;
            case "info":
            default:
                alert = new Alert(AlertType.INFORMATION);
                break;
        }

        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        if (imagePath != null) {
            try {
                Image image = new Image(getClass().getResourceAsStream(imagePath));
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(50);
                imageView.setFitWidth(50);
                alert.setGraphic(imageView);
            } catch (Exception e) {
                System.out.println("Error loading notification image: " + imagePath);
                e.printStackTrace();
            }
        }

        alert.showAndWait();
    }
}
