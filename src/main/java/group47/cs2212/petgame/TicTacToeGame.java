package group47.cs2212.petgame;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Button;
import java.util.Random;

public class TicTacToeGame {

    private static String currentPlayer = "X";
    private static String[][] gameBoard = new String[3][3];
    private static Button[][] buttonGrid = new Button[3][3];

    public static void beginGame(GridPane grid) {
        grid.getChildren().clear();
        resetBoard();

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                final int currentRow = row;
                final int currentCol = col;
                Button cellButton = new Button();
                cellButton.setMinSize(100, 100);
                cellButton.setStyle("-fx-font-size: 24; -fx-base: lightgrey;");
                cellButton.setOnAction(e -> processMove(cellButton, currentRow, currentCol));
                grid.add(cellButton, col, row);
                buttonGrid[row][col] = cellButton; // Store the button in the array
            }
        }
    }

    private static void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                gameBoard[row][col] = "";
            }
        }
    }

    private static void processMove(Button button, int row, int col) {
        if (!gameBoard[row][col].equals("") || currentPlayer.equals("O")) return; // Block if spot is taken or it's computer's turn

        gameBoard[row][col] = currentPlayer;
        button.setText(currentPlayer);

        if (checkForWinner()) {
            announceWinner();
        } else {
            switchToNextPlayer();
            if (currentPlayer.equals("O")) {
                // Let the computer make its move
                computerMove();
            }
        }
    }

    private static void switchToNextPlayer() {
        currentPlayer = currentPlayer.equals("X") ? "O" : "X";
    }

    private static boolean checkForWinner() {
        // Check rows and columns for a win
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][0].equals(currentPlayer) && gameBoard[i][1].equals(currentPlayer) && gameBoard[i][2].equals(currentPlayer)) return true;
            if (gameBoard[0][i].equals(currentPlayer) && gameBoard[1][i].equals(currentPlayer) && gameBoard[2][i].equals(currentPlayer)) return true;
        }
        // Check diagonals for a win
        if (gameBoard[0][0].equals(currentPlayer) && gameBoard[1][1].equals(currentPlayer) && gameBoard[2][2].equals(currentPlayer)) return true;
        if (gameBoard[0][2].equals(currentPlayer) && gameBoard[1][1].equals(currentPlayer) && gameBoard[2][0].equals(currentPlayer)) return true;

        return false;
    }

    private static void announceWinner() {
        Alert winnerAlert = new Alert(AlertType.INFORMATION);
        winnerAlert.setTitle("Tic Tac Toe");

        if (currentPlayer.equals("X")) {
            winnerAlert.setHeaderText("You won!");
        } else {
            winnerAlert.setHeaderText("You lost better luck next time.");
        }
        winnerAlert.showAndWait();

        resetBoard(); // Reset the board after announcing the winner
    }

    private static void computerMove() {
        Random random = new Random();

        // Find an empty spot
        while (true) {
            int row = random.nextInt(3);
            int col = random.nextInt(3);
            if (gameBoard[row][col].equals("")) {
                gameBoard[row][col] = "O";
                buttonGrid[row][col].setText("O");
                break;
            }
        }

        // Check for winner after computer's move
        if (checkForWinner()) {
            announceWinner();
        } else {
            switchToNextPlayer(); // Switch back to player after computer's move
        }
    }
}
