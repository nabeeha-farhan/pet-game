package group47.cs2212.petgame;

/**
 * A custom exception that represents errors occurring during the loading of a UI component.
 */
public class LoadUIException extends Exception {

    /**
     * Constructs a new LoadUIException with a specified error message and the underlying cause.
     *
     * @param message the detail message explaining the cause of the exception
     * @param cause   the throwable that caused this exception to be thrown
     */
    public LoadUIException(String message, Throwable cause) {
        super(message, cause);
    }
}
