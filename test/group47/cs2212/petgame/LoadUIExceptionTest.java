package group47.cs2212.petgame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LoadUIExceptionTest {

    @Test
    public void testLoadUIExceptionWithMessageAndCause() {
        // Define a custom error message
        String message = "UI loading failed!";

        // Define an exception that will serve as the cause
        Throwable cause = new RuntimeException("Underlying cause");

        // Create a new LoadUIException
        LoadUIException exception = new LoadUIException(message, cause);

        // Verify that the exception's message is correct
        assertEquals(message, exception.getMessage());

        // Verify that the cause of the exception is the RuntimeException we passed
        assertEquals(cause, exception.getCause());
    }

    @Test
    public void testLoadUIExceptionWithMessageOnly() {
        // Define a custom error message
        String message = "UI loading failed!";

        // Create a new LoadUIException with only the message (no cause)
        LoadUIException exception = new LoadUIException(message, null);

        // Verify that the exception's message is correct
        assertEquals(message, exception.getMessage());

        // Verify that the cause is null
        assertNull(exception.getCause());
    }

    @Test
    public void testLoadUIExceptionWithCauseOnly() {
        // Define an exception that will serve as the cause
        Throwable cause = new RuntimeException("Underlying cause");

        // Create a new LoadUIException with only the cause (no message)
        LoadUIException exception = new LoadUIException(null, cause);

        // Verify that the cause is correctly set
        assertEquals(cause, exception.getCause());

        // Verify that the message is null since we didn't pass one
        assertNull(exception.getMessage());
    }
}
