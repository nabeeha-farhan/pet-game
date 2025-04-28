package group47.cs2212.petgame;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CoinFlipGame {

    public static void initiateFlipGame(VBox displayArea) {
        Button flipButton = new Button("Flip the Coin");
        flipButton.setOnAction(event -> {
            String result = generateFlipResult();
            showOutcome(result);
        });

        displayArea.getChildren().clear();
        displayArea.getChildren().add(flipButton);
    }

    public static String generateFlipResult() {
        double randomChoice = Math.random();
        return (randomChoice > 0.5) ? "Heads" : "Tails";
    }

    private static void showOutcome(String result) {
        Alert outcomeAlert = new Alert(AlertType.INFORMATION);
        outcomeAlert.setTitle("Coin Flip Outcome");
        outcomeAlert.setHeaderText("The result of your coin flip is:");
        outcomeAlert.setContentText(result);
        outcomeAlert.showAndWait();
    }
}

