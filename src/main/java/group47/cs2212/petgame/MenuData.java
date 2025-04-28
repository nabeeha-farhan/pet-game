package group47.cs2212.petgame;

import javafx.scene.Group;

/**
 * Represents the data for a menu, including its associated UI group and visibility state.
 */
public class MenuData {
    /**
     * The JavaFX {@link Group} representing the menu's UI components.
     */
    private final Group group;

    /**
     * A flag indicating whether the menu is currently visible.
     */
    private boolean showing;

    /**
     * Constructs a new {@code MenuData} object with the specified UI group.
     * By default, the menu is not visible.
     *
     * @param group the JavaFX {@link Group} representing the menu's UI components
     */
    public MenuData(Group group) {
        this.group = group;
        this.showing = false;
    }

    /**
     * Gets the JavaFX {@link Group} associated with this menu.
     *
     * @return the {@link Group} representing the menu's UI components
     */
    public Group getGroup() {
        return this.group;
    }

    /**
     * Checks if the menu is currently visible.
     *
     * @return {@code true} if the menu is visible; {@code false} otherwise
     */
    public boolean isShowing() {
        return this.showing;
    }

    /**
     * Sets the visibility state of the menu.
     *
     * @param showing {@code true} to make the menu visible; {@code false} to hide it
     */
    public void setShowing(boolean showing) {
        this.showing = showing;
    }
}
