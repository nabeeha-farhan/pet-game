package group47.cs2212.petgame;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ShopTest {

    private Player player;
    private Shop shop;

    @BeforeEach
    void setUp() {
        player = new Player("P001", "TestPlayer", "testplayer@example.com");
        shop = new Shop(player);
        player.updateScore(100); // Give the player some coins for testing
    }

    @Test
    void testDisplayItemsForSale() {
        assertDoesNotThrow(shop::displayItemsForSale, "Displaying items should not throw exceptions.");
    }

    @Test
    void testPurchaseItemSuccess() {
        boolean result = shop.purchaseItem("Apple");
        assertTrue(result, "Player should be able to purchase Apple.");
        assertTrue(player.getInventory().hasItem("Apple"), "Inventory should contain purchased item.");
        assertEquals(95, player.getCurrentScore(), "Player's coins should be reduced by the item's price.");
    }

    @Test
    void testPurchaseItemFailureDueToLackOfFunds() {
        player.updateScore(-100); // Remove all coins
        boolean result = shop.purchaseItem("Apple");
        assertFalse(result, "Player should not be able to purchase items without enough coins.");
    }

    @Test
    void testPurchaseNonExistentItem() {
        boolean result = shop.purchaseItem("NonExistentItem");
        assertFalse(result, "Player should not be able to purchase an item that doesn't exist.");
    }
}
