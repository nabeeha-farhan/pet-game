package group47.cs2212.petgame;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TutorialControllerTest {

    private TutorialController tutorialController;
    private Button nextStepButton;
    private Button backStepButton;
    private Label tutorialText;
    private VBox selectionMenu;

    @BeforeEach
    public void setUp() throws Exception {
        // Load the FXML file and initialize the controller
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Tutorial.fxml"));
        loader.setControllerFactory(c -> {
            if (c == TutorialController.class) {
                tutorialController = new TutorialController();
                return tutorialController;
            }
            return null;
        });

        loader.load();

        // Initialize UI components
        nextStepButton = tutorialController.getNextStepButton();
        backStepButton = tutorialController.getBackStepButton();
        tutorialText = tutorialController.getTutorialText();
        selectionMenu = tutorialController.getSelectionMenu();
    }

    @Test
    public void testInitialState() {
        // Check initial button states and tutorial text
        assertNotNull(tutorialText, "Tutorial text label should be initialized");
        assertNotNull(nextStepButton, "Next step button should be initialized");
        assertNotNull(backStepButton, "Back step button should be initialized");

        assertEquals("Welcome to the tutorial!\nIn this virtual game, you'll adopt, care and play with your own pet!", tutorialText.getText(), "Initial tutorial text should be correct");
        assertTrue(backStepButton.isDisabled(), "Back button should be disabled at the start");
        assertEquals("Next", nextStepButton.getText(), "Next button should have text 'Next' initially");
    }

    @Test
    public void testNextStepNavigation() {
        // Simulate clicking 'Next' button and verify tutorial text
        nextStepButton.fire();

        assertEquals("In this virtual game, you'll adopt, care and play with your own pet!", tutorialText.getText(), "Tutorial text should update to step 1");
        assertFalse(backStepButton.isDisabled(), "Back button should be enabled after moving to the next step");

        // Move to the next step
        nextStepButton.fire();
        assertEquals("Step 1: Track your pet’s progress.", tutorialText.getText(), "Tutorial text should update to step 2");

        // Simulate clicking next until step 6
        for (int i = 3; i <= 6; i++) {
            nextStepButton.fire();
            assertNotNull(tutorialText.getText(), "Tutorial text should update to step " + i);
        }

        // Step 6 should show the finish button
        assertEquals("You're ready! Enjoy the full game.", tutorialText.getText(), "Step 6 should show the finish message");
        assertEquals("Finish Tutorial", nextStepButton.getText(), "Next button should show 'Finish Tutorial' after step 6");
    }

    @Test
    public void testBackStepNavigation() {
        // Move to step 1
        nextStepButton.fire();
        // Go back to step 0
        backStepButton.fire();

        // Verify tutorial text and button states after going back
        assertEquals("Welcome to the tutorial!\nIn this virtual game, you'll adopt, care and play with your own pet!", tutorialText.getText(), "After clicking 'Back', tutorial text should revert to step 0");
        assertTrue(backStepButton.isDisabled(), "Back button should be disabled after going back to the first step");
    }

    @Test
    public void testSelectionMenuVisibility() {
        // Move through steps to load the progress menu
        nextStepButton.fire(); // Step 1
        nextStepButton.fire(); // Step 2
        nextStepButton.fire(); // Step 3

        // At step 3, the progress menu should be visible
        assertTrue(selectionMenu.isVisible(), "Selection menu should be visible after Step 2");

        // Move to the next step, where a menu is displayed
        nextStepButton.fire(); // Step 4
        assertTrue(selectionMenu.isVisible(), "Selection menu should be visible after Step 3");

        // At step 5 and beyond, the menu should also be visible
        nextStepButton.fire(); // Step 5
        assertTrue(selectionMenu.isVisible(), "Selection menu should be visible after Step 4");
    }

}

