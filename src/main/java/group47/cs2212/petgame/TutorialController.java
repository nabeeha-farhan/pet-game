package group47.cs2212.petgame;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.ProgressBar;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class TutorialController {

    @FXML
    private Label tutorialText;
    @FXML
    private Button nextStepButton;
    @FXML
    private Button backStepButton;
    @FXML
    private VBox selectionMenu;

    private int step = 0;

    @FXML
    private void nextStep() throws Exception {
        step++;
        updateTutorialStep();
    }

    @FXML
    private void backStep() throws Exception {
        step--;
        updateTutorialStep();
    }
    public VBox getSelectionMenu() {
        return selectionMenu;
    }

    public Label getTutorialText() {
        return tutorialText;
    }

    public Button getNextStepButton() {
        return nextStepButton;
    }

    public Button getBackStepButton() {
        return backStepButton;
    }

    private void updateTutorialStep() throws Exception {
        System.out.println("Updating tutorial step: " + step); // Debugging line

        // Clear previous content
        selectionMenu.getChildren().clear();
        selectionMenu.setVisible(false);     // Hide menu by default
        nextStepButton.setDisable(false);

        // Center the text and apply style
        tutorialText.setAlignment(Pos.CENTER);
        tutorialText.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-alignment: center; -fx-text-fill: white;");

        // Ensure tutorialText is updated at the start of each case
        switch (step) {
            case 0:
                tutorialText.setText("Welcome to the tutorial!");
                backStepButton.setDisable(true); // Disable back button at the start
                break;
            case 1:
                tutorialText.setText("In this virtual game, you'll adopt, care and play with your own pet!");
                backStepButton.setDisable(false);
                break;
            case 2:
                tutorialText.setText("Step 1: Track your pet’s progress.");
                loadProgress();
                break;
            case 3:
                tutorialText.setText("Step 2: Use the Pet interaction menu to interact with your pet.");
                loadMenu();
                break;
            case 4:
                tutorialText.setText("Step 3: Check achievements to track your progress.");
                loadAchievements();
                break;
            case 5:
                tutorialText.setText("Step 4: Play mini-games to boost happiness.");
                loadMiniGames();
                break;
            case 6:
                tutorialText.setText("You're ready! Enjoy the full game.");
                nextStepButton.setText("Finish Tutorial");
                break;
            case 7:
                loadMainGame(); // Load main game on tutorial completion
                break;
            default:
                tutorialText.setText("Welcome to the tutorial!\n" +
                        "In this virtual game, you'll adopt, care and play for your own pet!");
                backStepButton.setDisable(true);
                break;
        }
    }


    private void handleSelection(String selectedOption) {
        // Example logic based on the selected option
        switch (selectedOption) {
            case "Feed":
                // Handle feeding logic
                System.out.println("Feeding the pet...");
                break;
            case "Put to Sleep":
                // Handle put to sleep logic
                System.out.println("Putting the pet to sleep...");
                break;
            case "Play":
                // Handle play logic
                System.out.println("Playing with the pet...");
                break;
            default:
                // Handle other cases if necessary
                System.out.println("Unknown action selected.");
                break;
        }
    }

    @FXML
    private void loadProgress() {
        // Dummy values for each attribute (you can modify these as needed)
        float happiness = 0.80f;  // 80% happiness
        float hygiene = 0.60f;    // 60% hygiene
        float energy = 0.40f;     // 40% energy
        float hunger = 0.20f;     // 20% hunger
        float health = 0.50f;     // 50% health

        String[] progressOptions = { "Happiness", "Hygiene", "Energy", "Hunger", "Health" };
        float[] progressValues = { happiness, hygiene, energy, hunger, health };

        selectionMenu.getChildren().clear();
        for (int i = 0; i < progressOptions.length; i++) {
            HBox progressBox = new HBox(10);
            progressBox.setAlignment(Pos.CENTER_LEFT);

            Label progressLabel = new Label(progressOptions[i] + ": ");
            progressLabel.setStyle("-fx-font-weight: bold;");

            ProgressBar progressBar = new ProgressBar();
            float progress = progressValues[i];
            progressBar.setProgress(progress);

            if (progress >= 0.55) {
                progressBar.setStyle("-fx-accent: green;");
            } else if (progress <= 0.25) {
                progressBar.setStyle("-fx-accent: red;");
            } else {
                progressBar.setStyle("-fx-accent: yellow;");
            }

            progressBox.getChildren().addAll(progressLabel, progressBar);

            selectionMenu.getChildren().add(progressBox);
        }

        selectionMenu.setVisible(true);
        nextStepButton.setDisable(false);
    }

    private void loadMenu() {
        String[] menuOptions = { "Feed", "Put to Sleep", "Play", "Take to the vet", "Exercise" };

        // Clear any previous content in the selectionMenu
        selectionMenu.getChildren().clear();

        VBox menu = new VBox(10); // 10px spacing between items
        menu.setStyle("-fx-padding: 20px; -fx-background-color: #2d2d2d; -fx-background-radius: 10px;");

        // Add each menu option as a Label to the menu
        for (String option : menuOptions) {
            Label label = new Label(option);
            label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px;");

            // Add hover effect to label
            label.setOnMouseEntered(event -> label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: yellow; -fx-padding: 10px;"));
            label.setOnMouseExited(event -> label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px;"));

            // Handle label click by passing the selected option to handleSelection method
            label.setOnMouseClicked(event -> handleSelection(option)); // pass the option as parameter

            // Add the label to the menu
            menu.getChildren().add(label);
        }

        // Add the VBox to the selectionMenu container
        selectionMenu.getChildren().add(menu);

        // Set the visibility and disable the next step button
        selectionMenu.setVisible(true);
        nextStepButton.setDisable(false);
    }

    private void loadAchievements() {
        String[] achievements = { "First Feeding", "Win a Game", "Keep Happy 3 Days" };

        // Clear any previous content in the selectionMenu
        selectionMenu.getChildren().clear();

        // Create a VBox to layout the menu items
        VBox menu = new VBox(10); // 10px spacing between items
        menu.setStyle("-fx-padding: 20px; -fx-background-color: #2d2d2d; -fx-background-radius: 10px;");

        // Add each achievement as a Label to the menu
        for (String achievement : achievements) {
            Label label = new Label(achievement);
            label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px;");

            // Add hover effect to label
            label.setOnMouseEntered(event -> label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: yellow; -fx-padding: 10px;"));
            label.setOnMouseExited(event -> label.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white; -fx-padding: 10px;"));

            // Add the label to the menu
            menu.getChildren().add(label);
        }

        // Add the VBox to the selectionMenu container
        selectionMenu.getChildren().add(menu);

        // Set the visibility and disable the next step button
        selectionMenu.setVisible(true);
        nextStepButton.setDisable(false);
    }


    private void loadMiniGames() {
        String[] games = { "Coin Flip", "Tic Tac Toe" };

        // Clear any previous content in the selectionMenu
        selectionMenu.getChildren().clear();

        // Add buttons for each game
        for (String game : games) {
            Button button = new Button(game);

            // Use lambda to pass the game name to the handleSelection method
            button.setOnAction(event -> handleSelection(game));

            // Add the button to the selection menu
            selectionMenu.getChildren().add(button);
        }

        // Show the menu and disable the next step button
        selectionMenu.setVisible(true);
        nextStepButton.setDisable(false);
    }

    private void loadMainGame() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) nextStepButton.getScene().getWindow();
        stage.setScene(new Scene(root));
    }
}
