/**
 * This exception will be thrown when trying to approach an empty stack
 */
public class EmptyStackException extends StackException{
    public EmptyStackException() {}

    public EmptyStackException(String message) {
        super(message);
    }

    public EmptyStackException(String message, Throwable cause) {
        super(message, cause);
    }
}
