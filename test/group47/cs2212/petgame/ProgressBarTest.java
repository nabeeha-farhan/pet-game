package group47.cs2212.petgame;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class ProgressBarTest {

    private UILoader uiLoader;

    @BeforeEach
    void setUp() {
        uiLoader = new UILoader();
    }

    @Test
    void testUpdateProgressBar() {
        double progress = 0.5;
        assertDoesNotThrow(() -> uiLoader.updateProgressBar("progressBarID", progress),
                "Updating progress bar should not throw exceptions.");
    }

    @Test
    void testInvalidProgressValue() {
        assertThrows(IllegalArgumentException.class,
                () -> uiLoader.updateProgressBar("progressBarID", 1.5),
                "Progress value greater than 1 should throw an exception.");
    }
}
