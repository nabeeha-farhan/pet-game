package group47.cs2212.petgame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * JUnit 5 test class for the Inventory class.
 * Tests the core functionality of managing items in the inventory, including adding, removing, and retrieving items.
 */
class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        // Initialize the inventory object before each test
        inventory = new Inventory();
    }

    @Test
    @DisplayName("Test Adding Items to Inventory")
    void testAddItem() {
        // Add items to the inventory and verify the count
        inventory.addItem("Apple", 5);
        assertEquals(5, inventory.getItemCount("Apple"), "Item count should be 5 after adding 5 Apples.");

        // Add more of the same item and verify the updated count
        inventory.addItem("Apple", 3);
        assertEquals(8, inventory.getItemCount("Apple"), "Item count should be 8 after adding 3 more Apples.");

        // Add a new item and verify its count
        inventory.addItem("Banana", 2);
        assertEquals(2, inventory.getItemCount("Banana"), "Item count should be 2 after adding 2 Bananas.");
    }

    @Test
    @DisplayName("Test Removing Items from Inventory")
    void testRemoveItem() {
        // Add items to the inventory
        inventory.addItem("Apple", 5);

        // Remove a portion of the item and verify the remaining count
        assertTrue(inventory.removeItem("Apple", 3), "Should successfully remove 3 Apples.");
        assertEquals(2, inventory.getItemCount("Apple"), "Item count should be 2 after removing 3 Apples.");

        // Remove all remaining items and verify the item is no longer in inventory
        assertTrue(inventory.removeItem("Apple", 2), "Should successfully remove the remaining 2 Apples.");
        assertFalse(inventory.hasItem("Apple"), "Inventory should no longer contain Apples.");

        // Attempt to remove an item not in the inventory
        assertFalse(inventory.removeItem("Apple", 1), "Should fail to remove an item not in the inventory.");
    }

    @Test
    @DisplayName("Test Removing More Items Than Available")
    void testRemoveMoreItemsThanAvailable() {
        // Add items to the inventory
        inventory.addItem("Apple", 2);

        // Attempt to remove more items than available and verify failure
        assertFalse(inventory.removeItem("Apple", 3), "Should not allow removal of more items than available.");
        assertEquals(2, inventory.getItemCount("Apple"), "Item count should remain 2 after failed removal attempt.");
    }

    @Test
    @DisplayName("Test Inventory Details Formatting")
    void testGetInventoryDetails() {
        // Add multiple items to the inventory
        inventory.addItem("Apple", 5);
        inventory.addItem("Banana", 3);

        // Verify the formatted inventory details
        String[] details = inventory.getInventoryDetails();
        assertArrayEquals(new String[]{"Apple x5", "Banana x3"}, details, "Inventory details should be formatted correctly.");
    }

    @Test
    @DisplayName("Test Checking Item Existence")
    void testHasItem() {
        // Add an item to the inventory
        inventory.addItem("Apple", 5);

        // Verify item existence in the inventory
        assertTrue(inventory.hasItem("Apple"), "Inventory should contain Apples.");
        assertFalse(inventory.hasItem("Banana"), "Inventory should not contain Bananas.");
    }

    @Test
    @DisplayName("Test Getting Item Count")
    void testGetItemCount() {
        // Add an item to the inventory
        inventory.addItem("Apple", 5);

        // Verify the count of existing and non-existing items
        assertEquals(5, inventory.getItemCount("Apple"), "Item count should match the added amount.");
        assertEquals(0, inventory.getItemCount("Banana"), "Item count for an absent item should be 0.");
    }

    @Test
    @DisplayName("Test Adding Items with Invalid Count")
    void testAddItemInvalidCount() {
        // Attempt to add items with invalid counts and verify exception is thrown
        assertThrows(IllegalArgumentException.class, () -> inventory.addItem("Apple", 0), "Adding an item with count <= 0 should throw an exception.");
        assertThrows(IllegalArgumentException.class, () -> inventory.addItem("Apple", -1), "Adding an item with negative count should throw an exception.");
    }

    @Test
    @DisplayName("Test Initializing Default Items")
    void testInitializeDefaultItems() {
        // Initialize the inventory with default items
        inventory.initializeDefaultItems();

        // Verify that default items are added with correct counts
        assertTrue(inventory.hasItem("Food1"), "Inventory should contain default item Food1.");
        assertTrue(inventory.hasItem("Peach"), "Inventory should contain default item Peach.");
        assertTrue(inventory.hasItem("Giftbox"), "Inventory should contain default item Giftbox.");

        assertEquals(5, inventory.getItemCount("Food1"), "Food1 should have a default count of 5.");
        assertEquals(3, inventory.getItemCount("Peach"), "Peach should have a default count of 3.");
        assertEquals(2, inventory.getItemCount("Giftbox"), "Giftbox should have a default count of 2.");
    }
}
