package group47.cs2212.petgame;

import javafx.scene.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the MenuData class.
 */
public class MenuDataTest {

    private MenuData menuData;
    private Group mockGroup;

    @BeforeEach
    public void setUp() {
        // Initialize a new mock Group before each test
        mockGroup = new Group();
        menuData = new MenuData(mockGroup);
    }

    @Test
    public void testGetGroup() {
        // Verify that the group returned by getGroup() is the same as the one passed in constructor
        assertEquals(mockGroup, menuData.getGroup(), "The group should match the one passed to the constructor");
    }

    @Test
    public void testIsShowingInitially() {
        // Verify that the menu is not showing by default (constructor sets it to false)
        assertFalse(menuData.isShowing(), "The menu should not be showing initially");
    }

    @Test
    public void testSetShowing() {
        // Test setting the visibility of the menu to true
        menuData.setShowing(true);
        assertTrue(menuData.isShowing(), "The menu should be showing after calling setShowing(true)");

        // Test setting the visibility of the menu to false
        menuData.setShowing(false);
        assertFalse(menuData.isShowing(), "The menu should not be showing after calling setShowing(false)");
    }
}
