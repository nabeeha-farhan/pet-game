package group47.cs2212.petgame;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.embed.swing.JFXPanel; // To initialize the JavaFX runtime for tests
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for UILoader
 */
class UILoaderTest {

    private UILoader uiLoader;

    @BeforeEach
    void setUp() {
        // Initialize the JavaFX runtime
        new JFXPanel();
        // Initialize UILoader before each test
        uiLoader = new UILoader();
    }

    @Test
    @DisplayName("Test Change UI with Valid FXML")
    void testChangeUI() {
        // Simulate changing the UI
        assertDoesNotThrow(() -> {
            Stage stage = new Stage();
            stage.setScene(new Scene(new ImageView())); // Create a dummy scene
            uiLoader.changeUI(null, "/group47/cs2212/petgame/LoadGame.fxml", null);
        }, "changeUI() should not throw exceptions for valid FXML files.");
    }

    @Test
    @DisplayName("Test Display Notification Without Image")
    void testDisplayNotification() {
        // Test displaying a notification 
        assertDoesNotThrow(() -> uiLoader.displayNotification("Test", "This is a test message", "info"),
                "displayNotification() should not throw exceptions.");
    }

    @Test
    @DisplayName("Test Set Image for ImageView")
    void testSetImage() {
        // Create an ImageView and test setting a valid image
        ImageView imageView = new ImageView();
        String validPath = "/group47/cs2212/petgame/background_pictures/default_event_bg.png";

        assertDoesNotThrow(() -> uiLoader.setImage(imageView, validPath),
                "Setting an image should not throw exceptions.");
        assertNotNull(imageView.getImage(), "Image should be set for the ImageView.");
    }

    @Test
    @DisplayName("Test Close Scene")
    void testCloseScene() {
        // Simulate closing a scene
        assertDoesNotThrow(() -> {
            Stage stage = new Stage();
            stage.setScene(new Scene(new ImageView())); // Create a dummy scene
            Node dummyNode = stage.getScene().getRoot();
            uiLoader.closeScene(dummyNode);
        }, "closeScene() should not throw exceptions.");
    }

    @Test
    @DisplayName("Test Load FXML")
    void testLoadFXML() {
        // Test loading a valid FXML file
        String fxmlPath = "/group47/cs2212/petgame/LoadGame.fxml";

        assertDoesNotThrow(() -> uiLoader.loadFXML(fxmlPath),
                "Loading a valid FXML file should not throw exceptions.");
    }
}
