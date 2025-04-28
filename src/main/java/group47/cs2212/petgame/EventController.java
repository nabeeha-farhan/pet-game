package group47.cs2212.petgame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Controller class for managing event-related interactions in the game.
 * Handles user actions for events and updates the UI dynamically.
 */
public class EventController {

    @FXML
    private Label eventTitleLabel; // Displays the event's title

    @FXML
    private Label eventDescriptionLabel; // Displays the event's description

    @FXML
    private ProgressBar eventProgressBar; // Displays progress related to the event

    @FXML
    private Button completeEventButton; // Button to complete the event

    @FXML
    private Button dismissEventButton; // Button to dismiss the event

    @FXML
    private ImageView eventImageView; // Displays the image associated with the event

    private Event currentEvent; // The current event being displayed
    private Player player; // The player interacting with the event
    private Pet pet; // The pet associated with the event
    private UILoader uiLoader; // UI Loader for handling transitions

    /**
     * Initializes the controller with the event, player, and pet data.
     * Populates the UI with event details.
     *
     * @param event     The event to display.
     * @param player    The player interacting with the event.
     * @param pet       The pet associated with the event.
     * @param uiLoader  The UI loader for managing transitions.
     */
    public void initialize(Event event, Player player, Pet pet, UILoader uiLoader) {
        this.currentEvent = event;
        this.player = player;
        this.pet = pet;
        this.uiLoader = uiLoader;

        // Populate UI with event details
        if (currentEvent != null) {
            eventProgressBar.setProgress(currentEvent.isCompleted() ? 1.0 : 0.0);
            eventTitleLabel.setText(currentEvent.getEventName());
            eventDescriptionLabel.setText(currentEvent.getEventDescription());
            setEventImage(currentEvent.getEventImage());
        }
    }

    /**
     * Handles the completion of the event.
     * Updates the player's progress, triggers the event, and saves the game state.
     */
    @FXML
    public void handleCompleteEvent() {
        if (currentEvent != null && !currentEvent.isCompleted()) {
            currentEvent.trigger(player, pet, uiLoader);
            eventProgressBar.setProgress(1.0); // Set progress to complete
            uiLoader.displayNotification(
                    "Event Completed",
                    "You successfully completed: " + currentEvent.getEventName(),
                    "info",
                    "/group47/cs2212/petgame/Pet_inventory/Giftbox.png"
            );
            System.out.println("Event completed: " + currentEvent.getEventName());
        } else {
            System.out.println("Event already completed or not initialized.");
        }
    }

    /**
     * Handles dismissing the event.
     * Closes the event UI without triggering the event.
     */
    @FXML
    public void handleDismissEvent(ActionEvent event) {
        uiLoader.closeScene((Button) event.getSource());
    }

    /**
     * Updates the progress bar dynamically.
     *
     * @param progress The progress value (0.0 to 1.0).
     */
    public void updateProgress(double progress) {
        eventProgressBar.setProgress(progress);
    }

    /**
     * Handles the selection and initialization of different event types.
     * Dynamically updates the UI based on the selected event.
     *
     * @param eventType The type of event (e.g., "Login Gifts", "Feed", "Heal").
     */
    public void handleEventSelection(String eventType) {
        switch (eventType) {
            case "Login Gifts":
                loadLoginGiftEvents();
                break;
            case "Feed":
                loadFeedEvents();
                break;
            case "Clean":
                loadCleanEvents();
                break;
            case "Heal":
                loadHealEvents();
                break;
            case "Energize":
                loadEnergizeEvents();
                break;
            case "Birthday":
                loadBirthdayEvents();
                break;
            default:
                System.out.println("Invalid event type: " + eventType);
                break;
        }
    }

    /**
     * Loads and initializes Login Gift events.
     */
    private void loadLoginGiftEvents() {
        setEventDetails("Daily Login", "daily_login.png", "Login daily to receive exciting rewards!");
        setEventDetails("Week 1 & 2 Login", "daily_login.png", "Login weekly to earn greater rewards!");
        setEventDetails("Monthly Login", "daily_login.png", "Login every month for exclusive gifts!");
    }

    /**
     * Loads and initializes Feed events.
     */
    private void loadFeedEvents() {
        setEventDetails("Feed Once", "feed.png", "Feed your pet for the first time to unlock this reward!");
        setEventDetails("Feed 50 Times", "feed.png", "Feed your pet 50 times for this milestone!");
        setEventDetails("Feed 100 Times", "feed.png", "Feed your pet 100 times to achieve mastery!");
    }

    /**
     * Loads and initializes Clean events.
     */
    private void loadCleanEvents() {
        setEventDetails("Clean Once", "clean.png", "Clean your pet for the first time to unlock this reward!");
        setEventDetails("Clean 10 Times", "clean.png", "Clean your pet 10 times for this milestone!");
        setEventDetails("Clean 50 Times", "clean.png", "Clean your pet 50 times to achieve mastery!");
    }

    /**
     * Loads and initializes Heal events.
     */
    private void loadHealEvents() {
        setEventDetails("Heal Once", "heal.png", "Heal your pet for the first time to unlock this reward!");
        setEventDetails("Heal 10 Times", "heal.png", "Heal your pet 10 times for this milestone!");
        setEventDetails("Heal 50 Times", "heal.png", "Heal your pet 50 times to achieve mastery!");
    }

    /**
     * Loads and initializes Energize events.
     */
    private void loadEnergizeEvents() {
        setEventDetails("Energize Once", "energize.png", "Energize your pet for the first time to unlock this reward!");
        setEventDetails("Energize 10 Times", "energize.png", "Energize your pet 10 times for this milestone!");
        setEventDetails("Energize 50 Times", "energize.png", "Energize your pet 50 times to achieve mastery!");
    }

    /**
     * Loads and initializes Birthday events.
     */
    private void loadBirthdayEvents() {
        setEventDetails("1 Week Birthday", "birthday.png", "Celebrate your pet's 1-week birthday with a special gift!");
        setEventDetails("2 Weeks Birthday", "birthday.png", "Celebrate your pet's 2-week birthday with this unique reward!");
        setEventDetails("3 Weeks Birthday", "birthday.png", "Celebrate your pet's 3-week birthday and earn exclusive rewards!");
    }

    /**
     * Sets event details and updates the UI.
     *
     * @param title       The title of the event.
     * @param imageName   The image file associated with the event.
     * @param description The description of the event.
     */
    private void setEventDetails(String title, String imageName, String description) {
        eventTitleLabel.setText(title);
        eventDescriptionLabel.setText(description);
        uiLoader.setImage(eventImageView, "/group47/cs2212/petgame/Pet_inventory/" + imageName);
    }

    /**
     * Sets image for the event based on its type.
     *
     * @param imageName The name of the image file to display.
     */
    private void setEventImage(String imageName) {
        if (imageName != null && !imageName.isEmpty()) {
            String imagePath = "/group47/cs2212/petgame/Pet_inventory/" + imageName;
            eventImageView.setImage(new Image(getClass().getResourceAsStream(imagePath)));
        } else {

            System.out.println("No image provided for event.");
        }
    }
}
