/**
 * This exception will be thrown when trying to enter max size of array to be smaller than 0
 */
public class NegativeCapacityException extends StackException {
    public NegativeCapacityException() {}

    public NegativeCapacityException(String message) {
        super(message);
    }

    public NegativeCapacityException(String message, Throwable cause) {
        super(message, cause);
    }
}
