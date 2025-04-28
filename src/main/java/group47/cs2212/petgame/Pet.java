package group47.cs2212.petgame;

import javafx.scene.image.Image;
import java.io.Serializable;

/**
 * Represents a virtual pet with attributes such as happiness, energy, hunger, and health.
 * Each pet has unique characteristics based on its type and ID.
 */
public class Pet implements Serializable {
    /**
     * The name of the pet.
     */
    public String name;

    /**
     * The unique ID of the pet.
     */
    public String id;

    /**
     * The type or category of the pet.
     */
    public String type;

    /**
     * The current state of the pet (e.g., "fine", "hungry", "sleepy").
     */
    public String state;

    private int happiness;
    private int energy;
    private int hunger;
    private int health;

    /**
     * Whether the pet is in a forced state (e.g., forced happiness or sleep).
     */
    private boolean forcedState;

    /**
     * The image representing the pet.
     */
    public transient Image petImage; // NOTES: 84 x 62 pixels original size

    /**
     * Constructs a new Pet object with the specified name, ID, type, and image.
     *
     * @param name     the name of the pet
     * @param id       the unique ID of the pet
     * @param type     the type or category of the pet
     * @param petImage the image representing the pet
     */
    public Pet(String name, String id, String type, Image petImage) {
        this.name = name;
        this.id = id;
        this.type = type;
        this.state = "fine";
        this.health = 100;
        this.happiness = 100;
        this.energy = 100;
        this.hunger = 100;
        this.forcedState = false;
        this.petImage = petImage;
    }

    /**
     * Retrieves the decay values for the pet's attributes based on its ID.
     *
     * @return an array containing the decay values for hunger, energy, and happiness
     */
    public Integer[] getAttributeDecay() {
        return switch (this.id) {
            case "pet1" -> // Mouse attribute decay
                    new Integer[]{-2, -4, -6}; // Hunger, Energy, Happiness
            case "pet2" -> // Bee attribute decay
                    new Integer[]{-4, -6, -2};
            case "pet3" -> // Dragon attribute decay
                    new Integer[]{-6, -2, -4};
            default -> // Error Case
                    new Integer[]{0, 0, 0};
        };
    }

    /**
     * Retrieves the decay values for the pet's attributes while sleeping.
     *
     * @return an array containing the decay values for hunger, energy, and happiness during sleep
     */
    public Integer[] getAttributeDecaySleep() {
        return switch (this.id) {
            case "pet1" -> // Mouse attribute decay
                    new Integer[]{-1, 10, -3};
            case "pet2" -> // Bee attribute decay
                    new Integer[]{-2, 10, -1};
            case "pet3" -> // Dragon attribute decay
                    new Integer[]{-3, 10, -2};
            default -> // Error Case
                    new Integer[]{0, 0, 0};
        };
    }

    /**
     * Updates the pet's name.
     *
     * @param newName the new name for the pet
     */
    public void setName(String newName) {
        this.name = newName;
    }

    /**
     * Updates the pet's ID.
     *
     * @param newId the new ID for the pet
     */
    public void setId(String newId) {
        this.id = newId;
    }

    /**
     * Updates the pet's type.
     *
     * @param newType the new type for the pet
     */
    public void setType(String newType) {
        this.type = newType;
    }

    /**
     * Updates the pet's state.
     *
     * @param state the new state for the pet
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * Updates the pet's image.
     *
     * @param newImage the new image for the pet
     */
    public void setImage(String newImage) {
        this.petImage = new Image(newImage);
    }

    /**
     * Sets whether the pet is in a forced state.
     *
     * @param newForcedState true if the pet is in a forced state, false otherwise
     */
    public void setForcedState(boolean newForcedState) {
        this.forcedState = newForcedState;
    }

    /**
     * Gets the pet's name.
     *
     * @return the name of the pet
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the pet's ID.
     *
     * @return the ID of the pet
     */
    public String getId() {
        return id;
    }

    /**
     * Gets the pet's type.
     *
     * @return the type of the pet
     */
    public String getType() {
        return type;
    }

    /**
     * Gets the pet's state.
     *
     * @return the current state of the pet
     */
    public String getState() {
        return state;
    }

    /**
     * Gets the pet's image.
     *
     * @return the image representing the pet
     */
    public Image getImage() {
        return petImage;
    }

    /**
     * Updates the pet's hunger level.
     *
     * @param newHunger the amount to update hunger by (positive or negative)
     */
    public void updateHunger(int newHunger) {
        this.hunger = Math.max(0, Math.min(100, this.hunger + newHunger));
    }

    /**
     * Updates the pet's happiness level.
     *
     * @param newHappiness the amount to update happiness by (positive or negative)
     */
    public void updateHappiness(int newHappiness) {
        this.happiness = Math.max(0, Math.min(100, this.happiness + newHappiness));
    }

    /**
     * Updates the pet's energy level.
     *
     * @param newEnergy the amount to update energy by (positive or negative)
     */
    public void updateEnergy(int newEnergy) {
        this.energy = Math.max(0, Math.min(100, this.energy + newEnergy));
    }

    /**
     * Updates the pet's health level.
     *
     * @param newHealth the amount to update health by (positive or negative)
     */
    public void updateHealth(int newHealth) {
        this.health = Math.max(0, Math.min(100, this.health + newHealth));
    }

    /**
     * Checks if the pet is happy.
     *
     * @return true if the pet's happiness is above 25, false otherwise
     */
    public boolean isHappy() {
        return happiness > 25;
    }

    /**
     * Checks if the pet is healthy.
     *
     * @return true if the pet's health is above 25, false otherwise
     */
    public boolean isHealthy() {
        return health > 25;
    }

    /**
     * Checks if the pet is sleepy.
     *
     * @return true if the pet's energy is above 25, false otherwise
     */
    public boolean isNotSleepy() {
        return energy > 25;
    }

    /**
     * Checks if the pet is hungry.
     *
     * @return true if the pet's hunger is above 25, false otherwise
     */
    public boolean isNotHungry() {
        return hunger > 25;
    }

    /**
     * Checks if the pet has entered a hungry state.
     *
     * @return true if the pet's hunger is 0, false otherwise
     */
    public boolean enterHungryState() {
        return this.hunger == 0;
    }

    /**
     * Checks if the pet has entered a sleep state.
     *
     * @return true if the pet's energy is 0, false otherwise
     */
    public boolean enterSleepState() {
        return this.energy == 0;
    }

    /**
     * Checks if the pet has entered an angry state.
     *
     * @return true if the pet's happiness is 0, false otherwise
     */
    public boolean enterAngryState() {
        return this.happiness == 0;
    }

    /**
     * Checks if the pet is in a forced state.
     *
     * @return true if the pet is in a forced state, false otherwise
     */
    public boolean isInForcedState() {
        return forcedState;
    }

    /**
     * Checks if the pet has entered a dead state.
     *
     * @return true if the pet's health is 0, false otherwise
     */
    public boolean enterDeadState() {
        return this.health == 0;
    }
    public void putToSleep() {
        this.energy = Math.min(this.energy + 50, 100); // Increase energy, capped at 100
        System.out.println("Pet is now sleeping and regaining energy.");
    }


    /**
     * Gets the pet's current attributes.
     *
     * @return an array containing happiness, energy, hunger, and health
     */
    public Integer[] getAttributes() {
        return new Integer[]{this.happiness, this.energy, this.hunger, this.health};
    }
}
