package group47.cs2212.petgame;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * The Inventory class manages the player's collection of items,
 * allowing them to store and use food and gift items for interacting with their pet.
 */
public class Inventory implements Serializable {

    private final Map<String, Integer> items;

    /**
     * Constructor to initialize the inventory with an empty collection.
     */
    public Inventory() {
        items = new HashMap<>();
        initializeDefaultItems();
    }

    /**
     * Adds an item to the inventory or updates its count.
     *
     * @param itemName the name of the item to add.
     * @param count    the number of items to add.
     */
    public void addItem(String itemName, int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("Count must be greater than zero.");
        }
        items.put(itemName, items.getOrDefault(itemName, 0) + count);
    }

    /**
     * Removes an item from the inventory if it exists and the count is sufficient.
     *
     * @param itemName the name of the item to remove.
     * @param count    the number of items to remove.
     * @return true if the item was successfully removed, false otherwise.
     */
    public boolean removeItem(String itemName, int count) {
        if (!items.containsKey(itemName) || count <= 0) {
            return false;
        }

        int currentCount = items.get(itemName);
        if (currentCount < count) {
            return false;
        }

        if (currentCount == count) {
            items.remove(itemName);
        } else {
            items.put(itemName, currentCount - count);
        }
        return true;
    }

    /**
     * Provides a formatted list of all items in the inventory.
     *
     * @return a string array with item names and their counts.
     */
    public String[] getInventoryDetails() {
        return items.entrySet().stream()
                .map(entry -> entry.getKey() + " x" + entry.getValue())
                .toArray(String[]::new);
    }

    /**
     * Checks if the inventory contains a specific item.
     *
     * @param itemName the name of the item to check.
     * @return true if the item exists in the inventory, false otherwise.
     */
    public boolean hasItem(String itemName) {
        return items.containsKey(itemName);
    }

    /**
     * Retrieves the count of a specific item in the inventory.
     *
     * @param itemName the name of the item to check.
     * @return the count of the item, or 0 if not present.
     */
    public int getItemCount(String itemName) {
        return items.getOrDefault(itemName, 0);
    }

    /**
     * Initializes the inventory with default items for gameplay.
     * Adds food and gift items with predefined counts.
     */
    public void initializeDefaultItems() {
        addItem("Food1", 99);  //  food item
        addItem("Peach", 99);  //  food item
        addItem("Giftbox", 99); // Gift item
    }
}
