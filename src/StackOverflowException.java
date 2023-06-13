/**
 * This exception will be thrown when trying to add an element to a full capacity stack
 */
public class StackOverflowException extends StackException {
    public StackOverflowException() {}

    public StackOverflowException(String message) {
        super(message);
    }

    public StackOverflowException(String message, Throwable cause) {
        super(message, cause);
    }
}
