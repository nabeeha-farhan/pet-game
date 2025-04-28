package group47.cs2212.petgame;

import java.util.HashMap;
import java.util.Map;

/**
 * The Shop class allows players to purchase items for their inventory.
 * Items include food and gift items for their pet.
 */
public class Shop {
    private Map<String, Item> itemsForSale; // Items available in the shop
    private Player player; // Reference to the current player

    /**
     * Constructor for the Shop class.
     *
     * @param player The current player accessing the shop.
     */
    public Shop(Player player) {
        this.player = player;
        this.itemsForSale = new HashMap<>();
        initializeShopItems();
    }

    /**
     * Initializes the shop with default items for sale.
     * Each item has a name, price, and type (e.g., food or gift).
     */
    private void initializeShopItems() {
        // Food Items
        itemsForSale.put("Apple", new Item("Apple", 5, "Food"));
        itemsForSale.put("Carrot", new Item("Carrot", 3, "Food"));
        itemsForSale.put("Bone", new Item("Bone", 8, "Food"));

        // Gift Items
        itemsForSale.put("Ball", new Item("Ball", 10, "Gift"));
        itemsForSale.put("Frisbee", new Item("Frisbee", 12, "Gift"));
        itemsForSale.put("Rope Toy", new Item("Rope Toy", 7, "Gift"));
    }

    /**
     * Displays all items available for purchase in the shop.
     */
    public void displayItemsForSale() {
        System.out.println("Welcome to the Shop!");
        System.out.println("Items available for purchase:");
        for (Item item : itemsForSale.values()) {
            System.out.println("- " + item.getName() + " (" + item.getType() + "): " + item.getPrice() + " coins");
        }
    }

    /**
     * Handles the purchase of an item by the player.
     *
     * @param itemName The name of the item to purchase.
     * @return True if the purchase was successful, false otherwise.
     */
    public boolean purchaseItem(String itemName) {
        if (!itemsForSale.containsKey(itemName)) {
            System.out.println("Item not found in the shop.");
            return false;
        }

        Item item = itemsForSale.get(itemName);
        int playerCoins = player.getCurrentScore(); // Assume coins are tracked as the player's score

        if (playerCoins >= item.getPrice()) {
            // Deduct coins and add item to the inventory
            player.updateScore(-item.getPrice());
            player.getInventory().addItem(item.getName(), 1); // Add item by name and count to inventory
            System.out.println("Successfully purchased " + item.getName() + "!");
            return true;
        } else {
            System.out.println("Insufficient coins to purchase " + item.getName() + ".");
            return false;
        }
    }
}
