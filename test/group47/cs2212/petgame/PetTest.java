package group47.cs2212.petgame;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PetTest {

    private Pet pet;
    private Image mockImage;

    @BeforeEach
    public void setUp() {
        // Initialize the pet object and mock image before each test
        mockImage = null; // Example mock image path
        pet = new Pet("Fluffy", "pet1", "Mouse", mockImage);
    }

    @Test
    public void testPetConstructor() {
        // Verify that the Pet object is initialized correctly
        assertEquals("Fluffy", pet.getName(), "The pet's name should be Fluffy");
        assertEquals("pet1", pet.getId(), "The pet's ID should be pet1");
        assertEquals("Mouse", pet.getType(), "The pet's type should be Mouse");
        assertEquals("fine", pet.getState(), "The pet's state should be fine by default");
        assertEquals(100, pet.getAttributes()[0], "Happiness should be 100 initially");
        assertEquals(100, pet.getAttributes()[1], "Energy should be 100 initially");
        assertEquals(100, pet.getAttributes()[2], "Hunger should be 100 initially");
        assertEquals(100, pet.getAttributes()[3], "Health should be 100 initially");
    }

    @Test
    public void testGetAttributeDecay() {
        // Verify the attribute decay for a specific pet (pet1)
        Integer[] expectedDecay = new Integer[]{-2, -4, -6};
        assertArrayEquals(expectedDecay, pet.getAttributeDecay(), "The decay values should match for pet1");
    }

    @Test
    public void testUpdateAttributes() {
        // Test updating the pet's attributes (hunger, happiness, energy, health)
        pet.updateHunger(-30);
        pet.updateHappiness(-20);
        pet.updateEnergy(10);
        pet.updateHealth(-50);

        assertEquals(70, pet.getAttributes()[2], "Hunger should be updated to 70");
        assertEquals(80, pet.getAttributes()[0], "Happiness should be updated to 80");
        assertEquals(100, pet.getAttributes()[1], "Energy should be updated to 100)");
        assertEquals(50, pet.getAttributes()[3], "Health should be updated to 50");
    }

    @Test
    public void testStateChecks() {
        // Test state checking methods
        pet.updateHappiness(-80); // Happiness is now 20
        pet.updateHealth(-90); // Health is now 10
        pet.updateEnergy(-100); // Energy is now 0
        pet.updateHunger(-100); // Hunger is now 0

        assertFalse(pet.isNotHungry(), "The pet should be hungry when hunger is less than 25");
        assertFalse(pet.isNotSleepy(), "The pet should be sleepy when energy is less than 25");
        assertFalse(pet.isHappy(), "The pet should not be happy when happiness is less than 25");
        assertFalse(pet.isHealthy(), "The pet should not be healthy when health is less than 25");

        assertTrue(pet.enterHungryState(), "The pet should enter a hungry state when hunger is 0");
        assertTrue(pet.enterSleepState(), "The pet should enter a sleep state when energy is 0");
        assertFalse(pet.enterAngryState(), "The pet should not enter an angry state when happiness is greater than 0");
        assertFalse(pet.enterDeadState(), "The pet should not enter a dead state when health is greater than 0");
    }

    @Test
    public void testForcedState() {
        // Test forced state functionality
        assertFalse(pet.isInForcedState(), "Pet should not be in a forced state initially");

        pet.setForcedState(true);
        assertTrue(pet.isInForcedState(), "Pet should be in a forced state after setting it to true");

        pet.setForcedState(false);
        assertFalse(pet.isInForcedState(), "Pet should not be in a forced state after setting it to false");
    }
}
