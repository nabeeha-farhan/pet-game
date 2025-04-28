package group47.cs2212.petgame;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.time.LocalTime;

public class ParentalControlsController {
    @FXML
    private Button menuButton;
    @FXML
    private TextField newPasswordField, confirmPasswordField, loginPasswordField, timeLimitField, blockHoursField;
    //private Button setPasswordButton, loginButton, setLimitsButton, setBlockHoursButton, viewStatsButton;
    @FXML
    private Label loginMessageLabel, setPasswordMessageLabel, playtimeStats;
    @FXML
    private Pane setPasswordPane, loginPane, mainControlsPane;

    private ParentalControls parentalControls;

    public ParentalControlsController() {
        this.parentalControls = new ParentalControls(null); // No password initially set
    }

    @FXML
    public void initialize() {
        if (parentalControls.isPasswordSet()) {
            showLoginPane();
        } else {
            showSetPasswordPane();
        }
    }

    private void showSetPasswordPane() {
        setPasswordPane.setVisible(true);
        loginPane.setVisible(false);
        mainControlsPane.setVisible(false);
    }

    private void showLoginPane() {
        setPasswordPane.setVisible(false);
        loginPane.setVisible(true);
        mainControlsPane.setVisible(false);
    }

    private void showMainControlsPane() {
        setPasswordPane.setVisible(false);
        loginPane.setVisible(false);
        mainControlsPane.setVisible(true);
    }

    @FXML
    private void setPassword() {
        String newPassword = newPasswordField.getText().trim();
        String confirmPassword = confirmPasswordField.getText().trim();

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            setPasswordMessageLabel.setText("Both fields are required.");
        } else if (!newPassword.equals(confirmPassword)) {
            setPasswordMessageLabel.setText("Passwords do not match.");
        } else {
            parentalControls.setPassword(newPassword);
            showLoginPane();
            showAlert("Success", "Password has been set successfully.");
        }
    }

    //@FXML
    /*private void authenticate() {
        String enteredPassword = loginPasswordField.getText().trim();

        if (parentalControls.authenticate(enteredPassword)) {
            showMainControlsPane();
            loginMessageLabel.setText("");
        } else {
            loginMessageLabel.setText("Invalid password. Please try again.");
        }
    }*/

    @FXML
    private void setDailyTimeLimit() {
        try {
            int limit = Integer.parseInt(timeLimitField.getText().trim());
            if (limit <= 0) throw new NumberFormatException();
            parentalControls.setDailyTimeLimit(limit);
            showAlert("Time Limit Set", "The daily time limit has been set to " + limit + " minutes.");
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid positive number for the time limit.");
        }
    }

    @FXML
    private void setBlockHours() {
        try {
            String[] hours = blockHoursField.getText().trim().split("-");
            if (hours.length != 2) throw new IllegalArgumentException("Invalid range format.");
            LocalTime start = LocalTime.parse(hours[0].trim());
            LocalTime end = LocalTime.parse(hours[1].trim());
            parentalControls.setRestrictedHours(start, end);
            showAlert("Block Hours Set", "Blocked from " + start + " to " + end);
        } catch (Exception e) {
            showAlert("Error", "Invalid time format. Use HH:mm-HH:mm (e.g., 21:00-07:00).");
        }
    }

    @FXML
    private void viewPlaytimeStats() {
        String stats = parentalControls.getPlaytimeStatistics();
        playtimeStats.setText(stats);
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void switchtoMain() throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("main.fxml"));
        Parent root = loader.load();

        Stage stage = (Stage) menuButton.getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
    }
}

