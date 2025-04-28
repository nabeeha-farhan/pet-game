package group47.cs2212.petgame;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents an event in the game that can be triggered based on specific conditions.
 * Events can provide rewards, track progress, and interact with player and pet stats.
 */
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;

    private String eventName;
    private String eventDescription;
    private LocalDate eventDate;
    private boolean isCompleted;
    private int rewardPoints;
    private String eventImage; // Image associated with the event
    private String eventThumbnail;
    private String eventBackground;

    /**
     * Constructs a new Event with the specified attributes.
     *
     * @param eventName        The name of the event.
     * @param eventDescription A description of the event.
     * @param eventDate        The date of the event.
     * @param rewardPoints     The reward points for completing the event.
     * @param eventImage       The image associated with the event.
     * @param eventThumbnail   The image path for the event thumbnail.
     * @param eventBackground  The image path for the event background.
     */
    public Event(String eventName, String eventDescription, LocalDate eventDate, int rewardPoints, String eventImage,
                 String eventThumbnail, String eventBackground) {
        this.eventName = eventName;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.rewardPoints = rewardPoints;
        this.eventImage = eventImage;
        this.eventThumbnail = eventThumbnail;
        this.eventBackground = eventBackground;
        this.isCompleted = false;
    }

    // Getters and Setters

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventThumbnail() {
        return eventThumbnail;
    }

    public void setEventThumbnail(String eventThumbnail) {
        this.eventThumbnail = eventThumbnail;
    }

    public String getEventBackground() {
        return eventBackground;
    }

    public void setEventBackground(String eventBackground) {
        this.eventBackground = eventBackground;
    }

    /**
     * Triggers the event, applying its effects on the player, pet, and UI loader.
     *
     * @param player   The player interacting with the event.
     * @param pet      The pet associated with the event.
     * @param uiLoader The UI loader to manage event-related UI changes.
     */
    public void trigger(Player player, Pet pet, UILoader uiLoader) {
        if (!isCompleted) {
            // Apply event rewards to the player
            player.updateScore(rewardPoints);

            // Log event completion
            System.out.println("Event triggered: " + eventName);

            // Display notification with the event image
            uiLoader.displayNotification(
                    "Event Completed",
                    "You have completed the event: " + eventName,
                    "info",
                    "/group47/cs2212/petgame/Pet_inventory/" + eventImage
            );

            // Mark the event as completed
            isCompleted = true;
        } else {
            System.out.println("Event already completed: " + eventName);
        }
    }

    @Override
    public String toString() {
        return "Event{" +
                "eventName='" + eventName + '\'' +
                ", eventDescription='" + eventDescription + '\'' +
                ", eventDate=" + eventDate +
                ", isCompleted=" + isCompleted +
                ", rewardPoints=" + rewardPoints +
                ", eventImage='" + eventImage + '\'' +
                ", eventThumbnail='" + eventThumbnail + '\'' +
                ", eventBackground='" + eventBackground + '\'' +
                '}';
    }
}
