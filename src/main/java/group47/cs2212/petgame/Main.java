package group47.cs2212.petgame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

/**
 * The main class for the Pet Game application, responsible for launching the JavaFX UI.
 * This class extends {@link javafx.application.Application} and sets up the main application window.
 */
public class Main extends Application {

    /**
     * Initializes the main stage and loads the primary scene for the Pet Game application.
     *
     * @param stage The primary stage for the application, provided by JavaFX.
     * @throws IOException If the FXML file could not be loaded.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("main.fxml")));
        stage.setTitle("Pet Game");
        stage.setScene(new Scene(root));
        extracted(stage);
    }

    /**
     * Displays the main application stage.
     *
     * @param stage The primary stage to be displayed.
     */
    private static void extracted(Stage stage) {
        stage.show();
    }

    /**
     * The main method for launching the JavaFX application.
     * This method calls {@link javafx.application.Application#launch(String...)} to start the JavaFX runtime.
     *
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        launch();
    }
}