package group47.cs2212.petgame;

import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class MiniGameController implements Initializable {

    @FXML
    private VBox miniGamesContainer;

    @FXML
    private VBox gamesMenu;

    @FXML
    private VBox coinFlipContainer;

    @FXML
    private VBox ticTacToeContainer;

    @FXML
    private Button flipCoinButton;

    @FXML
    private Button ticTacToeButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label coinFlipResult;

    @FXML
    private GridPane ticTacToeGrid;

    @FXML
    private Label ticTacToeStatus;

    @FXML
    private ImageView coinImage; // ImageView for the coin

    // This will be called after FXML is loaded
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set the coin image inside the initialize method
        coinImage.setImage(new Image(getClass().getResource("/group47/cs2212/petgame/pet_pictures/coin.png").toString()));
    }

    // This will flip the coin with animation
    @FXML
    private void flipCoin(ActionEvent event) {
        // Create a RotateTransition to rotate the coin
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(coinImage);
        rotateTransition.setDuration(Duration.seconds(1)); // Duration of the flip
        rotateTransition.setByAngle(720); // Total rotation in degrees (2 full rotations)
        rotateTransition.setCycleCount(1); // The animation runs once

        // Set an action to be performed after the animation completes
        rotateTransition.setOnFinished(e -> {
            // After the flip, randomly decide heads or tails
            String result = CoinFlipGame.generateFlipResult();
            coinFlipResult.setText(result); // Display the result
        });

        // Start the rotation animation
        rotateTransition.play();
    }

    // This will toggle the visibility of the mini-games menu
    @FXML
    private void toggleMiniGames(ActionEvent event) {
        gamesMenu.setVisible(!gamesMenu.isVisible());
    }

    // This will show the Coin Flip game
    @FXML
    private void openCoinFlipGame(ActionEvent event) {
        coinFlipContainer.setVisible(true);
        ticTacToeContainer.setVisible(false); // Hide Tic Tac Toe
    }

    // This will show the Tic Tac Toe game
    @FXML
    private void openTicTacToeGame(ActionEvent event) {
        ticTacToeContainer.setVisible(true);
        coinFlipContainer.setVisible(false); // Hide Coin Flip
        TicTacToeGame.beginGame(ticTacToeGrid); // Initialize Tic Tac Toe Game
    }

    @FXML
    private void exitCoinFlip(ActionEvent event) {
        coinFlipContainer.setVisible(false);
        gamesMenu.setVisible(false);
    }

    @FXML
    private void exitGame(ActionEvent event) {
        Node source = (Node) event.getSource();
        Scene scene = source.getScene();
        VBox miniGamesContainer = (VBox) ((Scene) scene).lookup("#miniGamesContainer");

        if (miniGamesContainer != null) {
            miniGamesContainer.setVisible(false);
        }
    }

    // This will handle Tic Tac Toe moves
    @FXML
    private void handleTicTacToeMove(ActionEvent event) {
        Button clickedButton = (Button) event.getSource();
        String currentPlayer = ticTacToeStatus.getText().contains("X") ? "X" : "O";
        clickedButton.setText(currentPlayer);
        clickedButton.setDisable(true); // Disable the button after the move

        // Check if the game is over
        if (checkWin()) {
            ticTacToeStatus.setText(currentPlayer + " wins!");
            disableAllTicTacToeButtons();
        } else if (isBoardFull()) {
            ticTacToeStatus.setText("It's a draw!");
        } else {
            ticTacToeStatus.setText(currentPlayer.equals("X") ? "Player O's turn" : "Player X's turn");
        }
    }

    // Check if there's a winner in Tic Tac Toe
    private boolean checkWin() {
        String[][] board = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = (Button) ticTacToeGrid.getChildren().get(i * 3 + j);
                board[i][j] = btn.getText();
            }
        }

        // Check rows, columns, and diagonals for a winner
        for (int i = 0; i < 3; i++) {
            if (board[i][0].equals(board[i][1]) && board[i][1].equals(board[i][2]) && !board[i][0].isEmpty()) {
                return true;
            }
            if (board[0][i].equals(board[1][i]) && board[1][i].equals(board[2][i]) && !board[0][i].isEmpty()) {
                return true;
            }
        }
        if (board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]) && !board[0][0].isEmpty()) {
            return true;
        }
        if (board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]) && !board[0][2].isEmpty()) {
            return true;
        }
        return false;
    }

    // Check if the Tic Tac Toe board is full
    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = (Button) ticTacToeGrid.getChildren().get(i * 3 + j);
                if (btn.getText().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    // Disable all buttons in the Tic Tac Toe grid
    private void disableAllTicTacToeButtons() {
        for (Node node : ticTacToeGrid.getChildren()) {
            if (node instanceof Button) {
                ((Button) node).setDisable(true);
            }
        }
    }

    // Reset Tic Tac Toe game
    @FXML
    private void resetTicTacToe(ActionEvent event) {
        for (Node node : ticTacToeGrid.getChildren()) {
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setText("");
                button.setDisable(false);
            }
        }
        ticTacToeStatus.setText("Player X's turn");
    }

    // Exit the Tic Tac Toe game
    @FXML
    private void exitTicTacToe(ActionEvent event) {
        ticTacToeContainer.setVisible(false);
        gamesMenu.setVisible(false);
    }
}
